package org.example.listaobecnosci.controller;

import org.example.listaobecnosci.Grupa;
import org.example.listaobecnosci.service.GrupaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GrupaControllerTest {

    @Mock
    private GrupaService grupaService;

    @InjectMocks
    private GrupaController grupaController;

    @Test
    void shouldReturnAllGrupy() {
        Grupa g1 = new Grupa();
        Grupa g2 = new Grupa();
        when(grupaService.getAllGrupy()).thenReturn(List.of(g1, g2));

        List<Grupa> result = grupaController.getAllGrupy();

        assertEquals(2, result.size());
        verify(grupaService).getAllGrupy();
    }

    @Test
    void shouldReturnGrupaById() {
        Grupa grupa = new Grupa();
        grupa.setId(1L);
        when(grupaService.getGrupaById(1L)).thenReturn(Optional.of(grupa));

        ResponseEntity<Grupa> response = grupaController.getGrupaById(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
        verify(grupaService).getGrupaById(1L);
    }

    @Test
    void shouldReturnNotFoundWhenGrupaDoesNotExist() {
        when(grupaService.getGrupaById(999L)).thenReturn(Optional.empty());

        ResponseEntity<Grupa> response = grupaController.getGrupaById(999L);

        assertTrue(response.getStatusCode().is4xxClientError());
        verify(grupaService).getGrupaById(999L);
    }

    @Test
    void shouldAddGrupa() {
        Grupa grupaToSave = new Grupa();
        grupaToSave.setNazwa("Test");

        Grupa savedGrupa = new Grupa();
        savedGrupa.setId(1L);
        savedGrupa.setNazwa("Test");

        when(grupaService.saveGrupa(grupaToSave)).thenReturn(savedGrupa);

        Grupa result = grupaController.addGrupa(grupaToSave);

        assertNotNull(result.getId());
        assertEquals("Test", result.getNazwa());
        verify(grupaService).saveGrupa(grupaToSave);
    }

    @Test
    void shouldDeleteGrupa() {
        doNothing().when(grupaService).deleteGrupa(1L);

        ResponseEntity<Void> response = grupaController.deleteGrupa(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        verify(grupaService).deleteGrupa(1L);
    }
}
