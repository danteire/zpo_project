package org.example.listaobecnosci.controller;

import org.example.listaobecnosci.Grupa;
import org.example.listaobecnosci.Termin;
import org.example.listaobecnosci.service.GrupaService;
import org.example.listaobecnosci.service.TerminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Kontroler REST zarządzający operacjami na encji {@link Termin}.
 *
 * Udostępnia endpointy do pobierania, tworzenia i usuwania terminów.
 * Wspiera operacje takie jak:
 * - pobranie wszystkich terminów,
 * - pobranie terminu po identyfikatorze,
 * - pobranie terminów przypisanych do konkretnej grupy,
 * - dodanie nowego terminu (wymaga przypisania do grupy),
 * - usunięcie terminu po identyfikatorze.
 *
 * Wykorzystuje serwisy {@link TerminService} oraz {@link GrupaService}.
 *
 * @author Patryk Paczkowski
 * @version 1.3
 */
@RestController
@RequestMapping("/terminy")
public class TerminController {

    private final TerminService terminService;
    private final GrupaService grupaService;

    /**
     * Konstruktor kontrolera.
     *
     * @param terminService serwis do obsługi terminów
     * @param grupaService serwis do obsługi grup
     */
    public TerminController(TerminService terminService, GrupaService grupaService) {
        this.terminService = terminService;
        this.grupaService = grupaService;
    }

    /**
     * Pobiera listę wszystkich terminów.
     *
     * @return lista terminów
     */
    @GetMapping
    public List<Termin> getAllTerminy() {
        return terminService.getAllTerminy();
    }

    /**
     * Pobiera termin na podstawie jego identyfikatora.
     *
     * @param id identyfikator terminu
     * @return odpowiedź HTTP z terminem lub 404, jeśli nie znaleziono
     */
    @GetMapping("/{id}")
    public ResponseEntity<Termin> getTerminById(@PathVariable Long id) {
        return terminService.getTerminById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Pobiera listę terminów przypisanych do grupy o podanym identyfikatorze.
     *
     * @param grupaId identyfikator grupy
     * @return odpowiedź HTTP z listą terminów lub 404, jeśli grupa nie istnieje
     */
    @GetMapping("/group/{grupaId}")
    public ResponseEntity<List<Termin>> getTerminyByGrupa(@PathVariable Long grupaId) {
        Optional<Grupa> grupaOpt = grupaService.getGrupaById(grupaId);
        if (grupaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Termin> terminy = terminService.getTerminByGrupaId(grupaId);
        return ResponseEntity.ok(terminy);
    }

    /**
     * Dodaje nowy termin. Termin musi być przypisany do istniejącej grupy.
     *
     * Przykładowy JSON:
     * {
     *   "data": "2025-06-03T10:00:00",
     *   "grupa": { "id": 1 }
     * }
     *
     * @param termin obiekt terminu do dodania
     * @return odpowiedź HTTP z zapisanym terminem lub 400, jeśli brak grupy lub grupa nie istnieje
     */
    @PostMapping
    public ResponseEntity<Termin> addTermin(@RequestBody Termin termin) {
        if (termin.getGrupa() != null && termin.getGrupa().getId() != null) {
            Optional<Grupa> grupaOpt = grupaService.getGrupaById(termin.getGrupa().getId());
            if (grupaOpt.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            termin.setGrupa(grupaOpt.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
        Termin savedTermin = terminService.saveTermin(termin);
        return ResponseEntity.ok(savedTermin);
    }

    /**
     * Usuwa termin o podanym identyfikatorze.
     *
     * @param id identyfikator terminu do usunięcia
     * @return odpowiedź HTTP z kodem 204 (no content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTermin(@PathVariable Long id) {
        terminService.deleteTermin(id);
        return ResponseEntity.noContent().build();
    }
}
