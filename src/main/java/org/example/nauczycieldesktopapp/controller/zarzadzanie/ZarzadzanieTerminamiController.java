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
import org.example.nauczycieldesktopapp.model.Termin;
import org.example.nauczycieldesktopapp.service.GrupaService;
import org.example.nauczycieldesktopapp.service.TerminService;
import org.example.nauczycieldesktopapp.view.dodawanie.DodawanieTerminuView;
import org.example.nauczycieldesktopapp.view.zarzadzanie.ZarzadzanieTerminamiView;

import java.io.IOException;
import java.util.List;

/**
 * Kontroler widoku zarządzania terminami.
 * Dziedziczy po MainMenuController, obsługuje tabelę grup oraz akcje dodawania i przeglądania terminów.
 */
public class ZarzadzanieTerminamiController extends MainMenuController {

    /** Tabela wyświetlająca listę grup. */
    @FXML
    private TableView<Grupa> groupTable;

    /** Kolumna z ID grupy (jako String). */
    @FXML
    private TableColumn<Grupa, String> idColumn;

    /** Kolumna z nazwą grupy. */
    @FXML
    private TableColumn<Grupa, String> nameColumn;

    /** Kolumna z przyciskiem do wyświetlania terminów danej grupy. */
    @FXML
    private TableColumn<Grupa, Void> listaBtnColumn;

    /** Kolumna z przyciskiem do usuwania grupy (jeśli nieużywana można usunąć). */
    @FXML
    private TableColumn<Grupa, Void> deleteColumn;

    /** Kolumna z przyciskiem do dodawania nowego terminu dla grupy. */
    @FXML
    private TableColumn<Grupa, Void> addColumn;

    /** Serwis do obsługi operacji na grupach. */
    private final GrupaService grupaService = new GrupaService();

    /** Serwis do obsługi operacji na terminach. */
    private final TerminService terminService = new TerminService();

    /** Lista obserwowalna grup, powiązana z tabelą. */
    private final ObservableList<Grupa> grupaObservableList = FXCollections.observableArrayList();

    /**
     * Inicjalizuje widok, konfigurując kolumny tabeli oraz przyciski,
     * a także ładuje listę grup z serwera.
     */
    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()).asString());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nazwaProperty());

        terminListButtonTable();
        addButtonTable();

        try {
            List<Grupa> groups = grupaService.getAllGroups();
            grupaObservableList.setAll(groups);
            groupTable.setItems(grupaObservableList);
        } catch (IOException e) {
            e.printStackTrace();
            // Tutaj można dodać Alert informujący użytkownika o błędzie ładowania danych
        }
    }

    /**
     * Ustawia kolumnę z przyciskiem "Dodaj termin" dla każdej grupy.
     * Po kliknięciu przycisku otwiera widok dodawania nowego terminu przypisanego do danej grupy.
     */
    private void addButtonTable() {
        Callback<TableColumn<Grupa, Void>, TableCell<Grupa, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btn = new Button("Dodaj termin");

            {
                btn.setOnAction(event -> {
                    Grupa grupa = getTableView().getItems().get(getIndex());
                    Termin termin = new Termin();
                    termin.setGrupa(grupa);

                    try {
                        DodawanieTerminuView.launch(termin);
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
        addColumn.setCellFactory(cellFactory);
    }

    /**
     * Ustawia kolumnę z przyciskiem "Pokaż Terminy" dla każdej grupy.
     * Po kliknięciu przycisku otwiera widok zarządzania terminami dla danej grupy.
     */
    private void terminListButtonTable() {
        Callback<TableColumn<Grupa, Void>, TableCell<Grupa, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btn = new Button("Pokaż Terminy");

            {
                btn.setOnAction(event -> {
                    Grupa grupa = getTableView().getItems().get(getIndex());

                    try {
                        ZarzadzanieTerminamiView.launchSubList(grupa);
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
