package it.units.gomokusdm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CLIControllerTest {

    @Test
    void test() {
        Board board = new BoardImplementation();
        board.setCell(Stone.BLACK, new Coordinates(0, 0));
        CLIController cli = CLIController.createInstance(System.out, System.in);
        cli.printBoard();
    }

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
        String inputString = "1\nPlayer One\nPlayer Two\n";
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(System.out, inputStream);
        cli.initializeGameCLI();
        Assertions.assertEquals("Player One", cli.getPlayer1().getUsername());
        Assertions.assertEquals("Player Two", cli.getPlayer2().getUsername());
    }

    @Test
    void testDefaultPlayersInitializationWithoutUsername() throws IOException {
        String inputString = "1\n\n\n";
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(System.out, inputStream);
        cli.initializeGameCLI();
        Assertions.assertEquals("Player_1", cli.getPlayer1().getUsername());
        Assertions.assertEquals("Player_2", cli.getPlayer2().getUsername());
    }

    @Test
    void testPlayersCoordinatesInput() throws IOException {
        String inputString = "1\n1\n"; //position (1,1)
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(System.out, inputStream);
        Player player = new Player("A", Stone.WHITE);
        Coordinates coordinates;
        try {
            coordinates = cli.getCoordinatesByPlayerInput(player);
        } catch (Game.InvalidMoveException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(new Coordinates(1, 1), coordinates);
    }

    @Test
    void testNonNumericPlayersCoordinatesInput() throws IOException {
        String inputString = "a\na\n";
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(System.out, inputStream);
        Player player = new Player("A", Stone.WHITE);
        Coordinates coordinates;
        try {
            coordinates = cli.getCoordinatesByPlayerInput(player);
        } catch (Game.InvalidMoveException e) {
            //throw new RuntimeException(e);
        }
        //TODO: assert exception
        //Assertions.assertNull(coordinates);
    }

    @Test
    void testCompleteGameSimulation() throws IOException {
        String inputString =
                """
                        1
                        player one
                        player two
                        9
                        10
                        9
                        8
                        9
                        11
                        9
                        7
                        9
                        12
                        9
                        6
                        9
                        13
                        9
                        5
                        """;
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(System.out, inputStream);
        Player player1 = cli.getPlayer1();
        //Player player2 = cli.getPlayer2();
        cli.initializeGameCLI();
        try {
            cli.startGameClI();
        } catch (Game.InvalidMoveException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(cli.getWinner(), player1);
    }

    @Test
    void testCompleteGameSimulationWithWrongPlayerInputs() throws IOException, NumberFormatException {
        String inputString =
                """
                        1
                        player one
                        player two
                        9
                        10
                        9
                        8
                        9
                        11
                        9
                        7
                        9
                        12
                        9
                        6
                        9
                        13
                        9
                        *!&%x
                        9
                        5
                        """;
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(System.out, inputStream);
        Player player1 = cli.getPlayer1();
        //Player player2 = cli.getPlayer2();
        cli.initializeGameCLI();
        try {
            cli.startGameClI();
        } catch (Game.InvalidMoveException e) {
            //throw new RuntimeException(e);
        }
        //TODO: assert exception
        //Assertions.assertEquals(cli.getWinner(), player1);
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
        try {
            cli.startGameClI();
        } catch (Game.InvalidMoveException e) {
            //throw new RuntimeException(e);
        }
        String cliOutput = byteArrayOutputStream.toString();

        //TODO: assert exception
        //Assertions.assertTrue(cliOutput.contains("Game has been stopped by player two"));
    }

}
