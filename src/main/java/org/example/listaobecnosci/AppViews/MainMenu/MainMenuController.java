package org.example.listaobecnosci.AppViews.MainMenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.example.listaobecnosci.AppViews.UseCases.ZarzadzanieStudentami.CaseView;
import org.example.listaobecnosci.AppViews.UseCases.DodajStudenta.DodajStudentView;

import java.io.IOException;


public class MainMenuController {

    @FXML
    public Button ZStud;
    public AnchorPane rightAP;
    public Button DodStud;

    @FXML
    private Text userName;

    @FXML
    private ImageView closeIcon;

    public void setUserName(String username) {
        userName.setText(username);
    }

    @FXML
    private void handleClose(javafx.scene.input.MouseEvent event) {
        Stage stage = (Stage) closeIcon.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleZStud(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        CaseView.launch(stage);
    }

    @FXML
    private void handleDodanieStudenta(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        DodajStudentView.launch(stage);
    }

}
