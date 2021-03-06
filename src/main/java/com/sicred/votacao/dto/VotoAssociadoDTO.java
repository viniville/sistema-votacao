package com.sicred.votacao.dto;

import com.sicred.votacao.enums.OpcaoVoto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class VotoAssociadoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Long idPauta;
    @NotNull
    private String cpfAssociado;
    @NotNull
    private OpcaoVoto voto;

    public Long getIdPauta() {
        return idPauta;
    }

    public void setIdPauta(Long idPauta) {
        this.idPauta = idPauta;
    }

    public String getCpfAssociado() {
        return cpfAssociado;
    }

    public void setCpfAssociado(String cpfAssociado) {
        this.cpfAssociado = cpfAssociado;
    }

    public OpcaoVoto getVoto() {
        return voto;
    }

    public void setVoto(OpcaoVoto voto) {
        this.voto = voto;
    }
}
