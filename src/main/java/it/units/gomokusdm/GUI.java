package it.units.gomokusdm;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;


public class GUI implements ActionListener, MouseListener {

    public JPanel getSettingsPanel() {
        return settingsPanel;
    }

    public static final String GAME_TITLE = "Gomoku";
    private final JPanel settingsPanel = new JPanel();
    private boolean settingsPanelAlreadyVisited = false;
    private final JComboBox<String> comboDimensions;
    int currentWindow; // finestra in cui mi trovo nel gioco
    int selectedBoardSize;
    private boolean isGameFinished = false;
    private Board board;
    //private Player winner; //come in CLI Controller
    private BoardGame game;
    private Player player1;
    private Player player2;
    private JFrame frame = new JFrame(); // frame totale
    private JPanel upperPanel = new JPanel(); // pannello soprastante con il titolo
    private JLabel title = new JLabel();
    private JPanel startPanel = new JPanel();
    private JTextField inputPlayer1 = new JTextField("Player 1");
    private JTextField inputPlayer2 = new JTextField("Player 2");
    private JPanel gridPanel = new JPanel();
    private final boolean gridPanelAlreadyVisited = false;
    private JButton[] buttons = new JButton[4];
    private JLabel boardImg19 = new JLabel(new ImageIcon(ImageIO.read(new URL("https://i.imgur.com/hq1JiiM.png"))));
    private JLabel boardImg15 = new JLabel(new ImageIcon(ImageIO.read(new URL("https://i.imgur.com/1S2qfYu.png"))));
    private BufferedImage blackStoneImg = ImageIO.read(new URL("https://i.imgur.com/cDfy5SP.png"));
    private BufferedImage whiteStoneImg = ImageIO.read(new URL("https://i.imgur.com/kIXiq4Q.png"));

    public GUI() throws IOException {

        this.selectedBoardSize = 19; //default
        String[] dimensions = {"15x15", "19x19"};
        comboDimensions = new JComboBox<>(dimensions);

        setMainElements();

        showStartingWindow();

        startFrame();
    }

    public int getCurrentWindow() {
        return currentWindow;
    }

    public void setCurrentWindow(int currentWindow) {
        this.currentWindow = currentWindow;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public BoardGame getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JPanel getUpperPanel() {
        return upperPanel;
    }

    public void setUpperPanel(JPanel upperPanel) {
        this.upperPanel = upperPanel;
    }

    public JLabel getTitle() {
        return title;
    }

    public void setTitle(JLabel title) {
        this.title = title;
    }

    public JPanel getStartPanel() {
        return startPanel;
    }

    public void setStartPanel(JPanel startPanel) {
        this.startPanel = startPanel;
    }

    public JTextField getInputPlayer1() {
        return inputPlayer1;
    }

    public void setInputPlayer1(JTextField inputPlayer1) {
        this.inputPlayer1 = inputPlayer1;
    }

    public JTextField getInputPlayer2() {
        return inputPlayer2;
    }

    public void setInputPlayer2(JTextField inputPlayer2) {
        this.inputPlayer2 = inputPlayer2;
    }

    public JPanel getGridPanel() {
        return gridPanel;
    }

    public void setGridPanel(JPanel gridPanel) {
        this.gridPanel = gridPanel;
    }

    public JButton[] getButtons() {
        return buttons;
    }

    public void setButtons(JButton[] buttons) {
        this.buttons = buttons;
    }

    public JLabel getBoardImg19() {
        return boardImg19;
    }

    public void setBoardImg19(JLabel boardImg19) {
        this.boardImg19 = boardImg19;
    }

    public JLabel getBoardImg15() {
        return boardImg15;
    }

    public void setBoardImg15(JLabel boardImg15) {
        this.boardImg15 = boardImg15;
    }

    public BufferedImage getBlackStoneImg() {
        return blackStoneImg;
    }

    public void setBlackStoneImg(BufferedImage blackStoneImg) {
        this.blackStoneImg = blackStoneImg;
    }

    public BufferedImage getWhiteStoneImg() {
        return whiteStoneImg;
    }

    public void setWhiteStoneImg(BufferedImage whiteStoneImg) {
        this.whiteStoneImg = whiteStoneImg;
    }

    public void setMainElements() {
        // Settings of Main elements: frame, title, panels

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700); // Size della finestra
        frame.getContentPane().setBackground(new Color(50, 50, 50)); // Colore della finestra
        frame.setTitle(GAME_TITLE); // Titolo finestra
        frame.setLayout(new BorderLayout()); // Layout Frame
        frame.setVisible(true);

        upperPanel.setLayout(new BorderLayout());
        upperPanel.setBounds(0, 0, 800, 100);
        upperPanel.add(title);

        startPanel.setLayout(null);
        startPanel.setBackground(new Color(234, 214, 84));

        gridPanel.setBackground(new Color(234, 214, 84));

        settingsPanel.setLayout(null);
        settingsPanel.setBackground(new Color(234, 214, 84));

        frame.add(upperPanel, BorderLayout.NORTH);

        title.setBackground(new Color(234, 214, 84)); // Colore testo
        title.setForeground(new Color(3, 3, 3)); // Colore testo
        title.setFont(new Font("Ink Free", Font.BOLD, 30)); // Font testo
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setText(GAME_TITLE);
        title.setOpaque(true);
    }

