package com.sicred.votacao.repository;

import com.sicred.votacao.model.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long> {

    @Query("select sv from SessaoVotacao sv where sv.pauta.id = ?1")
    SessaoVotacao findByIdPauta(Long id);
}
