package org.example.listaobecnosci.repository;

import org.example.listaobecnosci.Obecnosc;
import org.example.listaobecnosci.Student;
import org.example.listaobecnosci.Termin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObecnoscRepository extends JpaRepository<Obecnosc, Long> {
    List<Obecnosc> findByTermin(Termin termin);
    List<Obecnosc> findByStudent(Student student);
}
