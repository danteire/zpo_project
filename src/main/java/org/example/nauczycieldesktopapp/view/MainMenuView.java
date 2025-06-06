package org.example.nauczycieldesktopapp.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Klasa odpowiedzialna za wyświetlanie głównego menu aplikacji.
 * Pozwala uruchomić widok menu głównego z możliwością przesuwania okna
 * poprzez przeciąganie dowolnego obszaru widoku.
 */
public class MainMenuView {

    private static double xOffset = 0;
    private static double yOffset = 0;

    /**
     * Uruchamia widok głównego menu w podanym oknie Stage.
     * Ustawia nazwę użytkownika w kontrolerze i umożliwia przesuwanie okna.
     *
     * @param stage    okno, w którym ma zostać wyświetlone menu główne
     * @param username nazwa użytkownika, która zostanie ustawiona w kontrolerze
     * @throws IOException jeśli wystąpi problem z załadowaniem pliku FXML
     */
    public static void launch(Stage stage, String username) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainMenuView.class.getResource("/org/example/nauczycieldesktopapp/fxml/MainMenuView.fxml"));
        Parent root = loader.load();
        org.example.nauczycieldesktopapp.controller.MainMenuController controller = loader.getController();
        controller.setUserName(username);

        root.setOnMousePressed(e -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });

        root.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);
        });

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Main Menu");
        stage.show();
    }
}
