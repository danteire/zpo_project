package org.example.listaobecnosci.controller;

import org.example.listaobecnosci.Grupa;
import org.example.listaobecnosci.Termin;
import org.example.listaobecnosci.service.GrupaService;
import org.example.listaobecnosci.service.TerminService;
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
class TerminControllerTest {

    @Mock
    private TerminService terminService;

    @Mock
    private GrupaService grupaService;

    @InjectMocks
    private TerminController terminController;

    @Test
    void shouldReturnAllTerminy() {
        Termin t1 = new Termin();
        Termin t2 = new Termin();
        when(terminService.getAllTerminy()).thenReturn(List.of(t1, t2));

        List<Termin> result = terminController.getAllTerminy();

        assertEquals(2, result.size());
        verify(terminService).getAllTerminy();
    }

    @Test
    void shouldReturnTerminById() {
        Termin termin = new Termin();
        termin.setId(1L);
        when(terminService.getTerminById(1L)).thenReturn(Optional.of(termin));

        ResponseEntity<Termin> response = terminController.getTerminById(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
        verify(terminService).getTerminById(1L);
    }

    @Test
    void shouldReturnNotFoundIfTerminNotFound() {
        when(terminService.getTerminById(999L)).thenReturn(Optional.empty());

        ResponseEntity<Termin> response = terminController.getTerminById(999L);

        assertTrue(response.getStatusCode().is4xxClientError());
        verify(terminService).getTerminById(999L);
    }

    @Test
    void shouldReturnTerminyByGrupa() {
        Grupa grupa = new Grupa();
        grupa.setId(1L);
        Termin termin = new Termin();
        when(grupaService.getGrupaById(1L)).thenReturn(Optional.of(grupa));
        when(terminService.getTerminByGrupaId(1L)).thenReturn(List.of(termin));

        ResponseEntity<List<Termin>> response = terminController.getTerminyByGrupa(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        verify(grupaService).getGrupaById(1L);
        verify(terminService).getTerminByGrupaId(1L);
    }

    @Test
    void shouldReturnNotFoundIfGrupaNotFoundWhenGettingTerminy() {
        when(grupaService.getGrupaById(999L)).thenReturn(Optional.empty());

        ResponseEntity<List<Termin>> response = terminController.getTerminyByGrupa(999L);

        assertTrue(response.getStatusCode().is4xxClientError());
        verify(grupaService).getGrupaById(999L);
        verify(terminService, never()).getTerminByGrupaId(anyLong());
    }

    @Test
    void shouldAddTerminSuccessfully() {
        Grupa grupa = new Grupa();
        grupa.setId(1L);
        Termin terminToSave = new Termin();
        terminToSave.setGrupa(grupa);

        Termin savedTermin = new Termin();
        savedTermin.setId(10L);
        savedTermin.setGrupa(grupa);

        when(grupaService.getGrupaById(1L)).thenReturn(Optional.of(grupa));
        when(terminService.saveTermin(any(Termin.class))).thenReturn(savedTermin);

        ResponseEntity<Termin> response = terminController.addTermin(terminToSave);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals(10L, response.getBody().getId());
        verify(grupaService).getGrupaById(1L);
        verify(terminService).saveTermin(any(Termin.class));
    }

    @Test
    void shouldReturnBadRequestIfTerminHasNoGrupa() {
        Termin termin = new Termin(); // grupa == null

        ResponseEntity<Termin> response = terminController.addTermin(termin);

        assertTrue(response.getStatusCode().is4xxClientError());
        verify(grupaService, never()).getGrupaById(anyLong());
        verify(terminService, never()).saveTermin(any());
    }

    @Test
    void shouldReturnBadRequestIfGrupaIdIsNullInTermin() {
        Termin termin = new Termin();
        termin.setGrupa(new Grupa()); // grupa.id == null

        ResponseEntity<Termin> response = terminController.addTermin(termin);

        assertTrue(response.getStatusCode().is4xxClientError());
        verify(grupaService, never()).getGrupaById(anyLong());
        verify(terminService, never()).saveTermin(any());
    }

    @Test
    void shouldDeleteTermin() {
        doNothing().when(terminService).deleteTermin(1L);

        ResponseEntity<Void> response = terminController.deleteTermin(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        verify(terminService).deleteTermin(1L);
    }
}
