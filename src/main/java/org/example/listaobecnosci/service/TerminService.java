package org.example.listaobecnosci.service;

import org.example.listaobecnosci.Grupa;
import org.example.listaobecnosci.Termin;
import org.example.listaobecnosci.repository.TerminRepository;
import org.springframework.stereotype.Service;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Optional;

@Service
public class TerminService {

    private final TerminRepository terminRepository;
    private final GrupaService grupaService;

    public TerminService(TerminRepository terminRepository, GrupaService grupaService) {
        this.terminRepository = terminRepository;
        this.grupaService = grupaService;
    }

    public List<Termin> getAllTerminy() {
        return terminRepository.findAllWithGrupa();
    }


    public Optional<Termin> getTerminById(Long id) {
        Optional<Termin> terminOpt = terminRepository.findById(id);
        terminOpt.ifPresent(t -> Hibernate.initialize(t.getGrupa())); // ← tutaj!
        return terminOpt;
    }

    public List<Termin> getTerminByGrupaId(Long grupaId) {
        Optional<Grupa> grupaOpt = grupaService.getGrupaById(grupaId);
        return grupaOpt.map(grupa -> terminRepository.findByGrupaWithFetch(grupa)).orElse(List.of());
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
