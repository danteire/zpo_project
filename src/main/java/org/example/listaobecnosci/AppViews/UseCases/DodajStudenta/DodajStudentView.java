package org.example.listaobecnosci.AppViews.UseCases.DodajStudenta;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;


public class DodajStudentView {
    private static double xOffset = 0;
    private static double yOffset = 0;


    public static void launch(Stage stage) throws IOException {
        //stage init

        FXMLLoader fxmlLoader = new FXMLLoader(DodajStudentView.class.getResource("casestyle.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        //capture mouse window drag
        root.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });
        root.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - xOffset);
            stage.setY(mouseEvent.getScreenY() - yOffset);
        });

        stage.setScene(scene);
        stage.setTitle("Dodaj Studenta");
        stage.show();
    }
}
