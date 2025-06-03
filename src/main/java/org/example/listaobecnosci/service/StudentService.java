package org.example.listaobecnosci.service;

import org.example.listaobecnosci.Student;
import org.example.listaobecnosci.Grupa;
import org.example.listaobecnosci.repository.GrupaRepository;
import org.example.listaobecnosci.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serwis zarządzający operacjami na encji {@link Student}.
 *
 * Odpowiada za logikę biznesową dotyczącą studentów,
 * w tym pobieranie, zapisywanie, usuwanie oraz przypisywanie i usuwanie
 * powiązań studenta z grupą.
 *
 * Wykorzystuje {@link StudentRepository} i {@link GrupaRepository} do operacji na bazie danych.
 *
 * @author Patryk Paczkowski
 * @version 1.2
 */
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final GrupaRepository grupaRepository;

    /**
     * Konstruktor serwisu.
     *
     * @param studentRepository repozytorium studentów
     * @param grupaRepository repozytorium grup
     */
    public StudentService(StudentRepository studentRepository, GrupaRepository grupaRepository) {
        this.studentRepository = studentRepository;
        this.grupaRepository = grupaRepository;
    }

    /**
     * Pobiera listę wszystkich studentów.
     *
     * @return lista studentów
     */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * Pobiera studenta po jego identyfikatorze.
     *
     * @param id identyfikator studenta
     * @return opcjonalny obiekt {@link Student}
     */
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    /**
     * Pobiera listę studentów przypisanych do grupy o podanym identyfikatorze.
     *
     * @param grupaId identyfikator grupy
     * @return lista studentów należących do grupy
     */
    public List<Student> getStudentsByGrupaId(Long grupaId) {
        return studentRepository.findByGrupaId(grupaId);
    }

    /**
     * Zapisuje nowego studenta lub aktualizuje istniejącego.
     *
     * @param student obiekt studenta do zapisania
     * @return zapisany student
     */
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    /**
     * Usuwa studenta o podanym identyfikatorze.
     *
     * @param id identyfikator studenta do usunięcia
     */
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    /**
     * Przypisuje studenta do grupy na podstawie ich identyfikatorów.
     *
     * @param studentId identyfikator studenta
     * @param grupaId identyfikator grupy
     * @return zaktualizowany student z przypisaną grupą
     * @throws RuntimeException jeśli student lub grupa nie zostaną znalezieni
     */
    public Student assignStudentToGrupa(Long studentId, Long grupaId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Grupa grupa = grupaRepository.findById(grupaId)
                .orElseThrow(() -> new RuntimeException("Grupa not found"));
        student.setGrupa(grupa);
        return studentRepository.save(student);
    }

    /**
     * Usuwa powiązanie studenta z grupą.
     *
     * @param studentId identyfikator studenta
     * @return zaktualizowany student bez przypisanej grupy
     * @throws RuntimeException jeśli student nie zostanie znaleziony
     */
    public Student removeStudentFromGrupa(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        student.setGrupa(null);
        return studentRepository.save(student);
    }
}
