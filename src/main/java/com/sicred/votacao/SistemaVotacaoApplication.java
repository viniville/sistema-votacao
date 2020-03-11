package com.sicred.votacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

import java.util.TimeZone;

@SpringBootApplication
public class SistemaVotacaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaVotacaoApplication.class, args);
    }

    @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));
    }

}
