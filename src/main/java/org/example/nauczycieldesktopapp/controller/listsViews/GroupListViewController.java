package org.example.nauczycieldesktopapp.controller.listsViews;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.nauczycieldesktopapp.model.Grupa;
import org.example.nauczycieldesktopapp.service.GrupaService;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

/**
 * Kontroler obsługujący widok listy grup. Umożliwia wyświetlenie wszystkich grup
 * oraz wybór jednej z nich, np. do przypisania do innego bytu (student, termin itp.).
 *
 * @version 1.0
 */
public class GroupListViewController {

    /**
     * Tabela wyświetlająca grupy.
     */
    @FXML private TableView<Grupa> groupTable;

    /**
     * Kolumna z ID grupy.
     */
    @FXML private TableColumn<Grupa, String> idColumn;

    /**
     * Kolumna z nazwą grupy.
     */
    @FXML private TableColumn<Grupa, String> nazwaColumn;

    /**
     * Kolumna zawierająca przycisk do wyboru grupy.
     */
    @FXML private TableColumn<Grupa, Void> addColumn;

    /**
     * Lista wszystkich grup pobranych z serwisu.
     */
    private List<Grupa> grupy;

    /**
     * Callback wywoływany po wybraniu grupy.
     */
    public Consumer<Grupa> onGroupSelected;

    /**
     * Pobiera wszystkie grupy z serwisu i przypisuje je do tabeli.
     *
     * @throws IOException jeśli wystąpi błąd podczas komunikacji z serwerem
     */
    public void setGrupa() throws IOException {
        grupy = GrupaService.getAllGroups();
        ObservableList<Grupa> lista = FXCollections.observableArrayList(grupy);
        groupTable.setItems(lista);
    }

    /**
     * Ustawia callback, który zostanie wywołany po wybraniu grupy.
     *
     * @param callback funkcja wykonująca akcję na wybranej grupie
     */
    public void setOnGroupSelected(Consumer<Grupa> callback) {
        this.onGroupSelected = callback;
    }

    /**
     * Inicjalizuje kolumny tabeli oraz logikę przycisku "Wybierz".
     * Każdy wiersz tabeli zawiera przycisk, który zamyka okno i przekazuje wybraną grupę.
     */
    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getId()).asString());

        nazwaColumn.setCellValueFactory(cellData -> cellData.getValue().nazwaProperty());

        addColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Grupa, Void> call(TableColumn<Grupa, Void> param) {
                return new TableCell<>() {
                    private final Button selectBtn = new Button("Wybierz");

                    {
                        selectBtn.setOnAction(event -> {
                            Grupa selected = getTableView().getItems().get(getIndex());
                            if (onGroupSelected != null) {
                                onGroupSelected.accept(selected);
                            }
                            Stage stage = (Stage) getScene().getWindow();
                            stage.close();
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : selectBtn);
                    }
                };
            }
        });
    }
}
