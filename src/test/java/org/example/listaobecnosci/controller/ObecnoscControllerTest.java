package org.example.listaobecnosci.controller;

import org.example.listaobecnosci.Obecnosc;
import org.example.listaobecnosci.statusEnum;
import org.example.listaobecnosci.service.ObecnoscService;
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
class ObecnoscControllerTest {

    @Mock
    private ObecnoscService obecnoscService;

    @InjectMocks
    private ObecnoscController obecnoscController;

    @Test
    void shouldReturnAllObecnosci() {
        Obecnosc o1 = new Obecnosc();
        Obecnosc o2 = new Obecnosc();
        when(obecnoscService.getAllObecnosci()).thenReturn(List.of(o1, o2));

        List<Obecnosc> result = obecnoscController.getAllObecnosci();

        assertEquals(2, result.size());
        verify(obecnoscService).getAllObecnosci();
    }

    @Test
    void shouldReturnObecnoscById() {
        Obecnosc o = new Obecnosc();
        o.setId(1L);
        when(obecnoscService.getObecnoscById(1L)).thenReturn(Optional.of(o));

        ResponseEntity<Obecnosc> response = obecnoscController.getObecnoscById(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
        verify(obecnoscService).getObecnoscById(1L);
    }

    @Test
    void shouldReturnNotFoundWhenObecnoscNotFound() {
        when(obecnoscService.getObecnoscById(999L)).thenReturn(Optional.empty());

        ResponseEntity<Obecnosc> response = obecnoscController.getObecnoscById(999L);

        assertTrue(response.getStatusCode().is4xxClientError());
        verify(obecnoscService).getObecnoscById(999L);
    }

    @Test
    void shouldSaveOrUpdateObecnosc() {
        Obecnosc o = new Obecnosc();
        o.setId(1L);
        o.setStatus(statusEnum.OBECNY);

        when(obecnoscService.saveOrUpdateObecnosc(o)).thenReturn(o);

        Obecnosc result = obecnoscController.saveOrUpdateObecnosc(o);

        assertEquals(1L, result.getId());
        assertEquals(statusEnum.OBECNY, result.getStatus());
        verify(obecnoscService).saveOrUpdateObecnosc(o);
    }

    @Test
    void shouldDeleteObecnosc() {
        doNothing().when(obecnoscService).deleteObecnosc(1L);

        ResponseEntity<Void> response = obecnoscController.deleteObecnosc(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        verify(obecnoscService).deleteObecnosc(1L);
    }

    @Test
    void shouldReturnObecnosciByStudent() {
        Obecnosc o = new Obecnosc();
        when(obecnoscService.getByStudentId(1L)).thenReturn(List.of(o));

        List<Obecnosc> result = obecnoscController.getObecnosciByStudent(1L);

        assertEquals(1, result.size());
        verify(obecnoscService).getByStudentId(1L);
    }

    @Test
    void shouldReturnObecnosciByTermin() {
        Obecnosc o = new Obecnosc();
        when(obecnoscService.getByTerminId(2L)).thenReturn(List.of(o));

        List<Obecnosc> result = obecnoscController.getObecnosciByTermin(2L);

        assertEquals(1, result.size());
        verify(obecnoscService).getByTerminId(2L);
    }

    @Test
    void shouldReturnObecnosciByStatus() {
        Obecnosc o = new Obecnosc();
        o.setStatus(statusEnum.OBECNY);
        when(obecnoscService.getByStatus(statusEnum.OBECNY)).thenReturn(List.of(o));

        List<Obecnosc> result = obecnoscController.getObecnosciByStatus(statusEnum.OBECNY);

        assertEquals(1, result.size());
        assertEquals(statusEnum.OBECNY, result.getFirst().getStatus());
        verify(obecnoscService).getByStatus(statusEnum.OBECNY);
    }

    @Test
    void shouldReturnObecnosciByStudentAndTermin() {
        Obecnosc o = new Obecnosc();
        when(obecnoscService.getByStudentIdAndTerminId(1L, 2L)).thenReturn(List.of(o));

        List<Obecnosc> result = obecnoscController.getObecnosciByStudentAndTermin(1L, 2L);

        assertEquals(1, result.size());
        verify(obecnoscService).getByStudentIdAndTerminId(1L, 2L);
    }
}
