package it.units.gomokusdm;

import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.System.lineSeparator;

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
            printBoard();
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

    public void printBoard(){
        StringBuilder tmp = new StringBuilder();
        String repeatedLine = "|\t".repeat(board.getBoardDimension());
        List<List<String>> boardPartitionString = Lists.partition(Arrays.stream(board.toString().split("")).toList(), board.getBoardDimension());
        tmp.append(System.lineSeparator());
        for(int row = 0; row < boardPartitionString.size(); row++){
            tmp.append(String.format("%1s", row)).append("\t");
            for(int stone = 0; stone < boardPartitionString.get(row).size(); stone++){
                if(stone == boardPartitionString.get(row).size() - 1){
                    tmp.append(String.format("%-4s", boardPartitionString.get(row).get(stone)))
                            .append(System.lineSeparator());
                } else {
                    //tmp.append(String.format("%-4s", test.get(row).get(stone)).replace(" ", "-"));
                    tmp.append(Strings.padEnd(boardPartitionString.get(row).get(stone), 4, '-'));
                }

            }
            if(!(row == boardPartitionString.size()-1)) {
                tmp.append("\t")
                        .append(repeatedLine)
                        .append(System.lineSeparator());
            }
        }
        tmp.append("\t");
        IntStream.range(0, board.getBoardDimension())
                .forEach(pos -> tmp.append(String.format("%1s", pos)).append("\t"));
        tmp.append(System.lineSeparator());

        outputStream.print(tmp);
    }

}
