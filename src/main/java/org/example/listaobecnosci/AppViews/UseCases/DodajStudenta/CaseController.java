package org.example.listaobecnosci.AppViews.UseCases.DodajStudenta;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.listaobecnosci.AppViews.Student;
import org.example.listaobecnosci.AppViews.UseCases.ZarzadzanieStudentami.CaseView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CaseController {

    private String imie, nazwisko, indeks;

    @FXML
    public Button ZStud;
    public AnchorPane rightAP;
    public TextField imieField;
    public TextField nazwiskoField;
    public TextField indeksField;

    @FXML
    private ImageView closeIcon;
    @FXML
    private void handleClose(javafx.scene.input.MouseEvent event) {
        Stage stage = (Stage) closeIcon.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void handleZStud(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        CaseView.launch(stage);
    }

    public void handleDodajStudenta(ActionEvent event) {
        imie = imieField.getText();
        indeks = indeksField.getText();
        nazwisko = nazwiskoField.getText();

        if(imie.isBlank() || indeks.isBlank() || nazwisko.isBlank()){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Uzupełnij WSZYSTKIE pola!!!");
            alert.show();
            return;
        }
        System.out.println(imie);
        System.out.println(indeks);
        System.out.println(nazwisko);

        try {

            Map<String, String> dane = new HashMap<>();
            dane.put("imie", imie);
            dane.put("nazwisko", nazwisko);
            dane.put("nrIndeksu", indeks);

            // 2. Zamieniamy go na JSON (zakładamy że masz bibliotekę Jackson)
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(dane);
            System.out.println(json);

            // 3. Konfigurujemy połączenie HTTP z użyciem URL i HttpURLConnection
            URL url = new URL("http://3.71.11.3:8080/students");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true); // pozwala na pisanie do body

            // 4. Wysyłamy JSON do serwera
            try (var os = con.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // 5. Odczytujemy odpowiedź (opcjonalnie)
            int status = con.getResponseCode();
            System.out.println("Status odpowiedzi: " + status);

            if (status == 200) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Dodano studenta o indeksie: \n" + indeks + " do listy studentów");
                alert.setTitle("Komunikat");
                alert.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Niepoprawne dane!");
                alert.setTitle("Komunikat");
                alert.show();
            }

            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Błąd podczas wysyłania danych").show();
        }


    }
}
