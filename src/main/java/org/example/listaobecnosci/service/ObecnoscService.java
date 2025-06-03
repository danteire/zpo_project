package org.example.listaobecnosci.service;

import org.example.listaobecnosci.Obecnosc;
import org.example.listaobecnosci.repository.ObecnoscRepository;
import org.example.listaobecnosci.statusEnum;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObecnoscService {

    private final ObecnoscRepository obecnoscRepository;

    public ObecnoscService(ObecnoscRepository obecnoscRepository) {
        this.obecnoscRepository = obecnoscRepository;
    }

    public List<Obecnosc> getAllObecnosci() {
        return obecnoscRepository.findAll();
    }

    public Optional<Obecnosc> getObecnoscById(Long id) {
        return obecnoscRepository.findById(id);
    }

    public Obecnosc saveOrUpdateObecnosc(Obecnosc obecnosc) {
        if (obecnosc.getId() != null) {
            return obecnoscRepository.findById(obecnosc.getId())
                    .map(existing -> {
                        existing.setStatus(obecnosc.getStatus());
                        existing.setStudent(obecnosc.getStudent());
                        existing.setTermin(obecnosc.getTermin());
                        return obecnoscRepository.save(existing);
                    })
                    .orElseGet(() -> obecnoscRepository.save(obecnosc)); // jeśli ID jest podane, ale nie ma takiego rekordu
        }
        return obecnoscRepository.save(obecnosc); // jeśli ID == null
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
