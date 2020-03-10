package com.sicred.votacao.exception;

public class ApiBusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ApiBusinessException(String message) {
        super(message);
    }

    public ApiBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
