package chess;


public class Chess {

    boolean turn;       //whose turn it is
    public Piece[][] currentState = new Piece[8][8]; // the board
    AI ai;
    GUI gui;
    
    public Chess(){
        gui = new GUI(this);
        //gui.displayMainMenu();
    }
    
    public void makeMove(Move move){
        gui.lock();
        
        currentState = Engine.getBoardAfterMove(currentState, move);
        
        switch(Engine.stateCheck(currentState, true)){
            case 0:
                System.out.println("All-clear!");
                break;
            case 1:
                System.out.println("Checked!");
                break;
            case 2:
                System.out.println("Checkmate!");
                break;
        }
        
        gui.drawBoard();
        System.out.println("MadeMove");
        
        //get ai turn 
        //check gameover 
        //gui.drawBoard();
        
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
                    boolean col = "white".equalsIgnoreCase(customBoard[y][x].piece.colour);
                    System.out.println(customBoard[y][x].piece.colour);
                    boolean isHuman = (col == colour);
                    System.out.println(col);
                    System.out.println(isHuman);
                    System.out.println();
                    currentState[y][x] = new Piece(col, isHuman, type, true);
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
            currentState[1][i] = new Piece(!c,false,"pawn",false);
        }
        //Rooks
        currentState[0][0] = new Piece(!c,false,"rook",false);
        currentState[0][7] = new Piece(!c,false,"rook",false);
        //Knights
        currentState[0][1] = new Piece(!c,false,"knight",false);
        currentState[0][6] = new Piece(!c,false,"knight",false);
        //Bishops
        currentState[0][2] = new Piece(!c,false,"bishop",false);
        currentState[0][5] = new Piece(!c,false,"bishop",false);
        //Queen
        currentState[0][3] = new Piece(!c,false,"queen",false);
        //King
        currentState[0][4] = new Piece(!c,false,"king",false);
        
        //Player
        //Pawns
        for(int i=0;i<8;i++){
            currentState[6][i] = new Piece(c,true,"pawn",false);
        }
        //Rooks
        currentState[7][0] = new Piece(c,true,"rook",false);
        currentState[7][7] = new Piece(c,true,"rook",false);
        //Knights
        currentState[7][1] = new Piece(c,true,"knight",false);
        currentState[7][6] = new Piece(c,true,"knight",false);
        //Bishops
        currentState[7][2] = new Piece(c,true,"bishop",false);
        currentState[7][5] = new Piece(c,true,"bishop",false);
        //Queen
        currentState[7][3] = new Piece(c,true,"queen",false);
        //King
        currentState[7][4] = new Piece(c,true,"king",false);

    }
    
    
    // copies the current board state and returns it (for having board states)
    // not perfect but can be modularized to be compatable and usable in other situations
    public Piece[][] copyCurrentBoard(){
        Piece[][] newBoard = new Piece[8][8];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                newBoard[i][j] = currentState[i][j].copy();
            }
        }

        return newBoard;
    }
    
    
    
    public static void main(String[] args) {
        Chess c = new Chess();
    }

}
