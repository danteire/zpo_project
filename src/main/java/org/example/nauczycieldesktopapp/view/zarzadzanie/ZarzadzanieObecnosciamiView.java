package org.example.nauczycieldesktopapp.view.zarzadzanie;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.nauczycieldesktopapp.controller.listsViews.TerminListViewController;
import org.example.nauczycieldesktopapp.controller.zarzadzanie.ZarzadzanieObecnosciamiController;
import org.example.nauczycieldesktopapp.model.Termin;
import org.example.nauczycieldesktopapp.view.dodawanie.DodawanieStudentaView;

import java.io.IOException;

/**
 * Klasa odpowiedzialna za wyświetlanie widoku zarządzania obecnościami.
 * Umożliwia uruchomienie okna z listą obecności dla wybranego terminu.
 * Widok można przesuwać przeciągając dowolny obszar okna.
 */
public class ZarzadzanieObecnosciamiView {

    private static double xOffset = 0;
    private static double yOffset = 0;

    /**
     * Uruchamia widok zarządzania obecnościami dla podanego terminu.
     * Tworzy nowe okno Stage o ustalonych wymiarach i umożliwia jego przesuwanie.
     *
     * @param termin termin, dla którego ma zostać wyświetlona lista obecności
     * @throws IOException jeśli wystąpi problem z załadowaniem pliku FXML
     */
    public static void launch(Termin termin) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ZarzadzanieStudentamiView.class.getResource("/org/example/nauczycieldesktopapp/fxml/ZarzadzanieViews/ZarzadzanieObecnosciami.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 470, 460);
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
        stage.setTitle("Lista Obecności dla terminu: " + termin.dataProperty().getValue());

        ZarzadzanieObecnosciamiController controller = fxmlLoader.getController();
        controller.setTermin(termin);
        controller.setStudents(termin);
        controller.setStage(stage);
        stage.show();
    }

}
