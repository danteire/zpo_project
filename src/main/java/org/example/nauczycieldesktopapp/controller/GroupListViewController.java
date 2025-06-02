package org.example.nauczycieldesktopapp.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.nauczycieldesktopapp.model.Grupa;
import org.example.nauczycieldesktopapp.model.Student;
import org.example.nauczycieldesktopapp.service.GrupaService;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class GroupListViewController {

    @FXML private TableView<Grupa> groupTable;

    @FXML private TableColumn<Grupa, String> idColumn;
    @FXML private TableColumn<Grupa, String> nazwaColumn;
    @FXML private TableColumn<Grupa, Void> addColumn;

    private List<Grupa> grupy;
    public Consumer<Grupa> onGroupSelected;

    public void setGrupa() throws IOException {
        grupy = GrupaService.getAllGroups();

        ObservableList<Grupa> lista = FXCollections.observableArrayList(grupy);
        groupTable.setItems(lista);
    }

    public void setOnGroupSelected(Consumer<Grupa> callback) {
        this.onGroupSelected = callback;
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()).asString());
        nazwaColumn.setCellValueFactory(cellData -> cellData.getValue().nazwaProperty());

        addColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Grupa, Void> call(TableColumn<Grupa, Void> param) {
                return new TableCell<>() {
                    private final Button selectBtn = new Button("Wybierz");

                    {
                        selectBtn.setOnAction(event -> {
                            Grupa selected = getTableView().getItems().get(getIndex());

                            if (onGroupSelected != null) {
                                onGroupSelected.accept(selected);
                            }

                            Stage stage = (Stage) getScene().getWindow();
                            stage.close();
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : selectBtn);
                    }
                };
            }
        });
    }

}
