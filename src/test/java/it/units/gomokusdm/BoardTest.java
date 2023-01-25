package it.units.gomokusdm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BoardTest {
    private static final int DEFAULT_BOARD_SIZE = 19;
    @Test
    public void testBoardInitWithDefaultBoardSizeOf19x19() {
        Board board = new BoardImplementation();
        board.setupBoard(DEFAULT_BOARD_SIZE);
        int numOfNonEmptyStones = board.getNumberOfOccupiedPositionInBoard();
        Assertions.assertEquals(0, numOfNonEmptyStones);
    }

    @Test
    public void testDefaultBoardSize() {
        Board board = new BoardImplementation();
        board.setupBoard(DEFAULT_BOARD_SIZE);
        Assertions.assertEquals(DEFAULT_BOARD_SIZE, board.getBoardDimension());
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 9, 15})
    public void testBoardCreationWithDifferentBoardSize(int boardSize) {
        Board board = new BoardImplementation();
        board.setupBoard(boardSize);
        Assertions.assertEquals(boardSize, board.getBoardDimension());
    }

    private static Stream<Arguments> generateSomeValidCoordinates() {
        return Stream.of(
                Arguments.of(new Coordinates(10, 10)),
                Arguments.of(new Coordinates(0, 0)),
                Arguments.of(new Coordinates(0, 0)),
                Arguments.of(new Coordinates(8, 1)),
                Arguments.of(new Coordinates(0, 15)),
                Arguments.of(new Coordinates(18, 17)));
    }

    @ParameterizedTest
    @MethodSource("generateSomeValidCoordinates")
    public void testIfCoordinatesAreValidOn19x19Board(Coordinates coordinates) {
        Board board = new BoardImplementation();
        board.setupBoard(DEFAULT_BOARD_SIZE);
        Assertions.assertTrue(board.areValidCoordinates(coordinates));
    }

    private static Stream<Arguments> generateSomeInvalidCoordinates() {
        return Stream.of(
                Arguments.of(new Coordinates(-2, 3)),
                Arguments.of(new Coordinates(28, 21)),
                Arguments.of(new Coordinates(41, 0)),
                Arguments.of(new Coordinates(20, 4)));
    }

    @ParameterizedTest
    @MethodSource("generateSomeInvalidCoordinates")
    public void testIfCoordinatesAreInvalidOn19x19Board(Coordinates coordinates) {
        Board board = new BoardImplementation();
        board.setupBoard(DEFAULT_BOARD_SIZE);
        Assertions.assertFalse(board.areValidCoordinates(coordinates));
    }

    private Board occupyAllTheBoard() {
        Board board = new BoardImplementation();
        board.setupBoard(DEFAULT_BOARD_SIZE);
        IntStream.range(0, DEFAULT_BOARD_SIZE).forEach(row ->
                IntStream.range(0, DEFAULT_BOARD_SIZE)
                        .forEach(col -> {
                            if (col % 2 == 0) {
                                board.setCell(Stone.BLACK, new Coordinates(row, col));
                            } else {
                                board.setCell(Stone.WHITE, new Coordinates(row, col));
                            }
                        }));
        return board;
    }

    @Test
    public void testOccupyAllBoard() {
        Board board = occupyAllTheBoard();
        int numberOfEmptyCell = board.getNumberOfEmptyPositionInBoard();
        Assertions.assertEquals(0, numberOfEmptyCell);
    }

    /**
     * @Test void testBoardPrintBegin() {
     * Board board = new Board();
     * String result = board.toString();
     * String expected_result = "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n";
     * <p>
     * Assertions.assertEquals(expected_result, result);
     * <p>
     * }
     **/
    @Test
    public void testBoardToStringAtTheBeginning() {
        BoardImplementation board = new BoardImplementation();
        board.setupBoard(DEFAULT_BOARD_SIZE);
        String result = board.toString();
        int numberOfStonesInAFullBoard = (int) Math.pow(board.getBoardDimension(), 2);
        String expected_result = "*".repeat(numberOfStonesInAFullBoard);
        Assertions.assertEquals(expected_result, result);
    }


    @Test
    public void testGetStoneInTheFirstCellOfBoardAfterTheStartOfTheGame() {
        Board board = new BoardImplementation();
        board.setupBoard(DEFAULT_BOARD_SIZE);
        Coordinates coordinates = new Coordinates(0, 0);
        Assertions.assertEquals(Stone.EMPTY, board.getStoneAt(coordinates));
    }

    @Test
    void testGetAdjacentCoordinates() {
        Board board = new BoardImplementation();
        board.setupBoard(DEFAULT_BOARD_SIZE);
        Coordinates coordinateUsedToGetAdjCoordinates = new Coordinates(1, 1);
        List<Coordinates> expectedAdjacentCoordinates =List.of(
                        new Coordinates(0, 0),
                        new Coordinates(0, 1),
                        new Coordinates(0, 2),
                        new Coordinates(1, 0),
                        new Coordinates(1, 2),
                        new Coordinates(2, 0),
                        new Coordinates(2, 1),
                        new Coordinates(2, 2)
        );
        List<Coordinates> adjacentCoordinates = board.getAdjacentCoordinatesAt(coordinateUsedToGetAdjCoordinates);
        expectedAdjacentCoordinates
                .forEach(expctedAdjCoord -> Assertions.assertTrue(adjacentCoordinates.contains(expctedAdjCoord)));
    }

}
