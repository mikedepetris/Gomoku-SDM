package it.units.gomokusdm;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

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

    // Implemento metodo super basico che verifica se ci sono 5 stones dello stesso colore,
    // basandosi su i,j per le direzioni
    public boolean countFiveStones (int i, int j, int colIndex, int rowIndex, int winning_colour) {
        int i_increment = i/4 * (-1);
        int j_increment = j/4 * (-1);
        int counterStones = 0;
        while (i <= 4 && j <= 4) {
            if ( (rowIndex + j >= 0 && rowIndex + j < 19 ) && (colIndex + i >= 0 && colIndex + i < 19 ) ) {
                int stoneColourNumber = board.getStoneAt(new Coordinates(rowIndex+j, colIndex+i));
                if (stoneColourNumber == winning_colour) {
                    counterStones++;
                } else {
                    if (counterStones != 5) {
                        counterStones = 0;
                    } else {
                        i = 4; j = 4; // stop while, there are already five consecutive stones
                    }
                }
            }
            i=i+i_increment;
            j=j+j_increment;
        }
        return (counterStones == 5) ;
    }

    public boolean checkIfThereAreFiveConsecutiveStones(Coordinates coordinates, Colour colour) {
        boolean winning = false;
        int rowIndex = coordinates.getRowIndex();
        int colIndex = coordinates.getColIndex();
        int winning_colour = 1;
        if (colour == Colour.WHITE) {
            winning_colour = 2;
        }
        // controlla 4 direzioni
        // N.B sto richiamando 4 while
        return (countFiveStones(-4, 0, colIndex, rowIndex, winning_colour)) ||
                (countFiveStones(0, -4, colIndex, rowIndex, winning_colour)) ||
                (countFiveStones(-4, -4, colIndex, rowIndex, winning_colour)) ||
                (countFiveStones(4, -4, colIndex, rowIndex, winning_colour));

    }


    // controlla se esiste una stone adiacente intorno a quella che sto inserendo. se ne esiste almeno una,
    // posso inserire la stone
    public boolean isThereAnAdjacentStone(Coordinates coordinates) {
        boolean adjacentStone = false;
        ArrayList<Coordinates> adjacent_coordinates = coordinates.getAdjacentCoordinates();
        Iterator<Coordinates> iter = adjacent_coordinates.iterator();
        while (iter.hasNext()) {
            if (!board.isEmptyCell(iter.next())) {
                adjacentStone = true;
            }
        }
        return adjacentStone;
    }

    // la cella dev'essere vuota, e intorno ce ne deve essere almeno una adiacente
    public boolean isFeasibleMove(Coordinates coordinates) {
        return board.isEmptyCell(coordinates) && isThereAnAdjacentStone(coordinates);
    }
}
