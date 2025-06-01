package org.example.nauczycieldesktopapp.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.nauczycieldesktopapp.model.Grupa;
import org.example.nauczycieldesktopapp.model.Student;
import org.example.nauczycieldesktopapp.service.GrupaService;
import org.example.nauczycieldesktopapp.service.StudentService;

import java.io.IOException;
import java.util.List;

import static org.example.nauczycieldesktopapp.view.ZarzadzanieStudentamiView.launchSubList;

public class ZarzadzanieStudentamiController extends MainMenuController {


    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> imieColumn;
    @FXML private TableColumn<Student, String> nazwiskoColumn;
    @FXML private TableColumn<Student, String> indeksColumn;
    @FXML private TableColumn<Student, Void> grupaColumn;
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


    private void checkAndPrintGroupColumn(){
        Callback<TableColumn<Student, Void>, TableCell<Student, Void>> cellFactory = param -> new TableCell<>() {

            private final Button assignButton = new Button("Przypisz");

            {
                assignButton.setOnAction(event -> {
                    Student student = getTableView().getItems().get(getIndex());
                    Grupa grupa = new Grupa();
                    try {

                        launchSubList();


                        // Callback po wyborze grupy
                        setOnGroupSelected(grupa -> {
                            try {
                                boolean success = studentService.addStudentToGroup(student.getId(), );
                                if (success) {
                                    student.setGrupa(grupa);
                                    getTableView().refresh();
                                } else {
                                    System.err.println("Nie udało się przypisać.");
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });

                        Stage stage = new Stage();
                        stage.setTitle("Wybierz grupę");
                        stage.setScene(new javafx.scene.Scene(root, 400, 300));
                        stage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                    return;
                }

                Student student = getTableView().getItems().get(getIndex());

                if (student.getGrupa() != null) {
                    setGraphic(new Label(student.getGrupa().getNazwa()));
                } else {
                    setGraphic(assignButton);
                }
            }
        };

        grupaColumn.setCellFactory(cellFactory);
    }
}


