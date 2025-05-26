package org.example.listaobecnosci.service;

import lombok.RequiredArgsConstructor;
import org.example.listaobecnosci.Obecnosc;
import org.example.listaobecnosci.repository.ObecnoscRepository;
import org.example.listaobecnosci.statusEnum;
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

    public List<Obecnosc> getByStatus(statusEnum status) {
        return obecnoscRepository.findByStatus(status);
    }

    public List<Obecnosc> getByStudentId(Long studentId) {
        return obecnoscRepository.findByStudentId(studentId);
    }

    public List<Obecnosc> getByTerminId(Long terminId) {
        return obecnoscRepository.findByTerminId(terminId);
    }

    public List<Obecnosc> getByStudentIdAndTerminId(Long studentId, Long terminId) {
        return obecnoscRepository.findByStudentIdAndTerminId(studentId, terminId);
    }


}
