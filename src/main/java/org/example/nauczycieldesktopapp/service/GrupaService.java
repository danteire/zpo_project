package org.example.nauczycieldesktopapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.nauczycieldesktopapp.model.Grupa;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class GrupaService {

    public boolean addGrupa(Grupa grupa) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> dane = new HashMap<>();
        dane.put("nazwa", grupa.getNazwa());
        String json = mapper.writeValueAsString(dane);

        String restURL = "http://3.71.11.3:8080/groups";
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
