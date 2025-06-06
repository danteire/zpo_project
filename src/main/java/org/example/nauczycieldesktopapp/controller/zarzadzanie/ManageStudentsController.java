package org.example.nauczycieldesktopapp.controller.zarzadzanie;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.example.nauczycieldesktopapp.controller.MainMenuController;
import org.example.nauczycieldesktopapp.model.Student;
import org.example.nauczycieldesktopapp.service.StudentService;
import org.example.nauczycieldesktopapp.view.zarzadzanie.ManageStudentsView;

import java.io.IOException;
import java.util.List;

/**
 * Kontroler odpowiedzialny za wyświetlanie listy studentów,
 * usuwanie ich oraz przypisywanie do grup.
 */
public class ManageStudentsController extends MainMenuController {

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, String> imieColumn;

    @FXML
    private TableColumn<Student, String> nazwiskoColumn;

    @FXML
    private TableColumn<Student, String> indeksColumn;

    @FXML
    private TableColumn<Student, Void> grupaColumn;

    @FXML
    private TableColumn<Student, Void> deleteColumn;

    private final StudentService studentService = new StudentService();

    private final ObservableList<Student> studentObservableList = FXCollections.observableArrayList();

    /**
     * Inicjalizacja tabeli i wczytanie listy studentów.
     */
    @FXML
    public void initialize() {
        imieColumn.setCellValueFactory(cellData -> cellData.getValue().imieProperty());
        nazwiskoColumn.setCellValueFactory(cellData -> cellData.getValue().nazwiskoProperty());
        indeksColumn.setCellValueFactory(cellData -> cellData.getValue().indeksProperty());

        initializeDeleteButtonColumn();
        initializeGroupAssignmentColumn();

        try {
            List<Student> students = studentService.getAllStudents();
            studentObservableList.setAll(students);
            studentTable.setItems(studentObservableList);
        } catch (IOException e) {
            e.printStackTrace();
            // Tutaj można też wyświetlić Alert z informacją o błędzie.
        }
    }

    /**
     * Dodaje kolumnę z przyciskiem "Usuń" do tabeli studentów.
     */
    private void initializeDeleteButtonColumn() {
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

    /**
     * Dodaje kolumnę do przypisywania studenta do grupy.
     * Jeśli student należy już do grupy, pokazuje nazwę grupy zamiast przycisku.
     */
    private void initializeGroupAssignmentColumn() {
        Callback<TableColumn<Student, Void>, TableCell<Student, Void>> cellFactory = param -> new TableCell<>() {
            private final Button assignButton = new Button("Przypisz do grupy");

            {
                assignButton.setOnAction(event -> {
                    Student student = getTableView().getItems().get(getIndex());
                    try {
                        ManageStudentsView.launchSubList(grupa -> {
                            try {
                                boolean success = studentService.addStudentToGroup(student.getId(), grupa.getId());
                                if (success) {
                                    student.setGrupa(grupa);
                                    studentTable.refresh();
                                } else {
                                    System.err.println("Nie udało się przypisać studenta do grupy.");
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
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
