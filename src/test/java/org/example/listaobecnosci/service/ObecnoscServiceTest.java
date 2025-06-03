package org.example.listaobecnosci.service;

import org.example.listaobecnosci.*;
import org.example.listaobecnosci.repository.ObecnoscRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class ObecnoscServiceTest {

    @Mock
    private ObecnoscRepository obecnoscRepository;

    @InjectMocks
    private ObecnoscService obecnoscService;

    @Test
    void shouldReturnAllObecnosci() {
        List<Obecnosc> obecnosci = List.of(new Obecnosc(), new Obecnosc());
        when(obecnoscRepository.findAll()).thenReturn(obecnosci);

        List<Obecnosc> result = obecnoscService.getAllObecnosci();

        assertThat(result).hasSize(2);
        verify(obecnoscRepository).findAll();
    }

    @Test
    void shouldReturnObecnoscById() {
        Obecnosc obecnosc = new Obecnosc();
        obecnosc.setId(1L);
        when(obecnoscRepository.findById(1L)).thenReturn(Optional.of(obecnosc));

        Optional<Obecnosc> result = obecnoscService.getObecnoscById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        verify(obecnoscRepository).findById(1L);
    }

    @Test
    void shouldSaveNewObecnosc() {
        Obecnosc obecnosc = new Obecnosc();
        when(obecnoscRepository.save(obecnosc)).thenReturn(obecnosc);

        Obecnosc result = obecnoscService.saveOrUpdateObecnosc(obecnosc);

        assertThat(result).isEqualTo(obecnosc);
        verify(obecnoscRepository).save(obecnosc);
    }

    @Test
    void shouldUpdateExistingObecnosc() {
        Obecnosc existing = new Obecnosc();
        existing.setId(1L);
        existing.setStatus(statusEnum.BRAK);

        Obecnosc updated = new Obecnosc();
        updated.setId(1L);
        updated.setStatus(statusEnum.OBECNY);

        when(obecnoscRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(obecnoscRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Obecnosc result = obecnoscService.saveOrUpdateObecnosc(updated);

        assertThat(result.getStatus()).isEqualTo(statusEnum.OBECNY);
        verify(obecnoscRepository).findById(1L);
        verify(obecnoscRepository).save(existing);
    }

    @Test
    void shouldDeleteObecnosc() {
        obecnoscService.deleteObecnosc(1L);
        verify(obecnoscRepository).deleteById(1L);
    }

    @Test
    void shouldGetByStatus() {
        List<Obecnosc> obecnosci = List.of(new Obecnosc());
        when(obecnoscRepository.findByStatus(statusEnum.BRAK)).thenReturn(obecnosci);

        List<Obecnosc> result = obecnoscService.getByStatus(statusEnum.BRAK);

        assertThat(result).hasSize(1);
        verify(obecnoscRepository).findByStatus(statusEnum.BRAK);
    }

    @Test
    void shouldGetByStudentId() {
        List<Obecnosc> obecnosci = List.of(new Obecnosc());
        when(obecnoscRepository.findByStudentId(2L)).thenReturn(obecnosci);

        List<Obecnosc> result = obecnoscService.getByStudentId(2L);

        assertThat(result).hasSize(1);
        verify(obecnoscRepository).findByStudentId(2L);
    }

    @Test
    void shouldGetByTerminId() {
        List<Obecnosc> obecnosci = List.of(new Obecnosc());
        when(obecnoscRepository.findByTerminId(3L)).thenReturn(obecnosci);

        List<Obecnosc> result = obecnoscService.getByTerminId(3L);

        assertThat(result).hasSize(1);
        verify(obecnoscRepository).findByTerminId(3L);
    }

    @Test
    void shouldGetByStudentIdAndTerminId() {
        List<Obecnosc> obecnosci = List.of(new Obecnosc());
        when(obecnoscRepository.findByStudentIdAndTerminId(2L, 3L)).thenReturn(obecnosci);

        List<Obecnosc> result = obecnoscService.getByStudentIdAndTerminId(2L, 3L);

        assertThat(result).hasSize(1);
        verify(obecnoscRepository).findByStudentIdAndTerminId(2L, 3L);
    }
}
