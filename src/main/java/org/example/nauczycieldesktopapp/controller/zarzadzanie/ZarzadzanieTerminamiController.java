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

import static javafx.application.Application.launch;

public class ZarzadzanieTerminamiController extends MainMenuController {

    @FXML public TableView<Grupa> groupTable;

    @FXML public TableColumn<Grupa, String> idColumn;
    @FXML public TableColumn<Grupa, String> nameColumn;

    @FXML public TableColumn<Grupa, Void> listaBtnColumn;
    @FXML public TableColumn<Grupa, Void> deleteColumn;
    @FXML public TableColumn<Grupa, Void> addColumn;

    private final GrupaService grupaService = new GrupaService();
    private final TerminService terminService = new TerminService();

    private final ObservableList<Grupa> grupaObservableList = FXCollections.observableArrayList();



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
        }
    }

    private void addButtonTable() {
        Callback<TableColumn<Grupa, Void>, TableCell<Grupa, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btn = new Button("Dodaj");

            {
                btn.setOnAction(event -> {
                    Grupa grupa = getTableView().getItems().get(getIndex());

                    Termin termin = new Termin();
                    termin.setGrupa(grupa);
                    try {
                        //TODO: utworzyć okienko
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
