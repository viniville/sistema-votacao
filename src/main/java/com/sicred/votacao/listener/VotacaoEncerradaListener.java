package com.sicred.votacao.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * VotacaoEncerradaListener
 */
@Component
public class VotacaoEncerradaListener {

    @JmsListener(destination = "queue")
    public void receive(String msg) {
        System.out.println(msg);
    }

}