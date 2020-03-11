package com.sicred.votacao.task;

import java.util.Date;

import com.sicred.votacao.model.SessaoVotacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

/**
 * Classe responsável por agendar tarefa q será executada 10 segundos
 * após encerramento da sessão e enviará a mensagem de encerramento 
 */
@Component
public class SessaoEncerradaScheduleTask {

	@Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    private SessaoEncerradaEventTask task;

    public void agendarPostEventSessaoEncerrada(SessaoVotacao sessaoVotacao) {
        //Agenda a tarefa para 10 segundos depois da data de encerramento da sessão
        task.setIdPauta(sessaoVotacao.getPauta().getId());
        taskScheduler.schedule(task, new Date(sessaoVotacao.getDataFechamento().getTime() + 10000));
        
    }
}