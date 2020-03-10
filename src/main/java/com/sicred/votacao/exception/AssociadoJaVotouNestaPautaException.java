package com.sicred.votacao.exception;

@SuppressWarnings("serial")
public class AssociadoJaVotouNestaPautaException extends ApiBusinessException {

    public AssociadoJaVotouNestaPautaException(String message) {
        super(message);
    }

    public AssociadoJaVotouNestaPautaException(String message, Throwable cause) {
        super(message, cause);
    }
}
