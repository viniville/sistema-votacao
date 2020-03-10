package com.sicred.votacao.service;

import com.sicred.votacao.dto.HabilitadoParaVotarDTO;
import com.sicred.votacao.enums.StatusSessaoVotacao;
import com.sicred.votacao.exception.ApiBusinessException;
import com.sicred.votacao.exception.AssociadoJaVotouNestaPautaException;
import com.sicred.votacao.exception.AssociadoNaoHabilitadoParaVotarException;
import com.sicred.votacao.exception.DadosVotoAssociadoInvalidosException;
import com.sicred.votacao.model.SessaoVotacao;
import com.sicred.votacao.model.VotoAssociado;
import com.sicred.votacao.repository.VotoAssociadoRepository;
import com.sicred.votacao.utils.ValidaCPF;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VotoAssociadoService {

    @Autowired
	private Logger log;	

    @Value("${validar.cpf.servico.remoto}")
    private Boolean validarCpfServicoRemoto;

    @Autowired
    private VotoAssociadoRepository votoAssociadoRepository;

    @Autowired
    private SessaoVotacaoService sessaoVotacaoService;

    public VotoAssociado votar(VotoAssociado votoAssociado) {
        if(votoAssociado == null ||
                votoAssociado.getSessaoVotacao() == null ||
                votoAssociado.getCpfAssociado() == null ||
                votoAssociado.getVoto() == null) {
            throw new DadosVotoAssociadoInvalidosException("Dados do voto do associados informado são inválidos");
        }
        sessaoAbertaParaVotacao(votoAssociado.getSessaoVotacao());
        if (associadoHabilitadoParaVotar(votoAssociado.getCpfAssociado())) {
            if (associadoAindaNaoVotou(votoAssociado)) {
                return insert(votoAssociado);
            } else {
                throw new AssociadoJaVotouNestaPautaException("Associado já registrou voto nesta pauta");
            }
        } else {
            throw new AssociadoNaoHabilitadoParaVotarException("Associado inválido ou não habilitado para votar");
        }
    }

    private VotoAssociado insert(VotoAssociado votoAssociado) {
        log.debug("into insert method");
        if(votoAssociado.getId() != null) {
            votoAssociado.setId(null);
        }
        votoAssociado.setDataCadastro(new Date());
        return votoAssociadoRepository.save(votoAssociado);
    }

    private boolean associadoAindaNaoVotou(VotoAssociado votoAssociado) {
        log.debug("Verificando se associado ainda não havia votado");
        Optional<VotoAssociado> optionalVotoAssociado = votoAssociadoRepository.existeVotoAssociado(votoAssociado.getSessaoVotacao().getPauta().getId(),
                votoAssociado.getCpfAssociado());
        return optionalVotoAssociado.orElse(null) == null;
    }

    private boolean associadoHabilitadoParaVotar(String cpfAssociado) {
        log.debug("Verificando se associado esta habilitado para votar");
        //se HOUVER a configuracao no arquivo de propriedades e estiver com valor true, valido usando
        //o servico externo. Caso contrario usamos funcao interna para verificar se CPF é valido
        if(Boolean.TRUE.equals(validarCpfServicoRemoto)) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                String cpfValidoUrl = "http://user-info.herokuapp.com/users/";
                ResponseEntity<HabilitadoParaVotarDTO> response
                        = restTemplate.getForEntity(cpfValidoUrl + cpfAssociado, HabilitadoParaVotarDTO.class);
                if (response != null && response.getStatusCode() != null && response.getStatusCode().equals(HttpStatus.OK)) {
                    if (response.getBody() != null && "ABLE_TO_VOTE".equalsIgnoreCase(response.getBody().getStatus())) {
                        return true;
                    }
                }
            } catch (RestClientException e) {
                log.error(e.getMessage(), e.getCause());
            }
            return false;
        } else {
            return ValidaCPF.isCPF(cpfAssociado);
        }
    }

    private void sessaoAbertaParaVotacao(SessaoVotacao sessaoVotacao) {
        log.debug("into sessaoAbertaParaVotacao method");
        sessaoVotacao = sessaoVotacaoService.findById(sessaoVotacao.getId());
        log.debug(sessaoVotacao.getStatus().toString());
        if(sessaoVotacao.getStatus().equals(StatusSessaoVotacao.ENCERRADA)) {
            throw new ApiBusinessException("Sessão de votação desta pauta já está encerrada");
        }
        if(sessaoVotacao.getStatus().equals(StatusSessaoVotacao.NAO_INICIADA)) {
            throw new ApiBusinessException("Sessão de votação ainda não iniciada");
        }
    }

    public List<VotoAssociado> listarVotosSessao(SessaoVotacao sessaoVotacao) {
        return votoAssociadoRepository.findAllBySessaoVotacaoEquals(sessaoVotacao);
    }
}
