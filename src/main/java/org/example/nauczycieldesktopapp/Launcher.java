package org.example.nauczycieldesktopapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Główna klasa uruchamiająca aplikację.
 * Rozpoczyna działanie aplikacji od wyświetlenia widoku logowania.
 * Okno jest pozbawione domyślnej dekoracji i można je przesuwać
 * przeciągając dowolny obszar widoku.
 */
public class Launcher extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    /**
     * Metoda startowa uruchamiana przez JavaFX.
     * Ładuje widok logowania i ustawia podstawowe właściwości okna,
     * takie jak możliwość przeciągania okna.
     *
     * @param stage główne okno aplikacji
     * @throws Exception jeśli wystąpi problem z załadowaniem pliku FXML
     */
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/org/example/nauczycieldesktopapp/fxml/LoginView.fxml"));

        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });

        root.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - xOffset);
            stage.setY(mouseEvent.getScreenY() - yOffset);
        });

        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    /**
     * Punkt startowy aplikacji.
     *
     * @param args argumenty linii poleceń (nieużywane)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
