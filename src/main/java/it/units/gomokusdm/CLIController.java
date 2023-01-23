package it.units.gomokusdm;


import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

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

    private boolean isStopped;
    private static final int NULL_COORDINATE = -1;

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
        setGameInitialStatus();
        setPlayerNames(player1);
        setPlayerNames(player2);
    }

    public void startGameClI() throws IOException {
        this.isStopped = false;
        outputStream.printf("(%s) Black player's first move must be in the center of the board.\n",
                player1.getColour() == Stone.BLACK ? player1.getUsername() : player2.getUsername());
        while (!game.checkIfThereAreFiveConsecutiveStones(game.getLastMovingPlayer().getColour())) {
            Player nextMovingPlayer = game.getNextMovingPlayer();
            printBoard();
            outputStream.printf("\nIt's %s's turn.\n", nextMovingPlayer.getUsername());
            Coordinates coordinates = getCoordinatesByPlayerInput(nextMovingPlayer);
            if (isStopped) {
                break;
            }
            try {
                game.makeMove(nextMovingPlayer, coordinates);
            } catch (Exception e) {
                outputStream.println("Invalid coordinates!\nTry again."); //aggiungere il motivo dell'errore (con e.getMessage())
            }
        }
        if (!isStopped) {
            winner = game.getLastMovingPlayer();
            outputStream.printf("\n%s won the game!", winner.getUsername());
        } else {
            outputStream.printf("\nGame has been stopped by %s\n", game.getNextMovingPlayer().getUsername());
        }
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

    private void setGameInitialStatus() throws IOException {
        outputStream.println("Select board size to use for this game:");
        outputStream.printf("\t1. %s\n \t2. %s\n", "19x19", "15x15");
        String line;
        while ((line = reader.readLine()) != null){
            try{
                int selectedBoardSize = Integer.parseInt(line) == 1 ? 19 : 15;
                this.game.setupGame(selectedBoardSize);
                break;
            }catch (NumberFormatException e){
                outputStream.printf("Please, select option %d or %d.\nYour input was \"%s\". \n", 1, 2, line);
            }
        }
    }

    public int getSingleCoordinateByPlayer() throws IOException {
        String lineRead = reader.readLine();
        if (lineRead != null && lineRead.equals("STOP")) {
            stopGameCLI();
            return NULL_COORDINATE;
        }
        return Integer.parseInt(lineRead);
    }

    private void stopGameCLI() {
        isStopped = true;
    }

    public Coordinates getCoordinatesByPlayerInput(Player player) throws IOException, NumberFormatException {

        outputStream.printf("%s insert coordinate for row index:   ", player.getUsername());
        int rowCoordinate = getSingleCoordinateByPlayer();
        if (isStopped) {
            return null;
        }
        outputStream.printf("%s insert coordinate for column index:   ", player.getUsername());
        int columnCoordinate = getSingleCoordinateByPlayer();
        if (isStopped) {
            return null;
        }
        return new Coordinates(rowCoordinate, columnCoordinate);
    }

    public void printBoard() {
        StringBuilder tmp = new StringBuilder();
        String repeatedLine = "|\t".repeat(board.getBoardDimension());
        List<List<String>> boardPartitionString = Utilities.partition(Arrays.stream(board.toString().split("")).toList(), board.getBoardDimension());
        tmp.append(System.lineSeparator());
        for (int row = 0; row < boardPartitionString.size(); row++) {
            tmp.append(String.format("%1s", row)).append("\t");
            for (int stone = 0; stone < boardPartitionString.get(row).size(); stone++) {
                if (stone == boardPartitionString.get(row).size() - 1) {
                    tmp.append(String.format("%-4s", boardPartitionString.get(row).get(stone)))
                            .append(System.lineSeparator());
                } else {
                    tmp.append(String.format("%-4s", boardPartitionString.get(row).get(stone))
                            .replace(" ", "-"));
                }
            }
            if (!(row == boardPartitionString.size() - 1)) {
                tmp.append("\t")
                        .append(repeatedLine)
                        .append(System.lineSeparator());
            }
        }
        tmp.append("\t");
        IntStream.range(0, board.getBoardDimension())
                .forEach(value ->
                        tmp.append(String.format("%1s", value)).append("\t"));
        tmp.append(System.lineSeparator());
        outputStream.print(tmp);
    }

}
