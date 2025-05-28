package org.example.listaobecnosci.AppViews.Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.listaobecnosci.AppViews.MainMenu.MainMenuView;

import java.io.IOException;


public class LoginController {

    private String username, password;

    @FXML
    public Button loginButton;

    @FXML
    private ImageView closeIcon;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        username = usernameField.getText();
        password = passwordField.getText();
        System.out.println(username);
        System.out.println(password);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainMenuView.launch(stage, username);
    }

    @FXML
    private void handleClose(javafx.scene.input.MouseEvent event) {
        Stage stage = (Stage) closeIcon.getScene().getWindow();
        stage.close();
    }

}
