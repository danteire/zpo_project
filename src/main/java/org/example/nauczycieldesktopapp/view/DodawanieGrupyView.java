package org.example.nauczycieldesktopapp.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DodawanieGrupyView {

    private static double xOffset = 0;
    private static double yOffset = 0;

    public static void launch(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DodawanieStudentaView.class.getResource("/org/example/nauczycieldesktopapp/fxml/DodawanieGrupyView.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        root.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });

        root.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - xOffset);
            stage.setY(mouseEvent.getScreenY() - yOffset);
        });

        stage.setScene(scene);
        stage.setTitle("Dodaj Grupę");
        stage.show();
    }


}
