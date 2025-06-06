package org.example.nauczycieldesktopapp.controller.dodawanie;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.nauczycieldesktopapp.controller.MainMenuController;
import org.example.nauczycieldesktopapp.model.Student;
import org.example.nauczycieldesktopapp.service.StudentService;

import java.io.IOException;

/**
 * Kontroler odpowiedzialny za obsługę widoku dodawania nowego studenta.
 * Dziedziczy po {@link MainMenuController} i obsługuje logikę związaną z tworzeniem obiektów {@link Student}
 * oraz komunikacją z serwisem {@link StudentService}.
 *
 * @version 1.0
 */
public class DodawanieStudentaController extends MainMenuController {

    /**
     * Pole tekstowe do wprowadzania imienia studenta.
     */
    @FXML private TextField imieField;

    /**
     * Pole tekstowe do wprowadzania nazwiska studenta.
     */
    @FXML private TextField nazwiskoField;

    /**
     * Pole tekstowe do wprowadzania numeru indeksu studenta.
     */
    @FXML private TextField indeksField;

    /**
     * Serwis odpowiedzialny za operacje na studentach.
     */
    private final StudentService studentService = new StudentService();

    /**
     * Obsługuje zdarzenie dodania nowego studenta.
     * Sprawdza, czy wszystkie pola są wypełnione, a następnie tworzy obiekt {@link Student}
     * i przekazuje go do serwisu w celu zapisania.
     * Wyświetla odpowiedni komunikat w zależności od powodzenia operacji.
     *
     * @param event zdarzenie kliknięcia przycisku
     */
    @FXML
    public void handleDodawanieStudenta(ActionEvent event) {
        String imie = imieField.getText();
        String nazwisko = nazwiskoField.getText();
        String indeks = indeksField.getText();

        if (imie.isBlank() || indeks.isBlank() || nazwisko.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Uzupełnij WSZYSTKIE pola!");
            alert.show();
            return;
        }

        Student student = new Student(imie, nazwisko, indeks);

        try {
            boolean success = studentService.addStudent(student);
            if (success) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Dodano studenta o indeksie: \n" + indeks + " do listy studentów");
                alert.setTitle("Komunikat");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Niepoprawne dane!");
                alert.setTitle("Błąd");
                alert.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Błąd podczas wysyłania danych").show();
        }
    }
}
