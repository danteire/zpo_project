package org.example.nauczycieldesktopapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import org.example.nauczycieldesktopapp.model.Grupa;
import org.example.nauczycieldesktopapp.model.Student;

import org.example.nauczycieldesktopapp.service.StudentService;


import java.io.IOException;
import java.util.List;

public class StudentListViewController {

    private final StudentService studentService = new StudentService();

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, String> imieColumn;

    @FXML
    private TableColumn<Student, String> nazwiskoColumn;

    public void setGrupa(Grupa grupa) throws IOException {
        // Załaduj studentów z tej grupy
        List<Student> studenci = studentService.getStudentsByGrupa(grupa.getId());

        ObservableList<Student> lista = FXCollections.observableArrayList(studenci);
        studentTable.setItems(lista);
    }

    @FXML
    public void initialize() {
        imieColumn.setCellValueFactory(data -> data.getValue().imieProperty());
        nazwiskoColumn.setCellValueFactory(data -> data.getValue().nazwiskoProperty());
    }
}
