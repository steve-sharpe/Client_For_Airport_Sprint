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

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public List<Aircraft> getAircraftByPassengerId(Long passengerId) {
        String endpoint = "/passengers/" + passengerId + "/aircrafts";
        return fetchList(endpoint, new TypeReference<List<Aircraft>>() {});
    }

    public List<Airport> getAllAirports() {
        return fetchList("/airports", new TypeReference<List<Airport>>() {});
    }

    public Passenger getPassengerById(Long id) {
        return fetchObject("/passengers/" + id, Passenger.class);
    }

    public List<Passenger> getPassengersByCityId(Long cityId) {
        return fetchList("/cities/" + cityId + "/passengers", new TypeReference<List<Passenger>>() {});
    }

    public List<Airport> getAirportsByCityId(Long cityId) {
        return fetchList("/cities/" + cityId + "/airports", new TypeReference<List<Airport>>() {});
    }

    public City getCityById(Long cityId) {
        return fetchObject("/cities/" + cityId, City.class);
    }

    public Airport getAirportById(Long airportId) {
        return fetchObject("/airports/" + airportId, Airport.class);
    }

    public Aircraft getAircraftById(Long aircraftId) {
        return fetchObject("/aircraft/" + aircraftId, Aircraft.class);
    }

    public List<Aircraft> getAllAircraft() {
        return fetchList("/aircraft", new TypeReference<List<Aircraft>>() {});
    }

    public List<City> getAllCities() {
        return fetchList("/cities", new TypeReference<List<City>>() {});
    }

    public List<Passenger> getPassengers() {
        return fetchList("/passengers", new TypeReference<List<Passenger>>() {});
    }

    private <T> T fetchObject(String endpoint, Class<T> clazz) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + endpoint))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Handle status codes
            if (response.statusCode() == 200 && response.body() != null && !response.body().isEmpty()) {
                return objectMapper.readValue(response.body(), clazz);
            } else if (response.statusCode() == 204) {
                System.out.println("No content for the requested resource: " + endpoint);
                return null; // Handle No Content
            } else {
                System.err.println("Error (" + response.statusCode() + "): " + response.body());
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

            // Handle status codes
            if (response.statusCode() == 200 && response.body() != null && !response.body().isEmpty()) {
                return objectMapper.readValue(response.body(), typeReference);
            } else if (response.statusCode() == 204) {
                System.out.println("No content for the requested resource: " + endpoint);
                return List.of(); // Return empty list for No Content
            } else if (response.statusCode() == 404) {
                System.err.println("Error 404: Resource not found for endpoint: " + endpoint);
                return List.of();
            } else {
                System.err.println("Error (" + response.statusCode() + "): " + response.body());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Exception occurred: " + e.getMessage());
            return null;
        }
    }
}
