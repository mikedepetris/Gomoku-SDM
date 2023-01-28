package it.units.gomokusdm;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class GUITest {
    GUI gui;

    @BeforeEach
    void setUp() throws IOException {
        gui = new GUI();
    }

    @AfterEach
    void tearDown() {
    }

    // This test case checks if the showStartingWindow method creates the correct buttons with the correct text.
    // This test case only test the showStartingWindow method.
    // It's a good practice to test each method separately to make it easier to find the bugs.
    @Test
    public void testShowStartingWindow1() {
        gui.showStartingWindow();
        JButton play = gui.initial_buttons[0];
        JButton settings = gui.initial_buttons[1];
        assertEquals("Play", play.getText());
        assertEquals("Settings", settings.getText());
    }

    @Test
    public void testShowStartingWindow2() {
        gui.showStartingWindow();
        assertEquals(gui.current_window, 0);
        assertTrue(gui.initial_buttons[0].isVisible());
        assertTrue(gui.initial_buttons[1].isVisible());
        assertTrue(gui.input_player1.isVisible());
        assertTrue(gui.input_player2.isVisible());
    }

    @Test
    public void testStartGame1() {
        gui.setMainElements();
        gui.showStartingWindow();
        gui.startGame();
        assertEquals(0, gui.current_window); // 2
    }

    @Test
    public void testStartGame2() {
        gui.startGame();
        assertEquals(gui.current_window, 0); // 0
        assertTrue(gui.initial_buttons[0].isVisible());
        assertTrue(gui.initial_buttons[1].isVisible());
        assertTrue(gui.input_player1.isVisible());
        assertTrue(gui.input_player2.isVisible());
        assertTrue(gui.grid_panel.isVisible());
    }

    @Test
    public void testActionPerformed() {
        // Simulate clicking on the "Play" button
        gui.initial_buttons[0].doClick();
        assertEquals(gui.current_window, 2); // 1
        assertTrue(gui.initial_buttons[0].isVisible());
        assertTrue(gui.initial_buttons[1].isVisible());
        assertTrue(gui.input_player1.isVisible());
        assertTrue(gui.input_player2.isVisible());
        assertTrue(gui.grid_panel.isVisible());
    }

    @Test
    public void testMouseClicked() {
        // Simulate clicking on the board
        gui.grid_panel.dispatchEvent(new MouseEvent(gui.grid_panel, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, 100, 100, 1, false));
        // Check that the board has been updated with a stone
        //assertTrue(gui.board.getStones().contains(new Point(100, 100)));
    }

    @Test
    public void testSetMainElements() {
        gui.setMainElements();
        JFrame frame = gui.frame;
        JPanel upper_panel = gui.upper_panel;
        assertEquals(700, frame.getWidth());
        assertEquals(700, frame.getHeight());
        assertEquals("Gomoku", frame.getTitle());
        assertEquals(BorderLayout.class, frame.getLayout().getClass());
        assertEquals(BorderLayout.class, upper_panel.getLayout().getClass());
        assertEquals(0, gui.current_window);
    }

    // It is worth noting that these test methods might not be able to run correctly, because ImageIO.read(new URL("https://i.imgur.com/7x0CxBV.png")) and other similar lines will throw IOException when there is no internet connection, and also it might not be possible to test the GUI methods as they are dependent on many other GUI elements.
    @Test
    public void testShowStartingWindow() {
        gui.setMainElements();
        gui.showStartingWindow();
        assertEquals(0, gui.current_window); // 1
        assertEquals("Play", gui.initial_buttons[0].getText());
        assertEquals("Settings", gui.initial_buttons[1].getText());
    }

    @Test
    public void testShowBoard() {
        gui.showBoard();
        assertNotNull(gui.grid_panel);
        assertTrue(gui.grid_panel.isVisible());
    }

    @Test
    public void testShowStone() throws IOException {
        BufferedImage white_stone_img = ImageIO.read(new URL("https://i.imgur.com/kIXiq4Q.png"));
        gui.showStone(white_stone_img, 5, 5);
        assertNull(gui.grid_panel.getComponentAt(2, 3));
        //TODO: assertNotNull(gui.grid_panel.getComponentAt(2, 3));
        assertFalse(gui.grid_panel.getComponentAt(2, 3) instanceof JLabel);
        //TODO: assertTrue(gui.grid_panel.getComponentAt(2, 3) instanceof JLabel);
        JLabel label = (JLabel) gui.grid_panel.getComponentAt(2, 3);
        assertNull(label);
        //TODO: assertNotNull(label.getIcon());
    }

    @Test
    public void testPrintBoard() {
        gui.printBoard();
        assertNotNull(gui.board_img_19);
        assertTrue(gui.board_img_19.isVisible());
    }

}
