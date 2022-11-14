package it.units.freedomsdm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CoordinatesTest {

    @Test
    void getRowIndexOfCoordinate(){
        Coordinates coordinate = new Coordinates(0,0);
        Assertions.assertEquals(0, coordinate.getRowIdx());
    }

    @Test
    void getColIndexOfCoordinate(){
        Coordinates coordinate = new Coordinates(0,0);
        Assertions.assertEquals(0, coordinate.getColIdx());
    }

    @Test
    void setRowIndexOfCoordinate(){
        Coordinates coordinate = new Coordinates(0,0);
        coordinate.setRowIdx(1);
        Assertions.assertEquals(1, coordinate.getRowIdx());
    }

    @Test
    void setColIndexOfCoordinate(){
        Coordinates coordinate = new Coordinates(0,0);
        coordinate.setColIdx(1);
        Assertions.assertEquals(1, coordinate.getColIdx());
    }
}
