package com.steve.http.client;

import com.steve.domain.Aircraft;
import com.steve.domain.Airport;
import com.steve.domain.City;
import com.steve.domain.Passenger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class RESTClient {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl;

    public RESTClient(String baseUrl) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.baseUrl = "http://localhost:8080";
    }

    public List<City> getAllCities() {
        return fetchList("/cities", new TypeReference<List<City>>() {});
    }

    public List<Airport> getAllAirports() {
        return fetchList("/airports", new TypeReference<List<Airport>>() {});
    }

    public List<Aircraft> getAllAircraft() {
        System.out.println("Fetching all aircraft from " + baseUrl + "/aircrafts");
        List<Aircraft> aircraftList = fetchList("/aircrafts", new TypeReference<List<Aircraft>>() {});
        if (aircraftList != null) {
            System.out.println("Successfully fetched " + aircraftList.size() + " aircraft");
        } else {
            System.err.println("Failed to fetch aircraft");
        }
        return aircraftList;
    }

    public List<Passenger> getAllPassengers() {
        return fetchList("/passengers", new TypeReference<List<Passenger>>() {});
    }

    public boolean addPassengerToAircraft(Long passengerId, Long aircraftId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/passengers/" + passengerId + "/aircrafts/" + aircraftId))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response for adding passenger to aircraft: " + response.body());

            return response.statusCode() == 200;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    private <T> List<T> fetchList(String endpoint, TypeReference<List<T>> typeReference) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + endpoint))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response for " + endpoint + ": " + response.body());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), typeReference);
            } else {
                System.err.println("Error: " + response.body());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}