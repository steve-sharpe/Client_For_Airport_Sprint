package com.airline.http.cli;

import com.airline.domain.Aircraft;
import com.airline.domain.Airport;
import com.airline.domain.City;
import com.airline.domain.Passenger;
import com.airline.http.client.RESTClient;

import java.io.IOException;
import java.util.*;

public class CommandLineInterface {
    private final RESTClient restClient;

    public CommandLineInterface(String baseUrl) {
        this.restClient = new RESTClient(baseUrl);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n===================================");
            System.out.println("          MAIN MENU               ");
            System.out.println("===================================");
            System.out.println("1. List Passengers");
            System.out.println("2. List Aircraft");
            System.out.println("3. List Airports");
            System.out.println("4. List Cities");
            System.out.println("5. List Passengers with Aircraft Info");
            System.out.println("6. List Cities with Airport Info");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    showSubMenu("Passengers");
                    break;
                case 2:
                    showSubMenu("Aircraft");
                    break;
                case 3:
                    showSubMenu("Airports");
                    break;
                case 4:
                    showSubMenu("Cities");
                    break;
                case 5:
                    listPassengersWithAircraft();
                    break;
                case 6:
                    listCitiesWithAirports();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showSubMenu(String entity) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n===================================");
        System.out.println("        " + entity.toUpperCase() + " MENU          ");
        System.out.println("===================================");
        System.out.println("1. Show all " + entity);
        System.out.println("2. Show " + entity + " by ID");
        System.out.print("Enter your choice: ");

        int subChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (subChoice) {
            case 1:
                switch (entity) {
                    case "Passengers":
                        listPassengers();
                        break;
                    case "Aircraft":
                        listAircraft();
                        break;
                    case "Airports":
                        listAirports();
                        break;
                    case "Cities":
                        listCities();
                        break;
                }
                break;
            case 2:
                System.out.print("Enter " + entity.substring(0, entity.length() - 1) + " ID: ");
                Long id = scanner.nextLong();
                scanner.nextLine(); // consume newline
                switch (entity) {
                    case "Passengers":
                        showPassengerById(id);
                        break;
                    case "Aircraft":
                        showAircraftById(id);
                        break;
                    case "Airports":
                        showAirportById(id);
                        break;
                    case "Cities":
                        showCityById(id);
                        break;
                }
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void listPassengersWithAircraft() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Passenger ID: ");
        Long passengerId = scanner.nextLong();
        scanner.nextLine(); // consume newline

        Passenger passenger = restClient.getPassengerById(passengerId);
        if (passenger != null) {
            System.out.println("\n===================================");
            System.out.println("     PASSENGER WITH AIRCRAFT       ");
            System.out.println("===================================");
            System.out.printf("%-5s %-20s %-15s%n", "ID", "Name", "Phone");
            System.out.println("-----------------------------------");
            System.out.printf("%-5d %-20s %-15s%n", passenger.getId(), passenger.getFirstName() + " " + passenger.getLastName(), passenger.getPhoneNumber());
            List<Aircraft> aircrafts = passenger.getAircraft();
            if (aircrafts != null && !aircrafts.isEmpty()) {
                System.out.println("\n  Aircraft:");
                System.out.printf("    %-5s %-20s %-10s%n", "ID", "Model", "Capacity");
                System.out.println("    ---------------------------------------------");
                aircrafts.forEach(aircraft -> {
                    System.out.printf("    %-5d %-20s %-10d%n", aircraft.getId(), aircraft.getType(), aircraft.getNumberOfPassengers());
                });
            } else {
                System.out.println("  Aircraft: N/A");
            }
            System.out.println();
        } else {
            System.out.println("Passenger not found.");
        }
    }

    private void listCitiesWithAirports() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter City ID: ");
        Long cityId = scanner.nextLong();
        scanner.nextLine(); // consume newline

        City city = restClient.getCityById(cityId);
        if (city != null) {
            System.out.println("\n===================================");
            System.out.println("       CITY WITH AIRPORTS          ");
            System.out.println("===================================");
            System.out.printf("%-5s %-20s %-10s%n", "ID", "Name", "Population");
            System.out.println("-----------------------------------");
            System.out.printf("%-5d %-20s %-10d%n", city.getId(), city.getName(), city.getPopulation());
            List<Airport> airports = restClient.getAirportsByCityId(city.getId());
            if (airports != null && !airports.isEmpty()) {
                System.out.println("\n  Airports:");
                System.out.printf("    %-5s %-30s %-5s%n", "ID", "Airport Name", "Code");
                System.out.println("    ---------------------------------------------");
                airports.forEach(airport -> {
                    System.out.printf("    %-5d %-30s %-5s%n", airport.getId(), airport.getName(), airport.getCode());
                });
            } else {
                System.out.println("  No airports found.");
            }
            System.out.println();
        } else {
            System.out.println("City not found.");
        }
    }