    public void showStartingWindow() {
        this.currentWindow = 0;
        title.setText(GAME_TITLE);
        frame.add(startPanel);
        int xStartingComponents = 250; //allineare nella x gli elementi
        // è un allineamento provvisorio perchè non va bene, bisogna decidere un layout!

        // Bottone Play
        JButton play = new JButton("Play");
        buttons[0] = play;
        play.setBounds(xStartingComponents, 150, 200, 20);
        play.addActionListener(this);

        // Bottone Settings
        JButton settings = new JButton("Settings");
        buttons[1] = settings;
        settings.setBounds(xStartingComponents, 200, 200, 20);
        settings.addActionListener(this);

        // Labels/Text Field per inserimento nome Giocatore 1/2
        JLabel insertPlayer1 = new JLabel("Inserisci il nome del Giocatore 1:");
        JLabel insertPlayer2 = new JLabel("Inserisci il nome del Giocatore 2:");

        // Allineo
        insertPlayer1.setBounds(xStartingComponents, 260, 200, 20);
        inputPlayer1.setBounds(xStartingComponents, 290, 200, 20);
        insertPlayer2.setBounds(xStartingComponents, 320, 200, 20);
        inputPlayer2.setBounds(xStartingComponents, 350, 200, 20);

        // Aggiungo elementi al pannello iniziale
        startPanel.add(play);
        startPanel.add(settings);
        startPanel.add(insertPlayer1);
        startPanel.add(insertPlayer2);
        startPanel.add(inputPlayer1);
        startPanel.add(inputPlayer2);
    }

