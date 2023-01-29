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
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;


public class GUI implements ActionListener, MouseListener {

    private boolean isGameFinished = false;
    int currentWindow; // finestra in cui mi trovo nel gioco
    int selectedBoardSize;
    private Board board;
    private Game game;
    private Player player1;
    private Player player2;
    //private Player winner; //come in CLI Controller

    private JFrame frame = new JFrame(); // frame totale
    private JPanel upper_panel = new JPanel(); // pannello soprastante con il titolo
    private JLabel title = new JLabel();
    private JPanel start_panel = new JPanel();
    private JPanel settings_panel = new JPanel();
    private JTextField input_player1 = new JTextField("Player 1");
    private JTextField input_player2 = new JTextField("Player 2");
    private JPanel grid_panel = new JPanel();
    private JButton[] buttons = new JButton[3];
    private JLabel board_img_19 = new JLabel(new ImageIcon(ImageIO.read(new URL("https://i.imgur.com/7x0CxBV.png"))));
    private JLabel board_img_15 = new JLabel(new ImageIcon(ImageIO.read(new URL("https://i.imgur.com/4pxgWya.png"))));
    private BufferedImage black_stone_img = ImageIO.read(new URL("https://i.imgur.com/cDfy5SP.png"));
    private BufferedImage white_stone_img = ImageIO.read(new URL("https://i.imgur.com/kIXiq4Q.png"));
    private String[] dimensions = {"15x15", "19x19"};
    private JComboBox comboDimensions = new JComboBox(dimensions);
    public int getCurrentWindow() {
        return currentWindow;
    }

