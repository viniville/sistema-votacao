package com.sicred.votacao.repository;

import java.util.Optional;

import com.sicred.votacao.model.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long> {

    @Query("select sv from SessaoVotacao sv where sv.pauta.id = ?1")
    Optional<SessaoVotacao> findByIdPauta(Long id);
}
