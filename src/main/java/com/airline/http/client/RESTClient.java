package com.airline.http.client;

import com.airline.domain.Aircraft;
import com.airline.domain.Airport;
import com.airline.domain.City;
import com.airline.domain.Passenger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
        this.objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.baseUrl = baseUrl;
    }

    public List<City> getAllCities() {
        return fetchList("/cities", new TypeReference<List<City>>() {});
    }

    public List<Aircraft> getAircraftByPassengerId(Long passengerId) {
        return fetchList("/passengers/" + passengerId + "/aircrafts", new TypeReference<List<Aircraft>>() {});
    }

    public List<Airport> getAllAirports() {
        return fetchList("/airports", new TypeReference<List<Airport>>() {});
    }

    public Passenger getPassengerById(Long passengerId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/passengers/" + passengerId))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), Passenger.class);
            } else {
                System.err.println("Error: " + response.body());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Exception occurred: " + e.getMessage());
            return null;
        }
    }


    public List<Aircraft> getAllAircraft() {
        System.out.println("Fetching all aircraft from " + baseUrl + "/aircraft");
        List<Aircraft> aircraftList = fetchList("/aircraft", new TypeReference<List<Aircraft>>() {});
        if (aircraftList == null) {
            System.err.println("Failed to fetch aircraft");
            return List.of();
        }
        System.out.println("Successfully fetched " + aircraftList.size() + " aircraft");
        return aircraftList;
    }

    public City getCityById(Long cityId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/cities/" + cityId))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), City.class);
            } else {
                System.err.println("Error: " + response.body());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Exception occurred: " + e.getMessage());
            return null;
        }
    }

    public List<Passenger> getAllPassengers() {
        return fetchList("/passengers", new TypeReference<List<Passenger>>() {});
    }

    private <T> T fetchObject(String endpoint, Class<T> clazz) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + endpoint))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), clazz);
            } else {
                System.err.println("Error: " + response.body());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Exception occurred: " + e.getMessage());
            return null;
        }
    }

    private <T> List<T> fetchList(String endpoint, TypeReference<List<T>> typeReference) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + endpoint))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), typeReference);
            } else {
                System.err.println("Error: " + response.body());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Exception occurred: " + e.getMessage());
            return null;
        }
    }
}