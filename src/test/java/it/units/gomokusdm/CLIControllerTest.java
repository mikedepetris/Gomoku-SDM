package it.units.gomokusdm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class CLIControllerTest {

    @Test
    void testCLICreation() {
        CLIController cli = CLIController.createInstance(System.out, System.in);
        Assertions.assertEquals(cli.getClass(), CLIController.class);
    }

    @Test
    void testGetCLI() {
        CLIController cli = CLIController.createInstance(System.out, System.in);
        Assertions.assertEquals(CLIController.getInstance(), cli);
    }

    @Test
    void testPlayersInitialization() throws IOException {
        String inputString = "Player One\nPlayer Two\n";
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(System.out, inputStream);
        cli.startGameCLI();
        Assertions.assertEquals("Player One", cli.getPlayer1().getUsername());
        Assertions.assertEquals("Player Two", cli.getPlayer2().getUsername());
    }

    @Test
    void testPlayersInitializationWithoutUsername() throws IOException {
        String inputString = "\n\n";
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(System.out, inputStream);
        cli.startGameCLI();
        Assertions.assertEquals("Player_1", cli.getPlayer1().getUsername());
        Assertions.assertEquals("Player_2", cli.getPlayer2().getUsername());
    }

    @Test
    void testPlayersCoordinatesInput() throws IOException {
        String inputString = "1\n1\n"; //position (1,1)
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(System.out, inputStream);
        Player player= new Player("A", Colour.WHITE);
        Coordinates coordinates = cli.getCoordinatesByPlayerInput(player);
        Assertions.assertEquals(new Coordinates(1,1), coordinates);
    }
}
