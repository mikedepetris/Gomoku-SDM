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

    // Va prima implementato setCell in Board
    // Il test è invalido perchè il giocatore che inizia ("First"), è obbligato a inserire la pedina nera al centro.
    // Non può inserire nelle coordinate (1,1), ma mi aspetto in questo caso le coordinate (10,10) come prima mossa
    // Le successive mosse invece sono casuali
    @Test
    public void testMakeFirstMove() {
        Player firstPlayer = new Player("First", Colour.BLACK);
        Player secondPlayer = new Player("Second", Colour.WHITE);
        Board board = new Board();
        Game game = new Game(board, firstPlayer, secondPlayer);
        game.makeMove(firstPlayer, new Coordinates(1, 1)); // deve essere 10,10
        Colour stoneColor = switch (board.getBoard()[1][1]) {
            case 1 -> Colour.BLACK;
            case 2 -> Colour.WHITE;
            default -> null;
        };
        Assertions.assertEquals(stoneColor, firstPlayer.getColour());
    }

    // Va prima implementato setCell in Board
    // Il test è invalido perchè il giocatore che inizia ("First"), è obbligato a inserire la pedina nera al centro.
    // Non può inserire nelle coordinate (1,1), ma mi aspetto in questo caso le coordinate (10,10) come prima mossa
    // Le successive mosse invece sono casuali
    @Test
    public void testMakeConsecutiveMoves() {
        Player firstPlayer = new Player("First", Colour.BLACK);
        Player secondPlayer = new Player("Second", Colour.WHITE);
        Board board = new Board();
        Game game = new Game(board, firstPlayer, secondPlayer);
        game.makeMove(firstPlayer, new Coordinates(1, 1)); // deve essere 10,10
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
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
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
