package org.example.listaobecnosci.controller;

import org.example.listaobecnosci.Obecnosc;
import org.example.listaobecnosci.service.ObecnoscService;
import org.example.listaobecnosci.statusEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/obecnosci")
public class ObecnoscController {

    private final ObecnoscService obecnoscService;

    public ObecnoscController(ObecnoscService obecnoscService) {
        this.obecnoscService = obecnoscService;
    }

    @GetMapping
    public List<Obecnosc> getAllObecnosci() {
        return obecnoscService.getAllObecnosci();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Obecnosc> getObecnoscById(@PathVariable Long id) {
        return obecnoscService.getObecnoscById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public Obecnosc saveOrUpdateObecnosc(@RequestBody Obecnosc obecnosc) {
        return obecnoscService.saveOrUpdateObecnosc(obecnosc);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObecnosc(@PathVariable Long id) {
        obecnoscService.deleteObecnosc(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentId}")
    public List<Obecnosc> getObecnosciByStudent(@PathVariable Long studentId) {
        return obecnoscService.getByStudentId(studentId);
    }

    @GetMapping("/termin/{terminId}")
    public List<Obecnosc> getObecnosciByTermin(@PathVariable Long terminId) {
        return obecnoscService.getByTerminId(terminId);
    }

    @GetMapping("/status/{status}")
    public List<Obecnosc> getObecnosciByStatus(@PathVariable statusEnum status) {
        return obecnoscService.getByStatus(status);
    }

    @GetMapping("/student/{studentId}/termin/{terminId}")
    public List<Obecnosc> getObecnosciByStudentAndTermin(@PathVariable Long studentId, @PathVariable Long terminId) {
        return obecnoscService.getByStudentIdAndTerminId(studentId, terminId);
    }


}
