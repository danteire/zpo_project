package org.example.nauczycieldesktopapp.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.nauczycieldesktopapp.controller.listsViews.GenObecnosci;
import org.example.nauczycieldesktopapp.controller.listsViews.SprawdzObecnosciController;
import org.example.nauczycieldesktopapp.controller.listsViews.StudentListViewController;
import org.example.nauczycieldesktopapp.model.Grupa;
import org.example.nauczycieldesktopapp.model.Termin;
import org.example.nauczycieldesktopapp.view.dodawanie.DodawanieStudentaView;

import java.io.IOException;

public class SprawdzObecnosciView {

    private static double xOffset = 0;
    private static double yOffset = 0;

    public static void launch(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DodawanieStudentaView.class.getResource("/org/example/nauczycieldesktopapp/fxml/ListViews/SprawdzObecnosciView.fxml"));
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
        stage.setTitle("Sprawdź Obecności");

        SprawdzObecnosciController controller = fxmlLoader.getController();
        controller.setTermin();

        stage.show();
    }
    public static void launchSubList(Termin termin) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DodawanieStudentaView.class.getResource("/org/example/nauczycieldesktopapp/fxml/ListViews/ObecnosciListView.fxml"));
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

        stage.setScene(scene);
        stage.setTitle("Lista Obecnosci dla Terminu: " + termin.dataProperty() + " Grupy: " + termin.getGrupa().nazwaProperty());

        GenObecnosci controller = fxmlLoader.getController();

        controller.setTermin(termin);
        controller.setObecnosci();

        stage.show();
    }
}
