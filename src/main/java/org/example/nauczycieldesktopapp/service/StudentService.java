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

    public List<Student> getAllStudents() throws IOException {
        String restURL = "http://3.71.11.3:8080/students";
        URL endpoint = new URL(restURL);
        HttpURLConnection conn = (HttpURLConnection) endpoint.openConnection();
        conn.setRequestMethod("GET");

        Scanner scanner = new Scanner(conn.getInputStream());
        StringBuilder json = new StringBuilder();
        while (scanner.hasNextLine()) {
            json.append(scanner.nextLine());
        }
        scanner.close();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json.toString(), new TypeReference<>() {});
    }

    public boolean deleteStudent(Student student) {
        try {
            String restURL = "http://3.71.11.3:8080/students";
            URL url = new URL(restURL + "/" + student.getId());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            connection.disconnect();
            return responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addStudent(Student student) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> dane = new HashMap<>();
        dane.put("imie", student.getImie());
        dane.put("nazwisko", student.getNazwisko());
        dane.put("nrIndeksu", student.getNrIndeksu());
        String json = mapper.writeValueAsString(dane);

        String restURL = "http://3.71.11.3:8080/students";
        URL url = new URL(restURL);
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

        return status == 200;
    }
}
