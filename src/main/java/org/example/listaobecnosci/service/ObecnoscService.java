package org.example.listaobecnosci.service;

import lombok.RequiredArgsConstructor;
import org.example.listaobecnosci.Obecnosc;
import org.example.listaobecnosci.repository.ObecnoscRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ObecnoscService {

    private final ObecnoscRepository obecnoscRepository;

    public List<Obecnosc> getAllObecnosci() {
        return obecnoscRepository.findAll();
    }

    public Optional<Obecnosc> getObecnoscById(Long id) {
        return obecnoscRepository.findById(id);
    }

    public Obecnosc saveObecnosc(Obecnosc obecnosc) {
        return obecnoscRepository.save(obecnosc);
    }

    public void deleteObecnosc(Long id) {
        obecnoscRepository.deleteById(id);
    }
}
