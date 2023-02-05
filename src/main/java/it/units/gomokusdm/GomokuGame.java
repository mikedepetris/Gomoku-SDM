package it.units.gomokusdm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class GomokuGame implements BoardGame {

    public static final int MAX_NUMBER_OF_STONES = 60;
    public static final int WINNING_NUMBER_OF_STONES_5 = 5;
    private static final int DEFAULT_TIMER_DURATION_IN_SECONDS = 180;
    private final Board board;
    private PlayerTimersManager timersManager;
    private final boolean arePlayersTimerActive;
    private final boolean isOverlineWinner;
    private BoardGameStatus gameStatus;
    private final Player player1;
    private final Player player2;
    private Player currentMovingPlayer;
    private Player winner;
    private GameStatusChangedEventListener gameStatusChangedEventListener;

    public GomokuGame(Board board, Player player1, Player player2) {
        this(board, player1, player2, true, false); // overline is winner by default
    }

    public GomokuGame(Board board, Player player1, Player player2, boolean isOverlineWinner, boolean arePlayersTimerActive) {
        Utilities.getLoggerOfClass(getClass())
                .log(Level.INFO, "Game constructor called with player1=%s, player2=%s"
                        .formatted(player1.getUsername(), player2.getUsername()));
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        if (!checkPlayerNames(player1, player2)) {
            Utilities.getLoggerOfClass(getClass())
                    .log(Level.SEVERE, "ERROR: Game constructor called with same player names: player1=%s, player2=%s"
                            .formatted(player1.getUsername(), player2.getUsername()));
            throw new IllegalArgumentException("invalid player names");
        }
        if (!checkPlayerColours(player1, player2)) {
            Utilities.getLoggerOfClass(getClass()).log(
                    Level.SEVERE, "ERROR: Game constructor called with invalid player colors: player1=%s, player2=%s"
                            .formatted(player1.getColour().toString(), getPlayer2().getColour().toString()));
            throw new IllegalArgumentException("invalid player colors");
        }
        this.isOverlineWinner = isOverlineWinner;
        this.arePlayersTimerActive = arePlayersTimerActive;

        if(this.arePlayersTimerActive) {
            timersManager = new PlayerTimersManager(player1, player2, player -> {
                winner = (player == player1) ? player2 : player1;
                updateGameStatus();
            });
            timersManager.setPlayersTimer(DEFAULT_TIMER_DURATION_IN_SECONDS);
        }

        setupGame();
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public Player getPlayer1() {
        return player1;
    }

    @Override
    public Player getPlayer2() {
        return player2;
    }

    @Override
    public Player getCurrentMovingPlayer() {
        return currentMovingPlayer;
    }

    @Override
    public Player getNextMovingPlayer() {
        return player1 == currentMovingPlayer ? player2 : player1;
    }

    @Override
    public BoardGameStatus getGameStatus() {
        return gameStatus;
    }

    @Override
    public void addGameStatusChangedEventListener(GameStatusChangedEventListener listener) {
        this.gameStatusChangedEventListener = listener;
    }

    @Override
    public void makeMove(Player player, Coordinates coordinates) throws InvalidMoveThrowable {
        Utilities.getLoggerOfClass(getClass()).log(Level.INFO,
                "makeMove: player=%s %s".formatted(player.getUsername(), coordinates));
        if (isFeasibleMove(coordinates) && isTurnOfPlayer(player) && !isGameFinished()) {
            board.setCell(player.getColour(), coordinates);
            Utilities.getLoggerOfClass(getClass()).log(Level.INFO, BoardFormatter.formatBoardCompact(board));
            currentMovingPlayer = player;
            currentMovingPlayer.addMove(coordinates);
            updateGameStatus();
            if (arePlayersTimerActive) {
                timersManager.stopTimerCountForPlayerAndStartForTheOther(player);
            }
        } else {
            String exceptionMessage = "";
            if (!isFeasibleMove(coordinates)) {
                exceptionMessage += "Move not feasible  ";
            }
            if (!isTurnOfPlayer(player)) {
                exceptionMessage += "Is not " + player.getUsername() + "'s turn  ";
            }
            if (isGameFinished()) {
                exceptionMessage += "Game already finished  ";
            }
            throw new InvalidMoveThrowable(exceptionMessage);
        }
    }

    @Override
    public Player getWinner() {
        return winner;
    }

    private void updateGameStatus() {
        if (thereIsAWinner()) {
            changeGameStatus(BoardGameStatus.GAME_FINISHED_WITH_A_WINNER);
            winner = (winner == null) ? currentMovingPlayer : winner;
        } else if (areTheStonesOfAPlayerFinished()) {
            changeGameStatus(BoardGameStatus.GAME_FINISHED_WITH_A_DRAW);
        }
    }

    private void changeGameStatus(BoardGameStatus gameFinishedWithAWinner) {
        gameStatus = gameFinishedWithAWinner;
        if (gameStatusChangedEventListener != null) {
            gameStatusChangedEventListener.onChange(gameStatus);
        }
    }

    private boolean isGameFinished() {
        return !gameStatus.equals(BoardGameStatus.GAME_IN_PROGRESS);
    }

    private boolean thereIsAWinner() {
        if (winner != null) {
            return true;
        } else {
            if (isOverlineWinner)
                return checkIfThereAreFiveConsecutiveStones(getCurrentMovingPlayer().getColour());
            else
                return checkIfThereAreFiveConsecutiveStonesNoOverline(getCurrentMovingPlayer().getColour());
        }
    }

    private boolean isTurnOfPlayer(Player player) {
        return player != currentMovingPlayer;
    }

    private Coordinates getLastMoveCoordinates() {
        return currentMovingPlayer.getMovesList().get(currentMovingPlayer.getMovesList().size() - 1);
    }

    boolean areTheStonesOfAPlayerFinished() {
        return player1.getMovesList().size() >= MAX_NUMBER_OF_STONES
                || player2.getMovesList().size() >= MAX_NUMBER_OF_STONES;
    }

    private boolean checkIfThereAreFiveConsecutiveStones(Stone stone) {
        return checkIfThereAreFiveConsecutiveStones(stone, true);
    }

    private boolean checkIfThereAreFiveConsecutiveStonesNoOverline(Stone stone) {
        return checkIfThereAreFiveConsecutiveStones(stone, false);
    }

    private boolean checkIfThereAreFiveConsecutiveStones(Stone stone, boolean overline) {
        boolean areStonesEqual = false;
        Direction[] directions = {Direction.LEFT, Direction.UP, Direction.UP_MAIN_DIAGONAL, Direction.UP_ANTI_DIAGONAL};
        int len = 0;
        List<Coordinates> coordinatesToCheck = new ArrayList<>();
        while (len < directions.length) {
            findStonesToCheck(coordinatesToCheck, directions[len].getColIdx(), directions[len].getRowIdx());
            int maxNumberOfEqualStones = countMaxNumberOfEqualStones(coordinatesToCheck, stone);
            if (overline && maxNumberOfEqualStones >= WINNING_NUMBER_OF_STONES_5
                    || !overline && maxNumberOfEqualStones == WINNING_NUMBER_OF_STONES_5) {
                areStonesEqual = true;
                break;
            }
            len++;
            coordinatesToCheck.clear();
        }
        return areStonesEqual;
    }

    private void findStonesToCheck(List<Coordinates> coordinates, int colDirection, int rowDirection) {
        // check all possible position to support overline rule
        //int checkingStones = nStones - 1;
        int checkingStones = board.getBoardDimension() - 1;
        Coordinates insertedStoneCoordinates = getLastMoveCoordinates();
        int insertedStoneRow = insertedStoneCoordinates.getRowIndex();
        int insertedStoneCol = insertedStoneCoordinates.getColIndex();

        int i = colDirection * checkingStones;
        int j = rowDirection * checkingStones;

        while (areInRange(i, j, checkingStones)) {
            Coordinates possibleCoordinates = new Coordinates(insertedStoneRow + j, insertedStoneCol + i);
            if (board.areValidCoordinates(possibleCoordinates)) {
                coordinates.add(possibleCoordinates);
            }
            i = i - colDirection;
            j = j - rowDirection;
        }
    }

    private int countMaxNumberOfEqualStones(List<Coordinates> coordinates, Stone stone) {
        int i = 0;
        int counterStones = 0;
        int maxCounterStones = 0;
        //while (i < coordinates.size() && counterStones != nStones) {
        while (i < coordinates.size()) {
            Stone actualStone = board.getStoneAt(coordinates.get(i));
            if (Objects.equals(actualStone.toString(), stone.toString())) {
                counterStones++;
            } else {
                if (counterStones > maxCounterStones)
                    maxCounterStones = counterStones;
                counterStones = 0;
            }
            i++;
        }
        return (maxCounterStones);
    }


    // Controlla se esiste una stone adiacente intorno a quella che sto inserendo.
    // Se ne esiste almeno una, posso inserire la stone
    public boolean isThereAnAdjacentStone(Coordinates coordinates) {
        List<Coordinates> adjacentCoordinates = board.getAdjacentCoordinatesAt(coordinates);
        for (Coordinates el : adjacentCoordinates) {
            if (board.areValidCoordinates(coordinates) && !board.isEmptyCell(el)) {
                return true;
            }
        }
        return false;
    }

    public boolean isFeasibleMove(Coordinates coordinates) {
        return board.areValidCoordinates(coordinates) && board.isEmptyCell(coordinates)
                && isThereAnAdjacentStone(coordinates);
    }

    private static boolean checkPlayerNames(Player player1, Player player2) {
        return !Objects.equals(player1.getUsername(), player2.getUsername());
    }

    // ATTENZIONE: duplicato di areValidCoordinates in Board, si può generalizzare
    private static boolean areInRange(int i, int j, int checkingStones) {
        return (i <= checkingStones && i >= -checkingStones && j <= checkingStones && j >= -checkingStones);
    }

    private static boolean checkPlayerColours(Player player1, Player player2) {
        return player1.getColour() != player2.getColour();
    }

    private void setupGame() {
        gameStatus = BoardGameStatus.GAME_IN_PROGRESS;
        makeMandatoryFirstMove(player1.getColour() == Stone.BLACK ? player1 : player2);
    }

    private void makeMandatoryFirstMove(Player player) {
        Coordinates boardCenter =
                new Coordinates(board.getBoardDimension() / 2, board.getBoardDimension() / 2);
        board.setCell(player.getColour(), boardCenter);
        this.currentMovingPlayer = player;
        currentMovingPlayer.addMove(boardCenter);
        if (arePlayersTimerActive) {
            timersManager.startTimerForPlayer(player);
        }
    }

    public PlayerTimersManager getTimersManager() {
        return timersManager;
    }

}