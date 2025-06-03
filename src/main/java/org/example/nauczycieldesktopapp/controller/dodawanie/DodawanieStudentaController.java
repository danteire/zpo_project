package org.example.nauczycieldesktopapp.controller.dodawanie;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.nauczycieldesktopapp.controller.MainMenuController;
import org.example.nauczycieldesktopapp.model.Student;
import org.example.nauczycieldesktopapp.service.StudentService;

import java.io.IOException;

public class DodawanieStudentaController extends MainMenuController {

    @FXML private TextField imieField;
    @FXML private TextField nazwiskoField;
    @FXML private TextField indeksField;

    private final StudentService studentService = new StudentService();

    @FXML
    public void handleDodawanieStudenta(ActionEvent event) {
        String imie = imieField.getText();
        String nazwisko = nazwiskoField.getText();
        String indeks = indeksField.getText();

        if(imie.isBlank() || indeks.isBlank() || nazwisko.isBlank()){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Uzupełnij WSZYSTKIE pola!");
            alert.show();
            return;
        }

        Student student = new Student(imie, nazwisko, indeks);

        try {
            boolean success = studentService.addStudent(student);
            if (success) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Dodano studenta o indeksie: \n" + indeks + " do listy studentów");
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



