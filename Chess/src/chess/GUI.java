package chess;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class GUI extends JFrame{
    
    private BoardSquare[][] board = new BoardSquare[8][8];
    Chess chess;        //chess controller
    JPanel boardPane;   //game board display
    
    public GUI(Chess c){
        super("Chess");
        chess = c;
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public void displayMainMenu(){
        
        setSize(180,150);
        
        JPanel main = new JPanel();
        
        main.setLayout(new BoxLayout(main, 1));
        
        // button to start new game
        JButton start = new JButton("New Game");
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(Box.createRigidArea(new Dimension(0, 10)));
        start.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
               newGame();
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
        
        getContentPane().add(main);
    }
    
    
    public void newGame(){
        getContentPane().removeAll();
        JPanel colors = new JPanel();
        colors.setLayout(new BoxLayout(colors,1));
        
        colors.add(Box.createRigidArea(new Dimension(0, 20)));
        
        /* White */
        JButton white = new JButton("White");
        white.setAlignmentX(Component.CENTER_ALIGNMENT);
        white.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
               chess.initGameBoard(true);
               boardSetup();
            }
        });
        
        colors.add(Box.createRigidArea(new Dimension(0, 10)));
        colors.add(white);
        
        /* Black */
        JButton black = new JButton("Black");
        black.setAlignmentX(Component.CENTER_ALIGNMENT);
        black.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
               chess.initGameBoard(false);
               boardSetup();
            }
        });
        
        colors.add(Box.createRigidArea(new Dimension(0, 10)));
        colors.add(black);
        
        colors.setVisible(true);
        
        getContentPane().add(colors);//Adding to content pane, not to Frame
        validate();
        repaint();
    }
    
    private void boardSetup(){
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
                jp.setBorder(BorderFactory.createLineBorder(Color.black));
                jp.setSize(64, 64);
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
                b.revertColor();
            }
        }
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
        
        public BoardSquare(Color bg, int x, int y){
            initialColor = bg;
            X = x;
            Y = y;
            setBackground(bg);
            addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
               selectPiece();
            }
        });
        }
        
        public void revertColor(){
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
    }
}
