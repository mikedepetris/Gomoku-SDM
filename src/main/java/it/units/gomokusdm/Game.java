package it.units.gomokusdm;


import java.util.ArrayList;
import java.util.Iterator;

public class Game {

    private Board board;
    private Player player1;
    private Player player2;
    private Coordinates lastMoveCoordinates;

    public Game(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Coordinates getLastMoveCoordinates() {
        return lastMoveCoordinates;
    }

    public void makeMove(Player player, Coordinates coordinates) {
        if (isFeasibleMove(coordinates)) {
            board.setCell(player.getColour(), coordinates);
        }
        lastMoveCoordinates = new Coordinates(coordinates.getRowIndex(), coordinates.getColIndex());
    }
    
    // Implemento metodo super basico che verifica se ci sono nStones dello stesso colore,
    // basandosi su i,j per le direzioni
    // metodo poco leggibile anche se funziona, aperto al refactoring
    public boolean countStones (int colDirection, int rowDirection, int winningColour, int nStones) {
        int rowIndex = lastMoveCoordinates.getRowIndex(); int colIndex = lastMoveCoordinates.getColIndex();
        int i = colDirection*(nStones-1); int j = rowDirection*(nStones-1);
        int i_increment = i/(nStones-1); int j_increment = j/(nStones-1);
        int counterStones = 0;
        if (nStones <= board.getBoardDimension() && nStones > 0) {
            while (i <= (nStones - 1) && i >= -(nStones - 1) && j <= (nStones - 1) && j >= -(nStones - 1)) {
                if ((rowIndex + j >= 0 && rowIndex + j < board.getBoardDimension()) &&
                        (colIndex + i >= 0 && colIndex + i < board.getBoardDimension())) {
                    int stoneColourNumber = board.getStoneAt(new Coordinates(rowIndex + j, colIndex + i));
                    if (stoneColourNumber == winningColour) {
                        counterStones++;
                    } else {
                        if (counterStones != nStones) {
                            counterStones = 0;
                        } else {
                            i = (nStones - 1); j = (nStones - 1); // stop while, there are already five consecutive stones
                        }
                    }
                }
                i = i - i_increment;
                j = j - j_increment;
            }
        }
        return (counterStones == nStones) ;
    }

    public boolean checkIfThereAreFiveConsecutiveStones(Colour colour) {
        boolean areThereFiveStones = false;
        int winningColour = 1;
        if (colour == Colour.WHITE) {
            winningColour = 2;
        }
        int[] directions = {-1,0,0,-1,-1,-1,1,-1}; // -1 -> sx - +1 -> dx
        int len = 0;
        while (len < directions.length) {
            areThereFiveStones = countStones(directions[len], directions[len+1], winningColour,5);
            if (areThereFiveStones) {
                len = directions.length; // stop while if I've already found 5 stones in a direction
            }
            len=len+2;
        }
        return (areThereFiveStones);

    }


    // controlla se esiste una stone adiacente intorno a quella che sto inserendo. se ne esiste almeno una,
    // posso inserire la stone
    public boolean isThereAnAdjacentStone(Coordinates coordinates) {
        boolean adjacentStone = false;
        ArrayList<Coordinates> adjacent_coordinates = coordinates.getAdjacentCoordinates();
        Iterator<Coordinates> iter = adjacent_coordinates.iterator();
        while (iter.hasNext()) {
            Coordinates el = iter.next();
            if (el.getRowIndex() >= 0 && el.getRowIndex() < board.getBoardDimension() &&
                    el.getColIndex() >= 0 && el.getColIndex() < board.getBoardDimension()) {
                if (!board.isEmptyCell(el)) {
                    adjacentStone = true;
                }
            }
        }
        return adjacentStone;
    }

    // la cella dev'essere vuota, e intorno ce ne deve essere almeno una adiacente
    public boolean isFeasibleMove(Coordinates coordinates) {
        return board.isEmptyCell(coordinates) && isThereAnAdjacentStone(coordinates);
    }
}
