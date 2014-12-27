package chess;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class GUI extends JFrame{
    
    private BoardSquare[][] board = new BoardSquare[8][8];
    Chess chess;
    BoardSquare selectedSquare;
    
    public GUI(Chess c){
        super("Chess");
        chess = c;
        initMenuBar();
        initGameBoard();
        setSize(480,480);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    /**
     * Initializes menu bar
     */
    
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
    
    /**
     * Initializes GameBoard
     */
    private void initGameBoard() {
        boolean colour = true;
        JPanel boardDisplay = new JPanel(new GridLayout(8, 8));
        boardDisplay.setSize(512,512);
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
                boardDisplay.add(jp);
                colour = !colour;
            }
            colour = !colour;
        }
        setContentPane(boardDisplay);
    }
    
    /**
     * Draws a gameboard
     * 
     * @param p 
     * -gameboard to draw
     */
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
    
    /**
     * GameSquare
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
