package org.example.nauczycieldesktopapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.nauczycieldesktopapp.view.SprawdzObecnosciView;
import org.example.nauczycieldesktopapp.view.dodawanie.DodawanieGrupyView;
import org.example.nauczycieldesktopapp.view.dodawanie.DodawanieStudentaView;
import org.example.nauczycieldesktopapp.view.zarzadzanie.ZarzadzanieGrupamiView;
import org.example.nauczycieldesktopapp.view.zarzadzanie.ZarzadzanieStudentamiView;
import org.example.nauczycieldesktopapp.view.zarzadzanie.ZarzadzanieTerminamiView;

import java.io.IOException;

/**
 * Kontroler głównego menu aplikacji.
 * <p>
 * Obsługuje interakcje użytkownika z przyciskami głównego menu,
 * uruchamiając odpowiednie widoki do zarządzania studentami, grupami, terminami
 * oraz sprawdzania obecności.
 */
public class MainMenuController {

    /** Przycisk do zarządzania grupami */
    @FXML public Button ZGrup;

    /** Przycisk do sprawdzania obecności */
    @FXML public Button DzOb;

    /** Przycisk do zarządzania studentami */
    @FXML private Button ZStud;

    /** Przycisk do dodawania nowego studenta */
    @FXML private Button DodStud;

    /** Panel po prawej stronie interfejsu (możliwe miejsce do wyświetlania zawartości) */
    @FXML private AnchorPane rightAP;

    /** Pole tekstowe wyświetlające nazwę zalogowanego użytkownika */
    @FXML private Text userName;

    /** Ikona do zamknięcia okna aplikacji */
    @FXML private ImageView closeIcon;

    /**
     * Ustawia nazwę użytkownika wyświetlaną w głównym menu.
     *
     * @param username nazwa użytkownika do wyświetlenia
     */
    public void setUserName(String username) {
        userName.setText(username);
    }

    /**
     * Zamknięcie okna aplikacji po kliknięciu ikony zamknięcia.
     *
     * @param event zdarzenie kliknięcia myszy na ikonę zamknięcia
     */
    @FXML
    private void handleClose(javafx.scene.input.MouseEvent event) {
        Stage stage = (Stage) closeIcon.getScene().getWindow();
        stage.close();
    }

    /**
     * Obsługa kliknięcia przycisku do zarządzania studentami.
     * Uruchamia widok zarządzania studentami.
     *
     * @param event zdarzenie kliknięcia przycisku
     * @throws IOException jeśli wystąpi błąd ładowania widoku
     */
    @FXML
    private void handleZarzadzanieStudentami(ActionEvent event) throws IOException {
        System.out.println("Zarządzanie studentami clicked");
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        ZarzadzanieStudentamiView.launch(stage);
    }

    /**
     * Obsługa kliknięcia przycisku do dodawania nowego studenta.
     * Uruchamia widok dodawania studenta.
     *
     * @param event zdarzenie kliknięcia przycisku
     * @throws IOException jeśli wystąpi błąd ładowania widoku
     */
    @FXML
    private void handleDodajStudenta(ActionEvent event) throws IOException {
        System.out.println("Dodaj studenta clicked");
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        DodawanieStudentaView.launch(stage);
    }

    /**
     * Obsługa kliknięcia przycisku do dodawania nowej grupy.
     * Uruchamia widok dodawania grupy.
     *
     * @param event zdarzenie kliknięcia przycisku
     * @throws IOException jeśli wystąpi błąd ładowania widoku
     */
    @FXML
    private void handleDodajGrupe(ActionEvent event) throws IOException {
        System.out.println("Dodaj grupe clicked");
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        DodawanieGrupyView.launch(stage);
    }

    /**
     * Obsługa kliknięcia przycisku do zarządzania grupami.
     * Uruchamia widok zarządzania grupami.
     *
     * @param event zdarzenie kliknięcia przycisku
     * @throws IOException jeśli wystąpi błąd ładowania widoku
     */
    @FXML
    private void handleZarzadanieGrupami(ActionEvent event) throws IOException {
        System.out.println("Zarządaj grupami clicked");
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        ZarzadzanieGrupamiView.launch(stage);
    }

    /**
     * Obsługa kliknięcia przycisku do zarządzania terminami.
     * Uruchamia widok zarządzania terminami.
     *
     * @param event zdarzenie kliknięcia przycisku
     * @throws IOException jeśli wystąpi błąd ładowania widoku
     */
    @FXML
    private void handleZarzadzanieTerminami(ActionEvent event) throws IOException {
        System.out.println("Zarządzenie Terminami Clicked");
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        ZarzadzanieTerminamiView.launch(stage);
    }

    /**
     * Obsługa kliknięcia przycisku do sprawdzania obecności.
     * Uruchamia widok sprawdzania obecności.
     *
     * @param event zdarzenie kliknięcia przycisku
     * @throws IOException jeśli wystąpi błąd ładowania widoku
     */
    @FXML
    public void handleSprawdzObecnosci(ActionEvent event) throws IOException {
        System.out.println("Sprawdź Obecnosci Clicked");
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        SprawdzObecnosciView.launch(stage);
    }
}
