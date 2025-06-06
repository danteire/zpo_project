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
import org.example.nauczycieldesktopapp.model.Grupa;
import org.example.nauczycieldesktopapp.model.Termin;
import org.example.nauczycieldesktopapp.service.TerminService;
import org.example.nauczycieldesktopapp.view.zarzadzanie.ManageObecnosciView;

import java.io.IOException;
import java.util.List;

/**
 * Kontroler widoku listy terminów przypisanych do danej grupy.
 * Umożliwia wyświetlanie, usuwanie oraz zarządzanie obecnościami dla terminów.
 *
 * @version 1.0
 */
public class TerminListViewController {

    @FXML public TableView<Termin> terminsTable;
    @FXML public TableColumn<Termin, String> idColumn;
    @FXML public TableColumn<Termin, String> dateColumn;
    @FXML public TableColumn<Termin, Void> deleteColumn;
    @FXML public TableColumn<Termin, Void> checkColumn;

    private final TerminService terminService = new TerminService();

    /**
     * Ustawia i wyświetla wszystkie terminy należące do podanej grupy.
     *
     * @param grupa grupa, dla której mają zostać wyświetlone terminy
     * @throws IOException w przypadku błędu podczas pobierania danych z serwera
     */
    public void setTerminy(Grupa grupa) throws IOException {
        List<Termin> terminy = terminService.getTerminByGrupa(grupa.getId());
        ObservableList<Termin> lista = FXCollections.observableArrayList(terminy);
        terminsTable.setItems(lista);
    }

    /**
     * Inicjalizuje kolumny tabeli oraz dodaje przyciski do usuwania i zarządzania obecnością.
     */
    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()).asString());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dataProperty());

        checkTerminButton();
        usunTerminButton();
    }

    /**
     * Tworzy kolumnę z przyciskiem "Usuń Termin", który pozwala usuwać terminy z tabeli i serwera.
     */
    private void usunTerminButton() {
        Callback<TableColumn<Termin, Void>, TableCell<Termin, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btn = new Button("Usuń Termin");

            {
                btn.setOnAction(event -> {
                    Termin termin = getTableView().getItems().get(getIndex());
                    boolean success;
                    try {
                        success = terminService.deleteTermin(termin);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (success) {
                        if (getIndex() >= 0 && getIndex() < getTableView().getItems().size()) {
                            getTableView().getItems().remove(getIndex());
                        }
                        System.out.println("Usunięto termin dla grupy");
                    } else {
                        System.err.println("Błąd usuwania");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        };
        deleteColumn.setCellFactory(cellFactory);
    }

    /**
     * Tworzy kolumnę z przyciskiem "Sprawdź Obecność", który otwiera widok zarządzania obecnościami dla danego terminu.
     */
    private void checkTerminButton() {
        Callback<TableColumn<Termin, Void>, TableCell<Termin, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btn = new Button("Sprawdź Obecność");

            {
                btn.setOnAction(event -> {
                    Termin termin = getTableView().getItems().get(getIndex());
                    try {
                        ManageObecnosciView.launch(termin);
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
        checkColumn.setCellFactory(cellFactory);
    }
}
