package com.sicred.votacao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name = "pauta")
public class Pauta extends VotacaoAbstractModel<Long> {

    @NotNull
    @Column(name = "titulo", nullable = false, unique = true)
    private String titulo;

    @NotBlank
    @Column(name = "descricao", length = 1024)
    private String descricao;

    @JsonIgnore
    @OneToOne(mappedBy = "pauta", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private SessaoVotacao sessaoVotacao;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public SessaoVotacao getSessaoVotacao() {
        return sessaoVotacao;
    }

    public void setSessaoVotacao(SessaoVotacao sessaoVotacao) {
        this.sessaoVotacao = sessaoVotacao;
    }

}
