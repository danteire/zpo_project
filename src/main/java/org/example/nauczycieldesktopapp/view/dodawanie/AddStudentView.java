package org.example.nauczycieldesktopapp.view.dodawanie;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AddStudentView {
    private static double xOffset = 0;
    private static double yOffset = 0;

    /**
     * Uruchamia widok dodawania studenta w nowym oknie Stage.
     * Umożliwia przesuwanie okna poprzez przeciąganie jego powierzchni.
     *
     * @param stage Stage, na którym zostanie wyświetlony widok
     * @throws IOException jeśli wystąpi problem z załadowaniem pliku FXML
     */
    public static void launch(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AddStudentView.class.getResource("/org/example/nauczycieldesktopapp/fxml/DodawanieViews/DodawanieStudentaView.fxml"));
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
}
