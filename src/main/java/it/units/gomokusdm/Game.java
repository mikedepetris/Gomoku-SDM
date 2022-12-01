package it.units.gomokusdm;


import java.util.ArrayList;
import java.util.Iterator;

public class Game {

    private Board board;
    private Player player1;
    private Player player2;

    //private Coordinates lastMoveCoordinates;

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

    public void makeMove(Player player, Coordinates coordinates) {
        if (isFeasibleMove(coordinates)) {
            board.setCell(player.getColour(), coordinates);
        }
    }

    private boolean checkIfThereAreFiveConsecutiveStones() {
        return true;
    }


    // controlla se esiste una stone adiacente intorno a quella che sto inserendo. se ne esiste almeno una,
    // posso inserire la stone
    public boolean isThereAnAdjacentStone(Coordinates coordinates) {
        boolean adjacentStone = false;
        ArrayList<Coordinates> adjacent_coordinates = coordinates.getAdjacentCoordinates();
        Iterator<Coordinates> iter = adjacent_coordinates.iterator();
        while (iter.hasNext()) {
            if (!board.isEmptyCell(iter.next())) {
                adjacentStone = true;
            }
        }
        return adjacentStone;
    }

    // la cella dev'essere vuota, e intorno ce ne deve essere almeno una adiacente
    public boolean isFeasibleMove(Coordinates coordinates) {
        return board.isEmptyCell(coordinates) && isThereAnAdjacentStone(coordinates);
    }
}
