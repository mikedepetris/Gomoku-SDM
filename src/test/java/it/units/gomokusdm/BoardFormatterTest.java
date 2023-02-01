package it.units.gomokusdm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BoardFormatterTest {

    private static final int BOARD_DIMENSION_15 = 15;
    private static final int BOARD_DIMENSION_19 = 19;

    @Test
    void testWhiteBoardFormatting() {
        Board board = new BoardImplementation(BOARD_DIMENSION_15);
        String formattedBoard = BoardFormatter.formatBoard(board);

        String expectedOutputString = System.lineSeparator() +
                "0\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "1\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "2\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "3\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "4\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "5\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "6\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "7\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "8\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "9\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "10\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "11\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "12\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "13\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "14\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t0\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10\t11\t12\t13\t14\t" +
                System.lineSeparator();

        Assertions.assertEquals(expectedOutputString, formattedBoard);
    }

    @Test
    void testBoardFormattingWithAStone() {
        Board board = new BoardImplementation(BOARD_DIMENSION_15);
        new GomokuGame(board, new Player("one", Stone.BLACK), new Player("two", Stone.WHITE));
        String formattedBoard = BoardFormatter.formatBoard(board);

        String expectedOutputString = System.lineSeparator() +
                "0\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "1\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "2\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "3\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "4\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "5\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "6\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "7\t*---*---*---*---*---*---*---B---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "8\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "9\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "10\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "11\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "12\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "13\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "14\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t0\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10\t11\t12\t13\t14\t" +
                System.lineSeparator();

        Assertions.assertEquals(expectedOutputString, formattedBoard);
    }

    @Test
    void testWhiteBoardFormatting15to1andAtoQ() {
        Board board = new BoardImplementation(BOARD_DIMENSION_15);
        String formattedBoard = BoardFormatter.formatBoard15to1andAtoO(board);

        String expectedOutputString = System.lineSeparator() +
                "15\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "14\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "13\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "12\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "11\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "10\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "9\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "8\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "7\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "6\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "5\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "4\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "3\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "2\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t|\t" + System.lineSeparator() +
                "1\t*---*---*---*---*---*---*---*---*---*---*---*---*---*---*   " + System.lineSeparator() +
                "\ta\tb\tc\td\te\tf\tg\th\ti\tj\tk\tl\tm\tn\to\t" +
                System.lineSeparator();

        Assertions.assertEquals(expectedOutputString, formattedBoard);
    }
}
