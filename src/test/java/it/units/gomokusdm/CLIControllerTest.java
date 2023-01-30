package it.units.gomokusdm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
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
        String inputString = "1%sPlayer One%sPlayer Two%s".formatted(System.lineSeparator()
                , System.lineSeparator(), System.lineSeparator());
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(System.out, inputStream);
        cli.initializeGameCLI();
        Assertions.assertEquals("Player One", cli.getPlayer1().getUsername());
        Assertions.assertEquals("Player Two", cli.getPlayer2().getUsername());
    }

    @Test
    void testDefaultPlayersInitializationWithoutUsername() throws IOException {
        String inputString = "1" + System.lineSeparator() + System.lineSeparator() + System.lineSeparator();
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(System.out, inputStream);
        cli.initializeGameCLI();
        Assertions.assertEquals("Player_1", cli.getPlayer1().getUsername());
        Assertions.assertEquals("Player_2", cli.getPlayer2().getUsername());
    }

    @Test
    void testPlayersCoordinatesInput() throws IOException, CLIController.WrongStringFormatException {
        String inputString = "1,1" + System.lineSeparator(); //position (1,1)
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(System.out, inputStream);
        Player player = new Player("A", Stone.WHITE);
        String input = cli.getPlayerInput(player);
        Coordinates coordinates = cli.getCoordinatesFromString(input);
        Assertions.assertEquals(new Coordinates(1, 1), coordinates);
    }

    @Test
    void testNonNumericPlayersCoordinatesInput() throws IOException {
        String inputString = "a" + System.lineSeparator() + "a" + System.lineSeparator();
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(System.out, inputStream);
        Player player = new Player("A", Stone.WHITE);
        String input = cli.getPlayerInput(player);
        Assertions.assertThrowsExactly(CLIController.WrongStringFormatException.class,
                () -> cli.getCoordinatesFromString(input));
    }

    @Test
    void testCompleteGameSimulation() throws IOException {
        String inputString =
                """
                        1
                        player one
                        player two
                        9, 10
                        9, 8
                        9, 11
                        9, 7
                        9, 12
                        9, 6
                        9, 13
                        9, 5
                        """;
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(System.out, inputStream);
        Player player1 = cli.getPlayer1();
        cli.initializeGameCLI();
        cli.startGameClI();
        Assertions.assertEquals(cli.getWinner(), player1);
    }

    @Test
    void testCompleteGameSimulationWithWrongPlayerInputs() throws IOException, NumberFormatException {
        String inputString =
                """
                        1
                        player one
                        player two
                        9, 10
                        9, 8
                        9, 11
                        9, 7
                        9, 12
                        9, 6
                        9, 13
                        9, *!&%x
                        9, 5
                        """;
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(System.out, inputStream);
        Player player1 = cli.getPlayer1();
        cli.initializeGameCLI();
        cli.startGameClI();
        Assertions.assertEquals(cli.getWinner(), player1);
    }

    @Test
    void testGameStoppedByPlayerInputs() throws IOException {
        String inputString =
                """
                        1
                        player one
                        player two
                        STOP
                        """;
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream outputStream = new PrintStream(byteArrayOutputStream);
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(outputStream, inputStream);
        cli.initializeGameCLI();
        cli.startGameClI();
        String cliOutput = byteArrayOutputStream.toString();

        Assertions.assertTrue(cliOutput.contains("Game has been stopped by "));
    }


}
