package it.units.freedomsdm;

public class Board {
    private static final int BOARD_DIMENSION = 19;
    private final int[][] board;

    public Board() {
        this.board = new int[BOARD_DIMENSION][BOARD_DIMENSION];
    }

    public int[][] getBoard() {
        return board;
    }


}
