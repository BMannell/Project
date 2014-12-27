package chess;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Ben
 */
public class Chess {

    boolean turn;       //whose turn it is
    public Piece[][] currentState = new Piece[8][8];
    Player player1;
    Player player2;
    GUI gui;
    
    public Chess(){
        initGameBoard(true);
        gui = new GUI(this);
        gui.drawBoard();
    }
    
    public void playerColour(boolean c){
        
    }
    
    public void playGame() {
        boolean play;
        Move move;
        do {
            if (turn) { // player 1
                move = player1.getMove();
            } else { // player 2
                move = player2.getMove();
            }
            currentState = Engine.makeMove(move);
            gui.drawBoard();
            logMove(move);

            checkStalemate();
            play = Engine.stateCheck(currentState);

            turn = !turn;
        } while (play);
    }

    public void checkStalemate() {

    }

    public void logMove(Move m) {

    }
    
    /**
     * @param c
     * determines players team
     * true = white
     * false= black
    */
    void initGameBoard(boolean c){
        // Opponent
        // Pawns
        for(int i=0;i<8;i++){
            currentState[1][i] = new Piece(!c,false,new int[]{1,i},"pawn");
        }
        //Rooks
        currentState[0][0] = new Piece(!c,false,new int[]{0,0},"rook");
        currentState[0][7] = new Piece(!c,false,new int[]{0,0},"rook");
        //Knights
        currentState[0][1] = new Piece(!c,false,new int[]{0,0},"knight");
        currentState[0][6] = new Piece(!c,false,new int[]{0,0},"knight");
        //Bishops
        currentState[0][2] = new Piece(!c,false,new int[]{0,0},"bishop");
        currentState[0][5] = new Piece(!c,false,new int[]{0,0},"bishop");
        //Queen
        currentState[0][3] = new Piece(!c,false,new int[]{0,0},"queen");
        //King
        currentState[0][4] = new Piece(!c,false,new int[]{0,0},"king");
        
        //Player
        //Pawns
        for(int i=0;i<8;i++){
            currentState[6][i] = new Piece(c,true,new int[]{1,i},"pawn");
        }
        //Rooks
        currentState[7][0] = new Piece(c,true,new int[]{0,0},"rook");
        currentState[7][7] = new Piece(c,true,new int[]{0,0},"rook");
        //Knights
        currentState[7][1] = new Piece(c,true,new int[]{0,0},"knight");
        currentState[7][6] = new Piece(c,true,new int[]{0,0},"knight");
        //Bishops
        currentState[7][2] = new Piece(c,true,new int[]{0,0},"bishop");
        currentState[7][5] = new Piece(c,true,new int[]{0,0},"bishop");
        //Queen
        currentState[7][3] = new Piece(c,true,new int[]{0,0},"queen");
        //King
        currentState[7][4] = new Piece(c,true,new int[]{0,0},"king");
    }
    
    public static void main(String[] args) {
        Chess c = new Chess();
    }

}
