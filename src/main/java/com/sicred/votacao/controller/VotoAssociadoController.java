package com.sicred.votacao.controller;

import com.sicred.votacao.dto.VotoAssociadoDTO;
import com.sicred.votacao.model.VotoAssociado;
import com.sicred.votacao.service.SessaoVotacaoService;
import com.sicred.votacao.service.VotoAssociadoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/v1/votoAssociado", produces = {MediaType.APPLICATION_JSON_VALUE})
public class VotoAssociadoController {

    private Log log = LogFactory.getLog(VotoAssociadoController.class);

    @Autowired
    private VotoAssociadoService votoAssociadoService;

    @Autowired
    private SessaoVotacaoService sessaoVotacaoService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> votar(@Valid @RequestBody VotoAssociadoDTO votoAssociadoDTO) {
        log.info("REST método votar()");
        VotoAssociado votoAssociado = new VotoAssociado();
        votoAssociado.setCpfAssociado(votoAssociadoDTO.getCpfAssociado());
        votoAssociado.setSessaoVotacao(sessaoVotacaoService.findByIdPauta(votoAssociadoDTO.getIdPauta()));
        votoAssociado.setVoto(votoAssociadoDTO.getVoto());
        votoAssociado = votoAssociadoService.votar(votoAssociado);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}").buildAndExpand(votoAssociado.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
