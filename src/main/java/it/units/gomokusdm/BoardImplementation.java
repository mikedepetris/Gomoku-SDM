package it.units.gomokusdm;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardImplementation implements Board {
    private final Map<Coordinates, Stone> board;

    public BoardImplementation() {
        this.board = new LinkedHashMap<>();
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
    public List<Coordinates> getAdjacentCoordinatesAt(Coordinates coordinates) {
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

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        board.values().forEach(tmp::append);
        return tmp.toString();
    }
}
