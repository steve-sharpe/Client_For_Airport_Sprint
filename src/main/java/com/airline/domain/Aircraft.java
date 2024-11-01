package com.airline.domain;

import java.util.Objects;
import java.util.Set;

public class Aircraft {
    private Long id;
    private String type;
    private int numberOfPassengers;
    private String airlineName;
    private Set<Passenger> passengers;
    private Set<Airport> airport; // Correct field

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Set<Airport> getAirport() {
        return airport;
    }

    public void setAirport(Set<Airport> airport) {
        this.airport = airport;
    }

    // Default constructor
    public Aircraft() {}

}