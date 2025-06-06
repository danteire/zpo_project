package org.example.nauczycieldesktopapp.view.zarzadzanie;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.nauczycieldesktopapp.controller.listsViews.GroupListViewController;
import org.example.nauczycieldesktopapp.controller.zarzadzanie.ManageStudentsController;
import org.example.nauczycieldesktopapp.model.Grupa;
import org.example.nauczycieldesktopapp.view.dodawanie.AddStudentView;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Klasa odpowiedzialna za wyświetlanie widoku zarządzania studentami.
 * Umożliwia uruchomienie głównego widoku oraz widoku wyboru grupy.
 * Okna można przesuwać przeciągając dowolny obszar widoku.
 */
public class ManageStudentsView {

    private static double xOffset = 0;
    private static double yOffset = 0;

    /**
     * Uruchamia główny widok zarządzania studentami w podanym oknie Stage.
     * Umożliwia przesuwanie okna przeciągając dowolny obszar widoku.
     *
     * @param stage okno, w którym ma zostać wyświetlony widok zarządzania studentami
     * @throws IOException jeśli wystąpi problem z załadowaniem pliku FXML
     */
    public static void launch(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ManageStudentsView.class.getResource("/org/example/nauczycieldesktopapp/fxml/ZarzadzanieViews/ZarzadzanieStudentamiView.fxml"));
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

        ManageStudentsController controller = fxmlLoader.getController();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Zarządzanie Studentami");
        stage.show();
    }

    /**
     * Uruchamia widok listy grup w nowym oknie Stage.
     * Po wybraniu grupy wywołuje przekazany Consumer i zamyka okno.
     * Umożliwia przesuwanie okna przeciągając dowolny obszar widoku.
     *
     * @param onGroupSelected akcja wykonywana po wybraniu grupy
     * @throws IOException jeśli wystąpi problem z załadowaniem pliku FXML
     */
    public static void launchSubList(Consumer<Grupa> onGroupSelected) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AddStudentView.class.getResource("/org/example/nauczycieldesktopapp/fxml/ListViews/GroupListView.fxml"));
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
