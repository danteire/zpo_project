package org.example.listaobecnosci.service;

import lombok.RequiredArgsConstructor;
import org.example.listaobecnosci.Student;
import org.example.listaobecnosci.Grupa;
import org.example.listaobecnosci.repository.GrupaRepository;
import org.example.listaobecnosci.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final GrupaRepository grupaRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Student assignStudentToGrupa(Long studentId, Long grupaId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Grupa grupa = grupaRepository.findById(grupaId)
                .orElseThrow(() -> new RuntimeException("Grupa not found"));
        student.setGrupa(grupa);
        return studentRepository.save(student);
    }

    public Student removeStudentFromGrupa(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        student.setGrupa(null);
        return studentRepository.save(student);
    }
}
