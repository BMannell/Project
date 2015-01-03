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
    AI ai;
    GUI gui;
    
    public Chess(){
        gui = new GUI(this);
        //gui.displayMainMenu();
    }
    
    public void makeMove(Move move){
        gui.lock();
        
        //Engine.getGameBoard();
        //Check game over
        
        gui.drawBoard();
        System.out.println("MadeMove");
        
        //get ai turn 
        //check gameover 
        
        gui.unlock();
    }
    
    /*
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
    }*/

    public void checkStalemate() {

    }

    public void logMove(Move m) {

    }
    
    public void initCustomGameBoard(CustomBoard.Square[][] customBoard, boolean colour){
        for(int y = 0; y<8;y++){
            for(int x =0; x<8;x++){
                if(customBoard[y][x].piece != null){
                    String type = customBoard[y][x].piece.type;
                    boolean col = ("white" == customBoard[y][x].piece.colour);
                    boolean isHuman = (col == colour);
                    currentState[y][x] = new Piece(col, isHuman, type);
                }
            }
        }
        gui.boardSetup();
    }
    
    public void newGame(int ply, boolean colour){
        initGameBoard(colour);
        ai = new AI(ply);
    }
    /* initGameBoard
     * @param c
     * determines players team
     * true = white
     * false= black
    */   
    public void initGameBoard(boolean c){
        // Opponent
        // Pawns
        for(int i=0;i<8;i++){
            currentState[1][i] = new Piece(!c,false,"pawn");
        }
        //Rooks
        currentState[0][0] = new Piece(!c,false,"rook");
        currentState[0][7] = new Piece(!c,false,"rook");
        //Knights
        currentState[0][1] = new Piece(!c,false,"knight");
        currentState[0][6] = new Piece(!c,false,"knight");
        //Bishops
        currentState[0][2] = new Piece(!c,false,"bishop");
        currentState[0][5] = new Piece(!c,false,"bishop");
        //Queen
        currentState[0][3] = new Piece(!c,false,"queen");
        //King
        currentState[0][4] = new Piece(!c,false,"king");
        
        //Player
        //Pawns
        for(int i=0;i<8;i++){
            currentState[6][i] = new Piece(c,true,"pawn");
        }
        //Rooks
        currentState[7][0] = new Piece(c,true,"rook");
        currentState[7][7] = new Piece(c,true,"rook");
        //Knights
        currentState[7][1] = new Piece(c,true,"knight");
        currentState[7][6] = new Piece(c,true,"knight");
        //Bishops
        currentState[7][2] = new Piece(c,true,"bishop");
        currentState[7][5] = new Piece(c,true,"bishop");
        //Queen
        currentState[7][3] = new Piece(c,true,"queen");
        //King
        currentState[7][4] = new Piece(c,true,"king");
    }
    
    public static void main(String[] args) {
        Chess c = new Chess();
    }

}
