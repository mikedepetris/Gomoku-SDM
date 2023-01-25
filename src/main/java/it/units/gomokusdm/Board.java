package it.units.gomokusdm;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Board {
    private final Map<Coordinates, Stone> board;
    public Board() {
        this.board = new LinkedHashMap<>();
    }

    public void setupBoard(int boardDimension) {
        IntStream.range(0, boardDimension)
                .forEach(row -> IntStream.range(0, boardDimension)
                        .forEach(col -> this.setCell(Stone.EMPTY, new Coordinates(row, col))));
    }

    public int getBoardDimension() {
        return (int) Math.sqrt(this.board.size());
    }

    public int getNumberOfEmptyPositionInBoard() {
        return (int) this.board
                .values()
                .stream()
                .filter(stone -> stone.equals(Stone.EMPTY))
                .count();
    }

    public int getNumberOfOccupiedPositionInBoard() {
        return (int) Math.pow(this.getBoardDimension(), 2) - getNumberOfEmptyPositionInBoard();
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
