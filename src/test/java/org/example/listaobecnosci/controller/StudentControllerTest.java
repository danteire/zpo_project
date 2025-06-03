package org.example.listaobecnosci.controller;

import org.example.listaobecnosci.Student;
import org.example.listaobecnosci.service.GrupaService;
import org.example.listaobecnosci.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentService studentService;

    @Autowired
    private GrupaService grupaService;

    @TestConfiguration
    static class TestConfig {

        @Bean
        public StudentService studentService() {
            return Mockito.mock(StudentService.class);
        }

        @Bean
        public GrupaService grupaService() {
            return Mockito.mock(GrupaService.class);
        }
    }

    @Test
    void shouldReturnAllStudents() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setImie("Jan");
        student.setNazwisko("Kowalski");

        when(studentService.getAllStudents()).thenReturn(List.of(student));

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].imie").value("Jan"));
    }

    @Test
    void shouldReturnStudentById() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setImie("Anna");
        student.setNazwisko("Nowak");

        when(studentService.getStudentById(1L)).thenReturn(Optional.of(student));

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imie").value("Anna"));
    }
}
