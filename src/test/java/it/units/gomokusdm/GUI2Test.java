package it.units.gomokusdm;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import javax.swing.*;
import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class GUI2Test {

    @Mock
    private JLabel boardLabel;
    @Mock
    private JPanel gridPanel;
    @Mock
    private Game game;
    @Mock
    private Board board;
    @Mock
    private Player player1;
    @Mock
    private Player player2;

    private GUI gui;

    @Before
    public void setUp() throws IOException {
        // Set up headless mode
        System.setProperty("java.awt.headless", "true");
        gui = new GUI();
        gui.startGame();
    }

//    @BeforeEach
//    public void setUpEach() throws IOException {
////        System.setProperty("java.awt.headless", "true");
////        gui = new GUI();
////        gui.startGame();
//        //MockitoAnnotations.openMocks(this);
////        when(player1.getColour()).thenReturn(Stone.BLACK);
////        when(player2.getColour()).thenReturn(Stone.WHITE);
////        when(game.getBoard()).thenReturn(board);
////        gui.board_img_19 = boardLabel;
////        gui.grid_panel = gridPanel;
//
//        //TODO
////        gui.game = game;
////        gui.player1 = player1;
////        gui.player2 = player2;
//    }

    @Test
    public void testShowBoard() {
        gui.showBoard();
//        verify(boardLabel).setIcon(any());
//        verify(gridPanel).setBackground(Color.getColor("234, 214, 84"));
    }

//    @Test
//    public void testShowStone() throws Exception {
//        Point point = new Point(1, 1);
//        gui.showStone(point, Stone.BLACK);
//        verify(board).putStone(eq(point), eq(Stone.BLACK));
//        verify(gridPanel).repaint();
//    }

    @Test
    public void testPrintBoard() {
        gui.printBoard();
//        verify(game).getBoard();
//        verify(game).printBoard();
    }
}
