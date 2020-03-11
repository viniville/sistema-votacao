package com.sicred.votacao.controller;

import com.sicred.votacao.dto.ResultadoVotacaoDTO;
import com.sicred.votacao.model.Pauta;
import com.sicred.votacao.service.PautaService;
import com.sicred.votacao.service.SessaoVotacaoService;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/pauta", produces = {MediaType.APPLICATION_JSON_VALUE})
public class PautaController {

    @Autowired
    private Logger log;

    @Autowired
    private PautaService pautaService;

    @Autowired
    private SessaoVotacaoService sessaoVotacaoService;    

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Pauta findById(@PathVariable("id") Long id) {
        log.debug("into findById method");
        return pautaService.findById(id);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<Pauta> findAll() {
        log.debug("into findAll method");
        return pautaService.findAll();
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> insert(@Valid @RequestBody Pauta pauta) {
        log.debug("into insert method");
        pauta = pautaService.insert(pauta);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}").buildAndExpand(pauta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value ="/{id},", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.OK)
    public Pauta update(@PathVariable Long id, @Valid @RequestBody Pauta pauta) {
        log.debug("into update method");
        Pauta pautaSalva = pautaService.findById(id);
        BeanUtils.copyProperties(pauta, pautaSalva, "id");
        return pautaService.update(pauta);
    }

    @GetMapping(value = "/{id}/resultado")
    @ResponseStatus(code = HttpStatus.OK)
    public ResultadoVotacaoDTO resultadoVotacao(@PathVariable("id") Long id) {
        return sessaoVotacaoService.resultadoVotacao(id);
    }

}
