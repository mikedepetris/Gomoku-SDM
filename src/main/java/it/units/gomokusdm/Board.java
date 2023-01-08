package it.units.gomokusdm;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Board {
    private final int boardDimension;
    private final Map<Coordinates, Stone> board = new LinkedHashMap<>();

    public Board(int boardDimension) {
        this.boardDimension = boardDimension;
        IntStream.range(0, this.boardDimension)
                .forEach(row -> IntStream.range(0, this.boardDimension)
                        .forEach(col -> board.put(new Coordinates(row, col), Stone.EMPTY)));
    }

    public Board() {
        this(19);
    }

    public int getBoardDimension() {
        return boardDimension;
    }

    public Map<Coordinates, Stone> getBoard() {
        return board;
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
    public String toString() {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < boardDimension; i++) {
            for (int j = 0; j < boardDimension; j++) {
                switch (board.get(new Coordinates(i, j))) {
                    case EMPTY -> temp.append("|0");
                    case BLACK -> temp.append("|B");
                    case WHITE -> temp.append("|W");
                }
            }
            temp.append("|");
            temp.append("\n");
        }
        return temp.toString();

    }
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
