package com.sicred.votacao.exception;

/**
 * SessaoVotacaoJaExisteException
 */
@SuppressWarnings("serial")
public class SessaoVotacaoJaExisteException extends ApiBusinessException {

    public SessaoVotacaoJaExisteException(String message) {
        super(message);
    }

    public SessaoVotacaoJaExisteException(String message, Throwable cause) {
        super(message, cause);
    }
}