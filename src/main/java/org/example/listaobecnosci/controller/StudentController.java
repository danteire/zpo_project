package org.example.listaobecnosci.controller;

import org.example.listaobecnosci.Grupa;
import org.example.listaobecnosci.Student;
import org.example.listaobecnosci.service.StudentService;
import org.example.listaobecnosci.service.GrupaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Kontroler REST zarządzający operacjami na encji {@link Student}.
 *
 * Udostępnia endpointy do pobierania, tworzenia, usuwania oraz zarządzania
 * przypisaniem studenta do grupy.
 *
 * Wykorzystuje serwisy {@link StudentService} oraz {@link GrupaService}.
 *
 * @author Patryk Paczkowski
 * @version 1.3
 */
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final GrupaService grupaService;

    /**
     * Konstruktor kontrolera.
     *
     * @param studentService serwis do obsługi studentów
     * @param grupaService serwis do obsługi grup
     */
    public StudentController(StudentService studentService, GrupaService grupaService) {
        this.studentService = studentService;
        this.grupaService = grupaService;
    }

    /**
     * Pobiera listę wszystkich studentów.
     *
     * @return lista studentów
     */
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    /**
     * Pobiera studenta po jego identyfikatorze.
     *
     * @param id identyfikator studenta
     * @return odpowiedź HTTP z obiektem studenta lub 404, jeśli nie znaleziono
     */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Dodaje nowego studenta.
     *
     * @param student obiekt studenta do dodania
     * @return dodany student
     */
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    /**
     * Usuwa studenta o podanym identyfikatorze.
     *
     * @param id identyfikator studenta do usunięcia
     * @return odpowiedź HTTP z kodem 204 (no content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Pobiera listę studentów przypisanych do grupy o podanym identyfikatorze.
     *
     * @param grupaId identyfikator grupy
     * @return lista studentów należących do grupy
     */
    @GetMapping("/groups/{grupaId}")
    public List<Student> getStudentsByGrupa(@PathVariable Long grupaId) {
        return studentService.getStudentsByGrupaId(grupaId);
    }

    /**
     * Przypisuje studenta do grupy na podstawie ich identyfikatorów.
     *
     * @param id identyfikator studenta
     * @param grupaId identyfikator grupy
     * @return odpowiedź HTTP z zaktualizowanym studentem lub 404, jeśli student lub grupa nie zostaną znalezieni
     */
    @PutMapping("/{id}/group/{grupaId}")
    public ResponseEntity<Student> assignStudentToGrupa(@PathVariable Long id, @PathVariable Long grupaId) {
        Optional<Student> studentOpt = studentService.getStudentById(id);
        Optional<Grupa> grupaOpt = grupaService.getGrupaById(grupaId);
        if (studentOpt.isEmpty() || grupaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Student student = studentOpt.get();
        student.setGrupa(grupaOpt.get());
        studentService.saveStudent(student);
        return ResponseEntity.ok(student);
    }

    /**
     * Usuwa powiązanie studenta z grupą.
     *
     * @param id identyfikator studenta
     * @return odpowiedź HTTP z zaktualizowanym studentem lub 404, jeśli student nie zostanie znaleziony
     */
    @DeleteMapping("/{id}/group")
    public ResponseEntity<Student> removeStudentFromGrupa(@PathVariable Long id) {
        try {
            Student updatedStudent = studentService.removeStudentFromGrupa(id);
            return ResponseEntity.ok(updatedStudent);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
