package org.example.nauczycieldesktopapp.controller.dodawanie;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.nauczycieldesktopapp.controller.MainMenuController;
import org.example.nauczycieldesktopapp.model.Grupa;
import org.example.nauczycieldesktopapp.service.GrupaService;

import java.io.IOException;

/**
 * Kontroler odpowiedzialny za obsługę widoku dodawania nowej grupy.
 * Dziedziczy po {@link MainMenuController} i obsługuje logikę związaną z tworzeniem obiektów {@link Grupa}
 * oraz komunikacją z serwisem {@link GrupaService}.
 *
 * @version 1.0
 */
public class AddGroupController extends MainMenuController {

    /**
     * Pole tekstowe w interfejsie użytkownika służące do wprowadzania nazwy grupy.
     */
    @FXML private TextField nazwaField;

    /**
     * Serwis odpowiedzialny za operacje na grupach.
     */
    private final GrupaService grupaService = new GrupaService();

    /**
     * Obsługuje zdarzenie kliknięcia przycisku dodającego nową grupę.
     * Waliduje dane wejściowe, tworzy obiekt grupy i wywołuje serwis w celu zapisania danych.
     * Wyświetla odpowiedni komunikat w zależności od powodzenia operacji.
     *
     */
    @FXML
    public void handleDodawanieGrupy() {
        String nazwa = nazwaField.getText();

        if (nazwa.isBlank()) {
            new Alert(Alert.AlertType.WARNING, "Podaj nazwę grupy!").show();
            return;
        }

        Grupa grupa = new Grupa(nazwa);
        try {
            boolean success = grupaService.addGrupa(grupa);
            if (success) {
                new Alert(Alert.AlertType.INFORMATION, "Dodano grupę: " + nazwa).show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Błąd podczas dodawania grupy!").show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Błąd komunikacji z serwerem!").show();
        }
    }
}
