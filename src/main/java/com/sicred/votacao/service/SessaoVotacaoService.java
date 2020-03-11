package com.sicred.votacao.service;

import com.sicred.votacao.dto.ResultadoVotacaoDTO;
import com.sicred.votacao.enums.OpcaoVoto;
import com.sicred.votacao.exception.ApiBusinessException;
import com.sicred.votacao.exception.PautaInexistenteException;
import com.sicred.votacao.exception.SessaoVotacaoInicioInvalidoException;
import com.sicred.votacao.exception.SessaoVotacaoJaExisteException;
import com.sicred.votacao.model.SessaoVotacao;
import com.sicred.votacao.model.VotoAssociado;
import com.sicred.votacao.repository.SessaoVotacaoRepository;
import com.sicred.votacao.task.SessaoEncerradaScheduleTask;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class SessaoVotacaoService {

    @Autowired
    private Logger log;

    @Autowired
    private SessaoVotacaoRepository sessaoVotacaoRepository;

    @Autowired
    private PautaService pautaService;

    @Autowired
    private SessaoEncerradaScheduleTask agendadorTarefa;

    public SessaoVotacao findByIdPauta(Long idPauta) {
        log.debug("into findByIdPauta method");
        Optional<SessaoVotacao> optSessao = sessaoVotacaoRepository.findByIdPauta(idPauta);
        if(!optSessao.isPresent()) {
            throw new ApiBusinessException("Não existe sessão de votação registrada para esta pauta");
        }
        return optSessao.get();
    }

    public SessaoVotacao findById(Long id) {
        Optional<SessaoVotacao> optSessao = sessaoVotacaoRepository.findById(id);
        if(!optSessao.isPresent()) {
            throw new EmptyResultDataAccessException(1);
        }
        return optSessao.get();
    }

    public SessaoVotacao abrirSessao(Long idPauta, Integer tempoDuracaoMinutos) {
        return abrirSessao(idPauta, new Date(), tempoDuracaoMinutos);
    }

    /**
     * Permite abrir uma Sessão de Votação para uma determinada Pauta. É possível passar uma data de abertura futura,
     * o que corresponde à agendar previamente a data de abertura da sessão de votação.
     * @param idPauta
     * @param dataHoraAbertura
     * @param tempoDuracaoMinutos
     * @return
     */
    public SessaoVotacao abrirSessao(Long idPauta, Date dataHoraAbertura, Integer tempoDuracaoMinutos) {
        if(dataHoraAbertura == null) {
            dataHoraAbertura = new Date();
        }
        //validamos se já existe uma sessão de votação para esta pauta - não deixa cadastrar mais de uma 
        validaJaExisteSessaoVotacao(idPauta);
        //validação da data de inicio q não pode ser retroativa
        validaDataInicioRetroativa(dataHoraAbertura);

        SessaoVotacao novaSessaoVotacao = new SessaoVotacao();
        novaSessaoVotacao.setPauta(pautaService.findById(idPauta));
        if(novaSessaoVotacao.getPauta() == null) {
            throw new PautaInexistenteException("Pauta inexistente");
        }
        //criei este atributo, pois ele permite q seja possÃ­vel, se quiser, agendar previamente a abertura de uma sessÃ£o 
        //ou seja, Ã© possÃ­vel criar um mÃ©todo que insere uma sessÃ£o de votaÃ§Ã£o com data de inÃ­cio jÃ¡ pre-definida
        novaSessaoVotacao.setDataAbertura(dataHoraAbertura); 
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataHoraAbertura);
        tempoDuracaoMinutos = tempoDuracaoMinutos != null && tempoDuracaoMinutos.intValue() > 1 ? tempoDuracaoMinutos : 1;
        cal.add(Calendar.MINUTE, tempoDuracaoMinutos.intValue());
        novaSessaoVotacao.setDataFechamento(cal.getTime());
        novaSessaoVotacao.setDataCadastro(new Date());
        novaSessaoVotacao = sessaoVotacaoRepository.save(novaSessaoVotacao);
        agendadorTarefa.agendarPostEventSessaoEncerrada(novaSessaoVotacao);
        return novaSessaoVotacao;
    }

    private void validaJaExisteSessaoVotacao(Long idPauta) {
        SessaoVotacao sessaoVotacaoPauta = null;
        try {
            sessaoVotacaoPauta = this.findByIdPauta(idPauta);
        } catch (RuntimeException ex) {
            return;
        }
        if(sessaoVotacaoPauta != null) {
            throw new SessaoVotacaoJaExisteException("Já existe sessão de votação para esta pauta");
        }        
    }

    private void validaDataInicioRetroativa(Date dataHoraAbertura) {
        //Valida data da inicio - não deixa cadastrar data de início retroatriva superior a 30 segundos
        Calendar calInicio = Calendar.getInstance();
        calInicio.add(Calendar.SECOND, -30);
        if(dataHoraAbertura.before(calInicio.getTime())) {
            throw new SessaoVotacaoInicioInvalidoException("Data de início da sessão de votação inválida");
        }
    }

    public void deleteById(Long id) {
        sessaoVotacaoRepository.deleteById(id);
    }

    @Transactional
    public ResultadoVotacaoDTO resultadoVotacao(Long idPauta) {
        log.debug("into resultadoVotacao method");
        ResultadoVotacaoDTO resultadoVotacaoDTO = new ResultadoVotacaoDTO();
        SessaoVotacao sessaoVotacao = this.findByIdPauta(idPauta);
        resultadoVotacaoDTO.setStatus(sessaoVotacao.getStatus().toString());
        resultadoVotacaoDTO.setTotalVotos(new Long(sessaoVotacao.getVotos().size()));
        resultadoVotacaoDTO.setTotalVotosNAO(0l);
        resultadoVotacaoDTO.setTotalVotosSIM(0L);
        if(sessaoVotacao != null) {
            for(VotoAssociado voto : sessaoVotacao.getVotos()) {
                if(OpcaoVoto.SIM.equals(voto.getVoto())) {
                    resultadoVotacaoDTO.setTotalVotosSIM(resultadoVotacaoDTO.getTotalVotosSIM().longValue() + 1L);
                } else {
                    resultadoVotacaoDTO.setTotalVotosNAO(resultadoVotacaoDTO.getTotalVotosNAO().longValue() + 1L);
                }
            }
        }
        return resultadoVotacaoDTO;
    }

}
