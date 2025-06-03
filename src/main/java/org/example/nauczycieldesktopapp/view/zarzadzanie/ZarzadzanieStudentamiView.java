package org.example.nauczycieldesktopapp.view.zarzadzanie;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.nauczycieldesktopapp.controller.listsViews.GroupListViewController;
import org.example.nauczycieldesktopapp.controller.zarzadzanie.ZarzadzanieStudentamiController;
import org.example.nauczycieldesktopapp.model.Grupa;
import org.example.nauczycieldesktopapp.view.dodawanie.DodawanieStudentaView;

import java.io.IOException;
import java.util.function.Consumer;

public class ZarzadzanieStudentamiView {

    private static double xOffset = 0;
    private static double yOffset = 0;

    public static void launch(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ZarzadzanieStudentamiView.class.getResource("/org/example/nauczycieldesktopapp/fxml/ZarzadzanieViews/ZarzadzanieStudentamiView.fxml"));
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

    public static void launchSubList(Consumer<Grupa> onGroupSelected) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DodawanieStudentaView.class.getResource("/org/example/nauczycieldesktopapp/fxml/ListViews/GroupListView.fxml"));
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
        stage.setTitle("Lista Grup");

        GroupListViewController controller = fxmlLoader.getController();
        controller.setGrupa(); // np. załaduj grupy z serwera
        controller.setOnGroupSelected(grupa -> {
            onGroupSelected.accept(grupa);  // wykonuje się w controllerze po wyborze
            stage.close(); // zamknij po wyborze
        });

        stage.setTitle("Wybierz grupę");
        stage.setScene(scene);
        stage.show();
    }
}
