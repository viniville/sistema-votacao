package com.sicred.votacao.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sicred.votacao.exception.ApiBusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * VotacaoExceptionHandler
 */
@ControllerAdvice
public class VotacaoExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String mensagem = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
        String mensagemDev = ex.getRootCause() != null ? 
                                ex.getRootCause().getMessage() : (ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage());
        List<Erro> erros = Arrays.asList(new Erro(mensagem, mensagemDev));
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Erro> erros = criarListDeErros(ex.getBindingResult());
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ApiBusinessException.class})
    public ResponseEntity<Object> handleApiBusinessException(ApiBusinessException ex, WebRequest request) {
        String mensagem = messageSource.getMessage("mensagem.business_validation", null, LocaleContextHolder.getLocale());
        String mensagemDev = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();
        List<Erro> erros = Arrays.asList(new Erro(mensagem, mensagemDev));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
        String mensagem = messageSource.getMessage("mensagem.recurso_nao_encontrado", null, LocaleContextHolder.getLocale());
        String mensagemDev = ex.getRootCause() != null ? 
                                ex.getRootCause().getMessage() : (ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage());
        List<Erro> erros = Arrays.asList(new Erro(mensagem, mensagemDev));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        String mensagem = messageSource.getMessage("mensagem.recurso_vinculado_inexistente", null, LocaleContextHolder.getLocale());
        String mensagemDev = ex.getRootCause() != null ? 
                                ex.getRootCause().getMessage() : (ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage());
        List<Erro> erros = Arrays.asList(new Erro(mensagem, mensagemDev));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private List<Erro> criarListDeErros(BindingResult bindingResult) {
        List<Erro> erros = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String msgUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String msgDesenv = fieldError.toString();
            erros.add(new Erro(msgUsuario, msgDesenv));
        }
        return erros;
    }
    
    public static class Erro {

        private String mensagemUsuario;
        private String mensagemDesenvolvedor;

        public Erro(String msgUsuario, String msgDesenv) {
            this.mensagemUsuario = msgUsuario;
            this.mensagemDesenvolvedor = msgDesenv;
        }

        public String getMensagemUsuario() {
            return this.mensagemUsuario;
        }

        public void setMensagemUsuario(String mensagem) {
            this.mensagemUsuario = mensagem;
        }


        public String getMensagemDesenvolvedor() {
            return this.mensagemDesenvolvedor;
        }

        public void setMensagemDesenvolvedor(String mensagem) {
            this.mensagemDesenvolvedor = mensagem;
        }

    }
}