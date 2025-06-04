package org.example.listaobecnosci.service;

import jakarta.transaction.Transactional;
import org.example.listaobecnosci.Obecnosc;
import org.example.listaobecnosci.repository.ObecnoscRepository;
import org.example.listaobecnosci.statusEnum;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serwis zarządzający operacjami na encji {@link Obecnosc}.
 *
 * Zapewnia metody do pobierania, zapisywania, aktualizacji oraz usuwania
 * obecności studentów na zajęciach.
 *
 * Wykorzystuje {@link ObecnoscRepository} do komunikacji z bazą danych.
 *
 * @author Patryk Paczkowski
 * @version 1.2
 */
@Service
public class ObecnoscService {

    private final ObecnoscRepository obecnoscRepository;

    /**
     * Konstruktor serwisu.
     *
     * @param obecnoscRepository repozytorium obecności
     */
    public ObecnoscService(ObecnoscRepository obecnoscRepository) {
        this.obecnoscRepository = obecnoscRepository;
    }

    /**
     * Pobiera listę wszystkich obecności.
     *
     * @return lista obiektów {@link Obecnosc}
     */
    public List<Obecnosc> getAllObecnosci() {
        return obecnoscRepository.findAll();
    }

    /**
     * Pobiera obecność po jej identyfikatorze.
     *
     * @param id identyfikator obecności
     * @return opcjonalny obiekt {@link Obecnosc}
     */
    public Optional<Obecnosc> getObecnoscById(Long id) {
        return obecnoscRepository.findById(id);
    }

    /**
     * Zapisuje nową obecność lub aktualizuje istniejącą na podstawie jej ID.
     *
     * @param obecnosc obiekt obecności do zapisania lub aktualizacji
     * @return zapisany lub zaktualizowany obiekt {@link Obecnosc}
     */
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

    /**
     * Usuwa obecność o podanym identyfikatorze.
     *
     * @param id identyfikator obecności do usunięcia
     */
    public void deleteObecnosc(Long id) {
        obecnoscRepository.deleteById(id);
    }


    /**
     * Usuwa wszystkie obecności powiązane z danym terminem.
     *
     * Metoda usuwa z bazy danych wszystkie rekordy obecności
     * przypisane do terminu o podanym identyfikatorze {@code terminId}.
     * Operacja jest wykonywana w ramach transakcji.
     *
     * @param terminId identyfikator terminu, którego obecności mają zostać usunięte
     */
    @Transactional
    public void deleteByTerminId(Long terminId) {
        obecnoscRepository.deleteByTerminId(terminId);
    }

    /**
     * Pobiera listę obecności o określonym statusie.
     *
     * @param status status obecności (np. OBECNY, NIEOBECNY)
     * @return lista obiektów {@link Obecnosc} o danym statusie
     */
    public List<Obecnosc> getByStatus(statusEnum status) {
        return obecnoscRepository.findByStatus(status);
    }

    /**
     * Pobiera listę obecności przypisanych do studenta o podanym ID.
     *
     * @param studentId identyfikator studenta
     * @return lista obecności studenta
     */
    public List<Obecnosc> getByStudentId(Long studentId) {
        return obecnoscRepository.findByStudentId(studentId);
    }

    /**
     * Pobiera listę obecności przypisanych do terminu o podanym ID.
     *
     * @param terminId identyfikator terminu
     * @return lista obecności dla danego terminu
     */
    public List<Obecnosc> getByTerminId(Long terminId) {
        return obecnoscRepository.findByTerminId(terminId);
    }

    /**
     * Pobiera listę obecności przypisanych do studenta i terminu o podanych ID.
     *
     * @param studentId identyfikator studenta
     * @param terminId identyfikator terminu
     * @return lista obecności spełniających oba kryteria
     */
    public List<Obecnosc> getByStudentIdAndTerminId(Long studentId, Long terminId) {
        return obecnoscRepository.findByStudentIdAndTerminId(studentId, terminId);
    }

}
