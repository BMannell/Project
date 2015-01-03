package chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.NumberFormat;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;


public class GUI extends JFrame{
    
    private BoardSquare[][] board = new BoardSquare[8][8];
    Chess chess;        //chess controller
    JPanel boardPane;   //game board display
    JRadioButton whiteButton;       //colour select
    JFormattedTextField plySelect;  //ply input
    
    public GUI(Chess c){
        super("Chess");
        chess = c;
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        displayMainMenu();
    }
    
    public void displayMainMenu(){
        
        setSize(180,200);
        
        JPanel main = new JPanel();
        
        main.setLayout(new BoxLayout(main, 1));
        
        // button to start new game
        JButton start = new JButton("New Game");
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(Box.createRigidArea(new Dimension(0, 10)));
        start.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
               displayGameOptions();
            }
        });
        main.add(start);
        
        //button to load game from file
        JButton load = new JButton("Load Game");
        load.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(Box.createRigidArea(new Dimension(0, 10)));
        load.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
               System.out.println("Load");
            }
        });
        load.setEnabled(false);
        main.add(load);
        
        //button to whitetate game file
        JButton spec = new JButton("Spectate Game");
        spec.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(Box.createRigidArea(new Dimension(0, 10)));
        spec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
               System.out.println("Spec");
            }
        });
        spec.setEnabled(false);
        main.add(spec);
        
        JButton test = new JButton("Custom Board");
        test.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(Box.createRigidArea(new Dimension(0, 10)));
        test.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
              customGame();
            }
        });
        main.add(test);
        
        getContentPane().add(main);
    }
    
    public void customGame(){
        getContentPane().removeAll();
        setSize(798,512);
        getContentPane().add(new CustomBoard(chess));
        validate();
        repaint();
    }
    
    public void displayGameOptions() {
        setSize(180,250);
        getContentPane().removeAll();
        JPanel colors = new JPanel();
        colors.setLayout(new BoxLayout(colors, BoxLayout.Y_AXIS));
        colors.setVisible(true);
        
        //colour select for player
        JPanel pColor = new JPanel();
        pColor.setLayout(new BoxLayout(pColor, BoxLayout.Y_AXIS));
        pColor.setVisible(true);
        pColor.setAlignmentX(Component.CENTER_ALIGNMENT);

        //create border for radio colour select
        TitledBorder title = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), "Colour");
        title.setTitleJustification(TitledBorder.CENTER);
        pColor.setBorder(title);

        //create radio buttons
        whiteButton = new JRadioButton("White");
        whiteButton.setSelected(true);
        JRadioButton blackButton = new JRadioButton("Black");

        //group radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(whiteButton);
        group.add(blackButton);

        //add buttons to panel
        pColor.add(whiteButton);
        pColor.add(blackButton);

        colors.add(Box.createRigidArea(new Dimension(0, 10)));
        colors.add(pColor);
        colors.add(Box.createRigidArea(new Dimension(0, 10)));
        
        /* Ply input panel*/
        JPanel ply = new JPanel();
        ply.setVisible(true);
        title = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), "Ply");
        title.setTitleJustification(TitledBorder.CENTER);
        ply.setBorder(title);
        Dimension plyD = new Dimension(80,60);
        ply.setMaximumSize(plyD);
        ply.setMinimumSize(plyD);
        ply.setPreferredSize(plyD);
        ply.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        /* Ply input field */
        plySelect = new JFormattedTextField(NumberFormat.getIntegerInstance());
        plySelect.setValue(new Integer(8));
        plySelect.setColumns(3);
        
        ply.add(plySelect);
        
        colors.add(ply);
        
        /* Start */
        JButton white = new JButton("Start");
        white.setAlignmentX(Component.CENTER_ALIGNMENT);
        white.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int ply = (int)(long)plySelect.getValue();
                if(ply < 1){
                    ply = 8;
                }
                chess.newGame(ply,whiteButton.isSelected());
                boardSetup();
            }
        });
        
        colors.add(Box.createRigidArea(new Dimension(0, 10)));
        colors.add(white);
        colors.add(Box.createRigidArea(new Dimension(0, 10)));
        
        getContentPane().add(colors);//Adding to content pane, not to Frame
        validate();
        repaint();
    }
    
    public void boardSetup(){
        initGameBoard();
        drawBoard();
        setSize(512,512);
        getContentPane().removeAll();
        getContentPane().add(boardPane);
        validate();
        repaint();
    }
    
    /* Initializes GameBoard */
    private void initGameBoard() {
        boolean colour = true;
        boardPane = new JPanel(new GridLayout(8, 8));
        boardPane.setSize(512,512);
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                BoardSquare jp;
                if(colour){
                    jp = new BoardSquare(Color.white, x, y);
                }
                else{
                    jp = new BoardSquare(Color.black, x, y);
                }
                board[y][x] = jp;
                boardPane.add(jp);
                colour = !colour;
            }
            colour = !colour;
        }
    }
    
    /* Draws current gameboard */
    public void drawBoard(){
        for(int y = 0; y<8;y++){
            for(int x=0;x<8;x++){
                board[y][x].removeAll();
                if(chess.currentState[y][x] != null){
                    board[y][x].addPiece(chess.currentState[y][x]);
                }
            }
        }
    }
    
    public void cleanBoard(){
        for(BoardSquare[] bs: board){
            for(BoardSquare b: bs){
                b.revertColour();
            }
        }
    }
    
    public void lock(){
        boardPane.setEnabled(false);
    }
    
    public void unlock(){
        boardPane.setEnabled(true);
    }
    
    /* Initializes menu bar 
    private void initMenuBar(){
        //Where the GUI is created:
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("A Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);
        
        setJMenuBar(menuBar);
    }
    */
    
    /**
     * BoardSquare
     * 
     * Variables
     * -position
     * -piece
     * -initial color (black, white)
     */
    
    public class BoardSquare extends JPanel{
        
        Color initialColor;
        int X;
        int Y;
        Piece piece;
        
        boolean toMove = false; //set to true if mvoe square
        public BoardSquare(Color bg, int x, int y){
            initialColor = bg;
            X = x;
            Y = y;
            setBackground(bg);
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    makeMove();
                }
            });
            setSize(64, 64);
            setBorder(BorderFactory.createLineBorder(Color.black));
        }
        
        public void revertColour(){
            setBackground(initialColor);
        }
        
        public void setBlue(){
            setBackground(Color.CYAN);
        }
        
        public void setRed(){
            setBackground(Color.RED);
        }
        
        public void setGreen(){
            setBackground(Color.GREEN);
        }
        
        public void selectPiece(){
            if(piece!=null && piece.team){
                cleanBoard();
                setBlue();
            }
        }
        
        public void addPiece(Piece p){
            piece = p;
            add(piece.image);
        }
        
        public void makeMove(){
            if(toMove){
                toMove =false;
                revertColour();
                chess.makeMove(new Move());
            }
            else if(piece != null && piece.team){
                cleanBoard();
                setBlue();
                toMove = true;
            }
        }
    }
}
