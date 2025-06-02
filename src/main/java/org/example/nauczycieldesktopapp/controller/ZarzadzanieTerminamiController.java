package org.example.nauczycieldesktopapp.controller;

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
import org.example.nauczycieldesktopapp.service.GrupaService;
import org.example.nauczycieldesktopapp.service.TerminService;
import org.example.nauczycieldesktopapp.view.ZarzadzanieTerminamiView;

import java.io.IOException;
import java.util.List;

public class ZarzadzanieTerminamiController extends MainMenuController{

    @FXML public TableView<Grupa> groupTable;

    @FXML public TableColumn<Grupa, String> idColumn;
    @FXML public TableColumn<Grupa, String> nameColumn;

    @FXML public TableColumn<Grupa, Void> listaBtnColumn;
    @FXML public TableColumn<Grupa, Void> deleteColumn;


    private final GrupaService grupaService = new GrupaService();
    private final TerminService terminService = new TerminService();

    private final ObservableList<Grupa> grupaObservableList = FXCollections.observableArrayList();



    @FXML
    public void initialize() {

        idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()).asString());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nazwaProperty());

        terminListButtonTable();

        try {
            List<Grupa> groups = grupaService.getAllGroups();
            grupaObservableList.setAll(groups);
            groupTable.setItems(grupaObservableList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addButtonTable() {
        Callback<TableColumn<Grupa, Void>, TableCell<Grupa, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btn = new Button("Dodaj");

            {
                btn.setOnAction(event -> {
                    Grupa grupa = getTableView().getItems().get(getIndex());
                    boolean success = false;
                    try {
                        Termin termin = new Termin();
                        success = terminService.deleteTermin(termin);
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
