package org.example.nauczycieldesktopapp.controller.listsViews;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.nauczycieldesktopapp.model.Obecnosc;
import org.example.nauczycieldesktopapp.model.Termin;
import org.example.nauczycieldesktopapp.service.ObecnoscService;
import org.example.nauczycieldesktopapp.service.TerminService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenObecnosci {

    @FXML public TableView<Obecnosc> studentTable;
    @FXML public TableColumn<Obecnosc, String> imieColumn;
    @FXML public TableColumn<Obecnosc, String> nazwiskoColumn;
    @FXML public TableColumn<Obecnosc, String> nrIndeksuColumn;
    @FXML public TableColumn<Obecnosc, String> statusColumn;

    @FXML public ObservableList<Obecnosc> obecnoscObservableList = FXCollections.observableArrayList();

    private static ObecnoscService obecnosciService = new ObecnoscService();
    private static TerminService terminService = new TerminService();

    private List<Obecnosc> obecnosci;

    private Termin termin;

    public void setObecnosci() throws IOException {
        this.obecnosci = obecnosciService.getObecnosciByTermin(termin);
        obecnoscObservableList.setAll(obecnosci);
        studentTable.setItems(obecnoscObservableList);
    }

    public void setTermin(Termin termin) {
        this.termin = termin;
    }

    @FXML
    public void initialize() throws IOException {

        imieColumn.setCellValueFactory(cellData -> cellData.getValue().getStudent().imieProperty());
        nazwiskoColumn.setCellValueFactory(cellData -> cellData.getValue().getStudent().nazwiskoProperty());
        nrIndeksuColumn.setCellValueFactory(cellData -> cellData.getValue().getStudent().indeksProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

    }

}
