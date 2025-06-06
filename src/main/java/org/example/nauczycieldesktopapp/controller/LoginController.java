package org.example.nauczycieldesktopapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.nauczycieldesktopapp.view.MainMenuView;

import java.io.IOException;

/**
 * Kontroler odpowiedzialny za obsługę logowania użytkownika.
 * <p>
 * Zarządza polami tekstowymi do wprowadzania nazwy użytkownika i hasła,
 * przyciskiem logowania oraz ikoną zamknięcia aplikacji.
 */
public class LoginController {

    /**
     * Ikona służąca do zamknięcia okna aplikacji.
     */
    @FXML
    private ImageView closeIcon;

    /**
     * Pole tekstowe do wprowadzania nazwy użytkownika.
     */
    @FXML
    private TextField usernameField;

    /**
     * Pole do wprowadzania hasła użytkownika.
     */
    @FXML
    private PasswordField passwordField;

    /**
     * Obsługuje akcję logowania wywołaną kliknięciem przycisku logowania.
     * <p>
     * Pobiera dane z pól tekstowych, wyświetla je na konsoli,
     * a następnie uruchamia główne menu aplikacji, przekazując nazwę użytkownika.
     *
     * @param event zdarzenie kliknięcia przycisku logowania
     * @throws IOException jeśli wystąpi problem z ładowaniem widoku głównego menu
     */
    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        System.out.println(username);
        System.out.println(password);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainMenuView.launch(stage, username);
    }

    /**
     * Obsługuje kliknięcie na ikonę zamknięcia aplikacji i zamyka okno.
     *
     */
    @FXML
    private void handleClose() {
        Stage stage = (Stage) closeIcon.getScene().getWindow();
        stage.close();
    }
}
