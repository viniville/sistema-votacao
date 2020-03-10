package com.sicred.votacao.exception;

@SuppressWarnings("serial")
public class PautaInexistenteException extends ApiBusinessException {

    public PautaInexistenteException(String message) {
        super(message);
    }

    public PautaInexistenteException(String message, Throwable cause) {
        super(message, cause);
    }
}
