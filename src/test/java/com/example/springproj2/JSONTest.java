package com.example.springproj2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class JSONTest {
    @Test
    @DisplayName("Test is the response list")
    void jsonListTest() throws IOException {
        URL url = new URL("https://60a21d3f745cd70017576092.mockapi.io/api/v1/repos");
        URLConnection connection = url.openConnection();

        HttpURLConnection httpConnection = (HttpURLConnection) connection;
        httpConnection.setRequestMethod("GET");

        StringBuilder content;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()))) {
            String line;
            content = new StringBuilder();

            while ((line = in.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }

//            System.out.println(content.toString());

            try {
                JSONArray array = new JSONArray(content.toString());
//                System.out.println(array);

                Set<String> nameSet = new HashSet<>();
                Set<String> idSet = new HashSet<>();

                for (int i = 0; i < array.length(); i++){

                    JSONObject obj = array.getJSONObject(i);
                    String name = obj.getString("name");
                    String id = obj.getString("id");

                    System.out.println(String.format("Name: %s\nID: %s\n", name,id));
                    boolean containsName = nameSet.contains(name);
                    boolean containsId = idSet.contains(id);

                    if (containsName) {
                        fail("Name is not unique");
                    }
                    if (containsId) {
                        fail("ID is not unique");
                    }

                    idSet.add(id);
                    nameSet.add(name);
                }
            } catch (JSONException jsonException) {
                fail("JSON response is not valid list");
            }

            assertTrue(true);
        }
        httpConnection.disconnect();

    }
}