package org.example.listaobecnosci.repository;

import org.example.listaobecnosci.Termin;
import org.example.listaobecnosci.Grupa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TerminRepository extends JpaRepository<Termin, Long> {

    @Query("SELECT t FROM Termin t JOIN FETCH t.grupa")
    List<Termin> findAllWithGrupa();

    @Query("SELECT t FROM Termin t JOIN FETCH t.grupa WHERE t.grupa = :grupa")
    List<Termin> findByGrupaWithFetch(@Param("grupa") Grupa grupa);
}
