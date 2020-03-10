package com.sicred.votacao.exception;

@SuppressWarnings("serial")
public class DadosVotoAssociadoInvalidosException extends ApiBusinessException {

    public DadosVotoAssociadoInvalidosException(String message) {
        super(message);
    }

    public DadosVotoAssociadoInvalidosException(String message, Throwable cause) {
        super(message, cause);
    }
}
