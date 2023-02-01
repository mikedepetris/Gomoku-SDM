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
    void testPlayersCoordinatesInput() throws IOException {
        String inputString = """
                2
                PlayerOne
                PlayerTwo
                8, h
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
        String outputBoardExpected =
                "Select board size to use for this game:" + System.lineSeparator() +
                        "\t1. 19x19 " + System.lineSeparator() +
                        " \t2. 15x15 " + System.lineSeparator() +
                        "Player 1 name:   Player 2 name:   " +
                        "(PlayerOne) Black player's first move must be in the center of the board. " +
                        System.lineSeparator() + System.lineSeparator() +
                        "0\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
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
                        "7\t*---*---*---*---*---*---*---B---*---*---*---*---*---*---*   " + System.lineSeparator() +
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
                        "\t0\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10\t11\t12\t13\t14\t" + System.lineSeparator() +
                        "It's PlayerTwo's turn. Insert \"STOP\" to end game. " + System.lineSeparator() +
                        "PlayerTwo insert coordinate [row, column]:   " + System.lineSeparator() +
                        "0\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
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
                        "\t0\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10\t11\t12\t13\t14\t" + System.lineSeparator() +
                        "It's PlayerOne's turn. Insert \"STOP\" to end game. " + System.lineSeparator() +
                        "PlayerOne insert coordinate [row, column]:   Game has been stopped by PlayerOne " +
                        System.lineSeparator();

        Assertions.assertEquals(outputBoardExpected, cliOutput);
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

        Assertions.assertTrue(
                cliOutput.contains("Invalid coordinates! String doesn't match the expected format: row,col"));
    }

    @Test
    void testCompleteGameSimulation() throws IOException {
//        1
//        player one
//        player two
//        9, 10
//        9, 8
//        9, 11
//        9, 7
//        9, 12
//        9, 6
//        9, 13
//        9, 5
        String inputString =
                """
                        1
                        player one
                        player two
                        10, j
                        10, h
                        10, k
                        10, g
                        10, l
                        10, f
                        10, m
                        10, e
                        """;
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(System.out, inputStream);
        Player player1 = cli.getPlayer1();
        cli.initializeGameCLI();
        cli.startGameClI();

        Assertions.assertEquals(cli.getWinner(), player1);

        String formattedBoard = cli.getFormattedBoard15to1andAtoO();

        String expectedOutputString = System.lineSeparator() +
                "19\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " +
                System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "18\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " +
                System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "17\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " +
                System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "16\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " +
                System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "15\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " +
                System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "14\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " +
                System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "13\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " +
                System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "12\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " +
                System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "11\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " +
                System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "10\t*---*---*---*---*---B---B---B---B---B---W---W---W---W---*---*---*---*---*   " +
                System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "9\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " +
                System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "8\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " +
                System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "7\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " +
                System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "6\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " +
                System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "5\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " +
                System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "4\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " +
                System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "3\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " +
                System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "2\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " +
                System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "1\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " +
                System.lineSeparator() +
                "\ta\tb\tc\td\te\tf\tg\th\ti\tj\tk\tl\tm\tn\to\tp\tq\tr\ts\t" + System.lineSeparator();
        Assertions.assertEquals(expectedOutputString, formattedBoard);

    }

    @Test
    void testCompleteGameSimulationWithWrongPlayerInputs() throws IOException, NumberFormatException {
        String inputString =
                //9, 10
                //9, 8
                //9, 11
                //9, 7
                //9, 12
                //9, 6
                //9, 13
                //9, *!&%x
                //9, 5
                """
                        1
                        player one
                        player two
                        10, j
                        10, h
                        10, k
                        10, g
                        10, l
                        10, f
                        10, m
                        9, *!&%x
                        10, e
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
