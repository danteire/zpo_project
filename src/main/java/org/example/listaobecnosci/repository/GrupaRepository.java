package org.example.listaobecnosci.repository;

import org.example.listaobecnosci.Grupa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repozytorium JPA dla encji {@link Grupa}.
 *
 * Umożliwia wykonywanie operacji CRUD na grupach studentów.
 *
 * Dziedziczy podstawowe metody z {@link JpaRepository}.
 *
 * @author Patryk Paczkowski
 * @version 1.1
 */
@Repository
public interface GrupaRepository extends JpaRepository<Grupa, Long> {
}
