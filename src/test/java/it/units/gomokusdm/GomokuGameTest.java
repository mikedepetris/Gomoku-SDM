package it.units.gomokusdm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.units.gomokusdm.BoardImplementationTest.*;
import static it.units.gomokusdm.GomokuGame.MAX_NUMBER_OF_STONES;

public class GomokuGameTest {
    private static final int DEFAULT_BOARD_SIZE = 19;

    @Test
    public void testGameInstantiation() {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation();
        GomokuGame gomokuGame = new GomokuGame(board, firstPlayer, secondPlayer);
        Assertions.assertEquals(gomokuGame.getBoard(), board);
        Assertions.assertEquals(gomokuGame.getPlayer1(), firstPlayer);
        Assertions.assertEquals(gomokuGame.getPlayer2(), secondPlayer);
    }

    @Test
    public void testInvalidGameInstantiationWithColorPlayersEqual() {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.BLACK);
        Board board = new BoardImplementation();
        Assertions.assertThrows(Exception.class, () -> new GomokuGame(board, firstPlayer, secondPlayer));
    }

    @Test
    public void testInvalidGameInstantiationWithPlayerNamesEqual() {
        Player firstPlayer = new Player("SameName", Stone.BLACK);
        Player secondPlayer = new Player("SameName", Stone.WHITE);
        Board board = new BoardImplementation();
        Assertions.assertThrows(Exception.class, () -> new GomokuGame(board, firstPlayer, secondPlayer));
        //Assertions.assertNotEquals(player1.getUsername(), player2.getUsername());
    }


    @Test
    public void testIsFeasibleMove() {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        GomokuGame gomokuGame = new GomokuGame(board, firstPlayer, secondPlayer);
        // ho messo public isFeasibleMove() provvisoriamente per testare poi rimettiamo private se c'è esigenza
        boolean result = gomokuGame.isFeasibleMove(new Coordinates(8, 9));
        Assertions.assertTrue(result);
    }

    @Test
    public void testIsNotFeasibleMove() {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        GomokuGame gomokuGame = new GomokuGame(board, firstPlayer, secondPlayer);
        // ho messo public isFeasibleMove() provvisoriamente per testare poi rimettiamo private se c'è esigenza
        Assertions.assertFalse(gomokuGame.isFeasibleMove(new Coordinates(1, 1)));
        Assertions.assertFalse(gomokuGame.isFeasibleMove(new Coordinates(19, 19)));
    }


    // The game has begun.
    // The board is in the initial state, with the black stone in the center (9,9)
    // The second player (white) tries to make the move, in adjacent coordinates (8,9)

    @Test
    public void testMakeFirstMoveAdjacent() {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        GomokuGame gomokuGame = new GomokuGame(board, firstPlayer, secondPlayer);
        //
        try {
            gomokuGame.makeMove(secondPlayer, new Coordinates(8, 9));
        } catch (GomokuGame.InvalidMoveThrowable e) {
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
        GomokuGame gomokuGame = new GomokuGame(board, firstPlayer, secondPlayer);
        try {
            gomokuGame.makeMove(secondPlayer, new Coordinates(1, 1));
        } catch (GomokuGame.InvalidMoveThrowable e) {
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
        new GomokuGame(board, firstPlayer, secondPlayer);
        Coordinates coordinates = new Coordinates(board.getBoardDimension() / 2,
                board.getBoardDimension() / 2);
        Assertions.assertEquals(Stone.BLACK, board.getStoneAt(coordinates));
    }


    @Test
    public void testMakeConsecutiveMoves() throws GomokuGame.InvalidMoveThrowable {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        GomokuGame gomokuGame = new GomokuGame(board, firstPlayer, secondPlayer);
        // pedina non adiacente, mi aspetto che non faccia nulla
        try {
            gomokuGame.makeMove(secondPlayer, new Coordinates(1, 2));
        } catch (GomokuGame.InvalidMoveThrowable e) {
            System.err.println("Handled makeMove Exception");
        } finally {
            // suppongo che si faccia ripetere la mossa, stavolta in un punto adiacente
            gomokuGame.makeMove(secondPlayer, new Coordinates(8, 9));
            gomokuGame.makeMove(firstPlayer, new Coordinates(7, 9));
            gomokuGame.makeMove(secondPlayer, new Coordinates(7, 8));
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
        GomokuGame gomokuGame = new GomokuGame(board, firstPlayer, secondPlayer);
        try {
            gomokuGame.makeMove(secondPlayer, new Coordinates(9, 10));
            gomokuGame.makeMove(firstPlayer, new Coordinates(9, 11));
            gomokuGame.makeMove(secondPlayer, new Coordinates(9, 12));
            gomokuGame.makeMove(firstPlayer, new Coordinates(9, 13));
            gomokuGame.makeMove(secondPlayer, new Coordinates(9, 14));
            gomokuGame.makeMove(firstPlayer, new Coordinates(9, 15));
            gomokuGame.makeMove(secondPlayer, new Coordinates(9, 16));
            gomokuGame.makeMove(firstPlayer, new Coordinates(9, 17));
            gomokuGame.makeMove(secondPlayer, new Coordinates(9, 18));
            gomokuGame.makeMove(firstPlayer, new Coordinates(0, 18));
            gomokuGame.makeMove(firstPlayer, new Coordinates(9, 19));
        } catch (GomokuGame.InvalidMoveThrowable e) {
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
    public void testIsPlayerWinningGame() throws GomokuGame.InvalidMoveThrowable {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        GomokuGame gomokuGame = new GomokuGame(board, firstPlayer, secondPlayer);
        boolean[] result = new boolean[8];
        int i = 0;
        gomokuGame.makeMove(secondPlayer, new Coordinates(9, 10));
        result[i] = gomokuGame.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WITH_A_WINNER);
        i++;
        gomokuGame.makeMove(firstPlayer, new Coordinates(8, 8));
        result[i] = gomokuGame.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WITH_A_WINNER);
        i++;
        gomokuGame.makeMove(secondPlayer, new Coordinates(9, 11));
        result[i] = gomokuGame.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WITH_A_WINNER);
        i++;
        gomokuGame.makeMove(firstPlayer, new Coordinates(7, 7));
        result[i] = gomokuGame.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WITH_A_WINNER);
        i++;
        gomokuGame.makeMove(secondPlayer, new Coordinates(9, 12));
        result[i] = gomokuGame.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WITH_A_WINNER);
        i++;
        gomokuGame.makeMove(firstPlayer, new Coordinates(6, 6));
        result[i] = gomokuGame.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WITH_A_WINNER);
        i++;
        gomokuGame.makeMove(secondPlayer, new Coordinates(9, 13));
        result[i] = gomokuGame.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WITH_A_WINNER);
        i++;
        gomokuGame.makeMove(firstPlayer, new Coordinates(5, 5));
        result[i] = gomokuGame.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WITH_A_WINNER);

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
        GomokuGame gomokuGame;
        try {
            gomokuGame = new GomokuGame(board, firstPlayer, secondPlayer);
            gomokuGame.makeMove(secondPlayer, new Coordinates(9, 10));
            result.add(gomokuGame.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WITH_A_WINNER));
            gomokuGame.makeMove(firstPlayer, new Coordinates(8, 8));
            result.add(gomokuGame.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WITH_A_WINNER));
            gomokuGame.makeMove(secondPlayer, new Coordinates(9, 11));
            result.add(gomokuGame.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WITH_A_WINNER));
            gomokuGame.makeMove(firstPlayer, new Coordinates(7, 7));
            result.add(gomokuGame.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WITH_A_WINNER));
            gomokuGame.makeMove(secondPlayer, new Coordinates(9, 12));
            result.add(gomokuGame.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WITH_A_WINNER));
            gomokuGame.makeMove(firstPlayer, new Coordinates(6, 6));
            result.add(gomokuGame.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WITH_A_WINNER));
            gomokuGame.makeMove(secondPlayer, new Coordinates(9, 13));
            result.add(gomokuGame.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WITH_A_WINNER));
            gomokuGame.makeMove(firstPlayer, new Coordinates(5, 5));
            result.add(gomokuGame.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WITH_A_WINNER));
        } catch (GomokuGame.InvalidMoveThrowable e) {
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
                IllegalArgumentException.class, () -> new GomokuGame(new BoardImplementation(), player1, player2),
                "invalid player names");
    }

    @Test
    void testWrongPlayerColourAssignments() {
        Player player1 = new Player("player1", Stone.BLACK);
        Player player2 = new Player("player2", Stone.BLACK);

        Assertions.assertThrowsExactly(
                IllegalArgumentException.class, () -> new GomokuGame(new BoardImplementation(), player1, player2),
                "invalid player colours");
    }

    @Test
    void testInvalidMoves() {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        GomokuGame gomokuGame = new GomokuGame(board, firstPlayer, secondPlayer);

        Assertions.assertThrowsExactly(
                GomokuGame.InvalidMoveThrowable.class,
                () -> gomokuGame.makeMove(secondPlayer, new Coordinates(0, 0)),
                "Move not feasible"
        );

        Assertions.assertThrowsExactly(
                GomokuGame.InvalidMoveThrowable.class,
                () -> gomokuGame.makeMove(firstPlayer, new Coordinates(0, 0)),
                "Is not First's turn"
        );
    }

    // If neither player can create a 5-stone lineup after placing all of their stones,
    // the game is declared a tie, and they must start over.
    @Test
    void testTie() {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        GomokuGame gomokuGame = new GomokuGame(board, firstPlayer, secondPlayer);

        for (int i = 0; i < MAX_NUMBER_OF_STONES - 1; i++) {
            secondPlayer.addMove(new Coordinates(0, 0));
        }
        Assertions.assertFalse(gomokuGame.areTheStonesOfAPlayerFinished());
        secondPlayer.addMove(new Coordinates(0, 0));
        Assertions.assertTrue(gomokuGame.areTheStonesOfAPlayerFinished());
        Assertions.assertEquals(60, secondPlayer.getMovesList().size());
        Assertions.assertFalse(secondPlayer.getMovesList().size() > 60);
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

        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        GomokuGame gomokuGame = new GomokuGame(board, firstPlayer, secondPlayer);
//        System.out.println("Calculations 1");
//        System.out.println(game.getPlayer1().getMovesList().size());
//        System.out.println(game.getPlayer2().getMovesList().size());
        boolean isGameTie = gomokuGame.areTheStonesOfAPlayerFinished();
        Assertions.assertFalse(isGameTie);

        long numberOfBlackStones = countBlackStones((BoardImplementation) gomokuGame.getBoard());
        long numberOfWhiteStones = countWhiteStones((BoardImplementation) gomokuGame.getBoard());
        Coordinates fakeCoordinates = new Coordinates(0, 0);
        for (int i = 0; i < numberOfBlackStones - 1; i++) // -1 perché c'è la prima mossa obbligata
            gomokuGame.getPlayer1().addMove(fakeCoordinates);
        for (int i = 0; i < numberOfWhiteStones; i++)
            gomokuGame.getPlayer2().addMove(fakeCoordinates);
//        System.out.println("Calculations 2");
//        System.out.println(game.getPlayer1().getMovesList().size());
//        System.out.println(game.getPlayer2().getMovesList().size());
        isGameTie = gomokuGame.areTheStonesOfAPlayerFinished();
        Assertions.assertTrue(isGameTie);
    }

    @Test
    void testPlayerTimerExpired() throws InterruptedException {
        final int timerDuration = 1;
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        GomokuGame gomokuGame = new GomokuGame(board, firstPlayer, secondPlayer, true, true);
        gomokuGame.getTimersManager().setPlayersTimer(timerDuration);
        try {
            gomokuGame.makeMove(secondPlayer, new Coordinates(9, 10));
        } catch (BoardGame.InvalidMoveThrowable e) {
            throw new RuntimeException(e);
        }
        Thread.sleep(timerDuration * 1000 + 100);

        Assertions.assertEquals(BoardGameStatus.GAME_FINISHED_WITH_A_WINNER, gomokuGame.getGameStatus());
        Assertions.assertEquals(secondPlayer, gomokuGame.getWinner());
    }

    @Test
    void testGameStatusChange() throws InterruptedException {
        final int timerDuration = 1;
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation(DEFAULT_BOARD_SIZE);
        GomokuGame gomokuGame = new GomokuGame(board, firstPlayer, secondPlayer, true, true);
        gomokuGame.getTimersManager().setPlayersTimer(timerDuration);
        try {
            gomokuGame.makeMove(secondPlayer, new Coordinates(9, 10));
        } catch (BoardGame.InvalidMoveThrowable e) {
            throw new RuntimeException(e);
        }

        gomokuGame.addGameStatusChangedEventListener(gameStatus ->
                Assertions.assertEquals(BoardGameStatus.GAME_FINISHED_WITH_A_WINNER, gameStatus));
        Thread.sleep(timerDuration * 1000 + 100);
    }
}
