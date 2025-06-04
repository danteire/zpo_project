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
import org.example.nauczycieldesktopapp.model.Grupa;
import org.example.nauczycieldesktopapp.model.Obecnosc;
import org.example.nauczycieldesktopapp.model.Termin;
import org.example.nauczycieldesktopapp.service.GrupaService;
import org.example.nauczycieldesktopapp.service.ObecnoscService;
import org.example.nauczycieldesktopapp.service.TerminService;
import org.example.nauczycieldesktopapp.view.SprawdzObecnosciView;
import org.example.nauczycieldesktopapp.view.zarzadzanie.ZarzadzanieGrupamiView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SprawdzObecnosciController extends MainMenuController {


    @FXML public TableView<Termin> obecnosciTable;

    @FXML public TableColumn<Termin, String> idColumn;
    @FXML public TableColumn<Termin, String> dateColumn;
    @FXML public TableColumn<Termin, String> nameColumn;
    @FXML public TableColumn<Termin, Void> genBtnColumn;

    @FXML public ObservableList<Termin> terminObservableList = FXCollections.observableArrayList();

    private static ObecnoscService obecnosciService = new ObecnoscService();
    private static TerminService terminService = new TerminService();

    private List<Termin> terminy;

    public void setTermin() throws IOException {
        this.terminy = terminService.getAllTermins();

        terminObservableList.setAll(terminy);
        obecnosciTable.setItems(terminObservableList);

    }




    @FXML
    public void initialize() throws IOException {

        idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()).asString());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dataProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getGrupa().nazwaProperty());

        genObecnoscListBtn();

    }

    private void genObecnoscListBtn() {
        Callback<TableColumn<Termin, Void>, TableCell<Termin, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btn = new Button("Generuj");

            {
                btn.setOnAction(event -> {

                    Termin termin = getTableView().getItems().get(getIndex());

                    try {
                        SprawdzObecnosciView.launchSubList(termin);
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
