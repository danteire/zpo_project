package org.example.listaobecnosci.AppViews.MainMenu;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainMenuView {

    private static double xOffset = 0;
    private static double yOffset = 0;

    public static void launch(Stage stage, String Username) throws IOException {
        //stage init

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuView.class.getResource("MainMenu.fxml"));
        Parent root = fxmlLoader.load();
        MainMenuController controller = fxmlLoader.getController();
        controller.setUserName(Username);

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
        stage.setTitle("Main Menu");
        stage.show();
    }
}
