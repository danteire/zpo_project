package org.example.listaobecnosci.AppViews.UseCases.ZarzadzanieStudentami;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.listaobecnosci.AppViews.Student;
import org.example.listaobecnosci.AppViews.UseCases.DodajStudenta.DodajStudentView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import static org.example.listaobecnosci.AppViews.UseCases.ZarzadzanieStudentami.CaseView.deleteRequest;

public class CaseController {

    @FXML
    public Button ZStud;
    public AnchorPane rightAP;
    public Button DodStud;


    @FXML
    private Text userName;

    @FXML
    private ImageView closeIcon;

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, String> imieColumn;

    @FXML
    private TableColumn<Student, String> nazwiskoColumn;

    @FXML
    private TableColumn<Student, String> indeksColumn;

    @FXML
    private TableColumn<Student, Void> deleteColumn;

    @FXML
    private void handleClose(javafx.scene.input.MouseEvent event) {
        Stage stage = (Stage) closeIcon.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void handleDodanieStudenta(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        DodajStudentView.launch(stage);
    }

    @FXML
    public void initialize() {
        imieColumn.setCellValueFactory(data -> data.getValue().imieProperty());
        nazwiskoColumn.setCellValueFactory(data -> data.getValue().nazwiskoProperty());
        indeksColumn.setCellValueFactory(data -> data.getValue().indeksProperty());

        addDeleteButtonToTable();


    }
    public void setStudentTable(List<Student> students) {
        studentTable.getItems().addAll(students);
    }
    private void addDeleteButtonToTable() {
        Callback<TableColumn<Student, Void>, TableCell<Student, Void>> cellFactory = new Callback<>() {

            @Override
            public TableCell<Student, Void> call(final TableColumn<Student, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Usuń");

                    {
                        btn.setOnAction(event -> {
                            Student student = getTableView().getItems().get(getIndex());
                            getTableView().getItems().remove(student);
                            deleteRequest(student);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };

        deleteColumn.setCellFactory(cellFactory);
    }

}
