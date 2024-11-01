package com.airline.domain;
import java.util.Objects;
import java.util.Set;

public class Airport {
    private Long id;
    private String name;
    private String code;
    private Set<Aircraft> aircrafts;
    private City city;

    // Default constructor
    public Airport() {}

    // Parameterized constructor
    public Airport(Long id, String name, String code, Set<Aircraft> aircrafts) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.aircrafts = aircrafts;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Aircraft> getAircrafts() {
        return aircrafts;
    }

    public void setAircrafts(Set<Aircraft> aircrafts) {
        this.aircrafts = aircrafts;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", city=" + city +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return Objects.equals(id, airport.id) &&
                Objects.equals(name, airport.name) &&
                Objects.equals(code, airport.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code);
    }
}