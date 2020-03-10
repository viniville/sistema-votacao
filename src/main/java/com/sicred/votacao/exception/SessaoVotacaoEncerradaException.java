package com.sicred.votacao.exception;

@SuppressWarnings("serial")
public class SessaoVotacaoEncerradaException extends ApiBusinessException {

    public SessaoVotacaoEncerradaException(String message) {
        super(message);
    }

    public SessaoVotacaoEncerradaException(String message, Throwable cause) {
        super(message, cause);
    }
}
