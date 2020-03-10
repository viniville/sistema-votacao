package com.sicred.votacao.exception;

public class AssociadoNaoHabilitadoParaVotarException extends ApiBusinessException {

    private static final long serialVersionUID = 1L;

    public AssociadoNaoHabilitadoParaVotarException(String message) {
        super(message);
    }

    public AssociadoNaoHabilitadoParaVotarException(String message, Throwable cause) {
        super(message, cause);
    }
}
