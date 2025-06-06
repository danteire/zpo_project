package org.example.nauczycieldesktopapp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.nauczycieldesktopapp.model.Student;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StudentService {

    private static final String BASE_URL = "http://3.71.11.3:8080/students";

    /**
     * Pobiera wszystkich studentów z serwera.
     * @return lista studentów
     * @throws IOException w przypadku błędu sieciowego
     */
    public List<Student> getAllStudents() throws IOException {
        URL endpoint = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) endpoint.openConnection();
        conn.setRequestMethod("GET");

        try (Scanner scanner = new Scanner(conn.getInputStream())) {
            StringBuilder json = new StringBuilder();
            while (scanner.hasNextLine()) {
                json.append(scanner.nextLine());
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json.toString(), new TypeReference<>() {});
        } finally {
            conn.disconnect();
        }
    }

    /**
     * Usuwa studenta o podanym ID.
     * @param student student do usunięcia
     * @return true jeśli usunięto, false w przeciwnym razie
     */
    public boolean deleteStudent(Student student) {
        String urlString = BASE_URL + "/" + student.getId();
        return sendDeleteRequest(urlString);
    }

    /**
     * Dodaje nowego studenta.
     * @param student student do dodania
     * @return true jeśli dodano poprawnie
     * @throws IOException w przypadku błędu sieciowego
     */
    public boolean addStudent(Student student) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> dane = new HashMap<>();
        dane.put("imie", student.getImie());
        dane.put("nazwisko", student.getNazwisko());
        dane.put("nrIndeksu", student.getNrIndeksu());
        String json = mapper.writeValueAsString(dane);

        URL url = new URL(BASE_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("POST");
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
     * Pobiera studentów powiązanych z grupą o podanym ID.
     * @param groupId ID grupy
     * @return lista studentów z grupy
     * @throws IOException w przypadku błędu sieciowego
     */
    public List<Student> getStudentsByGrupa(Long groupId) throws IOException {
        String urlString = BASE_URL + "/groups/" + groupId;
        URL endpoint = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) endpoint.openConnection();
        conn.setRequestMethod("GET");

        try (Scanner scanner = new Scanner(conn.getInputStream())) {
            StringBuilder json = new StringBuilder();
            while (scanner.hasNextLine()) {
                json.append(scanner.nextLine());
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json.toString(), new TypeReference<>() {});
        } finally {
            conn.disconnect();
        }
    }

    /**
     * Pobiera studenta po ID.
     * @param id ID studenta
     * @return lista z jednym studentem (zalecam zmienić na zwracanie pojedynczego obiektu)
     * @throws IOException w przypadku błędu sieciowego
     */
    public List<Student> getStudentsByID(Long id) throws IOException {
        String urlString = BASE_URL + "/" + id;
        URL endpoint = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) endpoint.openConnection();
        conn.setRequestMethod("GET");

        try (Scanner scanner = new Scanner(conn.getInputStream())) {
            StringBuilder json = new StringBuilder();
            while (scanner.hasNextLine()) {
                json.append(scanner.nextLine());
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json.toString(), new TypeReference<>() {});
        } finally {
            conn.disconnect();
        }
    }

    /**
     * Usuwa przypisanie studenta do grupy.
     * @param student student, którego grupa ma zostać usunięta
     * @return true jeśli usunięto poprawnie
     */
    public boolean removeStudentFromGroup(Student student) {
        String urlString = BASE_URL + "/" + student.getId() + "/group";
        return sendDeleteRequest(urlString);
    }

    /**
     * Dodaje studenta do grupy.
     * @param studentId ID studenta
     * @param groupId ID grupy
     * @return true jeśli operacja powiodła się
     * @throws IOException w przypadku błędu sieciowego
     */
    public boolean addStudentToGroup(Long studentId, Long groupId) throws IOException {
        String urlString = BASE_URL + "/" + studentId + "/group/" + groupId;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");

        int responseCode = connection.getResponseCode();
        connection.disconnect();

        return responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT;
    }

    /**
     * Pomocnicza metoda do wysyłania żądań DELETE.
     * @param urlString adres endpointu
     * @return true jeśli odpowiedź serwera to 200 lub 204
     */
    private boolean sendDeleteRequest(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            connection.disconnect();
            return responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
