package org.example.nauczycieldesktopapp.controller.listsViews;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.nauczycieldesktopapp.model.Obecnosc;
import org.example.nauczycieldesktopapp.model.Termin;
import org.example.nauczycieldesktopapp.service.ObecnoscService;
import org.example.nauczycieldesktopapp.service.TerminService;

import java.io.IOException;
import java.util.List;

/**
 * Kontroler widoku generowania obecności dla danego terminu.
 * Wyświetla listę obecności studentów w formie tabeli, bazując na danych pobranych
 * z serwisu {@link ObecnoscService}.
 *
 * @version 1.0
 */
public class GenObecnosci {

    /**
     * Tabela wyświetlająca dane o obecnościach studentów.
     */
    @FXML public TableView<Obecnosc> studentTable;

    /**
     * Kolumna tabeli odpowiadająca za imię studenta.
     */
    @FXML public TableColumn<Obecnosc, String> imieColumn;

    /**
     * Kolumna tabeli odpowiadająca za nazwisko studenta.
     */
    @FXML public TableColumn<Obecnosc, String> nazwiskoColumn;

    /**
     * Kolumna tabeli odpowiadająca za numer indeksu studenta.
     */
    @FXML public TableColumn<Obecnosc, String> nrIndeksuColumn;

    /**
     * Kolumna tabeli odpowiadająca za status obecności.
     */
    @FXML public TableColumn<Obecnosc, String> statusColumn;

    /**
     * Obserwowalna lista obecności, przypisywana do tabeli.
     */
    @FXML public ObservableList<Obecnosc> obecnoscObservableList = FXCollections.observableArrayList();

    /**
     * Serwis obsługujący operacje związane z obecnościami.
     */
    private static final ObecnoscService obecnosciService = new ObecnoscService();

    /**
     * Serwis obsługujący operacje związane z terminami.
     */
    private static final TerminService terminService = new TerminService();

    /**
     * Lista obecności studentów dla danego terminu.
     */
    private List<Obecnosc> obecnosci;

    /**
     * Termin, dla którego wyświetlana jest lista obecności.
     */
    private Termin termin;

    /**
     * Ustawia termin i pobiera związane z nim dane obecności.
     *
     * @throws IOException w przypadku błędu podczas pobierania danych z serwera
     */
    public void setObecnosci() throws IOException {
        this.obecnosci = obecnosciService.getObecnosciByTermin(termin);
        obecnoscObservableList.setAll(obecnosci);
        studentTable.setItems(obecnoscObservableList);
    }

    /**
     * Ustawia termin, na podstawie którego generowana będzie lista obecności.
     *
     * @param termin obiekt terminu
     */
    public void setTermin(Termin termin) {
        this.termin = termin;
    }

    /**
     * Inicjalizuje kolumny tabeli, przypisując im odpowiednie właściwości z modelu {@link Obecnosc}.
     *
     * @throws IOException w przypadku błędów inicjalizacji związanych z danymi
     */
    @FXML
    public void initialize() throws IOException {
        imieColumn.setCellValueFactory(cellData -> cellData.getValue().getStudent().imieProperty());
        nazwiskoColumn.setCellValueFactory(cellData -> cellData.getValue().getStudent().nazwiskoProperty());
        nrIndeksuColumn.setCellValueFactory(cellData -> cellData.getValue().getStudent().indeksProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
    }
}