    public Board getBoard() {
        return board;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getUpper_panel() {
        return upper_panel;
    }

    public JLabel getTitle() {
        return title;
    }

    public JPanel getStart_panel() {
        return start_panel;
    }

    public JTextField getInput_player1() {
        return input_player1;
    }

    public JTextField getInput_player2() {
        return input_player2;
    }

    public JPanel getGrid_panel() {
        return grid_panel;
    }

    public JButton[] getButtons() {
        return buttons;
    }

    public JLabel getBoard_img_19() {
        return board_img_19;
    }

    public JLabel getBoard_img_15() {
        return board_img_15;
    }

    public BufferedImage getBlack_stone_img() {
        return black_stone_img;
    }

    public BufferedImage getWhite_stone_img() {
        return white_stone_img;
    }
    public void setCurrentWindow(int currentWindow) {
        this.currentWindow = currentWindow;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void setUpper_panel(JPanel upper_panel) {
        this.upper_panel = upper_panel;
    }

    public void setTitle(JLabel title) {
        this.title = title;
    }

    public void setStart_panel(JPanel start_panel) {
        this.start_panel = start_panel;
    }

    public void setInput_player1(JTextField input_player1) {
        this.input_player1 = input_player1;
    }

    public void setInput_player2(JTextField input_player2) {
        this.input_player2 = input_player2;
    }

    public void setGrid_panel(JPanel grid_panel) {
        this.grid_panel = grid_panel;
    }

    public void setButtons(JButton[] buttons) {
        this.buttons = buttons;
    }

    public void setBoard_img_19(JLabel board_img_19) {
        this.board_img_19 = board_img_19;
    }

    public void setBoard_img_15(JLabel board_img_15) {
        this.board_img_15 = board_img_15;
    }

    public void setBlack_stone_img(BufferedImage black_stone_img) {
        this.black_stone_img = black_stone_img;
    }

    public void setWhite_stone_img(BufferedImage white_stone_img) {
        this.white_stone_img = white_stone_img;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public GUI() throws IOException {
        this.selectedBoardSize = 19;

        setMainElements();

        showStartingWindow();

        startFrame();
    }

    public void setMainElements() {
        // Settings of Main elements: frame, title, panels

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700); // Size della finestra
        frame.getContentPane().setBackground(new Color(50, 50, 50)); // Colore della finestra
        frame.setTitle("Gomoku"); // Titolo finestra
        frame.setLayout(new BorderLayout()); // Layout Frame
        frame.setVisible(true);

        upper_panel.setLayout(new BorderLayout());
        upper_panel.setBounds(0, 0, 800, 100);
        upper_panel.add(title);

        // non va bene, è senza layout, è provvisorio
        start_panel.setLayout(null);
        start_panel.setBackground(new Color(234, 214, 84));

        grid_panel.setBackground(new Color(234, 214, 84));

        settings_panel.setLayout(null);
        settings_panel.setBackground(new Color(234, 214, 84));

        frame.add(upper_panel, BorderLayout.NORTH);

        title.setBackground(new Color(234, 214, 84)); // Colore testo
        title.setForeground(new Color(3, 3, 3)); // Colore testo
        title.setFont(new Font("Ink Free", Font.BOLD, 30)); // Font testo
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setText("Gomoku");
        title.setOpaque(true);
    }

    public void showStartingWindow() {
        this.currentWindow = 0;
        title.setText("Gomoku");
        frame.add(start_panel);
        int x_starting_components = 250; //allineare nella x gli elementi
        // è un allineamento provvisorio perchè non va bene, bisogna decidere un layout!

        // Bottone Play
        JButton play = new JButton("Play");
        buttons[0] = play;
        //play.setFont(new Font("", Font.BOLD, 12));
        play.setBounds(x_starting_components, 150, 200, 20);
        play.addActionListener(this);

        // Bottone Settings
        JButton settings = new JButton("Settings");
        buttons[1] = settings;
        //play.setFont(new Font("", Font.BOLD, 12));
        settings.setBounds(x_starting_components, 200, 200, 20);
        settings.addActionListener(this);

        // Labels/Text Field per inserimento nome Giocatore 1/2
        JLabel insert_player1 = new JLabel("Inserisci il nome del Giocatore 1:");
        JLabel insert_player2 = new JLabel("Inserisci il nome del Giocatore 2:");

        // Allineo
        insert_player1.setBounds(x_starting_components, 260, 200, 20);
        input_player1.setBounds(x_starting_components, 290, 200, 20);
        insert_player2.setBounds(x_starting_components, 320, 200, 20);
        input_player2.setBounds(x_starting_components, 350, 200, 20);

        // Aggiungo elementi al pannello iniziale
        start_panel.add(play);
        start_panel.add(settings);
        start_panel.add(insert_player1);
        start_panel.add(insert_player2);
        start_panel.add(input_player1);
        start_panel.add(input_player2);


    }

    public void startFrame() {
        try {
            title.setText("Loading....");
            Thread.sleep(1000);
            title.setText("Gomoku");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void showSettings() {
        // Settings
        this.currentWindow = 1;
        frame.add(settings_panel);

        JLabel insertSize = new JLabel("Choose the dimension of the Board:");
        settings_panel.add(insertSize);
        insertSize.setBounds(250, 120, 300, 20);

        // Bottone Back to Main Menu
        JButton backToMainMenu = new JButton("Back to Main Menu");
        settings_panel.add(backToMainMenu);
        buttons[2] = backToMainMenu;
        //play.setFont(new Font("", Font.BOLD, 12));
        backToMainMenu.setBounds(250, 190, 200, 20);
        backToMainMenu.addActionListener(this);

        comboDimensions.setSelectedIndex(1);
        comboDimensions.addActionListener(this);
        comboDimensions.setBounds(250, 150, 200, 20);
        settings_panel.add(comboDimensions);



        title.setText("Settings");
    }

    public void showBoard() {
        // Base Settings
        this.player1 = new Player("", Stone.BLACK);
        this.player2 = new Player("", Stone.WHITE);
        this.board = new BoardImplementation(this.selectedBoardSize);
        try {
            this.game = new Game(board, player1, player2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Settings della board
        String user1 = input_player1.getText();
        String user2 = input_player2.getText();
        player1.setUsername(user1);
        player2.setUsername(user2);

        this.currentWindow = 2;
        frame.add(grid_panel);

        JLabel blackstone = new JLabel(new ImageIcon(black_stone_img));
        //printBoard(); // serve per controllare se la board è giusta rispetto alla classe principale Game
        switch (board.getBoardDimension()) {
            case 19:
                grid_panel.add(board_img_19);
                board_img_19.addMouseListener(this);
                // Inserisco la pedina nera al centro:
                blackstone.setBounds(26 * 9 + 22, 26 * 9 + 22, 24, 24);
                board_img_19.add(blackstone);
                break;
            case 15:
                grid_panel.add(board_img_15);
                board_img_15.addMouseListener(this);
                // Inserisco la pedina nera al centro:
                blackstone.setBounds(26 * 9 + 22, 26 * 9 + 22, 24, 24);
                board_img_15.add(blackstone);
                break;
        }

        title.setText("Turno: " + game.getPlayer2().getUsername());

    }

    public void showStone(BufferedImage stone_img, int resize_x, int resize_y) {
        JLabel stone = new JLabel(new ImageIcon(stone_img));
        int resize = 0;

        switch (board.getBoardDimension()) {
            case 19:
                resize = 22;
                stone.setBounds(resize_x + resize, resize_x + resize, 24, 24);
                board_img_19.add(stone);
                break;
            case 15:
                resize = 22+54;
                stone.setBounds(resize_x + resize, resize_x + resize, 24, 24);
                board_img_15.add(stone);
                break;
        }

        title.setText("Turno: " + game.getNextMovingPlayer().getUsername());
    }

    // EVENTS


    // Gestione evento del click su un pulsante nelle finestre
    @Override

    public void actionPerformed(ActionEvent e) {
        switch (this.currentWindow) {
            case 0:
                if (e.getSource() == buttons[0]) { // Ho cliccato Play
                    frame.remove(start_panel);
                    showBoard();
                }
                if (e.getSource() == buttons[1]) { // Ho cliccato Settings
                    frame.remove(start_panel);
                    showSettings();
                }
                break;
            case 1:
                if (e.getSource() == comboDimensions) {
                    JComboBox cb = (JComboBox)e.getSource();
                    String lineDimension = (String)cb.getSelectedItem();
                    this.selectedBoardSize = Integer.parseInt(lineDimension.substring(0,2));
                    this.board = new BoardImplementation(selectedBoardSize);
                }
                if (e.getSource() == buttons[2]) { // Ho cliccato Settings
                    frame.remove(settings_panel);
                    frame.repaint();
                    this.currentWindow = 0;
                    title.setText("Gomoku");
                    frame.add(start_panel);
                }
                break;
            case 2:

                break;
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
            int padding_board = 30;
            switch (board.getBoardDimension()) {
                case 19:
                    padding_board = 30;
                    break;
                case 15:
                    padding_board = 84;
                    break;
            }
            // se la dimensione è 19 fa 30, se è 15 è circa 109,6. andrebbe associato
            // Rilevo le x,y del mouse dopo aver cliccato
            int x = e.getX() - padding_board;
            int y = e.getY() - padding_board;
            System.out.println("coordinate: "+x+" "+y);
            int cell_dimension = 26; // è il valore della dimensione cella rispetto all'immagine

            int resize_x = 0;
            int resize_y = 0; // resize x e y rispetto a dove posizionare la stone come immagine
            int new_x = 0;
            int new_y = 0; // le vere coordinate che vengono rilevate per makeMove
            while (resize_x < ((x + 7) - cell_dimension)) {
                resize_x = resize_x + cell_dimension;
                new_y++;
            }
            while (resize_y < ((y + 7) - cell_dimension)) {
                resize_y = resize_y + cell_dimension;
                new_x++;
            }
            System.out.println("coordinate Game: "+new_y+" "+new_x);
            // Provo a eseguire una mossa
            Player nextMovingPlayer = game.getNextMovingPlayer();
            try {
                game.makeMove(nextMovingPlayer, new Coordinates(new_x, new_y));
                printBoard(); // serve per controllare se la board è giusta rispetto alla classe principale Game
                // Inserisco l'immagine di una stone bianca oppure nera a seconda dei casi
                if (nextMovingPlayer.getColour() == Stone.WHITE) {
                    showStone(white_stone_img, resize_x, resize_y);
                } else if (nextMovingPlayer.getColour() == Stone.BLACK) {
                    showStone(black_stone_img, resize_x, resize_y);
                }
            } catch (Game.InvalidMoveException ex) {
                //System.out.println(ex);
                title.setText("Mossa non valida, " + nextMovingPlayer.getUsername() + " riprova");
            }
            if (isGameTie()) {
                title.setText("La partita finisce pari. Sono finite le pietre per il giocatore " + game.getLastMovingPlayer().getUsername());
                // mi aspetto un metodo per uscire dal gioco / ricominciare
                isGameFinished = true;
            } else if (thereIsAWinner()) {
                title.setText(game.getLastMovingPlayer().getUsername() + " ha vinto!");
                // mi aspetto un metodo per uscire dal gioco / ricominciare
                isGameFinished = true;
            }
            grid_panel.repaint();
        }
    }

    private boolean isGameTie() {
        return game.checkIfStonesOfAPlayerAreFinished();
    }

    private boolean thereIsAWinner() {
        return game.checkIfThereAreFiveConsecutiveStones(game.getLastMovingPlayer().getColour());
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // Preso da CLI Controller:

    public void printBoard() {
        StringBuilder tmp = new StringBuilder();
        String repeatedLine = "|\t".repeat(board.getBoardDimension());
        List<List<String>> boardPartitionString = Utilities.partition(Arrays.stream(board.toString().split("")).toList(), board.getBoardDimension());
        tmp.append(System.lineSeparator());
        for (int row = 0; row < boardPartitionString.size(); row++) {
            tmp.append(String.format("%1s", row)).append("\t");
            for (int stone = 0; stone < boardPartitionString.get(row).size(); stone++) {
                if (stone == boardPartitionString.get(row).size() - 1) {
                    tmp.append(String.format("%-4s", boardPartitionString.get(row).get(stone)))
                            .append(System.lineSeparator());
                } else {
                    tmp.append(String.format("%-4s", boardPartitionString.get(row).get(stone))
                            .replace(" ", "-"));
                }
            }
            if (!(row == boardPartitionString.size() - 1)) {
                tmp.append("\t")
                        .append(repeatedLine)
                        .append(System.lineSeparator());
            }
        }
        tmp.append("\t");
        IntStream.range(0, board.getBoardDimension())
                .forEach(value ->
                        tmp.append(String.format("%1s", value)).append("\t"));
        tmp.append(System.lineSeparator());
        System.out.println(tmp);
    }


}
