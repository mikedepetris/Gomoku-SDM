package it.units.freedomsdm;

public class Piece {
    private Coordinates coordinates;
    private final Stone stone;

    public Piece(Coordinates coordinates, Colour colour) {
        this.coordinates = coordinates;
        this.stone = new Stone(colour);
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Stone getStone() {
        return stone;
    }

    public void setStone(Colour colour) {
        this.stone.setStoneColour(colour);
    }


}
