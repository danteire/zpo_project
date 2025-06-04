package org.example.nauczycieldesktopapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.nauczycieldesktopapp.model.Obecnosc;
import org.example.nauczycieldesktopapp.model.Status;
import org.example.nauczycieldesktopapp.model.Termin;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ObecnoscService {

    // wysłać obecnosci ((termin.grupa.(&&)AllStudents) (status)) - > Obecnosci

    public boolean sendObecnosc(Obecnosc obecnosc) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> dane = new HashMap<>();
        dane.put("id", obecnosc.getId());
        dane.put("status", obecnosc.getStatus().toString());
        System.out.println(dane);

        Map<String, Object> studentMap = new HashMap<>();
        studentMap.put("id", obecnosc.getStudentId());
        System.out.println(studentMap);
        dane.put("student", studentMap);

        Map<String, Object> terminMap = new HashMap<>();
        terminMap.put("id", obecnosc.getTerminID());
        System.out.println(terminMap);
        dane.put("termin", terminMap);

        String json = mapper.writeValueAsString(dane);

        String restURL = "http://3.71.11.3:8080/obecnosci";
        URL url = new URL(restURL);
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

        return status == 200;
    }


    public boolean deletObecnosc(Obecnosc obecnosc) throws IOException {
        try {
            String restURL = "http://3.71.11.3:8080/obecnosci";
            URL url = new URL(restURL + "/" + obecnosc.getId());
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

    public boolean deleteObecnoscByTermin(Termin termin) throws IOException {
        try {
            String restURL = "http://3.71.11.3:8080/obecnosci/termin";
            URL url = new URL(restURL + "/" + termin.getId());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            connection.disconnect();
            return responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //obecnosci/termin/(terminID)
    public List<Obecnosc> getObecnosciByTermin(Termin termin) throws IOException {
        String url = "http://3.71.11.3:8080/obecnosci/termin/" + termin.getId();
        URL endpoint = new URL(url);
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
}
