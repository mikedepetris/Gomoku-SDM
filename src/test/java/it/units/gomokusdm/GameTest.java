package it.units.gomokusdm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GameTest {

    @Test
    public void testGameInstantiation() throws Exception {
        Player firstPlayer = new Player("First", Colour.BLACK);
        Player secondPlayer = new Player("Second", Colour.WHITE);
        Board board = new Board();
        Game game = new Game(board, firstPlayer, secondPlayer);
        Assertions.assertEquals(game.getBoard(), board);
        Assertions.assertEquals(game.getPlayer1(), firstPlayer);
        Assertions.assertEquals(game.getPlayer2(), secondPlayer);
    }

    @Test
    public void testInvalidGameInstantiation() {
        Player firstPlayer = new Player("First", Colour.BLACK);
        Player secondPlayer = new Player("Second", Colour.BLACK);
        Board board = new Board();
        Assertions.assertThrows(Exception.class, () -> {
            Game game = new Game(board, firstPlayer, secondPlayer);
        });
    }

    @Test
    public void testIsFeasibleMove() throws Exception {
        Player firstPlayer = new Player("First", Colour.BLACK);
        Player secondPlayer = new Player("Second", Colour.WHITE);
        Board board = new Board();
        Game game = new Game(board, firstPlayer, secondPlayer);
        // ho messo public isFeasibleMove() provvisoriamente per testare poi rimettiamo private se c'è esigenza
        boolean result = game.isFeasibleMove(new Coordinates(8, 9));
        Assertions.assertTrue(result);

    }

    @Test
    public void testIsNotFeasibleMove() throws Exception {
        Player firstPlayer = new Player("First", Colour.BLACK);
        Player secondPlayer = new Player("Second", Colour.WHITE);
        Board board = new Board();
        Game game = new Game(board, firstPlayer, secondPlayer);
        // ho messo public isFeasibleMove() provvisoriamente per testare poi rimettiamo private se c'è esigenza
        boolean result = game.isFeasibleMove(new Coordinates(1, 1));
        Assertions.assertFalse(result);

    }


    // The game has begun.
    // The board is in the initial state, with the black stone in the center (9,9)
    // The second player (white) tries to make the move, in adjacent coordinates (8,9)

    @Test
    public void testMakeFirstMoveAdjacent() throws Exception {
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
    public void testMakeFirstMoveNotAdjacent() throws Exception {
        Player firstPlayer = new Player("First", Colour.BLACK);
        Player secondPlayer = new Player("Second", Colour.WHITE);
        Board board = new Board();
        Game game = new Game(board, firstPlayer, secondPlayer);
        try {
            game.makeMove(secondPlayer, new Coordinates(1, 1));
        } catch (Exception e) {
            System.err.println("Handled makeMove Exception");
        } finally {
            Colour stoneColor = switch (board.getBoard()[1][1]) {
                case 1 -> Colour.BLACK;
                case 2 -> Colour.WHITE;
                default -> null;
            };
            Assertions.assertEquals(stoneColor, null);
        }
    }

    @Test
    public void testGetStoneAtAfterTheFirstMovementOfBlackPlayer() throws Exception {
        Player firstPlayer = new Player("First", Colour.BLACK);
        Player secondPlayer = new Player("Second", Colour.WHITE);
        Board board = new Board();
        Game game = new Game(board, firstPlayer, secondPlayer);
        Coordinates coordinates = new Coordinates(board.getBoardDimension() / 2,
                board.getBoardDimension() / 2);
        Assertions.assertEquals(1, board.getStoneAt(coordinates));
    }


    @Test
    public void testMakeConsecutiveMoves() throws Exception {
        Player firstPlayer = new Player("First", Colour.BLACK);
        Player secondPlayer = new Player("Second", Colour.WHITE);
        Board board = new Board();
        Game game = new Game(board, firstPlayer, secondPlayer);
        // pedina non adiacente, mi aspetto che non faccia nulla
        try {
            game.makeMove(secondPlayer, new Coordinates(1, 2));
        } catch (Exception e) {
            System.err.println("Handled makeMove Exception");
        } finally {
            // suppongo che si faccia ripetere la mossa, stavolta in un punto adiacente
            game.makeMove(secondPlayer, new Coordinates(8, 9));
            game.makeMove(firstPlayer, new Coordinates(7, 9));
            game.makeMove(secondPlayer, new Coordinates(7, 8));
            int[][] expectedBoard =
                    {
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
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


    @Test
    public void testIsPlayerWinningGame() throws Exception {
        Player firstPlayer = new Player("First", Colour.BLACK);
        Player secondPlayer = new Player("Second", Colour.WHITE);
        Board board = new Board();
        Game game = new Game(board, firstPlayer, secondPlayer);

        boolean[] result = new boolean[8];
        int i = 0;
        game.makeMove(secondPlayer, new Coordinates(9, 10));
        result[i] = game.checkIfThereAreFiveConsecutiveStones(secondPlayer.getColour());
        i++;
        game.makeMove(firstPlayer, new Coordinates(8, 8));
        result[i] = game.checkIfThereAreFiveConsecutiveStones(firstPlayer.getColour());
        i++;
        game.makeMove(secondPlayer, new Coordinates(9, 11));
        result[i] = game.checkIfThereAreFiveConsecutiveStones(secondPlayer.getColour());
        i++;
        game.makeMove(firstPlayer, new Coordinates(7, 7));
        result[i] = game.checkIfThereAreFiveConsecutiveStones(firstPlayer.getColour());
        i++;
        game.makeMove(secondPlayer, new Coordinates(9, 12));
        result[i] = game.checkIfThereAreFiveConsecutiveStones(secondPlayer.getColour());
        i++;
        game.makeMove(firstPlayer, new Coordinates(6, 6));
        result[i] = game.checkIfThereAreFiveConsecutiveStones(firstPlayer.getColour());
        i++;
        game.makeMove(secondPlayer, new Coordinates(9, 13));
        result[i] = game.checkIfThereAreFiveConsecutiveStones(secondPlayer.getColour());
        i++;
        game.makeMove(firstPlayer, new Coordinates(5, 5));
        result[i] = game.checkIfThereAreFiveConsecutiveStones(firstPlayer.getColour());

        boolean[] expected_result = {false, false, false, false, false, false, false, true};
        for (int j = 0; j < result.length; j++) {
            Assertions.assertEquals(result[j], expected_result[j]);
        }
    }

    @Test
    public void testIsPlayerWinningGameWith5ConsecutiveStones() {
        Player firstPlayer = new Player("First", Colour.BLACK);
        Player secondPlayer = new Player("Second", Colour.WHITE);
        Board board = new Board();
        Game game = new Game(board, firstPlayer, secondPlayer);

        List<Boolean> result = new ArrayList<>();
        game.makeMove(secondPlayer, new Coordinates(9, 10));
        result.add(game.checkIfPlayerWins());
        game.makeMove(firstPlayer, new Coordinates(8, 8));
        result.add(game.checkIfPlayerWins());
        game.makeMove(secondPlayer, new Coordinates(9, 11));
        result.add(game.checkIfPlayerWins());
        game.makeMove(firstPlayer, new Coordinates(7, 7));
        result.add(game.checkIfPlayerWins());
        game.makeMove(secondPlayer, new Coordinates(9, 12));
        result.add(game.checkIfPlayerWins());
        game.makeMove(firstPlayer, new Coordinates(6, 6));
        result.add(game.checkIfPlayerWins());
        game.makeMove(secondPlayer, new Coordinates(9, 13));
        result.add(game.checkIfPlayerWins());
        game.makeMove(firstPlayer, new Coordinates(5, 5));
        result.add(game.checkIfPlayerWins());

        List<Boolean> expected_result = List.of(false, false, false, false, false, false, false, true);
        Assertions.assertEquals(expected_result, result);
    }

}
