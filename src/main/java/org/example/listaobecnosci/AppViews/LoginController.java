package org.example.listaobecnosci.AppViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;



public class LoginController {

    @FXML
    public Button loginButton;

    @FXML
    private ImageView closeIcon;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin(){
        String username = usernameField.getText();
        String password = passwordField.getText();
        System.out.println(username);
        System.out.println(password);
    }

    @FXML
    private void handleClose(javafx.scene.input.MouseEvent event) {
        Stage stage = (Stage) closeIcon.getScene().getWindow();
        stage.close();
    }

}
