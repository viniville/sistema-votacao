package com.sicred.votacao.config;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
/**
 * MessagingConfig
 */
@Configuration
@EnableJms
public class MessagingConfig {

    public static final String QUEUE_SESSAO_ENCERRADA_NAME = "queue_sessao_encerrada";

    @Bean
    public Queue queue() {
        return new ActiveMQQueue(QUEUE_SESSAO_ENCERRADA_NAME);
    }

}