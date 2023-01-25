package it.units.gomokusdm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;


public class GUI implements ActionListener, MouseListener {
    int current_window; // finestra in cui mi trovo nel gioco
    private Board board;
    private Game game;
    private final Player player1;
    private final Player player2;
    //private Player winner; //come in CLI Controller

    JFrame frame = new JFrame(); // frame totale
    JPanel upper_panel = new JPanel(); // pannello soprastante con il titolo
    JLabel title = new JLabel();
    JPanel start_panel = new JPanel();
    JTextField input_player1 = new JTextField("Player 1");
    JTextField input_player2 = new JTextField("Player 2");
    JPanel grid_panel = new JPanel();
    JButton[] initial_buttons = new JButton[2];
    JLabel board_img_19 = new JLabel(new ImageIcon(ImageIO.read(new URL("https://i.imgur.com/7x0CxBV.png"))));
    JLabel board_img_15 = new JLabel(new ImageIcon(ImageIO.read(new URL("https://i.imgur.com/4pxgWya.png"))));
    BufferedImage black_stone_img = ImageIO.read(new URL("https://i.imgur.com/cDfy5SP.png"));
    BufferedImage white_stone_img = ImageIO.read(new URL("https://i.imgur.com/kIXiq4Q.png"));

    GUI() throws IOException {

        // Base Settings
        this.player1 = new Player("", Stone.BLACK);
        this.player2 = new Player("", Stone.WHITE);
        this.board = new BoardImplementation();
        try {
            this.game = new Game(board, player1, player2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        setMainElements();

        showStartingWindow();

        startGame();
    }

    public void setMainElements() {
        // Settings of Main elements: frame, title, panels
        current_window = 0;

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

        frame.add(upper_panel, BorderLayout.NORTH);
        frame.add(start_panel);

        title.setBackground(new Color(234, 214, 84)); // Colore testo
        title.setForeground(new Color(3, 3, 3)); // Colore testo
        title.setFont(new Font("Ink Free", Font.BOLD, 30)); // Font testo
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setText("Gomoku");
        title.setOpaque(true);
    }

    public void showStartingWindow() {
        int x_starting_components = 250; //allineare nella x gli elementi
        // è un allineamento provvisorio perchè non va bene, bisogna decidere un layout!

        // Bottone Play
        JButton play = new JButton("Play");
        initial_buttons[0] = play;
        //play.setFont(new Font("", Font.BOLD, 12));
        play.setBounds(x_starting_components,150, 200,20);
        play.addActionListener(this);

        // Bottone Settings
        JButton settings = new JButton("Settings");
        initial_buttons[1] = settings;
        //play.setFont(new Font("", Font.BOLD, 12));
        settings.setBounds(x_starting_components,200, 200,20);
        settings.addActionListener(this);

        // Labels/Text Field per inserimento nome Giocatore 1/2
        JLabel insert_player1 = new JLabel("Inserisci il nome del Giocatore 1:");
        JLabel insert_player2 = new JLabel("Inserisci il nome del Giocatore 2:");

        // Allineo
        insert_player1.setBounds(x_starting_components,260, 200,20);
        input_player1.setBounds(x_starting_components,290, 200,20);
        insert_player2.setBounds(x_starting_components,320, 200,20);
        input_player2.setBounds(x_starting_components,350, 200,20);

        // Aggiungo elementi al pannello iniziale
        start_panel.add(play);
        start_panel.add(settings);
        start_panel.add(insert_player1);
        start_panel.add(insert_player2);
        start_panel.add(input_player1);
        start_panel.add(input_player2);


    }

    public void startGame() {
        try {
            title.setText("Loading....");
            Thread.sleep(1000);
            title.setText("Gomoku");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void showBoard() {
        // Settings della board
        String user1 = input_player1.getText();
        String user2 = input_player2.getText();
        player1.setUsername(user1);
        player2.setUsername(user2);
        this.current_window = 2;
        frame.add(grid_panel);
        //printBoard(); // serve per controllare se la board è giusta rispetto alla classe principale Game
        // if board dimension = 19...
        this.game.setupGame(19);
        grid_panel.add(board_img_19);
        board_img_19.addMouseListener(this);

        // Inserisco la pedina nera al centro:
        JLabel blackstone = new JLabel(new ImageIcon(black_stone_img));
        blackstone.setBounds(26*9+22, 26*9+22, 24, 24);
        board_img_19.add(blackstone);

        title.setText("Turno: "+game.getPlayer2().getUsername());

    }

    public void showStone(BufferedImage stone_img, int resize_x, int resize_y) {
        JLabel stone = new JLabel(new ImageIcon(stone_img));
        // è +22 per size=19
        stone.setBounds(resize_x+22, resize_y+22, 24, 24);
        board_img_19.add(stone);
        title.setText("Turno: " + game.getNextMovingPlayer().getUsername());
    }

    // EVENTS


    // Gestione evento del click su un pulsante nelle finestre
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (this.current_window) {
            case 0:
                if (e.getSource() == initial_buttons[0]) { // Ho cliccato Play
                    frame.remove(start_panel);
                    showBoard();
                }
                if (e.getSource() == initial_buttons[1]) { // Ho cliccato Settings
                    // da fare, dobbiamo decidere che impostazioni cambiare
                    // si suppone che board_dimension possa cambiare..
                }

                break;
            case 1:

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
        int padding_board = 30; // se la dimensione è 19 fa 30, se è 15 è circa 109,6. andrebbe associato
        // Rilevo le x,y del mouse dopo aver cliccato
        int x=e.getX()-padding_board; int y=e.getY()-padding_board;
        int cell_dimension = 26; // è il valore della dimensione cella rispetto all'immagine

        int resize_x = 0; int resize_y = 0; // resize x e y rispetto a dove posizionare la stone come immagine
        int new_x = 0; int new_y = 0; // le vere coordinate che vengono rilevate per makeMove
        while(resize_x < ((x+7)-cell_dimension))  {
            resize_x = resize_x + cell_dimension;
            new_y++;
        }
        while(resize_y < ((y+7)-cell_dimension)) {
            resize_y = resize_y + cell_dimension;
            new_x++;
        }
        //System.out.println("coordinate Game: "+new_y+" "+new_x);
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
            title.setText("Mossa non valida, " + nextMovingPlayer.getUsername() + " riprova");
        }

        if (game.checkIfThereAreFiveConsecutiveStones(game.getLastMovingPlayer().getColour())) {
            title.setText(game.getLastMovingPlayer().getUsername() + " ha vinto!");
            // mi aspetto un metodo per uscire dal gioco / ricominciare
        }
        grid_panel.repaint();
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
