package org.example.listaobecnosci.AppViews.UseCases.ZarzadzanieStudentami;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.listaobecnosci.AppViews.Student;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;

public class CaseView {

    private static double xOffset = 0;
    private static double yOffset = 0;

    public static List<Student> students;

    public static void launch(Stage stage) throws IOException {
        //stage init

        FXMLLoader fxmlLoader = new FXMLLoader(CaseView.class.getResource("casestyle.fxml"));
        Parent root = fxmlLoader.load();

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

        getRequest();

        CaseController controller = fxmlLoader.getController();
        controller.setStudentTable(students);

        stage.setScene(scene);
        stage.setTitle("Zarządzanie Studentami");
        stage.show();
    }

    public static void getRequest() throws IOException {
        URL endpoint = new URL("http://3.71.11.3:8080/students");
        HttpURLConnection conn = (HttpURLConnection) endpoint.openConnection();
        conn.setRequestMethod("GET");

        Scanner scanner = new Scanner(conn.getInputStream());
        StringBuilder json = new StringBuilder();
        while (scanner.hasNextLine()) {
            json.append(scanner.nextLine());
        }
        scanner.close();

        ObjectMapper mapper = new ObjectMapper();
        students = mapper.readValue(json.toString(), new TypeReference<>() {});
//            studentList.getItems().setAll(students);

        for (Student student : students) {
            System.out.println(student.toString() + " " + student.getId() + " " + student.getImie() + " " + student.getNazwisko());
        }
    }
    public static void deleteRequest(Student student) {

        try {
            URL url = new URL("http://3.71.11.3:8080/students/" + student.getId());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                System.out.println("Usunięto studenta o ID: " + student.getId());
                students.remove(student);
            } else {
                System.out.println("Błąd usuwania: " + responseCode);
            }

            connection.disconnect();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
