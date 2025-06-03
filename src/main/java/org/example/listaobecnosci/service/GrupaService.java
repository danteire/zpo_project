package org.example.listaobecnosci.service;

import org.example.listaobecnosci.Grupa;
import org.example.listaobecnosci.repository.GrupaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serwis zarządzający operacjami na encji {@link Grupa}.
 *
 * Zapewnia metody do pobierania, zapisywania oraz usuwania grup.
 *
 * Wykorzystuje {@link GrupaRepository} do operacji na bazie danych.
 *
 * @author Patryk Paczkowski
 * @version 1.2
 */
@Service
public class GrupaService {

    private final GrupaRepository grupaRepository;

    /**
     * Konstruktor serwisu.
     *
     * @param grupaRepository repozytorium grup
     */
    public GrupaService(GrupaRepository grupaRepository) {
        this.grupaRepository = grupaRepository;
    }

    /**
     * Pobiera listę wszystkich grup.
     *
     * @return lista grup
     */
    public List<Grupa> getAllGrupy() {
        return grupaRepository.findAll();
    }

    /**
     * Pobiera grupę po jej identyfikatorze.
     *
     * @param id identyfikator grupy
     * @return opcjonalny obiekt {@link Grupa}
     */
    public Optional<Grupa> getGrupaById(Long id) {
        return grupaRepository.findById(id);
    }

    /**
     * Zapisuje nową grupę lub aktualizuje istniejącą.
     *
     * @param grupa obiekt grupy do zapisania
     * @return zapisany obiekt {@link Grupa}
     */
    public Grupa saveGrupa(Grupa grupa) {
        return grupaRepository.save(grupa);
    }

    /**
     * Usuwa grupę o podanym identyfikatorze.
     *
     * @param id identyfikator grupy do usunięcia
     */
    public void deleteGrupa(Long id) {
        grupaRepository.deleteById(id);
    }
}
