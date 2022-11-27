package it.units.gomokusdm;
import net.bytebuddy.matcher.CollectionOneToOneMatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CoordinatesTest {

    @Test
    void getRowIndexOfCoordinate(){
        Coordinates coordinate = new Coordinates(0,0);
        Assertions.assertEquals(0, coordinate.getRowIndex());
    }

    @Test
    void getColIndexOfCoordinate(){
        Coordinates coordinate = new Coordinates(0,0);
        Assertions.assertEquals(0, coordinate.getColIndex());
    }

    @Test
    void setRowIndexOfCoordinate(){
        Coordinates coordinate = new Coordinates(0,0);
        coordinate.setRowIndex(1);
        Assertions.assertEquals(1, coordinate.getRowIndex());
    }

    @Test
    void setColIndexOfCoordinate(){
        Coordinates coordinate = new Coordinates(0,0);
        coordinate.setColIndex(1);
        Assertions.assertEquals(1, coordinate.getColIndex());
    }

    @Test
    void testGetAdjacentCoordinates() {
        Coordinates coordinate = new Coordinates(1,1);
        Coordinates[] expectedAdjacentCoordinates =
                {
                        new Coordinates(0, 0),
                        new Coordinates(0, 1),
                        new Coordinates(0, 2),
                        new Coordinates(1, 0),
                        new Coordinates(1, 1),      //potrebbe essere rimosso (non è adiacente nel senso stretto)
                        new Coordinates(1, 2),
                        new Coordinates(2, 0),
                        new Coordinates(2, 1),
                        new Coordinates(2, 2)
                };
        ArrayList<Coordinates> adjacentCoordinates = coordinate.getAdjacentCoordinates();
        for (int index = 0; index < adjacentCoordinates.size(); index++) {
            Assertions.assertEquals(adjacentCoordinates.get(index), expectedAdjacentCoordinates[index]);
        }
    }

    @Test
    void testEqualsCoordinates() {
        Coordinates firstCoordinates = new Coordinates(1, 1);
        Coordinates secondCoordinates = new Coordinates(1, 1);
        Assertions.assertEquals(firstCoordinates, secondCoordinates);
    }
}
