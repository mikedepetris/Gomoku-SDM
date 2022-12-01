package it.units.gomokusdm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameTest {

    @Test
    public void testGameInstantiation() {
        Player firstPlayer = new Player("First", Colour.BLACK);
        Player secondPlayer = new Player("Second", Colour.WHITE);
        Board board = new Board();
        Game game = new Game(board, firstPlayer, secondPlayer);
        Assertions.assertEquals(game.getBoard(), board);
        Assertions.assertEquals(game.getPlayer1(), firstPlayer);
        Assertions.assertEquals(game.getPlayer2(), secondPlayer);
    }

    @Test
    public void testIsFeasibleMove() {
        Player firstPlayer = new Player("First", Colour.BLACK);
        Player secondPlayer = new Player("Second", Colour.WHITE);
        Board board = new Board();
        Game game = new Game(board, firstPlayer, secondPlayer);
        // ho messo public isFeasibleMove() provvisoriamente per testare poi rimettiamo private se c'è esigenza
        boolean result = game.isFeasibleMove(new Coordinates(8,9));
        Assertions.assertEquals(true, result);

    }

    @Test
    public void testIsNotFeasibleMove() {
        Player firstPlayer = new Player("First", Colour.BLACK);
        Player secondPlayer = new Player("Second", Colour.WHITE);
        Board board = new Board();
        Game game = new Game(board, firstPlayer, secondPlayer);
        // ho messo public isFeasibleMove() provvisoriamente per testare poi rimettiamo private se c'è esigenza
        boolean result = game.isFeasibleMove(new Coordinates(1,1));
        Assertions.assertEquals(false, result);

    }


    // The game has begun.
    // The board is in the initial state, with the black stone in the center (9,9)
    // The second player (white) tries to make the move, in adjacent coordinates (8,9)

    @Test
    public void testMakeFirstMoveAdjacent() {
        Player firstPlayer = new Player("First", Colour.BLACK);
        Player secondPlayer = new Player("Second", Colour.WHITE);
        Board board = new Board();
        Game game = new Game(board, firstPlayer, secondPlayer);
        //
        game.makeMove(secondPlayer, new Coordinates(8, 9));
        Colour stoneColor = switch (board.getBoard()[8][9]) {
            case 1 -> Colour.BLACK;
            case 2 -> Colour.WHITE;
            default -> null;
        };
        Assertions.assertEquals(stoneColor, secondPlayer.getColour());
    }

    // The game has begun.
    // The board is in the initial state, with the black stone in the center (9,9)
    // The second player (white) tries to make the move, but in not adjacent coordinates (1,1)
    // the cell is empty
    @Test
    public void testMakeFirstMoveNotAdjacent() {
        Player firstPlayer = new Player("First", Colour.BLACK);
        Player secondPlayer = new Player("Second", Colour.WHITE);
        Board board = new Board();
        Game game = new Game(board, firstPlayer, secondPlayer);
        //
        game.makeMove(secondPlayer, new Coordinates(1, 1));
        Colour stoneColor = switch (board.getBoard()[1][1]) {
            case 1 -> Colour.BLACK;
            case 2 -> Colour.WHITE;
            default -> null;
        };
        Assertions.assertEquals(stoneColor, null);
    }


    @Test
    public void testMakeConsecutiveMoves() {
        Player firstPlayer = new Player("First", Colour.BLACK);
        Player secondPlayer = new Player("Second", Colour.WHITE);
        Board board = new Board();
        Game game = new Game(board, firstPlayer, secondPlayer);
        // pedina non adiacente, mi aspetto che non faccia nulla
        game.makeMove(secondPlayer, new Coordinates(1, 2));
        // suppongo che si faccia ripetere la mossa, stavolta in un punto adiacente
        game.makeMove(secondPlayer, new Coordinates(8, 9));
        int[][] expectedBoard =
                {
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
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
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[0].length; j++) {
                Assertions.assertEquals(board.getBoard()[i][j], expectedBoard[i][j]);
            }
        }
    }

}
