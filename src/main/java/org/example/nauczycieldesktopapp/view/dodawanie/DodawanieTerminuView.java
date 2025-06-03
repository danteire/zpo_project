package org.example.nauczycieldesktopapp.view.dodawanie;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.nauczycieldesktopapp.controller.dodawanie.DodawanieTerminuController;
import org.example.nauczycieldesktopapp.model.Termin;


import java.io.IOException;

public class DodawanieTerminuView {

    private static double xOffset = 0;
    private static double yOffset = 0;

    public static void launch(Termin termin) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(DodawanieStudentaView.class.getResource("/org/example/nauczycieldesktopapp/fxml/DodawanieViews/DodawanieTerminuView.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 600, 400);
        Stage stage = new Stage();

        root.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });

        root.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - xOffset);
            stage.setY(mouseEvent.getScreenY() - yOffset);
        });

        DodawanieTerminuController controller = fxmlLoader.getController();
        controller.setTermin(termin);

        stage.setScene(scene);
        stage.show();
    }

}
