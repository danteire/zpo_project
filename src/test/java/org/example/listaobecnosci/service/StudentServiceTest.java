package org.example.listaobecnosci.service;

import org.example.listaobecnosci.Grupa;
import org.example.listaobecnosci.Student;
import org.example.listaobecnosci.repository.GrupaRepository;
import org.example.listaobecnosci.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private GrupaRepository grupaRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void shouldReturnAllStudents() {
        List<Student> mockStudents = List.of(new Student(), new Student());
        when(studentRepository.findAll()).thenReturn(mockStudents);

        List<Student> result = studentService.getAllStudents();

        assertEquals(2, result.size());
        verify(studentRepository).findAll();
    }

    @Test
    void shouldAssignStudentToGrupa() {
        Student student = new Student();
        student.setId(1L);
        Grupa grupa = new Grupa();
        grupa.setId(2L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(grupaRepository.findById(2L)).thenReturn(Optional.of(grupa));
        when(studentRepository.save(any(Student.class))).thenAnswer(i -> i.getArgument(0));

        Student updatedStudent = studentService.assignStudentToGrupa(1L, 2L);

        assertEquals(grupa, updatedStudent.getGrupa());
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFound() {
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> studentService.removeStudentFromGrupa(999L));
    }
}
