package com.sicred.votacao.exception;

@SuppressWarnings("serial")
public class SessaoVotacaoInicioInvalidoException extends RuntimeException {

    public SessaoVotacaoInicioInvalidoException(String message) {
        super(message);
    }

    public SessaoVotacaoInicioInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }

}
