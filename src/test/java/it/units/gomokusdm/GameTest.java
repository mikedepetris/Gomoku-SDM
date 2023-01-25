package it.units.gomokusdm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GameTest {
    private int defaultBoardSize = 19;
    @Test
    public void testGameInstantiation() throws Exception {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation();
        Game game = new Game(board, firstPlayer, secondPlayer);
        Assertions.assertEquals(game.getBoard(), board);
        Assertions.assertEquals(game.getPlayer1(), firstPlayer);
        Assertions.assertEquals(game.getPlayer2(), secondPlayer);
    }

    @Test
    public void testInvalidGameInstantiation() {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.BLACK);
        Board board = new BoardImplementation();
        Assertions.assertThrows(Exception.class, () -> {
            Game game = new Game(board, firstPlayer, secondPlayer);
        });
    }

    @Test
    public void testInvalidGameInstantiationWithPlayerNamesEqual() {
        Player firstPlayer = new Player("SameName", Stone.BLACK);
        Player secondPlayer = new Player("SameName", Stone.WHITE);
        Board board = new BoardImplementation();
        Assertions.assertThrows(Exception.class, () -> {
            Game game = new Game(board, firstPlayer, secondPlayer);
        });
        //Assertions.assertNotEquals(player1.getUsername(), player2.getUsername());
    }


    @Test
    public void testIsFeasibleMove() throws Exception {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation();
        Game game = new Game(board, firstPlayer, secondPlayer);
        game.setupGame(defaultBoardSize);
        // ho messo public isFeasibleMove() provvisoriamente per testare poi rimettiamo private se c'è esigenza
        boolean result = game.isFeasibleMove(new Coordinates(8, 9));
        Assertions.assertTrue(result);

    }

    @Test
    public void testIsNotFeasibleMove() throws Exception {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation();
        Game game = new Game(board, firstPlayer, secondPlayer);
        game.setupGame(defaultBoardSize);
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
        Board board = new BoardImplementation();
        Game game = new Game(board, firstPlayer, secondPlayer);
        game.setupGame(defaultBoardSize);
        //
        try {
            game.makeMove(secondPlayer, new Coordinates(8, 9));
        } catch (Game.InvalidMoveException e) {
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
    public void testMakeFirstMoveNotAdjacent() throws Exception {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation();
        Game game = new Game(board, firstPlayer, secondPlayer);
        game.setupGame(defaultBoardSize);
        try {
            game.makeMove(secondPlayer, new Coordinates(1, 1));
        } catch (Game.InvalidMoveException e) {
            System.err.println("Handled makeMove Exception");
        } finally {
            Stone stoneColor = board.getStoneAt(new Coordinates(1, 1));
            Assertions.assertEquals(stoneColor, Stone.EMPTY);
        }
    }

    @Test
    public void testGetStoneAtAfterTheFirstMovementOfBlackPlayer() throws Exception {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation();
        Game game = new Game(board, firstPlayer, secondPlayer);
        game.setupGame(defaultBoardSize);
        Coordinates coordinates = new Coordinates(board.getBoardDimension() / 2,
                board.getBoardDimension() / 2);
        Assertions.assertEquals(Stone.BLACK, board.getStoneAt(coordinates));
    }


    @Test
    public void testMakeConsecutiveMoves() throws Game.InvalidMoveException {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation();
        Game game = new Game(board, firstPlayer, secondPlayer);
        game.setupGame(defaultBoardSize);
        // pedina non adiacente, mi aspetto che non faccia nulla
        try {
            game.makeMove(secondPlayer, new Coordinates(1, 2));
        } catch (Game.InvalidMoveException e) {
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
                    Assertions.assertEquals(board.getStoneAt(new Coordinates(i, j)), Stone.castIntToStone(expectedBoard[i][j]));
                }
            }
        }

    }

    @Test
    public void testMakeConsecutiveMovesOutsideTheBoard() throws Game.InvalidMoveException {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation();
        board.setupBoard(defaultBoardSize);
        Game game = new Game(board, firstPlayer, secondPlayer);
        game.setupGame(defaultBoardSize);
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
        } catch (Game.InvalidMoveException e) {
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
                    Assertions.assertEquals(board.getStoneAt(new Coordinates(i, j)), Stone.castIntToStone(expectedBoard[i][j]));
                }
            }
        }

    }


    @Test
    public void testIsPlayerWinningGame() throws Game.InvalidMoveException {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation();
        Game game = new Game(board, firstPlayer, secondPlayer);
        game.setupGame(defaultBoardSize);
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
        List<Boolean> result = new ArrayList<>();

        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation();
        board.setupBoard(defaultBoardSize);
        Game game = null;
        try {
            game = new Game(board, firstPlayer, secondPlayer);
            game.setupGame(defaultBoardSize);
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
        } catch (Game.InvalidMoveException e) {
            throw new RuntimeException(e);
        }

        List<Boolean> expected_result = List.of(false, false, false, false, false, false, false, true);
        Assertions.assertEquals(expected_result, result);
    }

    /* Genero una sequenza di makeMove validi alternando fra bianco e nero (senza controllare la vittoria), per riempire
     tutta la board. Si dovrebbe interrompere prima di un tot limite mosse, lanciando l'eccezione.
     */

    @Test
    public void testLimitMovesPlayer() {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation();
        Game game = null;
        try {
            game = new Game(board, firstPlayer, secondPlayer);
            game.setupGame(defaultBoardSize);
            int i = board.getBoardDimension() / 2;
            Player actualPlayer = firstPlayer;
            // traccio diagonale di mosse alternate per raggiungere la cima
            while (i > 0) {
                actualPlayer = secondPlayer;
                i--;
                game.makeMove(secondPlayer, new Coordinates(i, i));

                if (i > 0) {
                    i--;
                    game.makeMove(firstPlayer, new Coordinates(i, i));
                    actualPlayer = firstPlayer;
                }
            }
            // ora riempo la board
            int j = 0;
            i = 0;
            while (i < board.getBoardDimension()) {
                while (j < board.getBoardDimension()) {
                    if (board.isEmptyCell(new Coordinates(i, j))) {
                        if (actualPlayer.getColour() == Stone.BLACK) {
                            actualPlayer = secondPlayer;
                        } else {
                            actualPlayer = firstPlayer;
                        }
                        game.makeMove(actualPlayer, new Coordinates(i, j));
                    }
                    j++;
                }
                j = 0;
                i++;
            }
        } catch (Game.InvalidMoveException e) {
            // Mi aspetto che lanci un'eccezione quando ho raggiunto il limite di 60 per ogni giocatore
            // Quindi dovrei avere esattamente 120 mosse fatte, e il player ha tentato di oltrepassare il limite
            int movesLimit = 60 * 2;
            int totalMovesCounter = game.getPlayer1().getMovesList().size() + game.getPlayer2().getMovesList().size();
            Assertions.assertEquals(movesLimit, totalMovesCounter);
        }

    }

    @Test
    void testWrongPlayerUsernamesAssignments() {
        Player player1 = new Player("player", Stone.BLACK);
        Player player2 = new Player("player", Stone.WHITE);

        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new Game(new BoardImplementation(), player1, player2),
                "invalid player names");
    }

    @Test
    void testWrongPlayerColourAssignments() {
        Player player1 = new Player("player1", Stone.BLACK);
        Player player2 = new Player("player2", Stone.BLACK);

        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new Game(new BoardImplementation(), player1, player2),
                "invalid player colours");
    }

    @Test
    void testInvalidMoves() {
        Player firstPlayer = new Player("First", Stone.BLACK);
        Player secondPlayer = new Player("Second", Stone.WHITE);
        Board board = new BoardImplementation();
        board.setupBoard(defaultBoardSize);
        Game game = new Game(board, firstPlayer, secondPlayer);
        game.setupGame(defaultBoardSize);


        Assertions.assertThrowsExactly(
                Game.InvalidMoveException.class,
                () -> game.makeMove(secondPlayer, new Coordinates(0,0)),
                "Move not feasible"
        );

        Assertions.assertThrowsExactly(
                Game.InvalidMoveException.class,
                () -> game.makeMove(firstPlayer, new Coordinates(0,0)),
                "Is not First's turn"
        );

        for (int i = 0; i < 60; i++) {
            secondPlayer.getMovesList().add(new Coordinates(0, 0));
        }
        Assertions.assertThrowsExactly(
                Game.InvalidMoveException.class,
                () -> game.makeMove(secondPlayer, new Coordinates(9,10)),
                "Second has terminated the number of moves allowed"
        );
    }

}
