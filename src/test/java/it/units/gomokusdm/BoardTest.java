package it.units.gomokusdm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BoardTest {



    @Test
    public void testBoardCreation() {
        Board board = new Board();
        //Assertions.assertEquals(19, board.getBoard().length);
        int nonEmptyStone = IntStream.range(0,19)
                .map(row -> IntStream.range(0, 19)
                        .map(col -> board.getStoneAt(new Coordinates(row, col)))
                        .sum())
                .sum();

        Assertions.assertEquals(0, nonEmptyStone);
    }

    @Test
     public void testDefaultBoardSize() {
        Board board = new Board();
        int defaultGomokuBoardSize = 19;
        Assertions.assertEquals(defaultGomokuBoardSize, board.getBoardDimension());
    }

    @ParameterizedTest
    @ValueSource(ints = {5,9,15})
    //TODO refactoring board con dimensione dinamica
    public void testBoardCreationWithDifferentBoardSize(int boardSize) {
        Board board = new Board();
        Assertions.assertNotEquals(boardSize, board.getBoard());
    }

    @ParameterizedTest
    @MethodSource("generateSomeValidCoordinates")
    public void testIfCoordinatesAreValidOn19x19Board(Coordinates coordinates){
        Board board = new Board();
        Assertions.assertTrue(board.areValidCoordinates(coordinates));
    }

    private static Stream<Arguments> generateSomeValidCoordinates() {
        return Stream.of(
                Arguments.of(new Coordinates(10,10)),
                Arguments.of(new Coordinates(0,0)),
                Arguments.of(new Coordinates(0,0)),
                Arguments.of(new Coordinates(8,1)),
                Arguments.of(new Coordinates(0,15)),
                Arguments.of(new Coordinates(18,17)));
    }

    @ParameterizedTest
    @MethodSource("generateSomeInvalidCoordinates")
    public void testIfCoordinatesAreInvalidOn19x19Board(Coordinates coordinates){
        Board board = new Board();
        Assertions.assertFalse(board.areValidCoordinates(coordinates));
    }

    private static Stream<Arguments> generateSomeInvalidCoordinates() {
        return Stream.of(
                Arguments.of(new Coordinates(-2,3)),
                Arguments.of(new Coordinates(28,21)),
                Arguments.of(new Coordinates(41,0)),
                Arguments.of(new Coordinates(20,4)));
    }

    private static Board occupyAllTheBoard(){
        Board board = new Board();
        IntStream.range(0,19).forEach(row ->
                IntStream.range(0, 19)
                        .forEach(col -> {
                            if (col % 2 == 0) {
                                board.setCell(Colour.BLACK, new Coordinates(row, col));
                            } else {
                                board.setCell(Colour.WHITE, new Coordinates(row, col));
                            }
                        }));
        return board;
    }

    @Test
    public void testOccupyAllBoard(){
        Board board = occupyAllTheBoard();
        int numberOfNonEmptyCell = IntStream.range(0,19)
                .map(row -> IntStream.range(0, 19)
                        .map(col -> board.getStoneAt(new Coordinates(row, col)))
                        .filter(stones -> stones == 0)
                        .sum())
                .sum();
        Assertions.assertEquals(0, numberOfNonEmptyCell);
    }

    @Test
    void testBoardPrintBegin() {
        Board board = new Board();
        String result = board.toString();
        String expected_result = "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
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
        Coordinates coordinates = new Coordinates(9, 8);

        board.setCell(c, coordinates);
        String result = board.toString();
        String expected_result = "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
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
    public void testGetStoneInTheFirstCellOfBoardAfterTheStartOfTheGame() {
        Board board = new Board();
        Coordinates coordinates = new Coordinates(0, 0);
        Assertions.assertEquals(0, board.getStoneAt(coordinates));
    }

}
