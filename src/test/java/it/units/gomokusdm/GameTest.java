package it.units.gomokusdm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.units.gomokusdm.Game.MAX_NUMBER_OF_STONES;

public class GameTest {
    private static final int DEFAULT_BOARD_SIZE = 19;

    @Test
    public void testGameInstantiation() {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation();
        Game game = new Game(board, firstPlayer, secondPlayer);
        Assertions.assertEquals(game.getBoard(), board);
        Assertions.assertEquals(game.getPlayer1(), firstPlayer);
        Assertions.assertEquals(game.getPlayer2(), secondPlayer);
    }

    @Test
    public void testInvalidGameInstantiationWithColorPlayersEqual() {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.BLACK);
        Board board = new BoardImplementation();
        Assertions.assertThrows(Exception.class, () -> new Game(board, firstPlayer, secondPlayer));
    }

    @Test
    public void testInvalidGameInstantiationWithPlayerNamesEqual() {
        Player firstPlayer = new Player("SameName", Stone.BLACK);
        Player secondPlayer = new Player("SameName", Stone.WHITE);
        Board board = new BoardImplementation();
        Assertions.assertThrows(Exception.class, () -> new Game(board, firstPlayer, secondPlayer));
        //Assertions.assertNotEquals(player1.getUsername(), player2.getUsername());
    }


    @Test
    public void testIsFeasibleMove() {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        Game game = new Game(board, firstPlayer, secondPlayer);
        // ho messo public isFeasibleMove() provvisoriamente per testare poi rimettiamo private se c'è esigenza
        boolean result = game.isFeasibleMove(new Coordinates(8, 9));
        Assertions.assertTrue(result);
    }

    @Test
    public void testIsNotFeasibleMove() {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        Game game = new Game(board, firstPlayer, secondPlayer);
        // ho messo public isFeasibleMove() provvisoriamente per testare poi rimettiamo private se c'è esigenza
        Assertions.assertFalse(game.isFeasibleMove(new Coordinates(1, 1)));
        Assertions.assertFalse(game.isFeasibleMove(new Coordinates(19, 19)));
    }


    // The game has begun.
    // The board is in the initial state, with the black stone in the center (9,9)
    // The second player (white) tries to make the move, in adjacent coordinates (8,9)

    @Test
    public void testMakeFirstMoveAdjacent() {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        Game game = new Game(board, firstPlayer, secondPlayer);
        //
        try {
            game.makeMove(secondPlayer, new Coordinates(8, 9));
        } catch (Game.InvalidMoveThrowable e) {
            e.printStackTrace();
        }
        Stone stoneColor = board.getStoneAt(new Coordinates(8, 9));
        Assertions.assertEquals(stoneColor, secondPlayer.getColour());
    }

    // The game has begun.
    // The board is in the initial state, with the black stone in the center (9,9)
    // The second player (white) tries to make the move, but in not adjacent coordinates (1,1)
    // the cell is empty
    @Test
    public void testMakeFirstMoveNotAdjacent() {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        Game game = new Game(board, firstPlayer, secondPlayer);
        try {
            game.makeMove(secondPlayer, new Coordinates(1, 1));
        } catch (Game.InvalidMoveThrowable e) {
            System.err.println("Handled makeMove Exception");
        } finally {
            Stone stoneColor = board.getStoneAt(new Coordinates(1, 1));
            Assertions.assertEquals(stoneColor, Stone.EMPTY);
        }
    }

    @Test
    public void testGetStoneAtAfterTheFirstMovementOfBlackPlayer() {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        Game game = new Game(board, firstPlayer, secondPlayer);
        Coordinates coordinates = new Coordinates(board.getBoardDimension() / 2,
                board.getBoardDimension() / 2);
        Assertions.assertEquals(Stone.BLACK, board.getStoneAt(coordinates));
    }


    @Test
    public void testMakeConsecutiveMoves() throws Game.InvalidMoveThrowable {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        Game game = new Game(board, firstPlayer, secondPlayer);
        // pedina non adiacente, mi aspetto che non faccia nulla
        try {
            game.makeMove(secondPlayer, new Coordinates(1, 2));
        } catch (Game.InvalidMoveThrowable e) {
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
            for (int i = 0; i < board.getBoardDimension(); i++) {
                for (int j = 0; j < board.getBoardDimension(); j++) {
                    Assertions.assertEquals(board.getStoneAt(new Coordinates(i, j)),
                            Stone.castIntToStone(expectedBoard[i][j]));
                }
            }
        }

    }

    @Test
    public void testMakeConsecutiveMovesOutsideTheBoard() {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        Game game = new Game(board, firstPlayer, secondPlayer);
        try {
            game.makeMove(secondPlayer, new Coordinates(9, 10));
            game.makeMove(firstPlayer, new Coordinates(9, 11));
            game.makeMove(secondPlayer, new Coordinates(9, 12));
            game.makeMove(firstPlayer, new Coordinates(9, 13));
            game.makeMove(secondPlayer, new Coordinates(9, 14));
            game.makeMove(firstPlayer, new Coordinates(9, 15));
            game.makeMove(secondPlayer, new Coordinates(9, 16));
            game.makeMove(firstPlayer, new Coordinates(9, 17));
            game.makeMove(secondPlayer, new Coordinates(9, 18));
            game.makeMove(firstPlayer, new Coordinates(0, 18));
            game.makeMove(firstPlayer, new Coordinates(9, 19));
        } catch (Game.InvalidMoveThrowable e) {
            System.err.println("Handled makeMove Exception");
        } finally {
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
            for (int i = 0; i < board.getBoardDimension(); i++) {
                for (int j = 0; j < board.getBoardDimension(); j++) {
                    Assertions.assertEquals(board.getStoneAt(new Coordinates(i, j)),
                            Stone.castIntToStone(expectedBoard[i][j]));
                }
            }
        }

    }


    @Test
    public void testIsPlayerWinningGame() throws Game.InvalidMoveThrowable {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        Game game = new Game(board, firstPlayer, secondPlayer);
        boolean[] result = new boolean[8];
        int i = 0;
        game.makeMove(secondPlayer, new Coordinates(9, 10));
        result[i] = game.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WHIT_A_WINNER);
        i++;
        game.makeMove(firstPlayer, new Coordinates(8, 8));
        result[i] = game.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WHIT_A_WINNER);
        i++;
        game.makeMove(secondPlayer, new Coordinates(9, 11));
        result[i] = game.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WHIT_A_WINNER);
        i++;
        game.makeMove(firstPlayer, new Coordinates(7, 7));
        result[i] = game.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WHIT_A_WINNER);
        i++;
        game.makeMove(secondPlayer, new Coordinates(9, 12));
        result[i] = game.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WHIT_A_WINNER);
        i++;
        game.makeMove(firstPlayer, new Coordinates(6, 6));
        result[i] = game.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WHIT_A_WINNER);
        i++;
        game.makeMove(secondPlayer, new Coordinates(9, 13));
        result[i] = game.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WHIT_A_WINNER);
        i++;
        game.makeMove(firstPlayer, new Coordinates(5, 5));
        result[i] = game.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WHIT_A_WINNER);

        boolean[] expected_result = {false, false, false, false, false, false, false, true};
        for (int j = 0; j < result.length; j++) {
            Assertions.assertEquals(result[j], expected_result[j]);
        }
    }

    @Test
    public void testIsPlayerWinningGameWith5ConsecutiveStones() {
        List<Boolean> result = new ArrayList<>();

        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        Game game;
        try {
            game = new Game(board, firstPlayer, secondPlayer);
            game.makeMove(secondPlayer, new Coordinates(9, 10));
            result.add(game.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WHIT_A_WINNER));
            game.makeMove(firstPlayer, new Coordinates(8, 8));
            result.add(game.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WHIT_A_WINNER));
            game.makeMove(secondPlayer, new Coordinates(9, 11));
            result.add(game.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WHIT_A_WINNER));
            game.makeMove(firstPlayer, new Coordinates(7, 7));
            result.add(game.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WHIT_A_WINNER));
            game.makeMove(secondPlayer, new Coordinates(9, 12));
            result.add(game.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WHIT_A_WINNER));
            game.makeMove(firstPlayer, new Coordinates(6, 6));
            result.add(game.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WHIT_A_WINNER));
            game.makeMove(secondPlayer, new Coordinates(9, 13));
            result.add(game.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WHIT_A_WINNER));
            game.makeMove(firstPlayer, new Coordinates(5, 5));
            result.add(game.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WHIT_A_WINNER));
        } catch (Game.InvalidMoveThrowable e) {
            throw new RuntimeException(e);
        }

        List<Boolean> expected_result = List.of(false, false, false, false, false, false, false, true);
        Assertions.assertEquals(expected_result, result);
    }


    @Test
    void testWrongPlayerUsernamesAssignments() {
        Player player1 = new Player("player", Stone.BLACK);
        Player player2 = new Player("player", Stone.WHITE);

        Assertions.assertThrowsExactly(
                IllegalArgumentException.class, () -> new Game(new BoardImplementation(), player1, player2),
                "invalid player names");
    }

    @Test
    void testWrongPlayerColourAssignments() {
        Player player1 = new Player("player1", Stone.BLACK);
        Player player2 = new Player("player2", Stone.BLACK);

        Assertions.assertThrowsExactly(
                IllegalArgumentException.class, () -> new Game(new BoardImplementation(), player1, player2),
                "invalid player colours");
    }

    @Test
    void testInvalidMoves() {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        Game game = new Game(board, firstPlayer, secondPlayer);

        Assertions.assertThrowsExactly(
                Game.InvalidMoveThrowable.class,
                () -> game.makeMove(secondPlayer, new Coordinates(0, 0)),
                "Move not feasible"
        );

        Assertions.assertThrowsExactly(
                Game.InvalidMoveThrowable.class,
                () -> game.makeMove(firstPlayer, new Coordinates(0, 0)),
                "Is not First's turn"
        );
    }

    // If neither player can create a 5-stone lineup after placing all of their stones,
    // the game is declared a tie, and they must start over.
    @Test
    void testTie() {
        //TODO: da implementare con delle mosse valide (la classe Game è cambiata)
    }

}
