package org.example.listaobecnosci.controller;

import org.example.listaobecnosci.Obecnosc;
import org.example.listaobecnosci.service.ObecnoscService;
import org.example.listaobecnosci.statusEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Kontroler REST zarządzający operacjami na encji {@link Obecnosc}.
 *
 * Udostępnia endpointy do pobierania, zapisywania, usuwania oraz filtrowania
 * obecności na podstawie różnych kryteriów (student, termin, status).
 *
 * Wykorzystuje serwis {@link ObecnoscService}.
 *
 * @author Patryk Paczkowski
 * @version 1.3
 */
@RestController
@RequestMapping("/obecnosci")
public class ObecnoscController {

    private final ObecnoscService obecnoscService;

    /**
     * Konstruktor kontrolera.
     *
     * @param obecnoscService serwis do obsługi obecności
     */
    public ObecnoscController(ObecnoscService obecnoscService) {
        this.obecnoscService = obecnoscService;
    }

    /**
     * Pobiera listę wszystkich obecności.
     *
     * @return lista obecności
     */
    @GetMapping
    public List<Obecnosc> getAllObecnosci() {
        return obecnoscService.getAllObecnosci();
    }

    /**
     * Pobiera obecność po jej identyfikatorze.
     *
     * @param id identyfikator obecności
     * @return odpowiedź HTTP z obiektem obecności lub 404, jeśli nie znaleziono
     */
    @GetMapping("/{id}")
    public ResponseEntity<Obecnosc> getObecnoscById(@PathVariable Long id) {
        return obecnoscService.getObecnoscById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Zapisuje lub aktualizuje obecność.
     *
     * @param obecnosc obiekt obecności do zapisania lub aktualizacji
     * @return zapisany lub zaktualizowany obiekt obecności
     */
    @PutMapping
    public Obecnosc saveOrUpdateObecnosc(@RequestBody Obecnosc obecnosc) {
        return obecnoscService.saveOrUpdateObecnosc(obecnosc);
    }

    /**
     * Usuwa obecność o podanym identyfikatorze.
     *
     * @param id identyfikator obecności do usunięcia
     * @return odpowiedź HTTP z kodem 204 (no content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObecnosc(@PathVariable Long id) {
        obecnoscService.deleteObecnosc(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Pobiera listę obecności dla konkretnego studenta.
     *
     * @param studentId identyfikator studenta
     * @return lista obecności przypisanych do studenta
     */
    @GetMapping("/student/{studentId}")
    public List<Obecnosc> getObecnosciByStudent(@PathVariable Long studentId) {
        return obecnoscService.getByStudentId(studentId);
    }

    /**
     * Pobiera listę obecności dla konkretnego terminu.
     *
     * @param terminId identyfikator terminu
     * @return lista obecności przypisanych do terminu
     */
    @GetMapping("/termin/{terminId}")
    public List<Obecnosc> getObecnosciByTermin(@PathVariable Long terminId) {
        return obecnoscService.getByTerminId(terminId);
    }

    /**
     * Pobiera listę obecności według statusu.
     *
     * @param status status obecności (np. OBECNY, NIEOBECNY)
     * @return lista obecności o podanym statusie
     */
    @GetMapping("/status/{status}")
    public List<Obecnosc> getObecnosciByStatus(@PathVariable statusEnum status) {
        return obecnoscService.getByStatus(status);
    }

    /**
     * Pobiera listę obecności dla studenta i terminu.
     *
     * @param studentId identyfikator studenta
     * @param terminId identyfikator terminu
     * @return lista obecności dla podanego studenta i terminu
     */
    @GetMapping("/student/{studentId}/termin/{terminId}")
    public List<Obecnosc> getObecnosciByStudentAndTermin(@PathVariable Long studentId, @PathVariable Long terminId) {
        return obecnoscService.getByStudentIdAndTerminId(studentId, terminId);
    }
}
