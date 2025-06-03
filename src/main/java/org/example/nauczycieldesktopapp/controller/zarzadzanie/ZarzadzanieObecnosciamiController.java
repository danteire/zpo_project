package org.example.nauczycieldesktopapp.controller.zarzadzanie;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.example.nauczycieldesktopapp.controller.MainMenuController;
import org.example.nauczycieldesktopapp.model.*;
import org.example.nauczycieldesktopapp.service.GrupaService;
import org.example.nauczycieldesktopapp.service.ObecnoscService;
import org.example.nauczycieldesktopapp.service.StudentService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZarzadzanieObecnosciamiController extends MainMenuController {

    @FXML public TableView<Obecnosc> obecnosciTable;

    @FXML public TableColumn<Obecnosc, String> idColumn;
    @FXML public TableColumn<Obecnosc, String> nameColumn;
    @FXML public TableColumn<Obecnosc, Void> obecnyCheckColumn;
    @FXML public TableColumn<Obecnosc, Void> spozniaonyCheckColumn;
    @FXML public TableColumn<Obecnosc, Void> nieobecnyCheckColumn;
    @FXML public Button wyslijBtn;


    private List<Obecnosc> obecnosci;
    private List<Student> students;
    public Termin termin;

    private ObecnoscService obecnosciService = new ObecnoscService();
    private StudentService studentService = new StudentService();

    private final ObservableList<Obecnosc> obecnosciData = FXCollections.observableArrayList();


    @FXML public void initialize() throws IOException {
        if (termin == null) return;

        setStudents();
        prepareObecnosci();

        idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()).asString());
        nameColumn.setCellValueFactory(cellData -> {
            Student s = null;
            try {
                s = cellData.getValue().getStudent(cellData.getValue().getId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return new ReadOnlyObjectWrapper<>(s.getImie() + " " + s.getNazwisko());
        });

        addCheckBoxColumn(obecnyCheckColumn, Status.OBECNY);
        addCheckBoxColumn(spozniaonyCheckColumn, Status.SPOZNIONY);
        addCheckBoxColumn(nieobecnyCheckColumn, Status.NIEOBECNY);

        obecnosciTable.setItems(obecnosciData);

    }

    public void setTermin(Termin termin) {
        this.termin = termin;
    }
    public void setStudents() throws IOException {
        students = studentService.getStudentsByGrupa(termin.getGrupa().getId());
    }
    private void prepareObecnosci() {
        obecnosciData.clear();
        for (Student student : students) {
            Obecnosc ob = new Obecnosc();
            ob.setStudentId(student.getId());
            ob.setTerminID(termin.getId());
            ob.setStatus(Status.BRAK); // brak początkowego statusu
            obecnosciData.add(ob);
        }
    }

    private void addCheckBoxColumn(TableColumn<Obecnosc, Void> column, Status status) {
        column.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Obecnosc, Void> call(final TableColumn<Obecnosc, Void> param) {
                return new TableCell<>() {
                    private final CheckBox checkBox = new CheckBox();

                    {
                        checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                            if (isNowSelected) {
                                Obecnosc ob = getTableView().getItems().get(getIndex());
                                ob.setStatus(status);
                                uncheckOtherBoxes(ob);
                                obecnosciTable.refresh();
                            } else {
                                Obecnosc ob = getTableView().getItems().get(getIndex());
                                if (status.equals(ob.getStatus())) {
                                    ob.setStatus(null);
                                }
                            }
                        });
                    }

                    private void uncheckOtherBoxes(Obecnosc obecnosc) {
                        // this forces single selection by updating table
                        for (Obecnosc ob : obecnosciData) {
                            if (ob == obecnosc) continue;
                        }
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Obecnosc ob = getTableView().getItems().get(getIndex());
                            checkBox.setSelected(status.equals(ob.getStatus()));
                            setGraphic(checkBox);
                        }
                    }
                };
            }
        });
    }

    @FXML
    public void wyslijObecnosci() {
        for (Obecnosc ob : obecnosciData) {
            if (ob.getStatus() != null) {
                try {
                    obecnosciService.sendObecnosc(ob);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
