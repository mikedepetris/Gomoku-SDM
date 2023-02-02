package it.units.gomokusdm;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
//@ExtendWith(MockitoExtension.class)
class GUITest {

    private GUI gui;

    @Before
    void setUp() {
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
    void testShowStartingWindow1() {
        gui.showStartingWindow();
        JButton play = gui.getButtons()[0];
        JButton settings = gui.getButtons()[1];
        assertEquals("Play", play.getText());
        assertEquals("Settings", settings.getText());
    }

    @Test
    void testShowStartingWindow2() {
        gui.showStartingWindow();
        assertEquals(gui.currentWindow, 0);
        assertTrue(gui.getButtons()[0].isVisible());
        assertTrue(gui.getButtons()[1].isVisible());
        assertTrue(gui.getInputPlayer1().isVisible());
        assertTrue(gui.getInputPlayer2().isVisible());
    }

    @Test
    void testStartGame1() {
        gui.setMainElements();
        gui.showStartingWindow();
        gui.startFrame();
        assertEquals(0, gui.currentWindow); // 2
    }

    @Test
    void testStartGame2() {
        gui.startFrame();
        assertEquals(gui.currentWindow, 0); // 0
        assertTrue(gui.getButtons()[0].isVisible());
        assertTrue(gui.getButtons()[1].isVisible());
        assertTrue(gui.getInputPlayer1().isVisible());
        assertTrue(gui.getInputPlayer2().isVisible());
        assertTrue(gui.getGridPanel().isVisible());
    }

    @Test
    void testGameStart() {
        // Simulate clicking on the "Play" button
        gui.getButtons()[0].doClick();
        assertEquals(gui.currentWindow, 2); // 1
        assertTrue(gui.getButtons()[0].isVisible());
        assertTrue(gui.getButtons()[1].isVisible());
        assertTrue(gui.getInputPlayer1().isVisible());
        assertTrue(gui.getInputPlayer2().isVisible());
        assertTrue(gui.getGridPanel().isVisible());
    }

    @Test
    void testGameStartWithSameNamePlayers() {
        gui.getInputPlayer1().setText("Same name");
        gui.getInputPlayer2().setText("Same name");
        // Simulate clicking on the "Play" button
        // and check it throws exception
        assertThrowsExactly(RuntimeException.class, () ->
                        gui.getButtons()[0].doClick(),
                "invalid player names");
        assertEquals(gui.currentWindow, 0);
        assertTrue(gui.getButtons()[0].isVisible());
        assertTrue(gui.getButtons()[1].isVisible());
        assertTrue(gui.getInputPlayer1().isVisible());
        assertTrue(gui.getInputPlayer2().isVisible());
        assertTrue(gui.getGridPanel().isVisible());
    }

    @Test
    void testMouseClicked() {
        // Simulate clicking on the board
        gui.getGridPanel().dispatchEvent(new MouseEvent(gui.getGridPanel(), MouseEvent.MOUSE_CLICKED
                , System.currentTimeMillis(), 0, 100, 100, 1, false));
        // Check that the board has been updated with a stone
        //assertTrue(gui.board.getStones().contains(new Point(100, 100)));
    }

    @Test
    void testSetMainElements() {
        gui.setMainElements();
        JFrame frame = gui.getFrame();
        JPanel upper_panel = gui.getUpperPanel();
        assertEquals(700, frame.getWidth());
        assertEquals(700, frame.getHeight());
        assertEquals("Gomoku", frame.getTitle());
        assertEquals(BorderLayout.class, frame.getLayout().getClass());
        assertEquals(BorderLayout.class, upper_panel.getLayout().getClass());
        assertEquals(0, gui.currentWindow);
    }

    @Test
    void testShowBoard() {
        gui.showBoard();
        assertNotNull(gui.getGridPanel());
        assertTrue(gui.getGridPanel().isVisible());
    }


    // It is worth noting that these test methods might not be able to run correctly,
    // because ImageIO.read(new URL("https://i.imgur.com/7x0CxBV.png")) and other similar lines
    // will throw IOException when there is no internet connection,
    // and also it might not be possible to test the GUI methods as they are dependent on many other GUI elements.
    @Test
    void testShowStartingWindow() {
        gui.setMainElements();
        gui.showStartingWindow();
        assertEquals(0, gui.currentWindow);
        assertEquals("Play", gui.getButtons()[0].getText());
        assertEquals("Settings", gui.getButtons()[1].getText());
    }

    @Test
    void testShowSettings() {
        gui.setMainElements();
        gui.showStartingWindow();
        gui.startFrame();
        gui.showSettings();
        assertEquals(1, gui.currentWindow);
        assertTrue(gui.getSettingsPanel().isVisible());
        assertTrue(gui.getComboDimensions().isVisible());
        assertEquals("19x19", gui.getComboDimensions().getSelectedItem());
        assertTrue(gui.getCheckBoxMoveCounter().isVisible());
        assertFalse(gui.getCheckBoxMoveCounter().isSelected());
        assertTrue(gui.getCheckBoxIsOverlineWinner().isVisible());
        assertTrue(gui.getCheckBoxIsOverlineWinner().isSelected());
        assertEquals("Show move counter on stones", gui.getCheckBoxMoveCounter().getText());
    }
}
