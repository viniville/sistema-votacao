package com.sicred.votacao.repository;

import com.sicred.votacao.model.VotoAssociado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VotoAssociadoRepository extends JpaRepository<VotoAssociado, Long> {

    @Query("select va " +
            " from VotoAssociado va " +
            "      join va.sessaoVotacao sv " +
            " where sv.pauta.id = ?1" +
            "       and va.cpfAssociado like ?2 ")
    Optional<VotoAssociado> existeVotoAssociado(Long idPauta, String cpdAssociado);

}
