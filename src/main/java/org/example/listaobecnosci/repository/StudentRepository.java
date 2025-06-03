package org.example.listaobecnosci.repository;

import org.example.listaobecnosci.Student;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
