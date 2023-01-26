package it.units.gomokusdm;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardImplementation implements Board {
    private final int DEFAULT_BOARD_DIMENSION = 19;
    private final Map<Coordinates, Stone> board;

    public BoardImplementation() {
        this.board = new LinkedHashMap<>();
        setupBoard(DEFAULT_BOARD_DIMENSION);
    }

    public BoardImplementation(int boardDimension) {
        this.board = new LinkedHashMap<>();
        setupBoard(boardDimension);
    }

    @Override
    public void setupBoard(int boardDimension) {
        IntStream.range(0, boardDimension)
                .forEach(row -> IntStream.range(0, boardDimension)
                        .forEach(col -> this.setCell(Stone.EMPTY, new Coordinates(row, col))));
    }

    @Override
    public int getBoardDimension() {
        return (int) Math.sqrt(this.board.size());
    }

    @Override
    public int getNumberOfEmptyPositionInBoard() {
        return (int) this.board
                .values()
                .stream()
                .filter(stone -> stone.equals(Stone.EMPTY))
                .count();
    }

    @Override
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
    public List<Coordinates> getAdjacentCoordinatesAt(Coordinates coordinates){
        return Arrays.stream(Direction.values())
                .map(direction -> coordinates.getCoordinateMovedInDirectionWithStep(direction, 1))
                .filter(adjcoord -> !adjcoord.equals(coordinates))
                .collect(Collectors.toList());
    }

    @Override
    public boolean areValidCoordinates(Coordinates coordinates) {
        return (coordinates.getRowIndex() >= 0 && coordinates.getRowIndex() < getBoardDimension()) &&
                (coordinates.getColIndex() >= 0 && coordinates.getColIndex() < getBoardDimension());
    }

    @Override
    public boolean areStonesOfSameColourAt(Coordinates current, Coordinates coordinateInDirection) {
        return getStoneAt(current) == getStoneAt(coordinateInDirection);
    }

    public String getBoardInLine() {
        StringBuilder tmp = new StringBuilder();
        board.values().forEach(tmp::append);
        return tmp.toString();
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        String repeatedLine = "|\t".repeat(getBoardDimension());
        List<List<String>> boardPartitionString = Utilities.partition(Arrays.stream(board.toString().split("")).toList(), getBoardDimension());
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
        IntStream.range(0, getBoardDimension())
                .forEach(value ->
                        tmp.append(String.format("%1s", value)).append("\t"));
        tmp.append(System.lineSeparator());
        return tmp.toString();
    }
}
