package chess;

import java.util.ArrayList;


public class AI {
    int ply; // number of plys to consider
    Node minimax; // minimax tree head pointer
    Engine brain; // does a lot of the thinking for the AI
    
    
    public AI(int p){
        minimax = null; // set to null
        ply = p; // set number of plys to use
        brain = new Engine(); // initialize the engine
    }
    
    
    public Move takeTurn(Piece[][] currentBoardState){
        Move myMove;
        buildTree(currentBoardState);
        
        return null;
    }
    
    
    // builds the minimax tree to be worked on
    private void buildTree(Piece[][] board){
        Node head = new Node(board, 0); // create the node that is the head of the tree
        
        
        Node thisNode = minimax; // just a node pointer, start it at the head
        ArrayList<int[]> pieces = findPieces(board); // fetch AI players piece locations
        ArrayList<int[]> newPositions = null; // potential coordinates that a piece can move to
        Piece[][] b; // going to be the board that results from a move being made
        
        // for each of AI's pieces
        for(int i = 0; i < pieces.size(); i++){
            newPositions = brain.getMoves(board, pieces.get(i)[0], pieces.get(i)[1]);
            
            // for each move possible
            for(int j = 0; j < newPositions.size(); j++){
                // get the board resulting from moving the pieve to that spot
                b = brain.getBoardAfterMove(board, new Move(pieces.get(i)[0], pieces.get(i)[1], newPositions.get(j)[0], newPositions.get(j)[1]));
                thisNode.children.add(new Node(b,fitnessEvaluation(b))); // get boardState after making the move
            }
        }
    }
    
    
    
    public void construct(int argPly, Node cur){
        if(argPly == this.ply){
            return;
        }
        
       
        
    }
    
    // counts the pieces on the board that belong to the AI player
    private double fitnessEvaluation(Piece[][] b){
        double count = 0;
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(!b[i][j].team){ // belongs to the AI player
                    count++;
                }
            }
        }
        
        return count;
    }
    
    
    // fetches a list of which pieces on the board belong to the player
    // (used to know which pieces to make successors from)
    private ArrayList<int[]> findPieces(Piece[][] board){
        ArrayList<int[]> piecesList = new ArrayList();
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(!board[i][j].team){ // belongs to the AI player
                    piecesList.add(new int[]{j,i});
                }
            }
        }
        
        return piecesList;
    }
    
    /*
    private int alphaBetaMax( int alpha, int beta, int depthleft ) {
        if ( depthleft == 0 ){
            return evaluate();
        }
        for ( all moves) {
           score = alphaBetaMin( alpha, beta, depthleft - 1 );
           if( score >= beta )
              return beta;   // fail hard beta-cutoff
           if( score > alpha )
              alpha = score; // alpha acts like max in MiniMax
        }
        return alpha;
     }

     private int alphaBetaMin( int alpha, int beta, int depthleft ) {
        if ( depthleft == 0 ) return -evaluate();
        for ( all moves) {
           score = alphaBetaMax( alpha, beta, depthleft - 1 );
           if( score <= alpha )
              return alpha; // fail hard alpha-cutoff
           if( score < beta )
              beta = score; // beta acts like min in MiniMax
        }
        return beta;
     }
    
    */
}
