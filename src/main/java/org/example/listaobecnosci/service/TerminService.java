package org.example.listaobecnosci.service;

import org.example.listaobecnosci.Grupa;
import org.example.listaobecnosci.Termin;
import org.example.listaobecnosci.repository.TerminRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TerminService {

    private final TerminRepository terminRepository;

    public TerminService(TerminRepository terminRepository) {
        this.terminRepository = terminRepository;
    }

    public List<Termin> getAllTerminy() {
        return terminRepository.findAll();
    }

    public Optional<Termin> getTerminById(Long id) {
        return terminRepository.findById(id);
    }

    public Termin saveTermin(Termin termin) {
        return terminRepository.save(termin);
    }

    public void deleteTermin(Long id) {
        terminRepository.deleteById(id);
    }

    public Termin assignTerminToGrupa(Termin termin, Grupa grupa) {
        termin.setGrupa(grupa);
        return terminRepository.save(termin);
    }

}
