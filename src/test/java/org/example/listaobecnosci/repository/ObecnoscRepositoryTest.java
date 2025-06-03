package org.example.listaobecnosci.repository;

import org.example.listaobecnosci.*;
import org.example.listaobecnosci.statusEnum;
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
public class ObecnoscRepositoryTest {

    @Autowired
    private ObecnoscRepository obecnoscRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TerminRepository terminRepository;

    @Autowired
    private GrupaRepository grupaRepository;

    @Test
    void shouldFindByTermin() {
        Grupa grupa = new Grupa();
        grupa.setNazwa("3C");
        grupa = grupaRepository.save(grupa);

        Student student = new Student();
        student.setImie("Jan");
        student.setNazwisko("Kowalski");
        student.setNrIndeksu("999999");
        student.setGrupa(grupa);
        studentRepository.save(student);

        Termin termin = new Termin();
        termin.setGrupa(grupa);
        termin.setData(LocalDateTime.now());
        termin = terminRepository.save(termin);

        Obecnosc obecnosc = new Obecnosc();
        obecnosc.setStudent(student);
        obecnosc.setTermin(termin);
        obecnosc.setStatus(statusEnum.BRAK);
        obecnoscRepository.save(obecnosc);

        List<Obecnosc> byTermin = obecnoscRepository.findByTermin(termin);
        List<Obecnosc> byTerminId = obecnoscRepository.findByTerminId(termin.getId());
        List<Obecnosc> byStudent = obecnoscRepository.findByStudent(student);
        List<Obecnosc> byStudentId = obecnoscRepository.findByStudentId(student.getId());
        List<Obecnosc> byStatus = obecnoscRepository.findByStatus(statusEnum.BRAK);
        List<Obecnosc> byStudentAndTermin = obecnoscRepository.findByStudentIdAndTerminId(student.getId(), termin.getId());

        assertThat(byTermin).hasSize(1);
        assertThat(byTerminId).hasSize(1);
        assertThat(byStudent).hasSize(1);
        assertThat(byStudentId).hasSize(1);
        assertThat(byStatus).hasSize(1);
        assertThat(byStudentAndTermin).hasSize(1);
    }
}
