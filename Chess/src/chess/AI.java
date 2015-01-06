package chess;

import java.util.ArrayList;


public class AI {
    int ply; // number of plys to consider
    Node minimax; // minimax tree head pointer
    boolean inCheck; // boolean of whether the AI is in check
    
    public AI(int p){
        minimax = new Node(new Move(0,0,0,0), 0.0); // set to null
        ply = p; // set number of plys to use
    }
    
    
    public Move takeTurn(Piece[][] currentBoardState, boolean check){
        inCheck = check;
        Move myMove;
        constructTree(currentBoardState, 0, minimax, false);
        myMove = alphaBetaMax3Head(minimax, -999, 999, ply);
        return myMove;
    }
    
    
    /*
    recursive function to construct the tree
    
    parameters:
    - the number of plies moved
    down so far
    - current node
    - team making the moves
    */
    public void constructTree(Piece[][] board, int argPly, Node cur, boolean t){
        if(argPly == this.ply){
            return;
        }

        
        ArrayList<int[]> pieces = Engine.getPieces(board, t);

        ArrayList<int[]> newPlacements; // the list of potential places to move a piece 
        Move moveFromPiece = null; // a move extracted from the piece
        Piece[][] boardFromMoveFromPiece = null; // board that results from executing a move
        boolean checked = false;
        
        for(int i = 0; i < pieces.size(); i++){ // for each piece owned on the board

            if(Engine.stateCheck(board,t) != 0){
                checked = true;
            }
            else{
                checked = false;
            }
            newPlacements = Engine.getMoves(board, pieces.get(i)[0],pieces.get(i)[1], inCheck); // get moves for the current piece
            
            
            
            for(int j = 0; j < newPlacements.size(); j++){ // fir each of the new possible placements of this piece
                

                // get a move that the piece can make
                moveFromPiece = new Move(pieces.get(i)[0], pieces.get(i)[1], newPlacements.get(j)[0], newPlacements.get(j)[1]);
                
                // get the board that results from the move
                boardFromMoveFromPiece = Engine.getBoardAfterMove(board, moveFromPiece);
                
                
                // and add it as a node to the current nodes list of children
                cur.children.add(new Node(moveFromPiece, fitnessEvaluation(boardFromMoveFromPiece))); // get boardState after making the move
                
                
                
            }
        }
        
        // once all children are in place, call this method on them for the opposite player and 1 ply deeper
        for(int i = 0; i < cur.children.size(); i++){ // for each child node
            constructTree(boardFromMoveFromPiece, argPly+1,cur.children.get(i),!t);
        }        
    }
    
    
    // counts the pieces on the board that belong to the AI player
    private double fitnessEvaluation(Piece[][] b){
        double count = 0;
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(b[i][j] != null){
                    if(!b[i][j].team){ // belongs to the AI player
                        count++;
                    }
                    else{
                        count--;
                    }
                }
            }
        }
        
        return count;
    }
    
    /*
    private Move alphaBetaDecision(Node head){ // going to be the same as a max node for head
        Node choice = null;
        double result;
        double runningBest = -999;
        
        
        for(int i = 0; i < head.children.size(); i++){
            result = alphaBetaMin(head.children.get(i),runningBest,,ply);
            
            if(result > runningBest){
                runningBest = result;
                choice = head.children.get(i);
            }
            
            
            return choice.moveMade;
        }
        
    }
    */
    
    /*
    private Move alphaBetaDecision(Node n, double alpha, double beta, int depthleft ) {
        double score;
        
        if ( depthleft == 0 ){
            return n.moveMade;
        }
        for (int i = 0; i < n.children.size(); i++) {
           score = alphaBetaMin(n.children.get(i), alpha, beta, depthleft - 1 );
           if( score >= beta )
              return beta; // pruning
           if( score > alpha )
              alpha = score; // alpha acts like max in MiniMax
        }
        return alpha;
     }
    */
    
    
    
    
    
    
    

    
    /*
    private double alphaBetaMax(Node n, double alpha, double beta, int depthleft ) {
        double score;
        
=======
    /*
    private int alphaBetaMax( int alpha, int beta, int depthleft ) {
>>>>>>> inlineKing
        if ( depthleft == 0 ){
            return n.fitness;
        }
        for (int i = 0; i < n.children.size(); i++) {
           score = alphaBetaMin(n.children.get(i), alpha, beta, depthleft - 1 );
           if( score >= beta )
              return beta; // pruning
           if( score > alpha )
              alpha = score; // alpha acts like max in MiniMax
        }
        return alpha;
     }
    */
    
    /*
     private double alphaBetaMin(Node n, double alpha, double beta, int depthleft ) {
        double score;
         
         if ( depthleft == 0 ){
            return n.fitness;
        }
        for (int i = 0; i < n.children.size(); i++) {
           score = alphaBetaMin(n.children.get(i), alpha, beta, depthleft - 1 );
           if( score <= alpha )
              return alpha; // fail hard alpha-cutoff
           if( score < beta )
              beta = score; // beta acts like min in MiniMax
        }
        return beta;
     }
     */
     
     
    private double alphaBetaMax3(Node n, double alpha, double beta, int pliesLeft){
        double value;
        
        if(pliesLeft == 0){
            return n.fitness;
        }
        
        for(Node child: n.children){
            value = alphaBetaMin3(child, alpha, beta, pliesLeft-1);
            
            if( value >= beta ){ // biger than the minimum from above
              return value;
            }
            if( value > alpha ){ // bigger than above beta
              alpha = value;
            }
            
        }
        
        return alpha;
        
    } 
    private Move alphaBetaMax3Head(Node n, double alpha, double beta, int pliesLeft){
        double value;
        Node choice = null;
        
        if(pliesLeft == 0){
            return null;
        }
        
        for(Node child: n.children){
            value = alphaBetaMin3(child, alpha, beta, pliesLeft-1);
            
            if( value >= beta ){ // biger than the minimum from above
              return (new Move(child.moveMade.oldY, child.moveMade.oldX, child.moveMade.newY, child.moveMade.newX));
            }
            if( value > alpha ){ // bigger than above beta
              alpha = value;
              choice = child;
            }
        }
        
        return (new Move(choice.moveMade.oldY, choice.moveMade.oldX, choice.moveMade.newY, choice.moveMade.newX));
        
    }
    
    

    private double alphaBetaMin3(Node n, double alpha, double beta, int pliesLeft){
        double value;
        
        if(pliesLeft == 0){
            return n.fitness;
        }
        
        for(Node child: n.children){
            value = alphaBetaMax3(child, alpha, beta, pliesLeft-1);
            
            if( value <= alpha ){
              return alpha; // pruning
            }
           if( value < beta ){
              beta = value;
           }
            
        }
        
        return beta;
        
    }
    
    
    
     /*
     private double alphaBetaMinTwo(Node n, double alpha, double beta, int pliesLeft){
         double value;
         
         if(pliesLeft == 0){
             return n.fitness;
         }
         
         for(int i = 0; i < n.children.size(); i++){
             value = alphaBetaMaxTwo(n.children.get(i), alpha, beta, pliesLeft - 1 );
             
             if(value < beta){ // if the 
                 beta = value;
             }
         }
         return 0.0;
     }
     */
     
}
