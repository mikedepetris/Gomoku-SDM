package it.units.gomokusdm;

public class Stone {
    private final Colour stoneColour;
    private Coordinates coordinates;

    public Stone(Coordinates coordinates, Colour stoneColour) {
        this.coordinates = coordinates;
        this.stoneColour = stoneColour;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Colour getStoneColour() {
        return stoneColour;
    }

}
