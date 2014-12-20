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
        
        this.setJMenuBar(menuBar);
    }
    
    private void initGameBoard() {
        JPanel boardDisplay = new JPanel(new GridLayout(8, 8));
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                JPanel jp = new JPanel();
                jp.setBorder(BorderFactory.createLineBorder(Color.black));
                jp.setMaximumSize(new Dimension(30, 30));
                jp.setMinimumSize(new Dimension(30, 30));
                boardDisplay.add(jp);
            }
        }
        setContentPane(boardDisplay);
    }
}
