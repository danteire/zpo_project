package org.example.nauczycieldesktopapp.controller.zarzadzanie;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Stage;
import org.example.nauczycieldesktopapp.controller.MainMenuController;
import org.example.nauczycieldesktopapp.model.*;
import org.example.nauczycieldesktopapp.service.ObecnoscService;
import org.example.nauczycieldesktopapp.service.StudentService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZarzadzanieObecnosciamiController extends MainMenuController {

    @FXML
    private TableView<StudentObecnoscRow> tabelaObecnosci;

    @FXML
    private TableColumn<StudentObecnoscRow, String> colStudentID;

    @FXML
    private TableColumn<StudentObecnoscRow, Boolean> colObecny;

    @FXML
    private TableColumn<StudentObecnoscRow, Boolean> colSpozniony;

    @FXML
    private TableColumn<StudentObecnoscRow, Boolean> colNieobecny;

    private final ObservableList<StudentObecnoscRow> studenci = FXCollections.observableArrayList();

    private final StudentService studentService = new StudentService();
    private final ObecnoscService obecnoscService = new ObecnoscService();

    private Termin termin;

    private Stage stage;

    public void setTermin(Termin termin) {
        this.termin = termin;
    }

    public void setStudents(Termin termin) throws IOException {
        List<Student> students = studentService.getStudentsByGrupa(termin.getGrupa().getId());
        studenci.clear();
        for (Student s : students) {
            studenci.add(new StudentObecnoscRow(s));
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static class StudentObecnoscRow {
        private final Student student;
        private final BooleanProperty obecny = new SimpleBooleanProperty(false);
        private final BooleanProperty spozniony = new SimpleBooleanProperty(false);
        private final BooleanProperty nieobecny = new SimpleBooleanProperty(false);

        public StudentObecnoscRow(Student student) {
            this.student = student;

            obecny.addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    spozniony.set(false);
                    nieobecny.set(false);
                }
            });
            spozniony.addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    obecny.set(false);
                    nieobecny.set(false);
                }
            });
            nieobecny.addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    obecny.set(false);
                    spozniony.set(false);
                }
            });
        }

        public Student getStudent() {
            return student;
        }

        public BooleanProperty obecnyProperty() {
            return obecny;
        }

        public BooleanProperty spoznionyProperty() {
            return spozniony;
        }

        public BooleanProperty nieobecnyProperty() {
            return nieobecny;
        }
    }

    @FXML
    public void initialize() {
        colStudentID.setCellValueFactory(data -> {
            Student student = data.getValue().getStudent();
            return new SimpleStringProperty(student.getImie() + " " + student.getNazwisko()); // lub student.getId()
        });
        colObecny.setCellValueFactory(data -> data.getValue().obecnyProperty());
        colSpozniony.setCellValueFactory(data -> data.getValue().spoznionyProperty());
        colNieobecny.setCellValueFactory(data -> data.getValue().nieobecnyProperty());

        colObecny.setCellFactory(CheckBoxTableCell.forTableColumn(colObecny));
        colSpozniony.setCellFactory(CheckBoxTableCell.forTableColumn(colSpozniony));
        colNieobecny.setCellFactory(CheckBoxTableCell.forTableColumn(colNieobecny));

        tabelaObecnosci.setEditable(true);
        colObecny.setEditable(true);
        colSpozniony.setEditable(true);
        colNieobecny.setEditable(true);

        tabelaObecnosci.setItems(studenci);
    }

    @FXML
    private void handleWyslij(ActionEvent event) throws IOException {

        if (termin == null) {
            System.err.println("Termin nie został ustawiony!");
            return;
        }

        obecnoscService.deleteObecnoscByTermin(termin);

        List<Obecnosc> obecnosci = new ArrayList<>();

        for (StudentObecnoscRow row : studenci) {
            Status status;
            if (row.obecnyProperty().get()) {
                status = Status.OBECNY;
            } else if (row.spoznionyProperty().get()) {
                status = Status.SPOZNIONY;
            } else if (row.nieobecnyProperty().get()) {
                status = Status.NIEOBECNY;
            } else {
                continue;
            }

            Obecnosc ob = new Obecnosc();
            ob.setStudent(row.getStudent());
            ob.setStudentId(row.getStudent().getId());
            ob.setTerminID(termin.getId());
            ob.setStatus(status);

            obecnosci.add(ob);
        }

        obecnosci.forEach(obecnosc ->
        {
            try {
                boolean success = obecnoscService.sendObecnosc(obecnosc);
                if (success) {
                    System.out.println("Wysłano obecność:");
                    System.out.printf("Student: %s %s, Termin: %d, Status: %s%n", obecnosc.getStudent().getImie(), obecnosc.getStudent().getNazwisko(), obecnosc.getTerminID(), obecnosc.getStatus());
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Niepoprawne dane!");
                    alert.setTitle("Błąd");
                    alert.show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Błąd podczas wysyłania danych").show();
            }
        });

        stage.close();

    }
}
