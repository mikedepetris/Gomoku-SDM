package it.units.freedomsdm;

public class Coordinates {
    private int rowIndex;
    private int colIndex;
    public Coordinates(int rowIndex, int colIndex) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    @Override
    public String toString() {
        return "(" + this.getRowIndex() + "," + this.getColIndex() + ")";
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }
}
