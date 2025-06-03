package org.example.listaobecnosci.repository;

import org.example.listaobecnosci.Obecnosc;
import org.example.listaobecnosci.Student;
import org.example.listaobecnosci.Termin;
import org.example.listaobecnosci.statusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repozytorium JPA dla encji {@link Obecnosc}.
 *
 * Umożliwia wykonywanie operacji CRUD na obecnościach oraz
 * wyszukiwanie obecności według różnych kryteriów takich jak termin,
 * student, status czy ich identyfikatory.
 *
 * Dziedziczy podstawowe metody z {@link JpaRepository}.
 *
 * @author Patryk Paczkowski
 * @version 1.1
 */
@Repository
public interface ObecnoscRepository extends JpaRepository<Obecnosc, Long> {

    /**
     * Znajduje listę obecności powiązanych z podanym terminem.
     *
     * @param termin termin zajęć
     * @return lista obecności dla danego terminu
     */
    List<Obecnosc> findByTermin(Termin termin);

    /**
     * Znajduje listę obecności po identyfikatorze terminu.
     *
     * @param terminId identyfikator terminu
     * @return lista obecności dla terminu o podanym ID
     */
    List<Obecnosc> findByTerminId(Long terminId);

    /**
     * Znajduje listę obecności powiązanych z danym studentem.
     *
     * @param student student
     * @return lista obecności studenta
     */
    List<Obecnosc> findByStudent(Student student);

    /**
     * Znajduje listę obecności po identyfikatorze studenta.
     *
     * @param studentId identyfikator studenta
     * @return lista obecności dla studenta o podanym ID
     */
    List<Obecnosc> findByStudentId(Long studentId);

    /**
     * Znajduje listę obecności o określonym statusie.
     *
     * @param status status obecności
     * @return lista obecności o podanym statusie
     */
    List<Obecnosc> findByStatus(statusEnum status);

    /**
     * Znajduje obecność studenta na konkretnym terminie po identyfikatorach.
     *
     * @param studentId identyfikator studenta
     * @param terminId identyfikator terminu
     * @return lista obecności spełniających oba warunki
     */
    List<Obecnosc> findByStudentIdAndTerminId(Long studentId, Long terminId);
}
