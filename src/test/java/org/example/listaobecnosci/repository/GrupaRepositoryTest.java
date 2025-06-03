package org.example.listaobecnosci.repository;

import org.example.listaobecnosci.Grupa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

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
public class GrupaRepositoryTest {

    @Autowired
    private GrupaRepository grupaRepository;

    @Test
    void shouldSaveAndFindGrupa() {
        Grupa grupa = new Grupa();
        grupa.setNazwa("TestGrupa");
        grupa = grupaRepository.save(grupa);

        Optional<Grupa> found = grupaRepository.findById(grupa.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getNazwa()).isEqualTo("TestGrupa");
    }
}
