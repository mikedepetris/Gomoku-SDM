package it.units.gomokusdm;

import java.util.ArrayList;


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

    // siamo sicuri di voler mettere questo metodo qui? per trovare le coordinate adiacenti ci serve la dimensione
    // della board per soddisfare la condizione che le coordinate siano < 19 (in questo caso)
    // forse meglio board.getAdjacentCoordinates(coordinata/cella) così abbiamo già la dimensione della board?
    public ArrayList<Coordinates> getAdjacentCoordinates() {
        ArrayList<Coordinates> adjacentCoordinates = new ArrayList<>();
        for (int row = -1; row <= 1; row++) {
            for (int col = -1; col <= 1; col++) {
                int rowCoordinate = rowIndex + row;
                int columnCoordinate = colIndex + col;
                if ((rowCoordinate >= 0 && rowCoordinate < 19) && (columnCoordinate >= 0 && columnCoordinate < 19))
                    adjacentCoordinates.add(new Coordinates(rowCoordinate, columnCoordinate));
            }
        }
        return adjacentCoordinates;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordinates otherCoordinates) {
            return otherCoordinates.rowIndex == this.rowIndex && otherCoordinates.colIndex == this.colIndex;
        }
        return false;
    }

}
