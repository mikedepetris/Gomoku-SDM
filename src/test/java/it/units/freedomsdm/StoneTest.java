package it.units.freedomsdm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StoneTest {

    @Test
    void testIfPieceIsCorrectlyCreatedGivenPositionAndStoneColour(){
        Stone stone = new Stone( new Coordinates(0,0), Colour.BLACK);
        Assertions.assertEquals("(0,0)", stone.getCoordinates().toString());
        Assertions.assertEquals(Colour.BLACK, stone.getStoneColour());
    }

    @Test
    void checkIfStoneIsWhite(){
        Stone stone = new Stone( new Coordinates(0,0), Colour.WHITE);
        Assertions.assertEquals(Colour.WHITE, stone.getStoneColour());
    }

    @Test
    void checkIfStoneIsBlack(){
        Stone stone = new Stone( new Coordinates(0,0), Colour.BLACK);
        Assertions.assertEquals(Colour.BLACK, stone.getStoneColour());
    }

}
