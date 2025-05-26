package org.example.listaobecnosci.service;


import org.example.listaobecnosci.Grupa;
import org.example.listaobecnosci.repository.GrupaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class GrupaService {

    private final GrupaRepository grupaRepository;

    public GrupaService(GrupaRepository grupaRepository) {
        this.grupaRepository = grupaRepository;
    }

    public List<Grupa> getAllGrupy() {
        return grupaRepository.findAll();
    }

    public Optional<Grupa> getGrupaById(Long id) {
        return grupaRepository.findById(id);
    }

    public Grupa saveGrupa(Grupa grupa) {
        return grupaRepository.save(grupa);
    }

    public void deleteGrupa(Long id) {
        grupaRepository.deleteById(id);
    }
}
