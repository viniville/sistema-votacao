package com.sicred.votacao.exception;

@SuppressWarnings("serial")
public class SessaoVotacaoNaoIniciadaException extends ApiBusinessException {

    public SessaoVotacaoNaoIniciadaException(String message) {
        super(message);
    }

    public SessaoVotacaoNaoIniciadaException(String message, Throwable cause) {
        super(message, cause);
    }
}
