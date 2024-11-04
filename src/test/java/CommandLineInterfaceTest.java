import com.airline.domain.Passenger;
import com.airline.http.cli.CommandLineInterface;
import com.airline.http.client.RESTClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommandLineInterfaceTest {

    @Mock
    private RESTClient mockRestClient;

    private CommandLineInterface commandLineInterfaceUnderTest;

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        // Inject mockRestClient using constructor injection
        commandLineInterfaceUnderTest = new CommandLineInterface("someStringArgument");

        // Redirect System.out to capture output for verification
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void testListPassengers() {
        // Set up a mock response for getPassengers()
        Passenger passenger = new Passenger();
        passenger.setFirstName("John");
        passenger.setId(1L);
        List<Passenger> passengerList = new ArrayList<>();
        passengerList.add(passenger);

        when(mockRestClient.getPassengers()).thenReturn(passengerList);

        // Run the method that prints passenger list
        commandLineInterfaceUnderTest.listPassengers();

        // Verify output contains the expected passenger name
        assertTrue(outputStreamCaptor.toString().contains("John"));
    }
}