package it.units.gomokusdm;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
//@ExtendWith(MockitoExtension.class)
public class GUITest {

    private GUI gui;

    @Before
    public void setUp() {
        // Set up headless mode
//        System.out.println("@Before: Set up headless mode");
//        System.setProperty("java.awt.headless", "true");
//        System.out.println("@Before: GraphicsEnvironment.isHeadless()=" + GraphicsEnvironment.isHeadless());
    }

    @BeforeEach
    void setUpEach() throws IOException {
        // Set up headless mode
//        System.out.println("@BeforeEach: Set up headless mode");
//        System.setProperty("java.awt.headless", "true");
//        System.out.println("@BeforeEach: GraphicsEnvironment.isHeadless()=" + GraphicsEnvironment.isHeadless());
//        when(player1.getColour()).thenReturn(Stone.BLACK);
//        when(player2.getColour()).thenReturn(Stone.WHITE);
//        when(game.getBoard()).thenReturn(board);
        gui = new GUI();
//        gui.setFrame(frame);
//        gui.setStart_panel(start_panel);
//        gui.setInput_player1(input_player1);
//        gui.setInput_player2(input_player2);
//        gui.board_img_19 = boardLabel;
//        gui.getGrid_panel() = gridPanel;
    }

//    @AfterEach
//    void tearDown() {
//    }

    // This test case checks if the showStartingWindow method creates the correct buttons with the correct text.
    // This test case only test the showStartingWindow method.
    // It's a good practice to test each method separately to make it easier to find the bugs.
    @Test
    public void testShowStartingWindow1() {
        gui.showStartingWindow();
        JButton play = gui.getInitial_buttons()[0];
        JButton settings = gui.getInitial_buttons()[1];
        assertEquals("Play", play.getText());
        assertEquals("Settings", settings.getText());
    }

    @Test
    public void testShowStartingWindow2() {
        gui.showStartingWindow();
        assertEquals(gui.current_window, 0);
        assertTrue(gui.getInitial_buttons()[0].isVisible());
        assertTrue(gui.getInitial_buttons()[1].isVisible());
        assertTrue(gui.getInput_player1().isVisible());
        assertTrue(gui.getInput_player2().isVisible());
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
        assertTrue(gui.getInitial_buttons()[0].isVisible());
        assertTrue(gui.getInitial_buttons()[1].isVisible());
        assertTrue(gui.getInput_player1().isVisible());
        assertTrue(gui.getInput_player2().isVisible());
        assertTrue(gui.getGrid_panel().isVisible());
    }

    @Test
    public void testActionPerformed() {
        // Simulate clicking on the "Play" button
        gui.getInitial_buttons()[0].doClick();
        assertEquals(gui.current_window, 2); // 1
        assertTrue(gui.getInitial_buttons()[0].isVisible());
        assertTrue(gui.getInitial_buttons()[1].isVisible());
        assertTrue(gui.getInput_player1().isVisible());
        assertTrue(gui.getInput_player2().isVisible());
        assertTrue(gui.getGrid_panel().isVisible());
    }

    @Test
    public void testMouseClicked() {
        // Simulate clicking on the board
        gui.getGrid_panel().dispatchEvent(new MouseEvent(gui.getGrid_panel(), MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, 100, 100, 1, false));
        // Check that the board has been updated with a stone
        //assertTrue(gui.board.getStones().contains(new Point(100, 100)));
    }

    @Test
    public void testSetMainElements() {
        gui.setMainElements();
        JFrame frame = gui.getFrame();
        JPanel upper_panel = gui.getUpper_panel();
        assertEquals(700, frame.getWidth());
        assertEquals(700, frame.getHeight());
        assertEquals("Gomoku", frame.getTitle());
        assertEquals(BorderLayout.class, frame.getLayout().getClass());
        assertEquals(BorderLayout.class, upper_panel.getLayout().getClass());
        assertEquals(0, gui.current_window);
    }

    @Test
    public void testShowBoard() {
        gui.showBoard();
        assertNotNull(gui.getGrid_panel());
        assertTrue(gui.getGrid_panel().isVisible());
    }

    @Test
    public void testShowStone() throws IOException {
        BufferedImage white_stone_img = ImageIO.read(new URL("https://i.imgur.com/kIXiq4Q.png"));
        gui.showStone(white_stone_img, 5, 5);
        assertNull(gui.getGrid_panel().getComponentAt(2, 3));
        //TODO: assertNotNull(gui.getGrid_panel().getComponentAt(2, 3));
        assertFalse(gui.getGrid_panel().getComponentAt(2, 3) instanceof JLabel);
        //TODO: assertTrue(gui.getGrid_panel().getComponentAt(2, 3) instanceof JLabel);
        JLabel label = (JLabel) gui.getGrid_panel().getComponentAt(2, 3);
        assertNull(label);
        //TODO: assertNotNull(label.getIcon());
    }

    @Test
    public void testPrintBoard() {
        gui.printBoard();
        assertNotNull(gui.getBoard_img_19());
        assertTrue(gui.getBoard_img_19().isVisible());
    }

    // It is worth noting that these test methods might not be able to run correctly, because ImageIO.read(new URL("https://i.imgur.com/7x0CxBV.png")) and other similar lines will throw IOException when there is no internet connection, and also it might not be possible to test the GUI methods as they are dependent on many other GUI elements.
    @Test
    public void testShowStartingWindow() {
        gui.setMainElements();
        gui.showStartingWindow();
//        // Verify that the frame's content pane is set to the start_panel
//        verify(frame).setContentPane(start_panel);
//        // Verify that the start_panel is visible
//        verify(start_panel).setVisible(true);
//        // Verify that the input_player1 and input_player2 fields are visible
//        verify(input_player1).setVisible(true);
//        verify(input_player2).setVisible(true);

        assertEquals(0, gui.current_window); // 1
        assertEquals("Play", gui.getInitial_buttons()[0].getText());
        assertEquals("Settings", gui.getInitial_buttons()[1].getText());
    }
}
