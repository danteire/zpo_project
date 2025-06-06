package org.example.nauczycieldesktopapp.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.nauczycieldesktopapp.controller.listsViews.GenObecnosci;
import org.example.nauczycieldesktopapp.controller.listsViews.SprawdzObecnosciController;
import org.example.nauczycieldesktopapp.model.Termin;
import org.example.nauczycieldesktopapp.view.dodawanie.DodawanieStudentaView;

import java.io.IOException;

/**
 * Klasa odpowiedzialna za wyświetlanie widoku do sprawdzania obecności.
 * Umożliwia uruchomienie głównego widoku oraz podwidoku listy obecności dla konkretnego terminu.
 * Okna można przesuwać przeciągając dowolny obszar widoku.
 */
public class SprawdzObecnosciView {

    private static double xOffset = 0;
    private static double yOffset = 0;

    /**
     * Uruchamia główny widok sprawdzania obecności w podanym oknie Stage.
     * Umożliwia przesuwanie okna przeciągając dowolny obszar widoku.
     *
     * @param stage okno, w którym ma zostać wyświetlony widok sprawdzania obecności
     * @throws IOException jeśli wystąpi problem z załadowaniem pliku FXML
     */
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

    /**
     * Uruchamia podwidok listy obecności dla podanego terminu w nowym oknie Stage.
     * Umożliwia przesuwanie okna przeciągając dowolny obszar widoku.
     *
     * @param termin termin, dla którego ma zostać wyświetlona lista obecności
     * @throws IOException jeśli wystąpi problem z załadowaniem pliku FXML
     */
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
