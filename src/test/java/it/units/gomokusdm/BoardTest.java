package it.units.gomokusdm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    void testBoardCreation(){
        Board board = new Board();
        Assertions.assertEquals(19, board.getBoard().length);
    }

    @Test
    void testBoardCreationWithDifferentBoardSize(){
        Board board = new Board();
        Assertions.assertNotEquals(10, board.getBoard().length);
    }
}
