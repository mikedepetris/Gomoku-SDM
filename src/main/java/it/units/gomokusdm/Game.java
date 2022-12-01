package it.units.gomokusdm;


public class Game {

    private Board board;
    private Player player1;
    private Player player2;
    private Coordinates lastMoveCoordinates;

    public Game(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public boolean makeMove(Player player, Coordinates coordinates) {
        if (isFeasibleMove(coordinates)) {
            board.setCell(player.getColour(), coordinates);
            lastMoveCoordinates = coordinates;
            return true;
        }
        return false;
    }

    private boolean checkIfThereAreFiveConsecutiveStones() {
        return false;
    }

    private boolean isFeasibleMove(Coordinates coordinates) {
        return board.isEmptyCell(coordinates) && lastMoveCoordinates.getAdjacentCoordinates().contains(coordinates);
    }
}
