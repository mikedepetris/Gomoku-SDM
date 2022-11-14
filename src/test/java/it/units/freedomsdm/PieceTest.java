package it.units.freedomsdm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PieceTest {

    @Test
    void testIfPieceIsCorrectlyCreatedGivenPositionAndStoneColour(){
        Piece piece = new Piece( new Coordinates(0,0), Colour.BLACK);
        Assertions.assertEquals("(0,0)", piece.getCoordinates().toString());
        Assertions.assertEquals(Colour.BLACK, piece.getStone().getStoneColour());
    }
}
