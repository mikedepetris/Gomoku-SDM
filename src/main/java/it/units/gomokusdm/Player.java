package it.units.gomokusdm;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String username;
    private Colour colour;
    private final List<Stone> movesList = new ArrayList<>();

    public Player(String username, Colour colour) {
        this.username = username;
        this.colour = colour;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public List<Stone> getMovesList() {
        return movesList;
    }

    public void addMove(Coordinates coordinates){
        this.movesList.add(new Stone(coordinates, this.colour));
    }

}
