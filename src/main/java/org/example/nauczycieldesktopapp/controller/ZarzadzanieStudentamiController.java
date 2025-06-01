package org.example.nauczycieldesktopapp.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.example.nauczycieldesktopapp.model.Student;
import org.example.nauczycieldesktopapp.service.StudentService;

import java.io.IOException;
import java.util.List;

public class ZarzadzanieStudentamiController extends MainMenuController {

    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> imieColumn;
    @FXML private TableColumn<Student, String> nazwiskoColumn;
    @FXML private TableColumn<Student, String> indeksColumn;
    @FXML private TableColumn<Student, Void> deleteColumn;


    private final StudentService studentService = new StudentService();
    private final ObservableList<Student> studentObservableList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        imieColumn.setCellValueFactory(cellData -> cellData.getValue().imieProperty());
        nazwiskoColumn.setCellValueFactory(cellData -> cellData.getValue().nazwiskoProperty());
        indeksColumn.setCellValueFactory(cellData -> cellData.getValue().indeksProperty());

        deleteButtonTable();

        try {
            List<Student> students = studentService.getAllStudents();
            studentObservableList.setAll(students);
            studentTable.setItems(studentObservableList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void deleteButtonTable() {
        Callback<TableColumn<Student, Void>, TableCell<Student, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btn = new Button("Usuń");

            {
                btn.setOnAction(event -> {
                    Student student = getTableView().getItems().get(getIndex());
                    boolean success = studentService.deleteStudent(student);
                    if (success) {
                        studentObservableList.remove(student);
                        System.out.println("Usunięto studenta: " + student.getId());
                    } else {
                        System.err.println("Błąd usuwania studenta.");
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

}


