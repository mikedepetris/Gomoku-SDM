package it.units.gomokusdm;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {

    public static final int MAX_NUMBER_OF_STONES = 60;
    private final Board board;
    private final Player player1;
    private final Player player2;
    private Player lastMovingPlayer;

    public Game(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        if (!checkPlayerNames(player1, player2))
            throw new IllegalArgumentException("invalid player names");
        if (!checkPlayerColours(player1, player2)) {
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

    public void makeMove(Player player, Coordinates coordinates) throws InvalidMoveException {
        if (isFeasibleMove(coordinates) && isTurnOfPlayer(player)) {
//                && player.getMovesList().size() < MAX_NUMBER_OF_STONES) {
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
//            if (!(player.getMovesList().size() < MAX_NUMBER_OF_STONES)) {
//                exceptionMessage += player.getUsername() + " has terminated the number of moves allowed ";
//            }
            throw new InvalidMoveException(exceptionMessage);
        }
    }

    private boolean isTurnOfPlayer(Player player) {
        return player != lastMovingPlayer;
    }


    // ATTENZIONE: duplicato di areValidCoordinates in Board, si può generalizzare
    private static boolean areInRange(int i, int j, int checkingStones) {
        return (i <= checkingStones && i >= -checkingStones && j <= checkingStones && j >= -checkingStones);
    }

    /* vale davvero la pena usarlo?
    private boolean isValidNumberOfStones (int numberOfStones) {
        return (numberOfStones <= board.getBoardDimension() && numberOfStones > 0);
    }
    */

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
            findStonesToCheck(coordinatesToCheck, directions[len].getRowIdx(), directions[len].getColIdx(), numberOfStones);
            areStonesEqual = checkNStonesEqual(coordinatesToCheck, stone, numberOfStones);
            len++;
            coordinatesToCheck.clear();
        }
        return areStonesEqual;
    }

    private void findStonesToCheck(List<Coordinates> coordinates, int rowDirection, int colDirection, int nStones) {
        int checkingStones = nStones - 1;
        Coordinates insertedStoneCoordinates = getLastMoveCoordinates();
        int insertedStoneRow = insertedStoneCoordinates.getRowIndex();
        int insertedStoneCol = insertedStoneCoordinates.getColIndex();

        int i = rowDirection * checkingStones;
        int j = colDirection * checkingStones;

        while (areInRange(i, j, checkingStones)) {
            Coordinates possibleCoordinates = new Coordinates(insertedStoneRow + j, insertedStoneCol + i);
            if (board.areValidCoordinates(possibleCoordinates)) {
                coordinates.add(possibleCoordinates);
            }
            i = i - rowDirection;
            j = j - colDirection;
        }
    }

    private boolean checkNStonesEqual(List<Coordinates> coordinates, Stone stone, int nStones) {
        int i = 0;
        int counterStones = 0;
        while (i < coordinates.size() & counterStones != nStones) {
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

    public static class InvalidMoveException extends Throwable {
        public InvalidMoveException() {
            super();
        }

        public InvalidMoveException(String errorMessage) {
            super(errorMessage);
        }
    }


}
