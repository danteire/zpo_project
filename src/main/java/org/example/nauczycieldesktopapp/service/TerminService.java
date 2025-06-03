package org.example.nauczycieldesktopapp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.nauczycieldesktopapp.model.Student;
import org.example.nauczycieldesktopapp.model.Termin;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

//GET http://3.71.11.3:8080/terminy

public class TerminService {
    public List<Termin> getAllTermins() throws IOException {
        String url = "http://3.71.11.3:8080/terminy";
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

    public boolean deleteTermin(Termin termin) throws IOException {
        try {
            String restURL = "http://3.71.11.3:8080/terminy";
            URL url = new URL(restURL + "/" + termin.getId());
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

    public boolean addTermin(Termin termin) throws IOException {
        ObjectMapper mapper = new ObjectMapper();



        Map<String, Object> dane = new HashMap<>();
        dane.put("data", termin.getData().toString());
        System.out.println(dane);
        Map<String, Object> grupaMap = new HashMap<>();
        grupaMap.put("id", termin.getGrupa().getId());
        System.out.println(dane);
        dane.put("grupa", grupaMap);

        String json = mapper.writeValueAsString(dane);

        String restURL = "http://3.71.11.3:8080/terminy";
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

    public List<Termin> getTerminByGrupa(Long id) throws IOException {
        // GET IP/students/groups/_IDGRUPY_
        String restURL = "http://3.71.11.3:8080/terminy/group/" + id;
        URL endpoint = new URL(restURL);
        HttpURLConnection conn = (HttpURLConnection) endpoint.openConnection();
        conn.setRequestMethod("GET");

        Scanner scanner = new Scanner(conn.getInputStream());
        StringBuilder json = new StringBuilder();
        while (scanner.hasNextLine()) {
            json.append(scanner.nextLine());
        }
        scanner.close();

        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        System.out.println(json.toString());
        return mapper.readValue(json.toString(), new TypeReference<>() {});
    }
}
