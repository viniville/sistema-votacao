package com.sicred.votacao;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import javax.annotation.PostConstruct;
import javax.jms.Queue;

import com.sicred.votacao.listener.MySender;

import java.util.TimeZone;

@SpringBootApplication
@EnableJms
public class SistemaVotacaoApplication {

    @Autowired private MySender sss;

    public static void main(String[] args) {
        SpringApplication.run(SistemaVotacaoApplication.class, args);
    }

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("queue");
    }

    @PostConstruct
    public void init(){
        // Setting Spring Boot SetTimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));
        sss.send("ola mundo JMS");
    }

}
