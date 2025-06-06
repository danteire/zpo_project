package org.example.nauczycieldesktopapp.view.zarzadzanie;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.nauczycieldesktopapp.controller.listsViews.TerminListViewController;
import org.example.nauczycieldesktopapp.controller.zarzadzanie.ZarzadzanieTerminamiController;
import org.example.nauczycieldesktopapp.model.Grupa;
import org.example.nauczycieldesktopapp.view.dodawanie.DodawanieStudentaView;

import java.io.IOException;

/**
 * Klasa odpowiedzialna za wyświetlanie widoku zarządzania terminami.
 * Umożliwia uruchomienie głównego widoku terminów oraz widoku listy terminów dla danej grupy.
 * Okna można przesuwać przeciągając dowolny obszar widoku.
 */
public class ZarzadzanieTerminamiView {
    private static double xOffset = 0;
    private static double yOffset = 0;

    /**
     * Uruchamia główny widok zarządzania terminami w podanym oknie Stage.
     * Umożliwia przesuwanie okna przeciągając dowolny obszar widoku.
     *
     * @param stage okno, w którym ma zostać wyświetlony widok zarządzania terminami
     * @throws IOException jeśli wystąpi problem z załadowaniem pliku FXML
     */
    public static void launch(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ZarzadzanieStudentamiView.class.getResource("/org/example/nauczycieldesktopapp/fxml/ZarzadzanieViews/ZarzadzanieTerminami.fxml"));
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

        ZarzadzanieTerminamiController controller = fxmlLoader.getController();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Zarządzanie Terminami");
        stage.show();
    }

    /**
     * Uruchamia widok listy terminów dla podanej grupy w nowym oknie Stage.
     * Umożliwia przesuwanie okna przeciągając dowolny obszar widoku.
     *
     * @param grupa grupa, dla której ma zostać wyświetlona lista terminów
     * @throws IOException jeśli wystąpi problem z załadowaniem pliku FXML
     */
    public static void launchSubList(Grupa grupa) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DodawanieStudentaView.class.getResource("/org/example/nauczycieldesktopapp/fxml/ListViews/TerminListView.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 800, 400);
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
        stage.setTitle("Lista Terminów Grupy: " + grupa.nazwaProperty().getValue());

        TerminListViewController controller = fxmlLoader.getController();
        controller.setTerminy(grupa);

        stage.show();
    }
}
