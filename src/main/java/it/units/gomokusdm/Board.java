package it.units.gomokusdm;

import java.util.List;

public interface Board {
    void setupBoard(int boardDimension);

    int getBoardDimension();

    int getNumberOfEmptyPositionInBoard();

    int getNumberOfOccupiedPositionInBoard();

    Stone getStoneAt(Coordinates coordinates);

    void setCell(Stone stone, Coordinates coordinates);

    boolean isEmptyCell(Coordinates coordinates);

    List<Coordinates> getAdjacentCoordinatesAt(Coordinates coordinates);

    boolean areValidCoordinates(Coordinates coordinates);

    boolean areStonesOfSameColourAt(Coordinates current, Coordinates coordinateInDirection);
}
