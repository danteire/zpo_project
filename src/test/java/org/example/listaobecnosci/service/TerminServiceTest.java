package org.example.listaobecnosci.service;

import org.example.listaobecnosci.Grupa;
import org.example.listaobecnosci.Termin;
import org.example.listaobecnosci.repository.ObecnoscRepository;
import org.example.listaobecnosci.repository.TerminRepository;
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
class TerminServiceTest {

    @Mock
    private TerminRepository terminRepository;

    @Mock
    private ObecnoscRepository obecnoscRepository;

    @InjectMocks
    private TerminService terminService;

    @Test
    void shouldReturnAllTerminy() {
        List<Termin> terminy = List.of(new Termin(), new Termin());
        when(terminRepository.findAll()).thenReturn(terminy);

        List<Termin> result = terminService.getAllTerminy();

        assertThat(result).hasSize(2);
        verify(terminRepository).findAll();
    }

    @Test
    void shouldReturnTerminById() {
        Termin termin = new Termin();
        termin.setId(1L);
        when(terminRepository.findById(1L)).thenReturn(Optional.of(termin));

        Optional<Termin> result = terminService.getTerminById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        verify(terminRepository).findById(1L);
    }

    @Test
    void shouldReturnTerminyByGrupaId() {
        List<Termin> terminy = List.of(new Termin(), new Termin());

        when(terminRepository.findByGrupaId(1L)).thenReturn(terminy);

        List<Termin> result = terminService.getTerminByGrupaId(1L);

        assertThat(result).hasSize(2);
        verify(terminRepository).findByGrupaId(1L);
    }

    @Test
    void shouldReturnEmptyListIfGrupaNotFound() {
        when(terminRepository.findByGrupaId(99L)).thenReturn(List.of());

        List<Termin> result = terminService.getTerminByGrupaId(99L);

        assertThat(result).isEmpty();
        verify(terminRepository).findByGrupaId(99L);
    }

    @Test
    void shouldSaveTermin() {
        Termin termin = new Termin();
        when(terminRepository.save(termin)).thenReturn(termin);

        Termin result = terminService.saveTermin(termin);

        assertThat(result).isEqualTo(termin);
        verify(terminRepository).save(termin);
    }

    @Test
    void shouldDeleteTermin() {
        terminService.deleteTermin(1L);
        verify(terminRepository).deleteById(1L);
    }

    @Test
    void shouldAssignTerminToGrupa() {
        Termin termin = new Termin();
        Grupa grupa = new Grupa();
        when(terminRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Termin result = terminService.assignTerminToGrupa(termin, grupa);

        assertThat(result.getGrupa()).isEqualTo(grupa);
        verify(terminRepository).save(termin);
    }

    @Test
    void shouldDeleteTerminAndObecnosci() {
        Long terminId = 3L;

        doNothing().when(obecnoscRepository).deleteByTerminId(terminId);
        doNothing().when(terminRepository).deleteById(terminId);

        terminService.deleteTermin(terminId);

        verify(obecnoscRepository).deleteByTerminId(terminId);
        verify(terminRepository).deleteById(terminId);
    }

}
