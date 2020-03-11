package com.sicred.votacao.messaging.listener;

import com.sicred.votacao.config.MessagingConfig;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Recebe mensagens de sessão encerrada
 */
@Component
public class VotacaoEncerradaListener {

    @Autowired
    private Logger log;

    @JmsListener(destination = MessagingConfig.QUEUE_SESSAO_ENCERRADA_NAME)
    public void receive(String msg) {
        log.debug("recebeu mensagem de sessao encerrada");
        log.info("recebido mensagem de sessão encerrada:\n" + msg);
    }

}