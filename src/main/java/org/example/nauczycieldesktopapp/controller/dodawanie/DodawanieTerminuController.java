package org.example.nauczycieldesktopapp.controller.dodawanie;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import org.example.nauczycieldesktopapp.controller.MainMenuController;
import org.example.nauczycieldesktopapp.model.Termin;

import org.example.nauczycieldesktopapp.service.TerminService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DodawanieTerminuController extends MainMenuController {

    private final TerminService terminService = new TerminService();
    @FXML public DatePicker datePicker;

    @FXML

    Termin termin = new Termin();
    public void handleDodawanieTerminu(ActionEvent event) {

        LocalDate selectedDate = datePicker.getValue();

        if (selectedDate == null) {
            new Alert(Alert.AlertType.WARNING, "Podaj nazwę grupy!").show();
            return;
        }

        try {
            LocalDateTime fullDateTime = selectedDate.atTime(16, 0);
            termin.setData(fullDateTime);
            boolean success = terminService.addTermin(termin);
            if (success) {
                new Alert(Alert.AlertType.INFORMATION, "Dodano Termin: " + fullDateTime).show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Błąd podczas dodawania Terminu!").show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Błąd komunikacji z serwerem!").show();
        }
    }

    public void setTermin(Termin termin) {
        this.termin = termin;
    }
}
