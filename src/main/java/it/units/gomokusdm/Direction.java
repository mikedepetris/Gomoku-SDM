package it.units.gomokusdm;

/**
 * Enum that represents the direction in which to move.
 * It is used to avoid working with two integers to move in a certain direction
 * Idea from this stackoverflow discussion https://codereview.stackexchange.com/questions/115278/enums-for-the-four-directions
 */
public enum Direction {
    RIGHT(0, 1),
    LEFT(0, -1),
    UP(-1, 0),
    DOWN(1, 0),
    UP_MAIN_DIAGONAL(-1, -1),
    DOWN_MAIN_DIAGONAL(1, 1),
    UP_ANTI_DIAGONAL(-1, 1),
    DOWN_ANTI_DIAGONAL(1, -1);

    private final int rowIdx;
    private final int colIdx;

    Direction(int rowIdx, int colIdx) {
        this.rowIdx = rowIdx;
        this.colIdx = colIdx;
    }

    public int getRowIdx() {
        return rowIdx;
    }

    public int getColIdx() {
        return colIdx;
    }
}
