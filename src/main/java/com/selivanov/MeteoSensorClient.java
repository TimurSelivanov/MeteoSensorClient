package com.selivanov;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class MeteoSensorClient {
    public static void main(String[] args) {

        final String sensorName = "Test Sensor";
    }

    private static void makePostRequestWithJsonData(String url, Map<String, Object> jsonData) {
        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<Object>(jsonData, headers);

        try {
            restTemplate.patchForObject(url, request, String.class);
        } catch (HttpClientErrorException exception) {
            System.out.println("Error");
            System.out.println(exception.getMessage());
        }
    }
}
