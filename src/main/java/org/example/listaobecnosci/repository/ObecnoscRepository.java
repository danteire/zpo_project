package org.example.listaobecnosci.repository;

import org.example.listaobecnosci.Obecnosc;
import org.example.listaobecnosci.Student;
import org.example.listaobecnosci.Termin;
import org.example.listaobecnosci.statusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObecnoscRepository extends JpaRepository<Obecnosc, Long> {
    List<Obecnosc> findByTermin(Termin termin);
    List<Obecnosc> findByTerminId(Long terminId);
    List<Obecnosc> findByStudent(Student student);
    List<Obecnosc> findByStudentId(Long studentId);
    List<Obecnosc> findByStatus(statusEnum status);
    List<Obecnosc> findByStudentIdAndTerminId(Long studentId, Long terminId);
}
