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
    private final GrupaService grupaService;

    public TerminService(TerminRepository terminRepository, GrupaService grupaService) {
        this.terminRepository = terminRepository;
        this.grupaService = grupaService;
    }

    public List<Termin> getAllTerminy() {
        return terminRepository.findAll();
    }

    public Optional<Termin> getTerminById(Long id) {
        return terminRepository.findById(id);
    }

    public List<Termin> getTerminByGrupaId(Long grupaId) {
        Optional<Grupa> grupaOpt = grupaService.getGrupaById(grupaId);
        return grupaOpt.map(terminRepository::findByGrupa).orElse(List.of());
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
