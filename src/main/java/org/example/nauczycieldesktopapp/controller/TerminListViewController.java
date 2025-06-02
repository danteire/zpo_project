package org.example.nauczycieldesktopapp.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.nauczycieldesktopapp.model.Grupa;
import org.example.nauczycieldesktopapp.model.Student;
import org.example.nauczycieldesktopapp.model.Termin;
import org.example.nauczycieldesktopapp.service.TerminService;

import java.io.IOException;
import java.util.List;

public class TerminListViewController {

    @FXML public TableView<Termin> terminsTable;

    @FXML public TableColumn<Termin, String> idColumn;
    @FXML public TableColumn<Termin, String> dateColumn;
    @FXML public TableColumn<Termin, Void> checkColumn;

    private final TerminService terminService = new TerminService();

    public void setTerminy(Grupa grupa) throws IOException {
        List<Termin> terminy = terminService.getTerminByGrupa(grupa.getId());

        ObservableList<Termin> lista = FXCollections.observableArrayList(terminy);
        terminsTable.setItems(lista);
    }

    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()).asString());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dataProperty());

        checkObecnoscButtonTable();
    }

    private void checkObecnoscButtonTable() {
        Callback<TableColumn<Termin, Void>, TableCell<Termin, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btn = new Button("Usuń z grupy");

            {
                btn.setOnAction(event -> {
                    Termin termin = getTableView().getItems().get(getIndex());
                    boolean success = false;
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
        checkColumn.setCellFactory(cellFactory);
    }
}
