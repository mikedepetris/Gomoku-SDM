package it.units.gomokusdm;

import java.util.*;
import java.util.stream.IntStream;

public class BoardImplementation implements Board {
    private static final int DEFAULT_BOARD_DIMENSION = 19;
    private final Map<Coordinates, Stone> board;

    public BoardImplementation() {
        this.board = new LinkedHashMap<>();
        setupBoard(DEFAULT_BOARD_DIMENSION);
    }

    public BoardImplementation(int boardDimension) {
        this.board = new LinkedHashMap<>();
        setupBoard(boardDimension);
    }

    public void setupBoard(int boardDimension) {
        IntStream.range(0, boardDimension)
                .forEach(row -> IntStream.range(0, boardDimension)
                        .forEach(col -> this.setCell(Stone.EMPTY, new Coordinates(row, col))));
    }

    @Override
    public int getBoardDimension() {
        return (int) Math.sqrt(this.board.size());
    }

    public List<String> getBoardAsLines() {
        List<String> lines = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        int dimension = getBoardDimension();
        int pos = 0;
        for (Stone v : board.values()) {
            line.append(v);
            if (pos > dimension) {
                lines.add(line.toString());
                line.setLength(0);
                pos = 0;
            }
            pos++;
        }
        return lines;
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

    @Override
    public Stone getStoneAt(Coordinates coordinates) {
        return board.get(coordinates);
    }

    @Override
    public void setCell(Stone stone, Coordinates coordinates) {
        board.put(coordinates, stone);
    }

    @Override
    public boolean isEmptyCell(Coordinates coordinates) {
        if (areValidCoordinates(coordinates))
            return board.get(coordinates).equals(Stone.EMPTY);
        else
            return true;
    }


    @Override
    public List<Coordinates> getAdjacentCoordinatesAt(Coordinates coordinates) {
        return Arrays.stream(Direction.values())
                .map(direction -> coordinates.getCoordinateMovedInDirectionWithStep(direction, 1))
                .filter(adjcoord -> !adjcoord.equals(coordinates))
                .toList();
    }


    @Override
    public boolean areValidCoordinates(Coordinates coordinates) {
        return (coordinates.getRowIndex() >= 0 && coordinates.getRowIndex() < getBoardDimension()) &&
                (coordinates.getColIndex() >= 0 && coordinates.getColIndex() < getBoardDimension());
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        board.values().forEach(tmp::append);
        return tmp.toString();
    }
}
