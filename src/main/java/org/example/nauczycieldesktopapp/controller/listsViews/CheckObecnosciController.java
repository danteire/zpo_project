package org.example.nauczycieldesktopapp.controller.listsViews;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.example.nauczycieldesktopapp.controller.MainMenuController;
import org.example.nauczycieldesktopapp.model.Termin;
import org.example.nauczycieldesktopapp.service.TerminService;
import org.example.nauczycieldesktopapp.view.CheckObecnosciView;

import java.io.IOException;
import java.util.List;

/**
 * Kontroler obsługujący widok sprawdzania obecności dla poszczególnych terminów.
 * Wyświetla wszystkie dostępne terminy wraz z możliwością wygenerowania listy obecności.
 *
 * @version 1.0
 */
public class CheckObecnosciController extends MainMenuController {

    /**
     * Tabela z terminami do sprawdzenia obecności.
     */
    @FXML public TableView<Termin> obecnosciTable;

    /**
     * Kolumna ID terminu.
     */
    @FXML public TableColumn<Termin, String> idColumn;

    /**
     * Kolumna daty terminu.
     */
    @FXML public TableColumn<Termin, String> dateColumn;

    /**
     * Kolumna nazwy grupy przypisanej do terminu.
     */
    @FXML public TableColumn<Termin, String> nameColumn;

    /**
     * Kolumna z przyciskiem generującym listę obecności.
     */
    @FXML public TableColumn<Termin, Void> genBtnColumn;

    /**
     * Obserwowalna lista terminów przypisana do widoku.
     */
    @FXML public ObservableList<Termin> terminObservableList = FXCollections.observableArrayList();

    private static final TerminService terminService = new TerminService();

    /**
     * Pobiera wszystkie terminy z serwera i ustawia je w tabeli.
     *
     * @throws IOException jeśli wystąpi błąd podczas pobierania danych z serwera
     */
    public void setTermin() throws IOException {
        List<Termin> terminy = terminService.getAllTermins();
        terminObservableList.setAll(terminy);
        obecnosciTable.setItems(terminObservableList);
    }

    /**
     * Inicjalizuje widok i ustawia kolumny tabeli.
     *
     * @throws IOException jeśli wystąpi błąd podczas inicjalizacji
     */
    @FXML
    public void initialize() throws IOException {
        idColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getId()).asString());

        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dataProperty());

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getGrupa().nazwaProperty());

        genObecnoscListBtn();
    }

    /**
     * Ustawia w kolumnie przycisk "Generuj", który uruchamia widok z listą obecności
     * dla wybranego terminu.
     */
    private void genObecnoscListBtn() {
        Callback<TableColumn<Termin, Void>, TableCell<Termin, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btn = new Button("Generuj");

            {
                btn.setOnAction(event -> {
                    Termin termin = getTableView().getItems().get(getIndex());
                    try {
                        CheckObecnosciView.launchSubList(termin);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        };
        genBtnColumn.setCellFactory(cellFactory);
    }
}
