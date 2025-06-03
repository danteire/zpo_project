package org.example.listaobecnosci.service;

import org.example.listaobecnosci.Grupa;
import org.example.listaobecnosci.Termin;
import org.example.listaobecnosci.repository.TerminRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serwis zarządzający operacjami na encji {@link Termin}.
 *
 * Odpowiada za logikę biznesową dotyczącą terminów,
 * w tym pobieranie, zapisywanie, usuwanie i przypisywanie terminów do grup.
 *
 * Wykorzystuje {@link TerminRepository} do operacji na bazie danych
 * oraz {@link GrupaService} do zarządzania grupami.
 *
 * @author Patryk Paczkowski
 * @version 1.2
 */
@Service
public class TerminService {

    private final TerminRepository terminRepository;
    private final GrupaService grupaService;

    /**
     * Konstruktor serwisu.
     *
     * @param terminRepository repozytorium terminów
     * @param grupaService serwis zarządzający grupami
     */
    public TerminService(TerminRepository terminRepository, GrupaService grupaService) {
        this.terminRepository = terminRepository;
        this.grupaService = grupaService;
    }

    /**
     * Pobiera listę wszystkich terminów.
     *
     * @return lista terminów
     */
    public List<Termin> getAllTerminy() {
        return terminRepository.findAll();
    }

    /**
     * Pobiera termin po jego identyfikatorze.
     *
     * @param id identyfikator terminu
     * @return opcjonalny obiekt {@link Termin}
     */
    public Optional<Termin> getTerminById(Long id) {
        return terminRepository.findById(id);
    }

    /**
     * Pobiera listę terminów powiązanych z grupą o podanym identyfikatorze.
     *
     * @param grupaId identyfikator grupy
     * @return lista terminów lub pusta lista, jeśli grupa nie istnieje
     */
    public List<Termin> getTerminByGrupaId(Long grupaId) {
        Optional<Grupa> grupaOpt = grupaService.getGrupaById(grupaId);
        return grupaOpt.map(terminRepository::findByGrupa).orElse(List.of());
    }

    /**
     * Zapisuje nowy termin lub aktualizuje istniejący.
     *
     * @param termin obiekt terminu do zapisania
     * @return zapisany termin
     */
    public Termin saveTermin(Termin termin) {
        return terminRepository.save(termin);
    }

    /**
     * Usuwa termin o podanym identyfikatorze.
     *
     * @param id identyfikator terminu do usunięcia
     */
    public void deleteTermin(Long id) {
        terminRepository.deleteById(id);
    }

    /**
     * Przypisuje termin do określonej grupy i zapisuje zmiany.
     *
     * @param termin termin do przypisania
     * @param grupa grupa, do której termin ma być przypisany
     * @return zaktualizowany termin
     */
    public Termin assignTerminToGrupa(Termin termin, Grupa grupa) {
        termin.setGrupa(grupa);
        return terminRepository.save(termin);
    }

}
