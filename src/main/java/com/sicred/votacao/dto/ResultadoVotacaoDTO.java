package com.sicred.votacao.dto;

import java.io.Serializable;

public class ResultadoVotacaoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String status;
    private Long totalVotos;
    private Long totalVotosSIM;
    private Long totalVotosNAO;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTotalVotos() {
        return totalVotos;
    }

    public void setTotalVotos(Long totalVotos) {
        this.totalVotos = totalVotos;
    }

    public Long getTotalVotosSIM() {
        return totalVotosSIM;
    }

    public void setTotalVotosSIM(Long totalVotosSIM) {
        this.totalVotosSIM = totalVotosSIM;
    }

    public Long getTotalVotosNAO() {
        return totalVotosNAO;
    }

    public void setTotalVotosNAO(Long totalVotosNAO) {
        this.totalVotosNAO = totalVotosNAO;
    }
}
