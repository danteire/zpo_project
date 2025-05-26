package org.example.listaobecnosci.controller;

import org.example.listaobecnosci.Grupa;
import org.example.listaobecnosci.Termin;
import org.example.listaobecnosci.service.GrupaService;
import org.example.listaobecnosci.service.TerminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/terminy")

public class TerminController {

    private final TerminService terminService;
    private final GrupaService grupaService;

    public TerminController(TerminService terminService, GrupaService grupaService) {
        this.terminService = terminService;
        this.grupaService = grupaService;
    }

    @GetMapping
    public List<Termin> getAllTerminy() {
        return terminService.getAllTerminy();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Termin> getTerminById(@PathVariable Long id) {
        return terminService.getTerminById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Termin> addTermin(@RequestBody Termin termin) {
        // do przypisania terminu do grupy: grupa.id w JSON!
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTermin(@PathVariable Long id) {
        terminService.deleteTermin(id);
        return ResponseEntity.noContent().build();
    }
}
