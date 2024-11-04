# Airline Management Command Line Interface

## Overview
The Airline Management Command Line Interface (CLI) is a Java application that interacts with a RESTful API to manage and display information about passengers, aircraft, airports, and cities. It provides a user-friendly menu-driven interface for users to perform various operations related to airline management.

## Features
- List all passengers, aircraft, airports, and cities.
- Retrieve details of a specific passenger, aircraft, airport, or city by ID.
- View passengers with their associated aircraft information.
- View cities with their associated airport information.
- Exit the application safely.

## Requirements
- Java 11 or higher
- Maven (for dependency management)
- A running instance of the REST API (e.g., Spring Boot application) accessible at `http://localhost:8080`

## Setup
1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```

2. **Build the project**:
   Ensure you have Maven installed and run:
   ```bash
   mvn clean install
   ```

3. **Run the Application**:
   After building the project, run the application using:
   ```bash
   mvn exec:java -Dexec.mainClass="com.airline.http.cli.CommandLineInterface"
   ```

4. **API Setup**:
   Make sure the REST API is running on your local machine (default `http://localhost:8080`). The API should have endpoints defined for:
   - Getting passengers
   - Getting aircraft
   - Getting airports
   - Getting cities
   - Getting a passenger by ID
   - Getting an aircraft by ID
   - Getting an airport by ID
   - Getting a city by ID

## Usage
Upon running the application, you will see the main menu with the following options:

1. **List Passengers**: View all passengers.
2. **List Aircraft**: View all aircraft.
3. **List Airports**: View all airports.
4. **List Cities**: View all cities.
5. **List Passengers with Aircraft Info**: View a specific passenger's information along with their associated aircraft.
6. **List Cities with Airport Info**: View a specific city's information along with its associated airports.
7. **Exit**: Close the application.

### Example Interaction
```plaintext
===================================
          MAIN MENU               
===================================
1. List Passengers
2. List Aircraft
3. List Airports
4. List Cities
5. List Passengers with Aircraft Info
6. List Cities with Airport Info
7. Exit
Enter your choice: 1
```

## License
This project is licensed under the MIT License. See the LICENSE file for details.

## Contributing
Contributions are welcome! Please fork the repository and submit a pull request for any changes you make.

## Contact
For any inquiries or issues, please contact Steve @ steve.sharpe@keyin.com
