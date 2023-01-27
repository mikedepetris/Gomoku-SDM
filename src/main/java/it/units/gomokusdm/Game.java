package it.units.gomokusdm;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {

    private Board board;
    private Player player1;
    private Player player2;
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

    public void setupGame(int boardDimension){
        this.board.setupBoard(boardDimension);
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
        //this.lastMoveCoordinates = boardCenter;
        //lastMovingPlayer.addMove(lastMoveCoordinates);
        lastMovingPlayer.addMove(boardCenter);
    }

    public void makeMove(Player player, Coordinates coordinates) throws InvalidMoveException {
        if (isFeasibleMove(coordinates) && isTurnOfPlayer(player) && player.getMovesList().size() < 60) {
            board.setCell(player.getColour(), coordinates);
            lastMovingPlayer = player;
            lastMovingPlayer.addMove(coordinates);
        } else {
            String exceptionMessage = "";
            if (!isFeasibleMove(coordinates)) { exceptionMessage += "Move not feasible "; }
            if (!isTurnOfPlayer(player)) { exceptionMessage += "Is not " + player.getUsername() + "'s turn "; }
            if (!(player.getMovesList().size() < 60)) { exceptionMessage += player.getUsername() + " has terminated the number of moves allowed "; }
            throw new InvalidMoveException(exceptionMessage);
        }
    }

    private boolean isTurnOfPlayer(Player player) {
        return player != lastMovingPlayer;
    }


    public boolean countStones(int colDirection, int rowDirection, Stone winningStone, int numberOfStones) {
        int checkingStones = numberOfStones - 1;
        Coordinates insertedStoneCoordinates = getLastMoveCoordinates();
        int insertedStoneRow = insertedStoneCoordinates.getRowIndex(); int insertedStoneCol = insertedStoneCoordinates.getColIndex();

        int i = colDirection * checkingStones; int j = rowDirection * checkingStones;
        int counterStones = getCounterStones(winningStone, checkingStones, insertedStoneRow, insertedStoneCol, i, j);

        return (counterStones == numberOfStones);
    }

    private int getCounterStones(Stone winningStone, int checkingStones, int rowIndex, int colIndex, int i, int j) {
        int counterStones = 0;
        int i_increment = i / checkingStones; int j_increment = j / checkingStones;
        while (areInRange(i, j, checkingStones) && (counterStones != checkingStones+1)) {
            if (board.areValidCoordinates(new Coordinates(rowIndex + j, colIndex + i))) {
                Stone actualStone = board.getStoneAt(new Coordinates(rowIndex + j, colIndex + i));
                if (actualStone.toString() == winningStone.toString()) {
                    counterStones++;
                } else {
                    counterStones = 0;
                }
            }
            i = i - i_increment;
            j = j - j_increment;
        }
        return counterStones;
    }

    // ATTENZIONE: duplicato di areValidCoordinates in Board, si può generalizzare
    private static boolean areInRange(int i, int j, int checkingStones) {
        return (i <= checkingStones && i >= -checkingStones && j <= checkingStones && j >= -checkingStones);
    }

    private boolean isValidNumberOfStones (int numberOfStones) {
        return (numberOfStones <= board.getBoardDimension() && numberOfStones > 0);
    }

    private Coordinates getLastMoveCoordinates() {
        return lastMovingPlayer.getMovesList().get(lastMovingPlayer.getMovesList().size() - 1);
    }


    public boolean checkIfThereAreFiveConsecutiveStones(Stone stone) {
        boolean areThereFiveStones = false;
        int numberOfStonesToWin = 5;
        int[] directions = {-1, 0, 0, -1, -1, -1, 1, -1}; // -1 -> sx - +1 -> dx
        int len = 0;
        while (len < directions.length & isValidNumberOfStones(numberOfStonesToWin) & !areThereFiveStones) {
            areThereFiveStones = countStones(directions[len], directions[len + 1], stone, numberOfStonesToWin);
            len = len + 2;
        }
        return (areThereFiveStones);

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

    //Sposto sotto i metodi di Biagio..

     /*public boolean checkIfPlayerWins() {
        return getAllDirectionsResultOfConsecutiveNStones(5).contains(Boolean.TRUE);
    }*/

    /*private List<Boolean> getAllDirectionsResultOfConsecutiveNStones(int N) {
        List<Boolean> valuesOfAdjStonesInAllDirection = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            valuesOfAdjStonesInAllDirection.add(checkIfThereAreNConsecutiveStonesInDirection(direction, N));
        }
        return valuesOfAdjStonesInAllDirection;
    }*/

    /*private boolean checkIfThereAreNConsecutiveStonesInDirection(Direction direction, int N) {
        List<Boolean> valueOfAdjacentStones = new ArrayList<>();
        Coordinates lastMoveCoordinates = lastMovingPlayer.getMovesList().get(lastMovingPlayer.getMovesList().size()-1);
        Coordinates currentCoordinate = lastMoveCoordinates;
        valueOfAdjacentStones.add(Boolean.TRUE);
        int valueToControl = N - 1;
        while (valueToControl > 0) {
            Coordinates coordinatesInDirection = currentCoordinate.getCoordinateMovedInDirectionWithStep(direction, 1);
            if (!board.areValidCoordinates(coordinatesInDirection) || !checkIfStonesAreEqual(currentCoordinate, coordinatesInDirection)) {
                valueOfAdjacentStones.add(Boolean.FALSE);
                break;
            }
            valueOfAdjacentStones.add(Boolean.TRUE);
            currentCoordinate = coordinatesInDirection;
            valueToControl--;
        }
        return valueOfAdjacentStones.size() == N && !valueOfAdjacentStones.contains(Boolean.FALSE);
    }*/

    /*private boolean checkIfStonesAreEqual(Coordinates firstCoordinate, Coordinates secondCoordinate) {
        return board.areStonesOfSameColourAt(firstCoordinate, secondCoordinate);
    }*/
}
