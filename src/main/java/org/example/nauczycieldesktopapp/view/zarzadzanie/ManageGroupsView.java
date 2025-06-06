package org.example.nauczycieldesktopapp.view.zarzadzanie;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.nauczycieldesktopapp.controller.listsViews.StudentListViewController;
import org.example.nauczycieldesktopapp.model.Grupa;
import org.example.nauczycieldesktopapp.view.dodawanie.AddStudentView;

import java.io.IOException;

/**
 * Klasa zarządza widokiem zarządzania grupami w aplikacji desktopowej.
 * Umożliwia uruchomienie głównego widoku zarządzania grupami oraz widoku listy studentów dla konkretnej grupy.
 * Widoki umożliwiają przesuwanie okna przez przeciąganie dowolnego obszaru widoku.
 */
public class ManageGroupsView {

    private static double xOffset = 0;
    private static double yOffset = 0;

    /**
     * Uruchamia główny widok zarządzania grupami w podanym oknie Stage.
     * Umożliwia przesuwanie okna przeciągając dowolny obszar widoku.
     *
     * @param stage okno, w którym ma zostać wyświetlony widok zarządzania grupami
     * @throws IOException jeśli wystąpi problem z załadowaniem pliku FXML
     */
    public static void launch(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AddStudentView.class.getResource("/org/example/nauczycieldesktopapp/fxml/ZarzadzanieViews/ZarzadzanieGrupami.fxml"));
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
        stage.setTitle("Dodaj Studenta");
        stage.show();
    }

    /**
     * Uruchamia widok listy studentów dla podanej grupy w nowym oknie Stage.
     * Umożliwia przesuwanie okna przeciągając dowolny obszar widoku.
     *
     * @param grupa grupa, dla której ma zostać wyświetlona lista studentów
     * @throws IOException jeśli wystąpi problem z załadowaniem pliku FXML
     */
    public static void launchSubList(Grupa grupa) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AddStudentView.class.getResource("/org/example/nauczycieldesktopapp/fxml/ListViews/StudentListView.fxml"));
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
        stage.setTitle("Lista Studentów Grupy: " + grupa.nazwaProperty().getValue());

        StudentListViewController controller = fxmlLoader.getController();
        controller.setGrupa(grupa);

        stage.show();
    }
}
