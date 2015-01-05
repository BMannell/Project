package chess;

import java.util.ArrayList;


public class AI {
    int ply; // number of plys to consider
    Node minimax; // minimax tree head pointer
    
    
    public AI(int p){
        minimax = null; // set to null
        ply = p; // set number of plys to use
    }
    
    
    public Move takeTurn(Piece[][] currentBoardState){
        Move myMove;
        constructTree(0, minimax, false);
        
        return null;
    }
    
    
    /*
    recursive function to construct the tree
    
    parameters:
    - the number of plies moved
    down so far
    - current node
    - team making the moves
    */
    public void constructTree(int argPly, Node cur, boolean t){
        if(argPly == this.ply){
            return;
        }
        ArrayList<int[]> pieces = Engine.getPieces(cur.boardState, t);
        ArrayList<int[]> newPlacements; // the list of potential places to move a piece 
        Piece[][] boardFromMove = null; // board that results from executing a move
        boolean checked = false;
        
        for(int i = 0; i < pieces.size(); i++){ // for each piece owned on the board
            if(Engine.stateCheck(cur.boardState,t) != 0){
                checked = true;
            }
            else{
                checked = false;
            }
            newPlacements = Engine.getMoves(cur.boardState, pieces.get(i)[0],pieces.get(i)[1]); // get moves for the current piece
            
            for(int j = 0; j < newPlacements.size(); j++){ // fir each of the new possible placements of this piece
                // get the resulting board state
                boardFromMove = Engine.getBoardAfterMove(cur.boardState, new Move(pieces.get(i)[0], pieces.get(i)[1], newPlacements.get(j)[0], newPlacements.get(j)[1]));
                
                // and add it as a node to the current nodes list of children
                cur.children.add(new Node(boardFromMove, fitnessEvaluation(boardFromMove))); // get boardState after making the move
            }  
        }
        
        // once all children are in place, call this method on them for the opposite player and 1 ply deeper
        for(int i = 0; i < cur.children.size(); i++){ // for each child node
            constructTree(argPly+1,cur.children.get(i),!t);
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
