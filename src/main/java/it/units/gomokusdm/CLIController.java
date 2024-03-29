package it.units.gomokusdm;

import java.io.*;
import java.util.logging.Level;

public class CLIController {

    private static CLIController cli = null;
    private final PrintStream outputStream;
    private final BufferedReader reader;
    private final Player player1;
    private final Player player2;
    private Board board;
    private BoardGame game;
    private Player winner;


    private CLIController(PrintStream outputStream, InputStream inputStream) {
        this.outputStream = outputStream;
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.player1 = new Player("", Stone.BLACK);
        this.player2 = new Player("", Stone.WHITE);
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
        setBoardDimension();
        setPlayerName(player1);
        setPlayerName(player2);
        game = new GomokuGame(board, player1, player2);
        game.addGameStatusChangedEventListener(status -> {
        });
    }

    public void startGameClI() throws IOException {
        outputStream.printf("(%s) Black player's first move must be in the center of the board. %n",
                player1.getColour() == Stone.BLACK ? player1.getUsername() : player2.getUsername());
        Utilities.getLoggerOfClass(getClass()).log(Level.INFO, BoardFormatter.formatBoardCompact(board));
        while (game.getGameStatus().equals(BoardGameStatus.GAME_IN_PROGRESS)) {
            Player nextMovingPlayer = game.getNextMovingPlayer();
            printBoard();
            outputStream.printf("It's %s's turn. Insert \"STOP\" to end game. %n", nextMovingPlayer.getUsername());
            String playerInput = getPlayerInput(nextMovingPlayer);
            if (playerInput.equalsIgnoreCase("STOP")) {
                outputStream.printf("Game has been stopped by %s %n", nextMovingPlayer.getUsername());
                break;
            }
            try {
                Coordinates coordinates = getCoordinatesFromString(playerInput);
                game.makeMove(nextMovingPlayer, coordinates);
            } catch (GomokuGame.InvalidMoveThrowable | WrongStringFormatException e) {
                outputStream.printf("Invalid coordinates! %s %nTry again.", e.getMessage());
            }
        }
        if (game.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WITH_A_DRAW)) {
            outputStream.printf("Nobody won, the game is tie! %n");
        } else if (game.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WITH_A_WINNER)) {
            winner = game.getWinner();
            outputStream.printf("%s won the game! %n", winner.getUsername());
        }
    }

    private void setPlayerName(Player player) throws IOException {
        outputStream.printf("Player %d name:   ", player.equals(player1) ? 1 : 2);
        String playerName = reader.readLine();
        if (!playerName.isBlank()) {
            player.setUsername(playerName);
        } else {
            String defaultUsername = player.equals(player1) ? "Player_1" : "Player_2";
            player.setUsername(defaultUsername);
        }
    }

    private void setBoardDimension() throws IOException {
        outputStream.println("Select board size to use for this game:");
        outputStream.printf("\t1. %s %n \t2. %s %n", "19x19", "15x15");
        String line = reader.readLine();
        try {
            int selectedBoardSize = Integer.parseInt(line) == 1 ? 19 : 15;
            board = new BoardImplementation(selectedBoardSize);
        } catch (NumberFormatException e) {
            outputStream.printf("Please, select option %d or %d.%nYour input was \"%s\".%n", 1, 2, line);
        }
    }

    private Coordinates getCoordinatesFromString(String string) throws WrongStringFormatException {
        if (string != null && isAValidStringForCoordinates(string)) {
            String[] tokens = string.split(",");
            //return new Coordinates(Integer.parseInt(tokens[0].trim()), Integer.parseInt(tokens[1].trim()));
            int dim = board.getBoardDimension();
            return new Coordinates(dim - Integer.parseInt(tokens[0].trim()), tokens[1].trim().charAt(0) - 'a');
        }
        throw new WrongStringFormatException("String doesn't match the expected format: row,col");
    }

    public String getPlayerInput(Player player) throws IOException {
        outputStream.printf("%s insert coordinate [row, column]:   ", player.getUsername());
        return readPlayerInputFromInputStream();
    }

    private String readPlayerInputFromInputStream() throws IOException {
        return reader.readLine();
    }

    private boolean isAValidStringForCoordinates(String string) {
        //return string.matches("^\\d+,[\" ]*\\d+$");
        return string.matches("^\\d+,[\" ]*[a-z]$");
    }

    private void printBoard() {
        outputStream.print(BoardFormatter.formatBoard15to1andAtoO(board));
    }

    public String getFormattedBoard15to1andAtoO() {
        return BoardFormatter.formatBoard15to1andAtoO(board);
    }

    public static class WrongStringFormatException extends Exception {
        public WrongStringFormatException(String message) {
            super(message);
        }
    }
}
