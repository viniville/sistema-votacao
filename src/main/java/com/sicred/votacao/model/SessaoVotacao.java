package com.sicred.votacao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicred.votacao.enums.StatusSessaoVotacao;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name = "sessao_votacao")
public class SessaoVotacao extends VotacaoAbstractModel<Long> {

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pauta", referencedColumnName = "id", nullable = false, updatable = false)
    private Pauta pauta;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dh_abertura", nullable = false)
    private Date dataAbertura;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dh_fechamento", nullable = false)
    private Date dataFechamento;

    @OneToMany(mappedBy = "sessaoVotacao", fetch = FetchType.LAZY)
    private List<VotoAssociado> votos;

    public Pauta getPauta() {
        return pauta;
    }

    public void setPauta(Pauta pauta) {
        this.pauta = pauta;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Date getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(Date dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public List<VotoAssociado> getVotos() {
        return votos;
    }

    public void setVotos(List<VotoAssociado> votos) {
        this.votos = votos;
    }

    public StatusSessaoVotacao getStatus() {
        Date dataAtual = new Date();
        if(dataAtual.before(this.getDataAbertura())) {
            return StatusSessaoVotacao.NAO_INICIADA;
        } else {
            } if(dataAtual.after(this.getDataFechamento())) {
                return StatusSessaoVotacao.ENCERRADA;
            } else {
                return StatusSessaoVotacao.EM_ANDAMENTO;
            }
    }

}
