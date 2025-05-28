package org.example.listaobecnosci.AppViews.MainMenu;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class MainMenuController {
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

}
