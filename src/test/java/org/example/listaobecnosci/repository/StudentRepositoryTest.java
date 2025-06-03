package org.example.listaobecnosci.repository;

import org.example.listaobecnosci.Grupa;
import org.example.listaobecnosci.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // aby nie podmieniać bazy na H2, jeśli chcesz własną
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GrupaRepository grupaRepository;

    @Test
    void shouldFindStudentByNrIndeksu() {
        Student student = new Student();
        student.setImie("Adam");
        student.setNazwisko("Nowak");
        student.setNrIndeksu("123456");

        studentRepository.save(student);

        var result = studentRepository.findByNrIndeksu("123456");

        assertThat(result).isPresent();
        assertThat(result.get().getImie()).isEqualTo("Adam");
    }

    @Test
    void shouldFindStudentsByGrupaId() {
        Grupa grupa = new Grupa();
        grupa.setNazwa("1A");
        grupa = grupaRepository.save(grupa);

        Student student = new Student();
        student.setImie("Ewa");
        student.setNazwisko("Kowal");
        student.setNrIndeksu("654321");
        student.setGrupa(grupa);
        studentRepository.save(student);

        var results = studentRepository.findByGrupaId(grupa.getId());

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getGrupa().getNazwa()).isEqualTo("1A");
    }
}
