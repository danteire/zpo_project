package org.example.nauczycieldesktopapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.util.Callback;
import org.example.nauczycieldesktopapp.model.Grupa;
import org.example.nauczycieldesktopapp.model.Student;

import org.example.nauczycieldesktopapp.service.StudentService;


import java.io.IOException;
import java.util.List;

public class StudentListViewController {

    private final StudentService studentService = new StudentService();

    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> imieColumn;
    @FXML private TableColumn<Student, String> nazwiskoColumn;
    @FXML private TableColumn<Student, String> nrIndeksuColumn;
    @FXML private TableColumn<Student, Void> deleteStudentColumn;


    public void setGrupa(Grupa grupa) throws IOException {
        List<Student> studenci = studentService.getStudentsByGrupa(grupa.getId());

        ObservableList<Student> lista = FXCollections.observableArrayList(studenci);
        studentTable.setItems(lista);
    }

    private void deleteStudentFromGrupaButtonTable() {
        Callback<TableColumn<Student, Void>, TableCell<Student, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btn = new Button("Usuń z grupy");

            {
                btn.setOnAction(event -> {
                    Student student = getTableView().getItems().get(getIndex());
                    boolean success = studentService.removeStudentFromGroup(student);
                    if (success) {
                        if (getIndex() >= 0 && getIndex() < getTableView().getItems().size()) {
                            getTableView().getItems().remove(getIndex());
                        }
                        System.out.println("Usunięto studenta z grupy");
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
        deleteStudentColumn.setCellFactory(cellFactory);
    }

    @FXML
    public void initialize() {

        imieColumn.setCellValueFactory(data -> data.getValue().imieProperty());
        nazwiskoColumn.setCellValueFactory(data -> data.getValue().nazwiskoProperty());
        nrIndeksuColumn.setCellValueFactory(data -> data.getValue().indeksProperty());

        deleteStudentFromGrupaButtonTable();

    }
}
