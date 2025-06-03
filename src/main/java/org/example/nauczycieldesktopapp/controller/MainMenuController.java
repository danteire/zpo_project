package org.example.nauczycieldesktopapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.nauczycieldesktopapp.view.dodawanie.DodawanieGrupyView;
import org.example.nauczycieldesktopapp.view.dodawanie.DodawanieStudentaView;
import org.example.nauczycieldesktopapp.view.zarzadzanie.ZarzadzanieGrupamiView;
import org.example.nauczycieldesktopapp.view.zarzadzanie.ZarzadzanieStudentamiView;
import org.example.nauczycieldesktopapp.view.zarzadzanie.ZarzadzanieTerminamiView;

import java.io.IOException;

public class MainMenuController {

    @FXML public Button ZGrup;
    @FXML public Button DzOb;
    @FXML private Button ZStud;
    @FXML private Button DodStud;
    @FXML private AnchorPane rightAP;
    @FXML private Text userName;
    @FXML private ImageView closeIcon;

    public void setUserName(String username) {
        userName.setText(username);
    }

    @FXML
    private void handleClose(javafx.scene.input.MouseEvent event) {
        Stage stage = (Stage) closeIcon.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleZarzadzanieStudentami(ActionEvent event) throws IOException {
        System.out.println("Zarządzanie studentami clicked");
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        ZarzadzanieStudentamiView.launch(stage);
    }

    @FXML
    private void handleDodajStudenta(ActionEvent event) throws IOException {
        System.out.println("Dodaj studenta clicked");
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        DodawanieStudentaView.launch(stage);
    }

    @FXML
    private void handleDodajGrupe(ActionEvent event) throws IOException {
        System.out.println("Dodaj grupe clicked");
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        DodawanieGrupyView.launch(stage);
    }

    @FXML
    private void handleZarzadanieGrupami(ActionEvent event) throws IOException {
        System.out.println("Zarządaj grupami clicked");
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        ZarzadzanieGrupamiView.launch(stage);
    }
    @FXML
    private void handleZarzadzanieTerminami(ActionEvent event) throws IOException {
        System.out.println("Zarządzenie Terminami Clicked");
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        ZarzadzanieTerminamiView.launch(stage);
    }
}

