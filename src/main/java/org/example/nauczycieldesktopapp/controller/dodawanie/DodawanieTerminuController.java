package org.example.nauczycieldesktopapp.controller.dodawanie;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
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
    @FXML public ComboBox<Integer> hourBox;
    @FXML public ComboBox<Integer> minuteBox;


    @FXML Termin termin = new Termin();

    @FXML
    public void initialize() {
        for (int i = 0; i < 24; i++) hourBox.getItems().add(i);
        for (int i = 0; i < 60; i += 5) minuteBox.getItems().add(i);

        hourBox.setValue(16);
        minuteBox.setValue(0);
    }

    public void handleDodawanieTerminu(ActionEvent event) {

        LocalDate selectedDate = datePicker.getValue();
        Integer hour = hourBox.getValue();
        Integer minute = minuteBox.getValue();

        if (selectedDate == null || hour == null || minute == null) {
            new Alert(Alert.AlertType.WARNING, "Uzupełnij datę i godzinę!").show();
            return;
        }

        try {
            LocalDateTime fullDateTime = selectedDate.atTime(hour, minute);
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
