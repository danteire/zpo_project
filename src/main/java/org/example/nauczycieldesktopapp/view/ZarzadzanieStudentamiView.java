package org.example.nauczycieldesktopapp.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.nauczycieldesktopapp.controller.ZarzadzanieStudentamiController;

import java.io.IOException;

public class ZarzadzanieStudentamiView {

    private static double xOffset = 0;
    private static double yOffset = 0;

    public static void launch(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ZarzadzanieStudentamiView.class.getResource("/org/example/nauczycieldesktopapp/fxml/ZarzadzanieStudentamiView.fxml"));
        Parent root = fxmlLoader.load();

        // Dragging window logic
        root.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });
        root.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - xOffset);
            stage.setY(mouseEvent.getScreenY() - yOffset);
        });

        ZarzadzanieStudentamiController controller = fxmlLoader.getController();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Zarządzanie Studentami");
        stage.show();
    }
}
