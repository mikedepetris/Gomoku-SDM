package it.units.gomokusdm;

import java.io.*;
import java.util.Scanner;

public class CLIController {

    private static CLIController cli = null;

    private PrintStream outputStream;
    private InputStream inputStream;
    private BufferedReader reader;
    private Board board;
    private Game game;
    private Player player1;
    private Player player2;

    public static CLIController createInstance(PrintStream outputStream, InputStream inputStream) {
        if (cli == null) cli = new CLIController(outputStream, inputStream);
        return cli;
    }

    public static CLIController getInstance() {
        return cli;
    }

    public static void closeInstance() {
        cli = null;
    }

    private CLIController(PrintStream outputStream, InputStream inputStream) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.player1 = new Player("", Colour.BLACK);
        this.player2 = new Player("", Colour.WHITE);
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void startGameCLI() throws IOException {
        outputStream.println("*************************\nGOMOKU\n*************************");
        setPlayerNames(player1);
        setPlayerNames(player2);
    }

    private void setPlayerNames(Player player) throws IOException {
        outputStream.printf("Player %d name:   ", player.equals(player1) ? 1 : 2);
        String playerName = reader.readLine();
        player.setUsername(playerName);
    }


}
