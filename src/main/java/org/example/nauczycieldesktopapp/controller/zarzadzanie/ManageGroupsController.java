package org.example.nauczycieldesktopapp.controller.zarzadzanie;

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
import org.example.nauczycieldesktopapp.model.Grupa;
import org.example.nauczycieldesktopapp.service.GrupaService;
import org.example.nauczycieldesktopapp.view.zarzadzanie.ManageGroupsView;

import java.io.IOException;
import java.util.List;

/**
 * Kontroler odpowiedzialny za zarządzanie widokiem grup.
 * <p>
 * Udostępnia funkcjonalności takie jak wyświetlanie listy grup,
 * usuwanie grup oraz wyświetlanie listy studentów przypisanych do danej grupy.
 * Rozszerza {@link MainMenuController}.
 */
public class ManageGroupsController extends MainMenuController {

    /**
     * Tabela wyświetlająca grupy.
     */
    @FXML
    public TableView<Grupa> groupTable;

    /**
     * Kolumna wyświetlająca identyfikator grupy.
     */
    @FXML
    public TableColumn<Grupa, String> idColumn;

    /**
     * Kolumna wyświetlająca nazwę grupy.
     */
    @FXML
    public TableColumn<Grupa, String> nameColumn;

    /**
     * Kolumna z przyciskiem do wyświetlania studentów przypisanych do grupy.
     */
    @FXML
    public TableColumn<Grupa, Void> listaBtnColumn;

    /**
     * Kolumna z przyciskiem do usuwania grupy.
     */
    @FXML
    public TableColumn<Grupa, Void> deleteColumn;

    /**
     * Serwis odpowiedzialny za operacje na grupach.
     */
    private final GrupaService grupaService = new GrupaService();

    /**
     * Observable lista grup używana jako model danych dla tabeli.
     */
    private final ObservableList<Grupa> grupaObservableList = FXCollections.observableArrayList();

    /**
     * Metoda inicjalizująca kontroler.
     * Ustawia fabryki komórek dla kolumn, ładuje listę grup i ustawia je w tabeli.
     */
    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()).asString());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nazwaProperty());

        deleteButtonTable();
        studentListButtonTable();

        try {
            List<Grupa> groups = GrupaService.getAllGroups();
            grupaObservableList.setAll(groups);
            groupTable.setItems(grupaObservableList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tworzy i ustawia w kolumnie deleteColumn przycisk do usuwania grupy.
     * Po kliknięciu usuwa wybraną grupę z bazy i aktualizuje widok.
     */
    private void deleteButtonTable() {
        Callback<TableColumn<Grupa, Void>, TableCell<Grupa, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btn = new Button("Usuń");

            {
                btn.setOnAction(event -> {
                    Grupa grupa = getTableView().getItems().get(getIndex());
                    boolean success;
                    try {
                        success = grupaService.removeGrupa(grupa);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (success) {
                        grupaObservableList.remove(getIndex());
                        System.out.println("Usunięto grupę: " + grupa.getId());
                    } else {
                        System.err.println("Błąd usuwania grupy.");
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
     * Tworzy i ustawia w kolumnie listaBtnColumn przycisk do wyświetlania studentów przypisanych do grupy.
     * Po kliknięciu wywołuje nowy widok z listą studentów danej grupy.
     */
    private void studentListButtonTable() {
        Callback<TableColumn<Grupa, Void>, TableCell<Grupa, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btn = new Button("Pokaż studentów");

            {
                btn.setOnAction(event -> {

                    Grupa grupa = getTableView().getItems().get(getIndex());

                    try {
                        ManageGroupsView.launchSubList(grupa);
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

        listaBtnColumn.setCellFactory(cellFactory);
    }
}
