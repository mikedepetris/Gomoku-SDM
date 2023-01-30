package it.units.gomokusdm;

public interface BoardGame {

    Board getBoard();

    Player getPlayer1();

    Player getPlayer2();

    Player getCurrentMovingPlayer();

    Player getNextMovingPlayer();

    void makeMove(Player player, Coordinates coordinates) throws InvalidMoveThrowable;

    BoardGameStatus getGameStatus();

    Player getWinner();

    class InvalidMoveThrowable extends Throwable {
        public InvalidMoveThrowable(String errorMessage) {
            super(errorMessage);
        }
    }

}