    private void showPassengerById(Long id) {
        Passenger passenger = restClient.getPassengerById(id);
        if (passenger != null) {
            System.out.println("\n===================================");
            System.out.println("        PASSENGER DETAILS          ");
            System.out.println("===================================");
            System.out.printf("ID: %-5d%nName: %-20s %-20s%nPhone: %-15s%n", passenger.getId(), passenger.getFirstName(), passenger.getLastName(), passenger.getPhoneNumber());
        } else {
            System.out.println("Passenger not found.");
        }
        System.out.println();
    }

    private void showAircraftById(Long id) {
        Aircraft aircraft = restClient.getAircraftById(id);
        if (aircraft != null) {
            System.out.println("\n===================================");
            System.out.println("        AIRCRAFT DETAILS           ");
            System.out.println("===================================");
            System.out.printf("ID: %-5d%nModel: %-20s%nCapacity: %-10d%n", aircraft.getId(), aircraft.getType(), aircraft.getNumberOfPassengers());
        } else {
            System.out.println("Aircraft not found.");
        }
        System.out.println();
    }

    private void showAirportById(Long id) {
        Airport airport = restClient.getAirportById(id);
        if (airport != null) {
            System.out.println("\n===================================");
            System.out.println("         AIRPORT DETAILS           ");
            System.out.println("===================================");
            System.out.printf("ID: %-5d%nName: %-20s%nCode: %-5s%n", airport.getId(), airport.getName(), airport.getCode());
            City city = airport.getCity();
            if (city != null) {
                System.out.printf("City: %-20s (ID: %-5d)%n", city.getName(), city.getId());
            } else {
                System.out.println("City: N/A");
            }
        } else {
            System.out.println("Airport not found.");
        }
        System.out.println();
    }

    private void showCityById(Long id) {
        City city = restClient.getCityById(id);
        if (city != null) {
            System.out.println("\n===================================");
            System.out.println("          CITY DETAILS             ");
            System.out.println("===================================");
            System.out.printf("ID: %-5d%nName: %-20s%nPopulation: %-10d%n", city.getId(), city.getName(), city.getPopulation());
        } else {
            System.out.println("City not found.");
        }
        System.out.println();
    }

    public void listPassengers() {
        List<Passenger> passengers = restClient.getPassengers();
        if (passengers != null) {
            System.out.println("\n===================================");
            System.out.println("           PASSENGERS              ");
            System.out.println("===================================");
            System.out.printf("%-5s %-20s %-15s%n", "ID", "Name", "Phone");
            System.out.println("-----------------------------------");
            passengers.forEach(passenger ->
                    System.out.printf("%-5d %-20s %-15s%n", passenger.getId(), passenger.getFirstName() + " " + passenger.getLastName(), passenger.getPhoneNumber())
            );
        } else {
            System.out.println("No passengers found.");
        }
        System.out.println();
    }

    private void listAircraft() {
        List<Aircraft> aircrafts = restClient.getAllAircraft();
        if (aircrafts != null) {
            System.out.println("\n===================================");
            System.out.println("           AIRCRAFT               ");
            System.out.println("===================================");
            System.out.printf("%-5s %-20s %-10s%n", "ID", "Model", "Capacity");
            System.out.println("-----------------------------------");
            aircrafts.forEach(aircraft ->
                    System.out.printf("%-5d %-20s %-10d%n", aircraft.getId(), aircraft.getType(), aircraft.getNumberOfPassengers())
            );
        } else {
            System.out.println("No aircraft found.");
        }
        System.out.println();
    }

    private void listAirports() {
        List<Airport> airports = restClient.getAllAirports();
        if (airports != null && !airports.isEmpty()) {
            System.out.println("\n===================================");
            System.out.println("           AIRPORTS               ");
            System.out.println("===================================");
            System.out.printf("%-5s %-20s %-5s%n", "ID", "Name", "Code");
            System.out.println("-----------------------------------");
            airports.forEach(airport -> {
                System.out.printf("%-5d %-20s %-5s%n", airport.getId(), airport.getName(), airport.getCode());
            });
        } else {
            System.out.println("No airports found.");
        }
        System.out.println();
    }

    private void listCities() {
        List<City> cities = restClient.getAllCities();
        if (cities != null) {
            System.out.println("\n===================================");
            System.out.println("           CITIES                 ");
            System.out.println("===================================");
            System.out.printf("%-5s %-20s %-10s%n", "ID", "Name", "Population");
            System.out.println("-----------------------------------");
            cities.forEach(city ->
                    System.out.printf("%-5d %-20s %-10d%n", city.getId(), city.getName(), city.getPopulation())
            );

        } else {
            System.out.println("No cities found.");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        CommandLineInterface cli = new CommandLineInterface("http://localhost:8080");
        cli.run();
    }
}
