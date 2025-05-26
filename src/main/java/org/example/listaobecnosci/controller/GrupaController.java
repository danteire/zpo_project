package org.example.listaobecnosci.controller;

import lombok.RequiredArgsConstructor;
import org.example.listaobecnosci.Grupa;
import org.example.listaobecnosci.service.GrupaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupy")
@RequiredArgsConstructor
public class GrupaController {

    private final GrupaService grupaService;

    @GetMapping
    public List<Grupa> getAllGrupy() {
        return grupaService.getAllGrupy();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grupa> getGrupaById(@PathVariable Long id) {
        return grupaService.getGrupaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Grupa addGrupa(@RequestBody Grupa grupa) {
        return grupaService.saveGrupa(grupa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrupa(@PathVariable Long id) {
        grupaService.deleteGrupa(id);
        return ResponseEntity.noContent().build();
    }
}
