package org.example.listaobecnosci.repository;

import org.example.listaobecnosci.Termin;
import org.example.listaobecnosci.Grupa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TerminRepository extends JpaRepository<Termin, Long> {
    List<Termin> findByGrupa(Grupa grupa);
}
