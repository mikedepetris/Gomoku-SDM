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


    public boolean countStones(int colDirection, int rowDirection, int winningColour, int numberOfStones) {
        Coordinates lastMoveCoordinates = getLastMoveCoordinates();
        int rowIndex = lastMoveCoordinates.getRowIndex(); int colIndex = lastMoveCoordinates.getColIndex();
        int i = colDirection * (numberOfStones - 1); int j = rowDirection * (numberOfStones - 1);
        int i_increment = i / (numberOfStones - 1); int j_increment = j / (numberOfStones - 1);
        int counterStones = 0;
        if (numberOfStones <= board.getBoardDimension() && numberOfStones > 0) {
            while (i <= (numberOfStones - 1) && i >= -(numberOfStones - 1) && j <= (numberOfStones - 1) && j >= -(numberOfStones - 1)) {
                if (board.areValidCoordinates(new Coordinates(rowIndex + j, colIndex + i))) {
                    Stone stoneColourNumber = board.getStoneAt(new Coordinates(rowIndex + j, colIndex + i));
                    if (stoneColourNumber == Stone.castIntToStone(winningColour)) {
                        counterStones++;
                    } else {
                        if (counterStones != numberOfStones) {
                            counterStones = 0;
                        } else {
                            /*i = (numberOfStones - 1);
                            j = (numberOfStones - 1); */// stop while, there are already five consecutive stones
                            break;
                        }
                    }
                }
                i = i - i_increment;
                j = j - j_increment;
            }
        }
        return (counterStones == numberOfStones);
    }

    private Coordinates getLastMoveCoordinates() {
        return lastMovingPlayer.getMovesList().get(lastMovingPlayer.getMovesList().size() - 1);
    }


    public boolean checkIfThereAreFiveConsecutiveStones(Stone stone) {
        boolean areThereFiveStones = false;
        int winningColour = 1;
        if (stone == Stone.WHITE) {
            winningColour = 2;
        }
        int[] directions = {-1, 0, 0, -1, -1, -1, 1, -1}; // -1 -> sx - +1 -> dx
        int len = 0;
        while (len < directions.length) {
            areThereFiveStones = countStones(directions[len], directions[len + 1], winningColour, 5);
            if (areThereFiveStones) {
                //len = directions.length; // stop while if I've already found 5 stones in a direction
                break;
            }
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
