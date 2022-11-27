package it.units.gomokusdm;

public class Board {
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

    public String toString() {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i<BOARD_DIMENSION; i++) {
            for (int j = 0; j<BOARD_DIMENSION; j++) {
                switch (board[i][j]) {
                    case EMPTY -> temp.append("|0");
                    case BLACK_STONE -> temp.append("|B");
                    case WHITE_STONE -> temp.append("|W");
                }
            }
            temp.append("\n");
            // (System.getProperty("line.separator"))

        }
        return temp.toString();

    }


    public void setCell(Colour colour, Coordinates coordinates) {

    }

    public boolean isEmptyCell(Coordinates coordinates) {
        return board[coordinates.getRowIndex()][coordinates.getColIndex()] == EMPTY;
    }
}
