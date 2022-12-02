package it.units.gomokusdm;

import java.util.Objects;

public class Board {
    private static final int BOARD_DIMENSION = 19;
    private final int[][] board;

    private static final int EMPTY = 0;
    private static final int BLACK_STONE = 1;
    private static final int WHITE_STONE = 2;

    public Board() {
        this.board = new int[BOARD_DIMENSION][BOARD_DIMENSION];
        this.board[9][9] = BLACK_STONE;
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
            temp.append("|");
            temp.append("\n");
            // (System.getProperty("line.separator"))

        }
        return temp.toString();

    }


    public void setCell(Colour colour, Coordinates coordinates) {
        int row = coordinates.getRowIndex();
        int col = coordinates.getColIndex();
        if (Objects.equals(colour, "BLACK")) {
            board[row][col] = BLACK_STONE;
        } else {
            board[row][col] = WHITE_STONE;
        }
    }

    public boolean isEmptyCell(Coordinates coordinates) {
        return board[coordinates.getRowIndex()][coordinates.getColIndex()] == EMPTY;
    }

    public int getStoneAt(Coordinates coordinates){
        return board[coordinates.getRowIndex()][coordinates.getColIndex()];
    }

    public int getBoardDimension(){
        return BOARD_DIMENSION;
    }
}
