package org.example.listaobecnosci.repository;

import org.example.listaobecnosci.Termin;
import org.example.listaobecnosci.Grupa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repozytorium JPA dla encji {@link Termin}.
 *
 * Umożliwia wykonywanie operacji CRUD na terminach oraz
 * wyszukiwanie terminów powiązanych z określoną grupą.
 *
 * Dziedziczy podstawowe metody z {@link JpaRepository}.
 *
 * @author Patryk Paczkowski
 * @version 1.1
 */
@Repository
public interface TerminRepository extends JpaRepository<Termin, Long> {

    /**
     * Znajduje listę terminów powiązanych z daną grupą.
     *
     * @param grupa grupa, dla której szukamy terminów
     * @return lista terminów powiązanych z podaną grupą
     */
    List<Termin> findByGrupa(Grupa grupa);

    /**
     * Znajduje listę terminów powiązanych z daną grupą (jej id).
     *
     * @param id, dla którego szukamy terminów
     * @return lista terminów powiązanych z podaną grupą
     */
    List<Termin> findByGrupaId(Long id);
}
