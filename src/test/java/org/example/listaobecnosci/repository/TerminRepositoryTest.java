package org.example.listaobecnosci.repository;

import org.example.listaobecnosci.Grupa;
import org.example.listaobecnosci.Termin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class TerminRepositoryTest {

    @Autowired
    private TerminRepository terminRepository;

    @Autowired
    private GrupaRepository grupaRepository;

    @Test
    void shouldFindTerminByGrupa() {
        Grupa grupa = new Grupa();
        grupa.setNazwa("2B");
        grupa = grupaRepository.save(grupa);

        Termin termin = new Termin();
        termin.setGrupa(grupa);
        termin.setData(LocalDateTime.now());
        terminRepository.save(termin);

        List<Termin> results = terminRepository.findByGrupa(grupa);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getGrupa().getNazwa()).isEqualTo("2B");
    }
}
