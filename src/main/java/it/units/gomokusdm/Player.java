package it.units.gomokusdm;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String username;
    private Stone assignedStone;
    private final List<Coordinates> movesList = new ArrayList<>();

    public Player(String username, Stone assignedStone) {
        this.username = username.equals("") ? assignedStone.toString() : username;
        this.assignedStone = assignedStone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Stone getColour() {
        return assignedStone;
    }

    public void setColour(Stone stone) {
        this.assignedStone = stone;
    }

    public List<Coordinates> getMovesList() {
        return movesList;
    }

    public void addMove(Coordinates coordinates) {
        this.movesList.add(coordinates);
    }

}
