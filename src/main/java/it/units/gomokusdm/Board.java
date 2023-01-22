package it.units.gomokusdm;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Board {
    private final Map<Coordinates, Stone> board;
    private int boardDimension;
    public Board() {
        this.boardDimension = 0;
        this.board = new LinkedHashMap<>();
    }

    public void setupBoard(int boardDimension) {
        this.setBoardDimension(boardDimension);
        IntStream.range(0, this.getBoardDimension())
                .forEach(row -> IntStream.range(0, this.getBoardDimension())
                        .forEach(col -> this.setCell(Stone.EMPTY, new Coordinates(row, col))));
    }

    public int getBoardDimension() {
        return boardDimension;
    }

    private void setBoardDimension(int boardDimension) {
        this.boardDimension = boardDimension;
    }

    public int getNumberOfEmptyPositionInBoard() {
        return (int) this.board
                .values()
                .stream()
                .filter(stone -> stone.equals(Stone.EMPTY))
                .count();
    }

    public int getNumberOfOccupiedPositionInBoard() {
        return (int) Math.pow(this.boardDimension, 2) - getNumberOfEmptyPositionInBoard();
    }

    public Stone getStoneAt(Coordinates coordinates) {
        return board.get(coordinates);
    }

    public void setCell(Stone stone, Coordinates coordinates) {
        board.put(coordinates, stone);
    }

    public boolean isEmptyCell(Coordinates coordinates) {
        if (areValidCoordinates(coordinates))
            return board.get(coordinates).equals(Stone.EMPTY);
        else
            return true;
    }

    public boolean areValidCoordinates(Coordinates coordinates) {
        return (coordinates.getRowIndex() >= 0 && coordinates.getRowIndex() < getBoardDimension()) &&
                (coordinates.getColIndex() >= 0 && coordinates.getColIndex() < getBoardDimension());
    }

    /**
     * public String toString() {
     * StringBuilder temp = new StringBuilder();
     * for (int i = 0; i < boardDimension; i++) {
     * for (int j = 0; j < boardDimension; j++) {
     * switch (board.get(new Coordinates(i, j))) {
     * case EMPTY -> temp.append("|0");
     * case BLACK -> temp.append("|B");
     * case WHITE -> temp.append("|W");
     * }
     * }
     * temp.append("|");
     * temp.append("\n");
     * }
     * return temp.toString();
     * <p>
     * }
     **/

    public boolean areStonesOfSameColourAt(Coordinates current, Coordinates coordinateInDirection) {
        return getStoneAt(current) == getStoneAt(coordinateInDirection);
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        board.values().forEach(tmp::append);
        return tmp.toString();
    }
}
