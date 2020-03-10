package com.sicred.votacao.controller;

import com.sicred.votacao.dto.AberturaSessaoVotacaoDTO;
import com.sicred.votacao.model.SessaoVotacao;
import com.sicred.votacao.service.SessaoVotacaoService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/v1/sessaoVotacao", produces = {MediaType.APPLICATION_JSON_VALUE})
public class SessaoVotacaoController {

    @Autowired
    private Logger log;

    @Autowired
    private SessaoVotacaoService sessaoVotacaoService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public SessaoVotacao buscarPorId(@PathVariable("id") Long id) {
        log.debug("into buscarPorId method");
        return sessaoVotacaoService.findById(id);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> abrirSessaoVotacao(@Valid @RequestBody AberturaSessaoVotacaoDTO dto) {
        log.debug("into abrirSessaoVotacao method");
        SessaoVotacao sessaoVotacao = sessaoVotacaoService.abrirSessao(dto.getIdPauta(),dto.getDataAbertura(), dto.getTempoDuracaoMinutos());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}").buildAndExpand(sessaoVotacao.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
