package org.example.nauczycieldesktopapp.controller.zarzadzanie;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Stage;
import org.example.nauczycieldesktopapp.controller.MainMenuController;
import org.example.nauczycieldesktopapp.model.Obecnosc;
import org.example.nauczycieldesktopapp.model.Status;
import org.example.nauczycieldesktopapp.model.Student;
import org.example.nauczycieldesktopapp.model.Termin;
import org.example.nauczycieldesktopapp.service.ObecnoscService;
import org.example.nauczycieldesktopapp.service.StudentService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Kontroler do zarządzania obecnościami studentów na danym terminie.
 * Pozwala ustawić status obecności (obecny, spóźniony, nieobecny) dla każdego studenta
 * oraz wysłać te dane do serwera.
 */
public class ManageObecnosciController extends MainMenuController {

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

    /**
     * Ustawia termin, dla którego zarządzamy obecnościami.
     * @param termin termin, którego obecności chcemy edytować
     */
    public void setTermin(Termin termin) {
        this.termin = termin;
    }

    /**
     * Pobiera listę studentów z grupy danego terminu i wypełnia tabelę obiektami StudentObecnoscRow.
     * @param termin termin, na podstawie którego pobieramy studentów
     * @throws IOException w przypadku błędu podczas pobierania studentów
     */
    public void setStudents(Termin termin) throws IOException {
        List<Student> students = studentService.getStudentsByGrupa(termin.getGrupa().getId());
        studenci.clear();
        for (Student s : students) {
            studenci.add(new StudentObecnoscRow(s));
        }
    }

    /**
     * Ustawia stage (okno), który będzie zamykany po zapisaniu obecności.
     * @param stage okno do zamknięcia po wysłaniu danych
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Reprezentacja pojedynczego wiersza w tabeli obecności.
     * Przechowuje referencję do studenta oraz trzy właściwości boolean odpowiadające statusowi obecności.
     * Gwarantuje, że w danym momencie tylko jeden status może być zaznaczony.
     */
    public static class StudentObecnoscRow {
        private final Student student;
        private final BooleanProperty obecny = new SimpleBooleanProperty(false);
        private final BooleanProperty spozniony = new SimpleBooleanProperty(false);
        private final BooleanProperty nieobecny = new SimpleBooleanProperty(false);

        public StudentObecnoscRow(Student student) {
            this.student = student;

            // Wykluczenie wielokrotnego zaznaczenia statusów
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

    /**
     * Inicjalizacja tabeli i kolumn - ustawienie nazw studentów oraz checkboxów.
     */
    @FXML
    public void initialize() {
        colStudentID.setCellValueFactory(data -> {
            Student student = data.getValue().getStudent();
            return new SimpleStringProperty(student.getImie() + " " + student.getNazwisko());
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

    /**
     * Akcja wywoływana po kliknięciu przycisku wysyłania obecności.
     * Kasuje stare obecności i wysyła nowe statusy obecności do serwera.
     * Zamknięcie okna po pomyślnym wysłaniu.
     *
     * @throws IOException w przypadku błędu komunikacji z serwerem
     */
    @FXML
    private void handleWyslij() throws IOException {
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
                // Jeżeli żaden status nie został zaznaczony, pomijamy
                continue;
            }

            Obecnosc ob = new Obecnosc();
            ob.setStudent(row.getStudent());
            ob.setStudentId(row.getStudent().getId());
            ob.setTerminID(termin.getId());
            ob.setStatus(status);

            obecnosci.add(ob);
        }

        // Wysyłamy obecności pojedynczo i obsługujemy ewentualne błędy
        for (Obecnosc obecnosc : obecnosci) {
            try {
                boolean success = obecnoscService.sendObecnosc(obecnosc);
                if (success) {
                    System.out.printf("Wysłano obecność: Student: %s %s, Termin: %d, Status: %s%n",
                            obecnosc.getStudent().getImie(),
                            obecnosc.getStudent().getNazwisko(),
                            obecnosc.getTerminID(),
                            obecnosc.getStatus());
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

        stage.close();
    }
}
