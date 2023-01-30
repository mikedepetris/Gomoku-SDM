package it.units.gomokusdm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BoardImplementationTest {
    private static final int DEFAULT_BOARD_SIZE = 19;

    private static Stream<Arguments> generateSomeValidCoordinates() {
        return Stream.of(
                Arguments.of(new Coordinates(10, 10)),
                Arguments.of(new Coordinates(0, 0)),
                Arguments.of(new Coordinates(0, 0)),
                Arguments.of(new Coordinates(8, 1)),
                Arguments.of(new Coordinates(0, 15)),
                Arguments.of(new Coordinates(18, 17)));
    }

    private static Stream<Arguments> generateSomeInvalidCoordinates() {
        return Stream.of(Arguments.of(new Coordinates(-2, 3)),
                Arguments.of(new Coordinates(28, 21)),
                Arguments.of(new Coordinates(41, 0)),
                Arguments.of(new Coordinates(20, 4)));
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

    private static long countBlackStones(int[][] intBoard) {
        return Arrays.stream(intBoard).flatMapToInt(Arrays::stream).filter(i -> i == 1).count();
    }
    private static long countWhiteStones(int[][] intBoard) {
        return Arrays.stream(intBoard).flatMapToInt(Arrays::stream).filter(i -> i == 2).count();
    }

    public static long countBlackStones(BoardImplementation board) {
        return countBlackStones(getIntBoardFromLines(board.getBoardAsLines()));
    }
    public static long countWhiteStones(BoardImplementation board) {
        return countWhiteStones(getIntBoardFromLines(board.getBoardAsLines()));
    }
    private static long countNonZeroes(int[][] intBoard) {
        return Arrays.stream(intBoard).flatMapToInt(Arrays::stream).filter(i -> i != 0).count();
    }

    private static long countNonZeroes(List<String> boardLines) {
        int[][] intBoard = getIntBoardFromLines(boardLines);
        return countNonZeroes(intBoard);
    }

    static long countNonZeroes(BoardImplementation board) {
        int[][] intBoard = getIntBoardFromLines(board.getBoardAsLines());
        return countNonZeroes(intBoard);
    }

    // Leggi la board da file di testo come in printCompact ***BWB***
    static BoardImplementation readBoardFromFile(String filePath) {
        List<String> lines;
        try {
            //String filePath = "src/test/resources/board_06.txt";
            Path path = Paths.get(filePath);
            lines = Files.readAllLines(path);
            //lines = Files.readAllLines(Paths.get("board_06.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return getBoardFromLines(lines);
    }

    @Test
    public void testBoardInitWithDefaultBoardSizeOf19x19() {
        BoardImplementation board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        int numOfNonEmptyStones = board.getNumberOfOccupiedPositionInBoard();
        Assertions.assertEquals(0, numOfNonEmptyStones);
    }

    @Test
    public void testDefaultBoardSize() {
        BoardImplementation board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        Assertions.assertEquals(DEFAULT_BOARD_SIZE, board.getBoardDimension());
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 9, 15})
    public void testBoardCreationWithDifferentBoardSize(int boardSize) {
        Board board = new BoardImplementation(boardSize);
        Assertions.assertEquals(boardSize, board.getBoardDimension());
    }

    @ParameterizedTest
    @MethodSource("generateSomeValidCoordinates")
    public void testIfCoordinatesAreValidOn19x19Board(Coordinates coordinates) {
        BoardImplementation board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        Assertions.assertTrue(board.areValidCoordinates(coordinates));
    }

    @ParameterizedTest
    @MethodSource("generateSomeInvalidCoordinates")
    public void testIfCoordinatesAreInvalidOn19x19Board(Coordinates coordinates) {
        BoardImplementation board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        Assertions.assertFalse(board.areValidCoordinates(coordinates));
    }

    private BoardImplementation occupyAllTheBoard() {
        BoardImplementation board = new BoardImplementation(DEFAULT_BOARD_SIZE);
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
        BoardImplementation board = occupyAllTheBoard();
        int numberOfEmptyCell = board.getNumberOfEmptyPositionInBoard();
        Assertions.assertEquals(0, numberOfEmptyCell);
    }

    @Test
    public void testBoardToStringAtTheBeginning() {
        BoardImplementation board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        String result = board.toString();
        int numberOfStonesInAFullBoard = (int) Math.pow(board.getBoardDimension(), 2);
        String expected_result = "*".repeat(numberOfStonesInAFullBoard);
        Assertions.assertEquals(expected_result, result);
    }

    @Test
    public void testGetStoneInTheFirstCellOfBoardAfterTheStartOfTheGame() {
        Board board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        Coordinates coordinates = new Coordinates(0, 0);
        Assertions.assertEquals(Stone.EMPTY, board.getStoneAt(coordinates));
    }

    @Test
    void testGetAdjacentCoordinates() {
        BoardImplementation board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        Coordinates coordinateUsedToGetAdjCoordinates = new Coordinates(1, 1);
        List<Coordinates> expectedAdjacentCoordinates = List.of(new Coordinates(0, 0),
                new Coordinates(0, 1),
                new Coordinates(0, 2),
                new Coordinates(1, 0),
                new Coordinates(1, 2),
                new Coordinates(2, 0),
                new Coordinates(2, 1),
                new Coordinates(2, 2));
        List<Coordinates> adjacentCoordinates = board.getAdjacentCoordinatesAt(coordinateUsedToGetAdjCoordinates);
        expectedAdjacentCoordinates.forEach(
                expctedAdjCoord -> Assertions.assertTrue(adjacentCoordinates.contains(expctedAdjCoord)));
    }

    @Test
    public void testLoadBoard() {
        final int BOARD_DIMENSION = 19;
        BoardImplementation board = new BoardImplementation();
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
        BoardImplementation board = getBoardFromIntBoard(intBoard);
        System.out.println(insertLineSeparatorFunctional(board.toString(), boardDimension));
        int numberOfEmptyCell = board.getNumberOfEmptyPositionInBoard();
        Assertions.assertEquals(boardDimension * boardDimension - countNonZeroes(intBoard), numberOfEmptyCell);
    }

    @Test
    public void testLoadBoard4() {
        int[][] intBoard = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 1, 2, 1}, {0, 0, 0, 0},};
        final int boardDimension = intBoard.length;
        BoardImplementation board = getBoardFromIntBoard(intBoard);
        int numberOfEmptyCell = board.getNumberOfEmptyPositionInBoard();
        Assertions.assertEquals(boardDimension * boardDimension - countNonZeroes(intBoard), numberOfEmptyCell);
    }

    @Test
    public void testLoadBoard4FromLines() {
        List<String> lines = Arrays.asList("****", "****", "*BWB", "****");
        final int boardDimension = lines.size();
        int[][] intBoard = getIntBoardFromLines(lines);
        BoardImplementation board = getBoardFromIntBoard(intBoard);
        int numberOfEmptyCell = board.getNumberOfEmptyPositionInBoard();
        Assertions.assertEquals((long) boardDimension * boardDimension - countNonZeroes(intBoard), numberOfEmptyCell);
    }

    @Test
    public void testLoadBoardFromFile() {
        BoardImplementation board = readBoardFromFile("src/test/resources/board_06.txt");
        final int boardDimension = board.getBoardDimension();
        int numberOfEmptyCell = board.getNumberOfEmptyPositionInBoard();
        Assertions.assertEquals((long) boardDimension * boardDimension - countNonZeroes(board), numberOfEmptyCell);
        Assertions.assertEquals((long) boardDimension * boardDimension - 9, numberOfEmptyCell);
    }

    @Test
    public void testBoardGameDraw60Stones() {
        BoardImplementation board = readBoardFromFile("src/test/resources/board_game_draw_60_stones.txt");
        final int boardDimension = board.getBoardDimension();
        int numberOfEmptyCell = board.getNumberOfEmptyPositionInBoard();
        // 19*19=361 59+60=119 361-119=242
        Assertions.assertEquals(361, boardDimension * boardDimension);
        Assertions.assertEquals(countNonZeroes(board), (long) boardDimension * boardDimension - numberOfEmptyCell);
        Assertions.assertEquals(119, boardDimension * boardDimension - numberOfEmptyCell);
    }

    private static int[][] getIntBoardFromLines(List<String> lines) {
        return lines.stream()
                .map(line -> line.chars()
                        .map(c -> c == '*' ? 0 : c == 'B' ? 1 : 2)
                        .toArray())
                .toArray(int[][]::new);
    }

    private static int[][] getIntBoardFromBoard(BoardImplementation board) {
        return getIntBoardFromLines(board.getBoardAsLines());
    }

    private static BoardImplementation getBoardFromLines(List<String> lines) {
        return getBoardFromIntBoard(getIntBoardFromLines(lines));
    }

    private static BoardImplementation getBoardFromIntBoard(int[][] intBoard) {
        int boardDimension = intBoard.length;
        BoardImplementation board = new BoardImplementation(boardDimension);
//        for (int xcoord = 0; xcoord < boardDimension; xcoord++) {
//            for (int ycoord = 0; ycoord < boardDimension; ycoord++) {
//                if (intBoard[xcoord][ycoord] == 1) board.setCell(Stone.BLACK, new Coordinates(xcoord, ycoord));
//                else if (intBoard[xcoord][ycoord] == 2) board.setCell(Stone.WHITE, new Coordinates(xcoord, ycoord));
//            }
//        }
/*
      This version of the code uses nested IntStreams to iterate over the intBoard array
      and set the cells on the Board object based on the values in the array.
      The forEach method is used to perform the operations on each coordinate in the array.
      The method insertLineSeparatorFunctional is used to format the output string.
      Note that this code still using a nested loops, and it could be refactored further to use flatMap,
      but I think this is the most simple and readable way to refactor it.
*/
        IntStream.range(0, boardDimension).forEach(x ->
                IntStream.range(0, boardDimension).forEach(y -> {
                    if (intBoard[x][y] == 1)
                        board.setCell(Stone.BLACK, new Coordinates(x, y));
                    else if (intBoard[x][y] == 2)
                        board.setCell(Stone.WHITE, new Coordinates(x, y));
                })
        );
        System.out.println("insertLineSeparatorFunctional(board.toString(), boardDimension)");
        System.out.println(insertLineSeparatorFunctional(board.toString(), boardDimension));
        return board;
    }

}
