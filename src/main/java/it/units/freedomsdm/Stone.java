package it.units.freedomsdm;

public class Stone {
    private Colour stoneColour;

    public Stone(Colour stoneColour) {
        this.stoneColour = stoneColour;
    }

    public Colour getStoneColour() {
        return stoneColour;
    }

    public void setStoneColour(Colour stoneColour) {
        this.stoneColour = stoneColour;
    }
}
