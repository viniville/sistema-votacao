package com.sicred.votacao.service;

import com.sicred.votacao.model.Pauta;
import com.sicred.votacao.repository.PautaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    public Pauta findById(Long id) {
        Optional<Pauta> optPauta = pautaRepository.findById(id);
        if(!optPauta.isPresent()) {
            throw new EmptyResultDataAccessException(1);
        }
        return optPauta.get();
    }

    public List<Pauta> findAll() {
        return pautaRepository.findAll();
    }    

    public Pauta insert(Pauta pauta) {
        if(pauta.getId() != null) {
            pauta.setId(null);
        }
        pauta.setDataCadastro(new Date());
        return pautaRepository.save(pauta);
    }

    public Pauta update(Pauta pauta) {
        pauta.setDataModificacao(new Date());
        return pautaRepository.save(pauta);
    }    

    public void delete(Long id) {
        pautaRepository.deleteById(id);
    }

}
