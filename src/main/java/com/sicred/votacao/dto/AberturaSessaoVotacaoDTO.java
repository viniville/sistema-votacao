package com.sicred.votacao.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class AberturaSessaoVotacaoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Long idPauta;
    private Date dataAbertura;
    private Integer tempoDuracaoMinutos;

    public Long getIdPauta() {
        return idPauta;
    }

    public void setIdPauta(Long idPauta) {
        this.idPauta = idPauta;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

	public Integer getTempoDuracaoMinutos() {
		return tempoDuracaoMinutos;
	}

	public void setTempoDuracaoMinutos(Integer tempoDuracaoMinutos) {
		this.tempoDuracaoMinutos = tempoDuracaoMinutos;
	}
    
}
