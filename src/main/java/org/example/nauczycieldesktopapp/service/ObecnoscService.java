package org.example.nauczycieldesktopapp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.nauczycieldesktopapp.model.Obecnosc;
import org.example.nauczycieldesktopapp.model.Termin;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Serwis odpowiedzialny za komunikację z REST API dotyczącym obecności.
 */
public class ObecnoscService {

    private static final String BASE_URL = "http://3.71.11.3:8080/obecnosci";

    /**
     * Wysyła (aktualizuje lub tworzy) obecność na serwerze za pomocą metody PUT.
     *
     * @param obecnosc Obiekt obecności do wysłania
     * @return true jeśli operacja zakończyła się sukcesem (HTTP 200), false w przeciwnym razie
     * @throws IOException gdy wystąpi błąd sieciowy
     */
    public boolean sendObecnosc(Obecnosc obecnosc) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> dane = new HashMap<>();
        dane.put("id", obecnosc.getId());
        dane.put("status", obecnosc.getStatus().toString());

        Map<String, Object> studentMap = new HashMap<>();
        studentMap.put("id", obecnosc.getStudentId());
        dane.put("student", studentMap);

        Map<String, Object> terminMap = new HashMap<>();
        terminMap.put("id", obecnosc.getTerminID());
        dane.put("termin", terminMap);

        String json = mapper.writeValueAsString(dane);

        URL url = new URL(BASE_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("PUT");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        try (var os = con.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int status = con.getResponseCode();
        con.disconnect();

        return status == HttpURLConnection.HTTP_OK;
    }

    /**
     * Usuwa pojedynczą obecność na podstawie jej ID.
     *
     * @param obecnosc Obecność do usunięcia
     * @return true jeśli usunięcie powiodło się, false w przeciwnym razie
     * @throws IOException gdy wystąpi błąd sieciowy
     */
    public boolean deleteObecnosc(Obecnosc obecnosc) throws IOException {
        String urlString = BASE_URL + "/" + obecnosc.getId();
        return sendDeleteRequest(urlString);
    }

    /**
     * Usuwa wszystkie obecności powiązane z danym terminem.
     *
     * @param termin Termin, dla którego obecności mają zostać usunięte
     * @return true jeśli usunięcie powiodło się, false w przeciwnym razie
     * @throws IOException gdy wystąpi błąd sieciowy
     */
    public boolean deleteObecnoscByTermin(Termin termin) throws IOException {
        String urlString = BASE_URL + "/termin/" + termin.getId();
        return sendDeleteRequest(urlString);
    }

    /**
     * Pobiera listę obecności powiązanych z danym terminem.
     *
     * @param termin Termin, dla którego pobieramy obecności
     * @return lista obecności powiązanych z terminem
     * @throws IOException gdy wystąpi błąd sieciowy
     */
    public List<Obecnosc> getObecnosciByTermin(Termin termin) throws IOException {
        String urlString = BASE_URL + "/termin/" + termin.getId();
        URL endpoint = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) endpoint.openConnection();
        conn.setRequestMethod("GET");

        try (Scanner scanner = new Scanner(conn.getInputStream())) {
            StringBuilder json = new StringBuilder();
            while (scanner.hasNextLine()) {
                json.append(scanner.nextLine());
            }

            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.readValue(json.toString(), new TypeReference<>() {});
        } finally {
            conn.disconnect();
        }
    }

    /**
     * Pomocnicza metoda do wysyłania żądań DELETE.
     *
     * @param urlString adres URL endpointu
     * @return true jeśli odpowiedź serwera to 200 lub 204, false w przeciwnym razie
     * @throws IOException gdy wystąpi błąd sieciowy
     */
    private boolean sendDeleteRequest(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");

        int responseCode = connection.getResponseCode();
        connection.disconnect();

        return responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT;
    }
}
