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

public class ZarzadzanieObecnosciamiView {

    private static double xOffset = 0;
    private static double yOffset = 0;

    public static void launch(Termin termin) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ZarzadzanieStudentamiView.class.getResource("/org/example/nauczycieldesktopapp/fxml/ZarzadzanieViews/ZarzadzanieObecnosciami.fxml"));
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
        stage.setTitle("Lista Obecności dla terminu: " + termin.dataProperty().getValue());

        ZarzadzanieObecnosciamiController controller = fxmlLoader.getController();
        controller.setTermin(termin);
        controller.setStudents(termin);
        stage.show();
    }

}
