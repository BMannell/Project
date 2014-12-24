package chess;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author Ben
 */

public class GUI extends JFrame{
    
    private JPanel[][] board = new JPanel[8][8];
    
    public GUI(){
        super("Chess");
        initMenuBar();
        initGameBoard();
        setSize(480,480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    /**
     * intialize menu bar
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
    
    private void initGameBoard() {
        boolean colour = true;
        JPanel boardDisplay = new JPanel(new GridLayout(8, 8));
        boardDisplay.setSize(480,480);
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                BoardSquare jp;
                if(colour){
                    jp = new BoardSquare(Color.white, x, y);
                }
                else{
                    jp = new BoardSquare(Color.black, x, y);
                }
                jp.setBorder(BorderFactory.createLineBorder(Color.black));
                jp.setSize(60, 60);
                boardDisplay.add(jp);
                colour = !colour;
            }
            colour = !colour;
        }
        setContentPane(boardDisplay);
    }
    
    private int[][] getMoves(int x, int y){
        //return Engine.getMoves
        return new int[][]{{0, 0}};
    }
    
    //public void drawBoard(Piece[][] p){
        
    //}
    
    public class BoardSquare extends JPanel{
        
        Color initialColor;
        int X;
        int Y;
        //Piece p;
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
            //if(p!=null && p.team == 0){
            //    GUI.getMoves(X,Y);
            //}
        }
    }
}
