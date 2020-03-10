package com.sicred.votacao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicred.votacao.enums.OpcaoVoto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@SuppressWarnings("serial")
@Entity
@Table(name = "voto_associado")
public class VotoAssociado extends VotacaoAbstractModel<Long> {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sessao_votacao", nullable = false, updatable = false)
    private SessaoVotacao sessaoVotacao;

    @NotNull(message = "CPF do associado é obrigatório")
    @Pattern(regexp = "^[0-9]{11}$", message = "CPF inválido")
    @Column(name = "cpf_associado", length = 11)
    private String cpfAssociado;

    @NotNull(message = "Voto(Sim/Não) é obrigatório")
    @Column(name = "voto", nullable = false)
    @Enumerated(EnumType.STRING)
    private OpcaoVoto voto;

    public SessaoVotacao getSessaoVotacao() {
        return sessaoVotacao;
    }

    public void setSessaoVotacao(SessaoVotacao sessaoVotacao) {
        this.sessaoVotacao = sessaoVotacao;
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
