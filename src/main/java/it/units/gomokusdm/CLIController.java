package it.units.gomokusdm;

import java.io.*;

public class CLIController {

    private static CLIController cli = null;

    private final PrintStream outputStream;
    private final InputStream inputStream;
    private final BufferedReader reader;
    private Board board;
    private Game game;
    private final Player player1;
    private final Player player2;
    private Player winner;

    private CLIController(PrintStream outputStream, InputStream inputStream) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.player1 = new Player("", Stone.BLACK);
        this.player2 = new Player("", Stone.WHITE);
        this.board = new Board();
        try {
            this.game = new Game(board, player1, player2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getWinner() {
        return winner;
    }

    public void initializeGameCLI() throws IOException {
        outputStream.println("*************************\nGOMOKU\n*************************");
        setPlayerNames(player1);
        setPlayerNames(player2);
    }

    public void startGameClI() throws IOException {
        outputStream.printf("(%s) Black player's first move must be in the center of the board.\n",
                player1.getColour() == Stone.BLACK ? player1.getUsername() : player2.getUsername());

        while (!game.checkIfThereAreFiveConsecutiveStones(game.getLastMovingPlayer().getColour())) {
            Player nextMovingPlayer = game.getNextMovingPlayer();
            outputStream.println(board);
            outputStream.printf("\nIt's %s's turn.\n", nextMovingPlayer.getUsername());
            Coordinates coordinates = getCoordinatesByPlayerInput(nextMovingPlayer);
            try {
                game.makeMove(nextMovingPlayer, coordinates);
            } catch (Exception e) {
                outputStream.println("Invalid coordinates!\nTry again."); //aggiungere il motivo dell'errore (con e.getMessage())
            }
        }
        winner = game.getLastMovingPlayer();
        outputStream.printf("\n%s won the game!", winner.getUsername());
    }

    private void setPlayerNames(Player player) throws IOException {
        outputStream.printf("Player %d name:   ", player.equals(player1) ? 1 : 2);
        String playerName = reader.readLine();
        if (!playerName.isBlank()) {
            player.setUsername(playerName);
        } else {
            String defaultUsername = player.equals(player1) ? "Player_1" : "Player_2";
            player.setUsername(defaultUsername);
        }
    }

    public int getSingleCoordinateByPlayer() throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    public Coordinates getCoordinatesByPlayerInput(Player player) throws IOException {
        try {
            outputStream.printf("%s insert coordinate for row index:   ", player.getUsername());
            int rowCoordinate = getSingleCoordinateByPlayer();
            outputStream.printf("%s insert coordinate for column index:   ", player.getUsername());
            int columnCoordinate = getSingleCoordinateByPlayer();
            return new Coordinates(rowCoordinate, columnCoordinate);
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
    }


}
