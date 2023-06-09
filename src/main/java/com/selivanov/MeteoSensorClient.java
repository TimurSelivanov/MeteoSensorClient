package com.selivanov;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MeteoSensorClient {
    public static void main(String[] args) {

        final String sensorName = "Test Sensor";

        registerSensor(sensorName);

        Random random = new Random();

        double maxTemperature = 50.0;
        for (int i = 0; i < 100; i++) {
            System.out.println(i);
            sendMeasurement(random.nextDouble() * maxTemperature, random.nextBoolean(), sensorName);
        }

    }

    private static void makePostRequestWithJsonData(String url, Map<String, Object> jsonData) {
        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(jsonData, headers);

        try {
            restTemplate.postForObject(url, request, String.class);
        } catch (HttpClientErrorException exception) {
            System.out.println("Error");
            System.out.println(exception.getMessage());
        }
    }

    private static void registerSensor(String sensorName) {
        final String url = "http://localhost:8080/sensors/registration";

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("name", sensorName);

        makePostRequestWithJsonData(url, jsonData);
    }

    private static void sendMeasurement(double value, boolean isRaining, String sensorName) {
        final String url = "http://localhost:8080/measurements/add";

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("value", value);
        jsonData.put("isRaining", isRaining);
        jsonData.put("sensor", Map.of("name", sensorName));

        makePostRequestWithJsonData(url, jsonData);
    }
}
