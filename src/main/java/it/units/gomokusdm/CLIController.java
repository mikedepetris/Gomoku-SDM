package it.units.gomokusdm;


import java.io.*;

public class CLIController {

    private static CLIController cli = null;

    private final PrintStream outputStream;
    private final BufferedReader reader;
    private Board board;
    private Game game;
    private final Player player1;
    private final Player player2;
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
        outputStream.println("*************************\nGOMOKU\n*************************");
        setBoardDimension();
        game = new Game(board, player1, player2);
        setPlayerName(player1);
        setPlayerName(player2);
    }

    public void startGameClI() throws IOException {
        outputStream.printf("(%s) Black player's first move must be in the center of the board.\n",
                player1.getColour() == Stone.BLACK ? player1.getUsername() : player2.getUsername());
        while (!thereIsAWinner()) {
            Player nextMovingPlayer = game.getNextMovingPlayer();
            printBoard();
            outputStream.printf("\nIt's %s's turn.\n", nextMovingPlayer.getUsername());
            String playerInput = getPlayerInput(nextMovingPlayer);
            if (playerInput.equals("STOP")) {
                outputStream.printf("\nGame has been stopped by %s\n", game.getNextMovingPlayer().getUsername());
                break;
            }
            try {
                Coordinates coordinates = getCoordinatesFromString(playerInput);
                game.makeMove(nextMovingPlayer, coordinates);
            } catch (Game.InvalidMoveException | WrongStringFormatException e) {
                outputStream.println("Invalid coordinates!\nTry again."); //aggiungere il motivo dell'errore (con e.getMessage())
            }
        }
        if (thereIsAWinner()) {
            winner = game.getLastMovingPlayer();
            outputStream.printf("\n%s won the game!", winner.getUsername());
        }
    }

    private boolean thereIsAWinner() {
        return game.checkIfThereAreFiveConsecutiveStones(game.getLastMovingPlayer().getColour());
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
        outputStream.printf("\t1. %s\n \t2. %s\n", "19x19", "15x15");
        String line = reader.readLine();
        try {
            int selectedBoardSize = Integer.parseInt(line) == 1 ? 19 : 15;
            board = new BoardImplementation(selectedBoardSize);
        } catch (NumberFormatException e){
            outputStream.printf("Please, select option %d or %d.\nYour input was \"%s\". \n", 1, 2, line);
        }
    }

    public Coordinates getCoordinatesFromString(String string) throws WrongStringFormatException {
        if (string != null && isAValidStringForCoordinates(string)) {
            String[] tokens = string.split(",");
            return new Coordinates(Integer.parseInt(tokens[0].trim()), Integer.parseInt(tokens[1].trim()));
        }
        throw new WrongStringFormatException("string doesn't match the expected format");
    }

    public String getPlayerInput(Player player) throws IOException {
        outputStream.printf("%s insert coordinate [row, column]:   ", player.getUsername());
        return readPlayerInputFromInputStream();
    }

    private String readPlayerInputFromInputStream() throws IOException {
        return reader.readLine();
    }

    private boolean isAValidStringForCoordinates(String string) {
        return string.matches("^[0-9]+,[\" \"]*[0-9]+$");
    }

    public void printBoard() {
        outputStream.print(BoardFormatter.formatBoard(board));
    }

    public static class WrongStringFormatException extends Exception {
        public WrongStringFormatException(String message) {
            super(message);
        }
    }
}
