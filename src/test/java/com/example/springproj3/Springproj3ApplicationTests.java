package com.example.springproj3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import com.google.api.client.http.HttpHeaders;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;


@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Springproj3ApplicationTests {

    @LocalServerPort
    private int port;

    @Test
    public void AddUserTest(){
        String url = "http://localhost:"+port+"/api/users";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map = new HashMap<>();
        map.put("username", "test");
        map.put("password", "test");

        restTemplate.postForEntity(url, map, Void.class);


        String url2 = "http://localhost:"+port+"/api/users/test";
        HttpHeaders headers2 = HttpHeaders();
        HttpEntity<Object> entity2 = new HttpEntity<Object>(headers2);
        ResponseEntity<String> out2 = restTemplate.exchange(url2, HttpMethod.GET, entity2, String.class);

        assertEquals(out2.getStatusCode(), HttpStatus.OK);
    }

    private HttpHeaders HttpHeaders() {
        return null;
    }

}