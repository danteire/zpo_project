package org.example.listaobecnosci.repository;

import jakarta.transaction.Transactional;
import org.example.listaobecnosci.Obecnosc;
import org.example.listaobecnosci.Student;
import org.example.listaobecnosci.Termin;
import org.example.listaobecnosci.statusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

    /**
     * Usuwa wszystkie encje powiązane z danym terminem na podstawie jego identyfikatora.
     *
     * Metoda usuwa wszystkie rekordy (np. obecności), które są powiązane
     * z terminem o podanym identyfikatorze {@code terminId}.
     *
     * @param terminId identyfikator terminu, którego powiązane encje mają zostać usunięte
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Obecnosc o WHERE o.termin.id = :terminId")
    void deleteByTerminId(Long terminId);

    /**
     * Usuwa wszystkie wpisy obecności powiązane ze studentem o podanym identyfikatorze.
     *
     * Metoda wykonuje zapytanie usuwające wszystkie rekordy obecności
     * powiązane z {@link Student} o identyfikatorze {@code studentId}.
     * Metoda wymaga transakcji i jest oznaczona jako modyfikująca.
     *
     * @param studentId identyfikator studenta, którego obecności mają zostać usunięte
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Obecnosc o WHERE o.student.id = :studentId")
    void deleteByStudentId(Long studentId);


}
