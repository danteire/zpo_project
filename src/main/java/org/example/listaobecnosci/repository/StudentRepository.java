package org.example.listaobecnosci.repository;

import jakarta.transaction.Transactional;
import org.example.listaobecnosci.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repozytorium JPA dla encji {@link Student}.
 *
 * Umożliwia wykonywanie operacji CRUD na studentach oraz
 * wyszukiwanie studentów po numerze indeksu lub identyfikatorze grupy.
 *
 * Dziedziczy podstawowe metody z {@link JpaRepository}.
 *
 * @author Patryk Paczkowski
 * @version 1.1
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Znajduje studenta po jego numerze indeksu.
     *
     * @param nrIndeksu numer indeksu studenta
     * @return opcjonalny obiekt {@link Student}, jeśli istnieje student o podanym numerze
     */
    Optional<Student> findByNrIndeksu(String nrIndeksu);

    /**
     * Znajduje listę studentów należących do grupy o podanym identyfikatorze.
     *
     * @param grupaId identyfikator grupy
     * @return lista studentów przypisanych do grupy
     */
    List<Student> findByGrupaId(Long grupaId);

    /**
     * Ustawia pole {@code grupa} na {@code null} dla wszystkich studentów
     * powiązanych z grupą o podanym identyfikatorze.
     *
     * Metoda jest wykonywana jako zapytanie aktualizujące (bulk update),
     * aby odpiąć studentów od usuwanej grupy przed jej usunięciem.
     *
     * @param grupaId identyfikator grupy, której studenci mają zostać odłączeni
     */
    @Modifying
    @Transactional
    @Query("UPDATE Student s SET s.grupa = null WHERE s.grupa.id = :grupaId")
    void unsetGrupaForStudentsByGrupaId(Long grupaId);
}
