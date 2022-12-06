package it.units.gomokusdm;

public class Board {
    private int BOARD_DIMENSION;
    private final int[][] board;

    private static final int EMPTY = 0;
    private static final int BLACK_STONE = 1;
    private static final int WHITE_STONE = 2;

    public Board() {
        BOARD_DIMENSION = 19;
        this.board = new int[BOARD_DIMENSION][BOARD_DIMENSION];
        this.board[BOARD_DIMENSION/2][BOARD_DIMENSION/2] = BLACK_STONE;
    }

    public int getBoardDimension(){
        return BOARD_DIMENSION;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getStoneAt(Coordinates coordinates){
        return board[coordinates.getRowIndex()][coordinates.getColIndex()];
    }

    public void setCell(Colour colour, Coordinates coordinates) {
        if (colour == Colour.BLACK) {
            board[coordinates.getRowIndex()][coordinates.getColIndex()] = BLACK_STONE;
        } else {
            board[coordinates.getRowIndex()][coordinates.getColIndex()] = WHITE_STONE;
        }
    }

    public boolean isEmptyCell(Coordinates coordinates) {
        return board[coordinates.getRowIndex()][coordinates.getColIndex()] == EMPTY;
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
        }
        return temp.toString();

    }

    public boolean areStonesOfSameColourAt(Coordinates current, Coordinates coordinateInDirection) {
        return getStoneAt(current) == getStoneAt(coordinateInDirection);
    }

    /**
     * (0,0) (0,1) (0,2)
     * (1,0) (1,1) (1,2)
     * (2,0) (2,1) (2,2)
     *
     *
     *
     */


}
