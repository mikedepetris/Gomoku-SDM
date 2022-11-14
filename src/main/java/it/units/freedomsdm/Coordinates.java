package it.units.freedomsdm;

public class Coordinates {
    @Override
    public String toString() {
        return "(" + this.getRowIdx() + ", " + this.getColIdx();
    }

    private int rowIdx;
    private int colIdx;

    public Coordinates(int rowIdx, int colIdx) {
        this.rowIdx = rowIdx;
        this.colIdx = colIdx;
    }

    public int getRowIdx() {
        return rowIdx;
    }

    public void setRowIdx(int rowIdx) {
        this.rowIdx = rowIdx;
    }

    public int getColIdx() {
        return colIdx;
    }

    public void setColIdx(int colIdx) {
        this.colIdx = colIdx;
    }
}
