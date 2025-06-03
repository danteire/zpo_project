package org.example.listaobecnosci.service;

import org.example.listaobecnosci.Grupa;
import org.example.listaobecnosci.repository.GrupaRepository;
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
class GrupaServiceTest {

    @Mock
    private GrupaRepository grupaRepository;

    @InjectMocks
    private GrupaService grupaService;

    @Test
    void shouldReturnAllGrupy() {
        List<Grupa> grupy = List.of(new Grupa(), new Grupa());
        when(grupaRepository.findAll()).thenReturn(grupy);

        List<Grupa> result = grupaService.getAllGrupy();

        assertThat(result).hasSize(2);
        verify(grupaRepository).findAll();
    }

    @Test
    void shouldReturnGrupaById() {
        Grupa grupa = new Grupa();
        grupa.setId(1L);
        when(grupaRepository.findById(1L)).thenReturn(Optional.of(grupa));

        Optional<Grupa> result = grupaService.getGrupaById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        verify(grupaRepository).findById(1L);
    }

    @Test
    void shouldSaveGrupa() {
        Grupa grupa = new Grupa();
        when(grupaRepository.save(grupa)).thenReturn(grupa);

        Grupa result = grupaService.saveGrupa(grupa);

        assertThat(result).isEqualTo(grupa);
        verify(grupaRepository).save(grupa);
    }

    @Test
    void shouldDeleteGrupa() {
        grupaService.deleteGrupa(1L);
        verify(grupaRepository).deleteById(1L);
    }
}
