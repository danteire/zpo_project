package org.example.listaobecnosci.controller;

import org.example.listaobecnosci.Grupa;
import org.example.listaobecnosci.service.GrupaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Kontroler REST zarządzający operacjami na encji {@link Grupa}.
 *
 * Udostępnia endpointy do pobierania, tworzenia i usuwania grup.
 *
 * Wykorzystuje serwis {@link GrupaService}.
 *
 * @author Patryk Paczkowski
 * @version 1.3
 */
@RestController
@RequestMapping("/groups")
public class GrupaController {

    private final GrupaService grupaService;

    /**
     * Konstruktor kontrolera.
     *
     * @param grupaService serwis do obsługi grup
     */
    public GrupaController(GrupaService grupaService) {
        this.grupaService = grupaService;
    }

    /**
     * Pobiera listę wszystkich grup.
     *
     * @return lista grup
     */
    @GetMapping
    public List<Grupa> getAllGrupy() {
        return grupaService.getAllGrupy();
    }

    /**
     * Pobiera grupę po jej identyfikatorze.
     *
     * @param id identyfikator grupy
     * @return odpowiedź HTTP z obiektem grupy lub 404, jeśli nie znaleziono
     */
    @GetMapping("/{id}")
    public ResponseEntity<Grupa> getGrupaById(@PathVariable Long id) {
        return grupaService.getGrupaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Tworzy nową grupę.
     *
     * @param grupa obiekt grupy do zapisania
     * @return zapisany obiekt grupy
     */
    @PostMapping
    public Grupa addGrupa(@RequestBody Grupa grupa) {
        return grupaService.saveGrupa(grupa);
    }

    /**
     * Usuwa grupę o podanym identyfikatorze.
     *
     * @param id identyfikator grupy do usunięcia
     * @return odpowiedź HTTP z kodem 204 (no content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrupa(@PathVariable Long id) {
        grupaService.deleteGrupa(id);
        return ResponseEntity.noContent().build();
    }
}
