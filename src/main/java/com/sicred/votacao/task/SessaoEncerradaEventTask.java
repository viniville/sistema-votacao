package com.sicred.votacao.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicred.votacao.dto.ResultadoVotacaoDTO;
import com.sicred.votacao.messaging.sender.SistemaVotacaoMessagingSender;
import com.sicred.votacao.service.SessaoVotacaoService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * SessaoEncerradaEventTask
 */
@Component
public class SessaoEncerradaEventTask implements Runnable {

    @Autowired
    private Logger log;

    @Autowired
    private SistemaVotacaoMessagingSender senderMsg;

    @Autowired
    private SessaoVotacaoService sessaoVotacaoService;

    private Long idPauta;


    public void setIdPauta(Long idPauta) {
        this.idPauta = idPauta;
    }

    @Override
    public void run() {
        log.debug("into run method");
        if(idPauta != null) {
            ResultadoVotacaoDTO resultado = sessaoVotacaoService.resultadoVotacao(idPauta);
            try {
                String msg = (new ObjectMapper()).writeValueAsString(resultado);
                senderMsg.send(msg);
            } catch (JsonProcessingException e) {
                log.error("Erro na serialização do resultado da votação.\n" + e.getMessage(), e.getCause());
            }
        }
    }

}