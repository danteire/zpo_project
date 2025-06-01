package org.example.nauczycieldesktopapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.nauczycieldesktopapp.model.Grupa;
import org.example.nauczycieldesktopapp.service.GrupaService;

import java.io.IOException;

public class DodawanieGrupyController extends MainMenuController {

    @FXML private TextField nazwaField;

    private final GrupaService grupaService = new GrupaService();

    @FXML
    public void handleDodawanieGrupy(ActionEvent event) {
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
