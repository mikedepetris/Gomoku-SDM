package it.units.gomokusdm;

public class Board {
    // commento test
    private static final int BOARD_DIMENSION = 19;
    private final int[][] board;

    private static final int EMPTY = 0;
    private static final int BLACK_STONE = 1;
    private static final int WHITE_STONE = 2;

    public Board() {
        this.board = new int[BOARD_DIMENSION][BOARD_DIMENSION];
    }

    public int[][] getBoard() {
        return board;
    }

    public void printBoard() {
        for (int i = 0; i<BOARD_DIMENSION; i++) {
            for (int j = 0; j<BOARD_DIMENSION; j++) {
                switch (board[i][j]) {
                    case EMPTY -> System.out.print("|0");
                    case BLACK_STONE -> System.out.print("|B");
                    case WHITE_STONE -> System.out.print("|W");
                }
            }
        }

    }


}
