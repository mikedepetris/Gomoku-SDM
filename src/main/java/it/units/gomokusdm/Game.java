package it.units.gomokusdm;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class Game {

    public static final int MAX_NUMBER_OF_STONES = 60;
    private final Board board;
    private final Player player1;
    private final Player player2;
    private Player lastMovingPlayer;

    public Game(Board board, Player player1, Player player2) {
        Utilities.getLoggerOfClass(getClass())
                .log(Level.INFO, "Game constructor called with player1=%s, player2=%s"
                        .formatted(player1.getUsername(), player2.getUsername()));
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        if (!checkPlayerNames(player1, player2)) {
            Utilities.getLoggerOfClass(getClass())
                    .log(Level.ALL, "ERROR: Game constructor called with same player names: player1=%s, player2=%s"
                            .formatted(player1.getUsername(), player2.getUsername()));
            throw new IllegalArgumentException("invalid player names");
        }
        if (!checkPlayerColours(player1, player2)) {
            Utilities.getLoggerOfClass(getClass())
                    .log(Level.ALL, "ERROR: Game constructor called with invalid player colors: player1=%s, player2=%s"
                            .formatted(player1.getColour().toString(), getPlayer2().getColour().toString()));
            throw new IllegalArgumentException("invalid player colors");
        }
        setupGame();
    }

    private static boolean checkPlayerNames(Player player1, Player player2) {
        return !Objects.equals(player1.getUsername(), player2.getUsername());
    }

    private static boolean checkPlayerColours(Player player1, Player player2) {
        return player1.getColour() != player2.getColour();
    }

    // ATTENZIONE: duplicato di areValidCoordinates in Board, si può generalizzare
    private static boolean areInRange(int i, int j, int checkingStones) {
        return (i <= checkingStones && i >= -checkingStones && j <= checkingStones && j >= -checkingStones);
    }

    public Board getBoard() {
        return board;
    }

    public void setupGame() {
        makeMandatoryFirstMove(player1.getColour() == Stone.BLACK ? player1 : player2);
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getLastMovingPlayer() {
        return lastMovingPlayer;
    }

    public Player getNextMovingPlayer() {
        return player1 == lastMovingPlayer ? player2 : player1;
    }

    private void makeMandatoryFirstMove(Player player) {
        Coordinates boardCenter =
                new Coordinates(board.getBoardDimension() / 2, board.getBoardDimension() / 2);
        board.setCell(player.getColour(), boardCenter);
        this.lastMovingPlayer = player;
        lastMovingPlayer.addMove(boardCenter);
    }

    public void makeMove(Player player, Coordinates coordinates) throws InvalidMoveThrowable {
        if (isFeasibleMove(coordinates) && isTurnOfPlayer(player)) {
            board.setCell(player.getColour(), coordinates);
            lastMovingPlayer = player;
            lastMovingPlayer.addMove(coordinates);
        } else {
            String exceptionMessage = "";
            if (!isFeasibleMove(coordinates)) {
                exceptionMessage += "Move not feasible ";
            }
            if (!isTurnOfPlayer(player)) {
                exceptionMessage += "Is not " + player.getUsername() + "'s turn ";
            }
            throw new InvalidMoveThrowable(exceptionMessage);
        }
    }

    private boolean isTurnOfPlayer(Player player) {
        return player != lastMovingPlayer;
    }

    private Coordinates getLastMoveCoordinates() {
        return lastMovingPlayer.getMovesList().get(lastMovingPlayer.getMovesList().size() - 1);
    }

    public boolean checkIfStonesOfAPlayerAreFinished() {
        return player1.getMovesList().size() >= MAX_NUMBER_OF_STONES
                || player2.getMovesList().size() >= MAX_NUMBER_OF_STONES;
    }

    public boolean checkIfThereAreFiveConsecutiveStones(Stone stone) {
        int numberOfStones = 5;
        boolean areStonesEqual = false;
        Direction[] directions = {Direction.LEFT, Direction.UP, Direction.UP_MAIN_DIAGONAL, Direction.UP_ANTI_DIAGONAL};
        int len = 0;
        List<Coordinates> coordinatesToCheck = new ArrayList<>();
        while (len < directions.length && !areStonesEqual) {
            findStonesToCheck(coordinatesToCheck, directions[len].getColIdx(), directions[len].getRowIdx()
                    , numberOfStones);
            areStonesEqual = checkNStonesEqual(coordinatesToCheck, stone, numberOfStones);
            len++;
            coordinatesToCheck.clear();
        }
        return areStonesEqual;
    }

    private void findStonesToCheck(List<Coordinates> coordinates, int colDirection, int rowDirection, int nStones) {
        int checkingStones = nStones - 1;
        Coordinates insertedStoneCoordinates = getLastMoveCoordinates();
        int insertedStoneRow = insertedStoneCoordinates.getRowIndex();
        int insertedStoneCol = insertedStoneCoordinates.getColIndex();

        int i = colDirection * checkingStones;
        int j = rowDirection * checkingStones;

        while (areInRange(i, j, checkingStones)) {
            Coordinates possibleCoordinates = new Coordinates(insertedStoneRow + j, insertedStoneCol + i);
            if (board.areValidCoordinates(possibleCoordinates)) {
                coordinates.add(possibleCoordinates);
            }
            i = i - colDirection;
            j = j - rowDirection;
        }
    }

    private boolean checkNStonesEqual(List<Coordinates> coordinates, Stone stone, int nStones) {
        int i = 0;
        int counterStones = 0;
        while (i < coordinates.size() && counterStones != nStones) {
            Stone actualStone = board.getStoneAt(coordinates.get(i));
            if (Objects.equals(actualStone.toString(), stone.toString())) {
                counterStones++;
            } else {
                counterStones = 0;
            }
            i++;
        }
        return (counterStones == nStones);
    }


    // controlla se esiste una stone adiacente intorno a quella che sto inserendo. se ne esiste almeno una,
    // posso inserire la stone
    public boolean isThereAnAdjacentStone(Coordinates coordinates) {
        List<Coordinates> adjacentCoordinates = board.getAdjacentCoordinatesAt(coordinates);
        for (Coordinates el : adjacentCoordinates) {
            if (board.areValidCoordinates(coordinates) && !board.isEmptyCell(el)) {
                return true;
            }
        }
        return false;
    }

    public boolean isFeasibleMove(Coordinates coordinates) {
        return board.areValidCoordinates(coordinates) && board.isEmptyCell(coordinates)
                && isThereAnAdjacentStone(coordinates);
    }

    public static class InvalidMoveThrowable extends Throwable {
        public InvalidMoveThrowable() {
            super();
        }

        public InvalidMoveThrowable(String errorMessage) {
            super(errorMessage);
        }
    }


}
