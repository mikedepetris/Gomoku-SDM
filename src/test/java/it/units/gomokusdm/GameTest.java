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

    // Fallisce perchè: NullPointerException:this.lastMoveCoordinates is null
    @Test
    public void testMakeFirstMove() {
        Player firstPlayer = new Player("First", Colour.BLACK);
        Player secondPlayer = new Player("Second", Colour.WHITE);
        Board board = new Board();
        Game game = new Game(board, firstPlayer, secondPlayer);
        game.makeMove(firstPlayer, new Coordinates(1, 1));
        Colour stoneColor = switch (board.getBoard()[1][1]) {
            case 1 -> Colour.BLACK;
            case 2 -> Colour.WHITE;
            default -> null;
        };
        Assertions.assertEquals(stoneColor, firstPlayer.getColour());
    }

    // Fallisce perchè: NullPointerException:this.lastMoveCoordinates is null
    // Mi aspetto che fallisca perchè, il player ha fatto delle mosse in coordinate non adiacenti a quella centrale
    @Test
    public void testMakeConsecutiveMoves() {
        Player firstPlayer = new Player("First", Colour.BLACK);
        Player secondPlayer = new Player("Second", Colour.WHITE);
        Board board = new Board();
        Game game = new Game(board, firstPlayer, secondPlayer);
        //game.makeMove(firstPlayer, new Coordinates(1, 1));
        game.makeMove(secondPlayer, new Coordinates(1, 2));
        game.makeMove(firstPlayer, new Coordinates(1, 3));
        int[][] expectedBoard =
                {
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
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
