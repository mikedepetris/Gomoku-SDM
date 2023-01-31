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
        String inputString = """
                2
                PlayerOne
                PlayerTwo
                7, 8
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
        String outputBoardExpected =                 "0\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "1\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "2\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "3\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "4\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "5\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "6\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "7\t*---*---*---*---*---*---*---B---W---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "8\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "9\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "10\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "11\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "12\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "13\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "14\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t0\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10\t11\t12\t13\t14\t" +
                System.lineSeparator();

        Assertions.assertTrue(cliOutput.contains(outputBoardExpected));
    }

    @Test
    void testNonNumericPlayersCoordinatesInput() throws IOException {
        String inputString =
                """
                        1
                        player one
                        player two
                        abc, def
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

        Assertions.assertTrue(cliOutput.contains("Invalid coordinates! String doesn't match the expected format: row,col"));
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
