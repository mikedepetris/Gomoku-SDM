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

    @Test
    void testBoardPrintBegin() {
        Board board = new Board();
        String result = board.toString();
        //System.out.print(result);
        String expected_result ="|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|B|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n";

        Assertions.assertEquals(expected_result, result);

    }

    @Test
    void testSetCell() {
        Board board = new Board();
        Colour c = Colour.WHITE;
        Coordinates coordinates = new Coordinates(9,8);

        board.setCell(c, coordinates);
        String result = board.toString();
        String expected_result ="|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|W|B|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n";


    }

    @Test
    public void testGetStoneInTheFirstCellOfBoardAfterTheStartOfTheGame(){
        Board board = new Board();
        Coordinates coordinates = new Coordinates(0,0);
        Assertions.assertEquals(0, board.getStoneAt(coordinates));
    }

    @Test
    public void testGetStoneAtAfterTheFirstMovementOfBlackPlayer(){
        Board board = new Board();
        Coordinates coordinates = new Coordinates(board.getBoardDimension()/2, board.getBoardDimension()/2 );
        Assertions.assertEquals(1, board.getStoneAt(coordinates));
    }










}
