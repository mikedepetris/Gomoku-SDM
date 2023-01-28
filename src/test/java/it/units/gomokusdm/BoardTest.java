package it.units.gomokusdm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
        Board board = new BoardImplementation(boardSize);
        Assertions.assertEquals(boardSize, board.getBoardDimension());
    }

    private static Stream<Arguments> generateSomeValidCoordinates() {
        return Stream.of(Arguments.of(new Coordinates(10, 10)), Arguments.of(new Coordinates(0, 0)), Arguments.of(new Coordinates(0, 0)), Arguments.of(new Coordinates(8, 1)), Arguments.of(new Coordinates(0, 15)), Arguments.of(new Coordinates(18, 17)));
    }

    @ParameterizedTest
    @MethodSource("generateSomeValidCoordinates")
    public void testIfCoordinatesAreValidOn19x19Board(Coordinates coordinates) {
        Board board = new BoardImplementation();
        board.setupBoard(DEFAULT_BOARD_SIZE);
        Assertions.assertTrue(board.areValidCoordinates(coordinates));
    }

    private static Stream<Arguments> generateSomeInvalidCoordinates() {
        return Stream.of(Arguments.of(new Coordinates(-2, 3)), Arguments.of(new Coordinates(28, 21)), Arguments.of(new Coordinates(41, 0)), Arguments.of(new Coordinates(20, 4)));
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
        IntStream.range(0, DEFAULT_BOARD_SIZE).forEach(row -> IntStream.range(0, DEFAULT_BOARD_SIZE).forEach(col -> {
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
     * String expected_result = "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|" + System.lineSeparator() +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|" + System.lineSeparator() +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|" + System.lineSeparator() +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|" + System.lineSeparator() +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|" + System.lineSeparator() +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|" + System.lineSeparator() +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|" + System.lineSeparator() +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|" + System.lineSeparator() +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|" + System.lineSeparator() +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|" + System.lineSeparator() +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|" + System.lineSeparator() +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|" + System.lineSeparator() +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|" + System.lineSeparator() +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|" + System.lineSeparator() +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|" + System.lineSeparator() +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|" + System.lineSeparator() +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|" + System.lineSeparator() +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|" + System.lineSeparator() +
     * "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|" + System.lineSeparator();
     * <p>
     * Assertions.assertEquals(expected_result, result);
     * <p>
     * }
     **/
    @Test
    public void testBoardToStringAtTheBeginning() {
        BoardImplementation board = new BoardImplementation();
        board.setupBoard(DEFAULT_BOARD_SIZE);
        String result = board.getBoardInLine();
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
        List<Coordinates> expectedAdjacentCoordinates = List.of(new Coordinates(0, 0), new Coordinates(0, 1), new Coordinates(0, 2), new Coordinates(1, 0), new Coordinates(1, 2), new Coordinates(2, 0), new Coordinates(2, 1), new Coordinates(2, 2));
        List<Coordinates> adjacentCoordinates = board.getAdjacentCoordinatesAt(coordinateUsedToGetAdjCoordinates);
        expectedAdjacentCoordinates.forEach(expctedAdjCoord -> Assertions.assertTrue(adjacentCoordinates.contains(expctedAdjCoord)));
    }

    @Test
    public void testLoadBoard() {
        final int BOARD_DIMENSION = 19;
        Board board = new BoardImplementation();
        board.setupBoard(BOARD_DIMENSION);
        int[][] intBoard = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        for (int xcoord = 0; xcoord < BOARD_DIMENSION; xcoord++) {
            for (int ycoord = 0; ycoord < BOARD_DIMENSION; ycoord++) {
                if (intBoard[xcoord][ycoord] == 1) board.setCell(Stone.BLACK, new Coordinates(xcoord, ycoord));
                else if (intBoard[xcoord][ycoord] == 2) board.setCell(Stone.WHITE, new Coordinates(xcoord, ycoord));
            }
        }
        System.out.println(insertLineSeparatorFunctional(board.toString(), BOARD_DIMENSION));
        int numberOfEmptyCell = board.getNumberOfEmptyPositionInBoard();
        Assertions.assertEquals(BOARD_DIMENSION * BOARD_DIMENSION - 10, numberOfEmptyCell);
    }

    @Test
    public void testLoadBoardFunctional() {
        int[][] intBoard = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        int boardDimension = intBoard.length;
        Board board = new BoardImplementation();
        board.setupBoard(boardDimension);
        fillBoardFromIntBoard(board, intBoard);

        System.out.println(insertLineSeparatorFunctional(board.toString(), boardDimension));
        int numberOfEmptyCell = board.getNumberOfEmptyPositionInBoard();
        Assertions.assertEquals(boardDimension * boardDimension - countNonZeroes(intBoard), numberOfEmptyCell);
    }

    private static void fillBoardFromIntBoard(Board board, int[][] intBoard) {
        int boardDimension = intBoard.length;
//      This version of the code uses nested IntStreams to iterate over the intBoard array and set the cells on the Board object based on the values in the array.
//      The forEach method is used to perform the operations on each coordinate in the array.
//      The method insertLineSeparatorFunctional is used to format the output string.
//      Note that this code still using a nested loops and it could be refactored further to use flatMap, but I think this is the most simple and readable way to refactor it.
        IntStream.range(0, boardDimension).forEach(x ->
                IntStream.range(0, boardDimension).forEach(y -> {
                    if (intBoard[x][y] == 1)
                        board.setCell(Stone.BLACK, new Coordinates(x, y));
                    else if (intBoard[x][y] == 2)
                        board.setCell(Stone.WHITE, new Coordinates(x, y));
                })
        );
//        Arrays.stream(intBoard)
//                .flatMapToInt(Arrays::stream)
//                .forEach(i -> {
//                    int xcoord = i / boardDimension;
//                    int ycoord = i % boardDimension;
//                    if (intBoard[xcoord][ycoord] == 1)
//                        board.setCell(Stone.BLACK, new Coordinates(xcoord, ycoord));
//                    else if (intBoard[xcoord][ycoord] == 2)
//                        board.setCell(Stone.WHITE, new Coordinates(xcoord, ycoord));
//                });
    }

    @Test
    public void testLoadBoard4() {
        int[][] intBoard = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 1, 2, 1}, {0, 0, 0, 0},};
        final int boardDimension = intBoard.length;
        Board board = new BoardImplementation(boardDimension);
        for (int xcoord = 0; xcoord < boardDimension; xcoord++) {
            for (int ycoord = 0; ycoord < boardDimension; ycoord++) {
                if (intBoard[xcoord][ycoord] == 1) board.setCell(Stone.BLACK, new Coordinates(xcoord, ycoord));
                else if (intBoard[xcoord][ycoord] == 2) board.setCell(Stone.WHITE, new Coordinates(xcoord, ycoord));
            }
        }
        System.out.println(insertLineSeparatorFunctional(board.toString(), boardDimension));
        int numberOfEmptyCell = board.getNumberOfEmptyPositionInBoard();
        Assertions.assertEquals(boardDimension * boardDimension - countNonZeroes(intBoard), numberOfEmptyCell);
    }

    @Test
    public void testLoadBoard4FromLines() {
        List<String> lines = Arrays.asList("****", "****", "*BWB", "****");
        //intBoard = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 1, 2, 1}, {0, 0, 0, 0},};
        int[][] intBoard = getIntBoardFromLines(lines);
        final int boardDimension = intBoard.length;
        Board board = new BoardImplementation(boardDimension);
        for (int xcoord = 0; xcoord < boardDimension; xcoord++) {
            for (int ycoord = 0; ycoord < boardDimension; ycoord++) {
                if (intBoard[xcoord][ycoord] == 1) board.setCell(Stone.BLACK, new Coordinates(xcoord, ycoord));
                else if (intBoard[xcoord][ycoord] == 2) board.setCell(Stone.WHITE, new Coordinates(xcoord, ycoord));
            }
        }
        System.out.println(insertLineSeparatorFunctional(board.toString(), boardDimension));
        int numberOfEmptyCell = board.getNumberOfEmptyPositionInBoard();
        Assertions.assertEquals((long) boardDimension * boardDimension - countNonZeroes(intBoard), numberOfEmptyCell);
    }

    private static String insertLineSeparatorFunctional(String input, int lineLength) {
        return IntStream.range(0, input.length() / lineLength + 1)
                .mapToObj(i -> input.substring(i * lineLength, Math.min((i + 1) * lineLength, input.length())))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private static String insertLineSeparator(String input, int lineLength) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i += lineLength) {
            int endIndex = Math.min(i + lineLength, input.length());
            output.append(input, i, endIndex);
            output.append(System.lineSeparator());
        }
        return output.toString();
    }

    private static long countNonZeroes(int[][] intBoard) {
        long nonZeroes = Arrays.stream(intBoard).flatMapToInt(Arrays::stream).filter(i -> i != 0).count();
        return nonZeroes;
    }

    private static int[][] readBoardFromFile() {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get("path/to/file.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return getIntBoardFromLines(lines);
    }

    private static int[][] getIntBoardFromLines(List<String> lines) {
        return lines.stream()
                .map(line -> line.chars()
                        .map(c -> c == '*' ? 0 : c == 'B' ? 1 : 2)
                        .toArray())
                .toArray(int[][]::new);
    }
}
