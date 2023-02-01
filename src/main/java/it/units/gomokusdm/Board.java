package it.units.gomokusdm;

import java.util.List;

public interface Board {

    int getBoardDimension();

    Stone getStoneAt(Coordinates coordinates);

    void setCell(Stone stone, Coordinates coordinates);

    boolean isEmptyCell(Coordinates coordinates);

    List<Coordinates> getAdjacentCoordinatesAt(Coordinates coordinates);

    boolean areValidCoordinates(Coordinates coordinates);

}
