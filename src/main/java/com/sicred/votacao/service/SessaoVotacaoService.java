package com.sicred.votacao.service;

import com.sicred.votacao.exception.PautaInexistenteException;
import com.sicred.votacao.exception.SessaoVotacaoEncerradaException;
import com.sicred.votacao.exception.SessaoVotacaoInicioInvalidoException;
import com.sicred.votacao.exception.SessaoVotacaoNaoIniciadaException;
import com.sicred.votacao.model.SessaoVotacao;
import com.sicred.votacao.repository.SessaoVotacaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class SessaoVotacaoService {

    @Autowired
    private SessaoVotacaoRepository sessaoVotacaoRepository;

    @Autowired
    private PautaService pautaService;

    public SessaoVotacao findByIdPauta(Long id) {
        return sessaoVotacaoRepository.findByIdPauta(id);
    }

    public SessaoVotacao findById(Long id) {
        Optional<SessaoVotacao> optSessao = sessaoVotacaoRepository.findById(id);
        if(!optSessao.isPresent()) {
            throw new EmptyResultDataAccessException(1);
        }
        SessaoVotacao sessao = optSessao.get();
        if(sessao.getPauta() != null) {
            sessao.getPauta().getDescricao();
            sessao.getPauta().getTitulo();
        }
        return sessao;
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
        //validamos se já existe uma sessão de votação para esta pauta - não deixa cadastrar mais de uma 
        SessaoVotacao sessaoVotacaoPauta = this.findByIdPauta(idPauta);
        if(sessaoVotacaoPauta != null) {
            if((new Date()).after(sessaoVotacaoPauta.getDataFechamento())) {
                throw new SessaoVotacaoEncerradaException("Sessão de votação desta pauta já está encerrada");
            } else {
                throw new SessaoVotacaoNaoIniciadaException("Sessão de votação ainda não iniciou");
            }
        }
        validaDataInicioRetroativa(dataHoraAbertura);

        SessaoVotacao novaSessaoVotacao = new SessaoVotacao();
        novaSessaoVotacao.setPauta(pautaService.findById(idPauta));
        if(novaSessaoVotacao.getPauta() == null) {
            throw new PautaInexistenteException("Pauta inexistente");
        }
        //criei este atributo, pois ele permite q seja possível, se quiser, agendar previamente a abertura de uma sessão 
        //ou seja, é possível criar um método que insere uma sessão de votação com data de início já pre-definida
        novaSessaoVotacao.setDataAbertura(dataHoraAbertura); 
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataHoraAbertura);
        tempoDuracaoMinutos = tempoDuracaoMinutos != null && tempoDuracaoMinutos.intValue() > 1 ? tempoDuracaoMinutos : 1;
        cal.add(Calendar.MINUTE, tempoDuracaoMinutos.intValue());
        novaSessaoVotacao.setDataFechamento(cal.getTime());
        novaSessaoVotacao.setDataCadastro(new Date());
        return sessaoVotacaoRepository.save(novaSessaoVotacao);
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
}
