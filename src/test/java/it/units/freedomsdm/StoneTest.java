package it.units.freedomsdm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StoneTest {

    @Test
    void checkIfStoneIsWhite(){
        Stone stone = new Stone(Colour.WHITE);
        Assertions.assertEquals(Colour.WHITE, stone.getStoneColour());
    }

    @Test
    void checkIfStoneIsBlack(){
        Stone stone = new Stone(Colour.BLACK);
        Assertions.assertEquals(Colour.BLACK, stone.getStoneColour());
    }

}