    public void startFrame() {
        try {
            title.setText("Loading....");
            Thread.sleep(1000);
            title.setText(GAME_TITLE);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        // in attesa di input dal giocatore
    }

    public void showSettings() {
        // Settings
        this.currentWindow = 1;
        frame.add(settingsPanel);

        if (!settingsPanelAlreadyVisited) {
            JLabel insertSize = new JLabel("Choose the dimension of the Board:");
            settingsPanel.add(insertSize);
            insertSize.setBounds(250, 120, 300, 20);

            // Bottone Back to Main Menu
            JButton backToMainMenuStart = new JButton("Back to Main Menu");
            settingsPanel.add(backToMainMenuStart);
            buttons[2] = backToMainMenuStart;
            backToMainMenuStart.setBounds(250, 190, 200, 20);
            backToMainMenuStart.addActionListener(this);

            comboDimensions.setSelectedIndex(1);
            comboDimensions.addActionListener(this);
            comboDimensions.setBounds(250, 150, 200, 20);
            settingsPanel.add(comboDimensions);
            settingsPanelAlreadyVisited = true;
        }


        title.setText("Settings");
    }

    public void showBoard() {
        Utilities.getLoggerOfClass(getClass()).log(Level.INFO, "showBoard() invoked");
        // Base Settings
        this.player1 = new Player(inputPlayer1.getText(), Stone.BLACK);
        this.player2 = new Player(inputPlayer2.getText(), Stone.WHITE);
//        String user1 = inputPlayer1.getText();
//        String user2 = inputPlayer2.getText();
//        player1.setUsername(user1);
//        player2.setUsername(user2);
        this.board = new BoardImplementation(this.selectedBoardSize);
        try {
            this.game = new Game(board, player1, player2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.currentWindow = 2;
        frame.add(gridPanel);

        //JLabel blackstone = new JLabel(new ImageIcon(blackStoneImg));
        Utilities.getLoggerOfClass(getClass()).log(Level.INFO, BoardFormatter.formatBoardCompact(board));
        switch (board.getBoardDimension()) {
            case 19 -> {
                gridPanel.add(boardImg19);
                boardImg19.removeMouseListener(this);
                boardImg19.addMouseListener(this);
                // Inserisco la pedina nera al centro:
                showStone(blackStoneImg, 26 * 9, 26 * 9, Stone.BLACK, 1);
            }
            case 15 -> {
                gridPanel.add(boardImg15);
                boardImg15.removeMouseListener(this);
                boardImg15.addMouseListener(this);
                // Inserisco la pedina nera al centro:
                showStone(blackStoneImg, 26 * 9 - 54, 26 * 9 - 54, Stone.BLACK, 1);
            }
        }

        // Bottone Back to Main Menu
        JButton backToMainMenuGrid = new JButton("Back to Main Menu");
        gridPanel.add(backToMainMenuGrid);
        buttons[3] = backToMainMenuGrid;
        backToMainMenuGrid.setBounds(600, 600, 200, 20);
        backToMainMenuGrid.addActionListener(this);

        title.setText("Turn: " + game.getPlayer2().getUsername());

    }

    public void showStone(BufferedImage stoneImg, int resizeX, int resizeY, Stone stoneColor, int num) {
        JLabel stone = new JLabel(new ImageIcon(stoneImg));
        int resize;
        // resize computato in base all'immagine della board utilizzata (sia 19 che 15)
        switch (board.getBoardDimension()) {
            case 19 -> {
                resize = 22;
                stone.setBounds(resizeX + resize, resizeY + resize, 24, 24);
                //stone.setIcon(new ImageIcon(stoneImg));
                if (num > 0) {
                    if (stoneColor == Stone.BLACK)
                        stone.setForeground(Color.white);
                    else
                        stone.setForeground(Color.black);
                    stone.setText(String.valueOf(num));
                    stone.setHorizontalTextPosition(JLabel.CENTER);
                    stone.setVerticalTextPosition(JLabel.CENTER);
                }
                boardImg19.add(stone);
            }
            case 15 -> {
                resize = 22 + 54;
                stone.setBounds(resizeX + resize, resizeY + resize, 24, 24);
                if (num > 0) {
                    if (stoneColor == Stone.BLACK)
                        stone.setForeground(Color.white);
                    else
                        stone.setForeground(Color.black);
                    stone.setText(String.valueOf(num));
                    stone.setHorizontalTextPosition(JLabel.CENTER);
                    stone.setVerticalTextPosition(JLabel.CENTER);
                }
                boardImg15.add(stone);
            }
        }
        title.setText("Turno: " + game.getNextMovingPlayer().getUsername());
    }

    // EVENTS


    // Gestione evento del click su un pulsante nelle finestre
    @Override

    public void actionPerformed(ActionEvent e) {
        switch (this.currentWindow) {
            case 0 -> { // Window: main
                if (e.getSource() == buttons[0]) { // Click: Play
                    frame.remove(startPanel);
                    frame.repaint();
                    showBoard();
                    isGameFinished = false;
                }
                if (e.getSource() == buttons[1]) { // Click: Settings
                    frame.remove(startPanel);
                    frame.repaint();
                    showSettings();
                }
            }
            case 1 -> { // Window: Settings
                if (e.getSource() == comboDimensions) { // Click: "15x15", "19x19"
                    JComboBox<?> cb = null;
                    Object source = e.getSource();
                    if (source instanceof JComboBox) {
                        cb = (JComboBox<?>) source;
                    }
                    String lineDimension = (String) (cb != null ? cb.getSelectedItem() : null);
                    this.selectedBoardSize = Integer.parseInt(lineDimension != null ?
                            lineDimension.substring(0, 2) : null);
                    this.board = new BoardImplementation(selectedBoardSize);
                }
                if (e.getSource() == buttons[2]) { // Click: Back to Main Menu
                    frame.remove(settingsPanel);
                    frame.repaint();
                    this.currentWindow = 0;
                    title.setText("Gomoku");
                    frame.add(startPanel);
                }
            }
            case 2 -> { // Window: Game
                if (e.getSource() == buttons[3]) { // Click: Back to Main Menu
                    boardImg19.removeAll();
                    boardImg19.repaint();
                    boardImg15.removeAll();
                    boardImg15.repaint();
                    gridPanel.removeAll();
                    gridPanel.revalidate();
                    gridPanel.repaint();
                    frame.remove(gridPanel);
                    frame.repaint();
                    this.currentWindow = 0;
                    title.setText("Gomoku");
                    frame.add(startPanel);
                }
            }
        }
    }

    // Gestione eventi riguardo al mouse
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (!isGameFinished) {
            int paddingBoard = switch (board.getBoardDimension()) {
                //case 19 -> 30; default
                case 15 -> 84;
                default -> 30;
            };
            // se la dimensione è 19 fa 30 pixels, se è 15 è 84 pixels
            // Rilevo le x,y del mouse dopo aver cliccato
            int x = e.getX() - paddingBoard;
            int y = e.getY() - paddingBoard;
            int cell_dimension = 26; // è il valore in pixel della dimensione cella rispetto all'immagine

            int resizeX = 0;
            int resizeY = 0; // resize x e y rispetto a dove posizionare la stone come immagine
            int newX = 0;
            int newY = 0; // le vere coordinate che vengono rilevate per makeMove
            while (resizeX < ((x + 7) - cell_dimension)) {
                resizeX = resizeX + cell_dimension;
                newY++;
            }
            while (resizeY < ((y + 7) - cell_dimension)) {
                resizeY = resizeY + cell_dimension;
                newX++;
            }
            // Provo a eseguire una mossa
            Player nextMovingPlayer = game.getNextMovingPlayer();
            try {
                //System.out.printf("%s1: newX=%%d, newY=%%d%%n".formatted(System.lineSeparator()), newX, newY);
                game.makeMove(nextMovingPlayer, new Coordinates(newX, newY));
                //System.out.println(game.getBoard().getStoneAt(new Coordinates(9, 9)));
                // Inserisco l'immagine di una stone bianca oppure nera a seconda dei casi
                if (nextMovingPlayer.getColour() == Stone.WHITE) {
                    //System.out.printf("2");
                    //showStone(whiteStoneImg, resizeX, resizeY, nextMovingPlayer.getMovesList().size());
                    showStone(whiteStoneImg, resizeX, resizeY, Stone.WHITE
                            , ((BoardImplementation) game.getBoard()).getNumberOfOccupiedPositionInBoard());
                    //System.out.printf("3");
                } else if (nextMovingPlayer.getColour() == Stone.BLACK) {
                    //System.out.printf("4");
                    showStone(blackStoneImg, resizeX, resizeY, Stone.BLACK
                            , ((BoardImplementation) game.getBoard()).getNumberOfOccupiedPositionInBoard());
                    //System.out.printf("5");
                }
            } catch (Game.InvalidMoveThrowable ex) {
                System.out.printf("InvalidMoveThrowable: Invalid Move %d, %d, %s Try Again%s"
                        .formatted(newX + 1, newY + 1, nextMovingPlayer.getUsername(), System.lineSeparator()));
                title.setText("Invalid Move, %s Try Again".formatted(nextMovingPlayer.getUsername()));
            }
            gridPanel.repaint();
            if (game.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WITH_A_DRAW)) {
                title.setText("The match ends in a draw");
                isGameFinished = true;
                JOptionPane.showMessageDialog(frame,
                        "The match ends in a draw!%s%s number of stones:%d%s%s number of stones:%d"
                                .formatted(System.lineSeparator(),
                                        game.getPlayer1().getUsername(),
                                        game.getPlayer1().getMovesList().size(),
                                        System.lineSeparator(),
                                        game.getPlayer2().getUsername(),
                                        game.getPlayer2().getMovesList().size())
                );
            } else if (game.getGameStatus().equals(BoardGameStatus.GAME_FINISHED_WHIT_A_WINNER)) {
                title.setText(game.getCurrentMovingPlayer().getUsername() + " wins!");
                isGameFinished = true;
                JOptionPane.showMessageDialog(frame,
                        "%s wins!!%s%s number of stones:%d%s%s number of stones:%d"
                                .formatted(
                                        game.getCurrentMovingPlayer().getUsername(),
                                        System.lineSeparator(),
                                        game.getPlayer1().getUsername(),
                                        game.getPlayer1().getMovesList().size(),
                                        System.lineSeparator(),
                                        game.getPlayer2().getUsername(),
                                        game.getPlayer2().getMovesList().size())
                );
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
