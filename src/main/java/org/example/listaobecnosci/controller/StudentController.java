package org.example.listaobecnosci.controller;


import org.example.listaobecnosci.Grupa;
import org.example.listaobecnosci.Student;
import org.example.listaobecnosci.service.StudentService;
import org.example.listaobecnosci.service.GrupaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final GrupaService grupaService;

    public StudentController(StudentService studentService, GrupaService grupaService) {
        this.studentService = studentService;
        this.grupaService = grupaService;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/groups/{grupaId}")
    public List<Student> getStudentsByGrupa(@PathVariable Long grupaId) {
        return studentService.getStudentsByGrupaId(grupaId);
    }

    @PutMapping("/{id}/group/{grupaId}")
    public ResponseEntity<Student> assignStudentToGrupa(@PathVariable Long id, @PathVariable Long grupaId) {
        Optional<Student> studentOpt = studentService.getStudentById(id);
        Optional<Grupa> grupaOpt = grupaService.getGrupaById(grupaId);
        if(studentOpt.isEmpty() || grupaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Student student = studentOpt.get();
        student.setGrupa(grupaOpt.get());
        studentService.saveStudent(student);
        return ResponseEntity.ok(student);
    }

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
