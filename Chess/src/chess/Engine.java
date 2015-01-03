package chess;

import java.util.ArrayList;

public class Engine {
   
    public static ArrayList<int[]> getMoves(Piece[][] state,int y, int x){
        
        ArrayList<int[]> availMoves = new ArrayList<int[]>();
        Piece p = state[y][x]; // pointer to the piece needing moves
        
        // gets the game piece at the x and y coordinates and checks what type of piece it is as well as the team
        // assuming the x and y coordinate scheme is correct
        switch (p.type) {
            case "pawn":
                int direction; // up or down depending on team. Applied to any vertical positioning. only matters for pawn
        
                if(p.team){ // assuming true means white
                    direction = -1; // up (y + movement*-1 is upward movement)
                }
                else{
                    direction = 1; // down (y + movement*1 is downward movement)
                }

                

                if(state[y+(1*direction)][x] == null){ // if square ahead is empty
                    availMoves.add(new int[]{y+(1*direction),x});
                }
                if(state[y+(1*direction)][x-1] != null && state[y+(1*direction)][x-1].team != p.team){ // diagonally ahead to the left not empty and piece there is not same team
                   availMoves.add(new int[]{y+(1*direction),x-1});
                }
                if(state[y+(1*direction)][x+1] != null && state[y+(1*direction)][x+1].team != p.team){ // diagonally ahead to the left not empty and piece there is not same team
                   availMoves.add(new int[]{y+(1*direction),x+1});
                }
                
                break;
                
                
            case "rook":
                // loop in perpendicular directions adding empty spaces and the space of the first piece encountered if opposite team
                // iX and iy are set to the x and y variables +/- 1 to prevent allowing providing moves that are no movement at all (not actually necessary? wouldnt be null nor an enemy piece)
                
                
                // to the left edge of the board
                for(int iX = x-1; iX >= 0; iX--){
                    if(state[y][iX] == null){ // if nothing there
                        availMoves.add(new int[]{y,iX});
                    }
                    else if(state[y][iX] != null && state[y][iX].team == p.team){ // if ally piece there
                        break; // break out of the loop checking to the edge of the board
                    }
                    else if(state[y][iX] != null && state[y][iX].team != p.team){ // if enemy piece there
                        availMoves.add(new int[]{y,iX});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // to the right edge of the board
                for(int iX = x+1; iX <= 7; iX++){
                    if(state[y][iX] == null){ // if nothing there
                        availMoves.add(new int[]{y,iX});
                    }
                    else if(state[y][iX] != null && state[y][iX].team == p.team){ // if ally piece there
                        break; // break out of the loop checking to the edge of the board
                    }
                    else if(state[y][iX] != null && state[y][iX].team != p.team){ // if enemy piece there
                        availMoves.add(new int[]{y,iX});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // to the bottom edge of the board
                for(int iY = y-1; iY >= 0; iY--){
                    if(state[iY][x] == null){ // if nothing there
                        availMoves.add(new int[]{iY,x});
                    }
                    else if(state[iY][x] != null && state[iY][x].team == p.team){ // if ally piece there
                        break; // break out of the loop checking to the edge of the board
                    }
                    else if(state[iY][x] != null && state[iY][x].team != p.team){ // if enemy piece there
                        availMoves.add(new int[]{iY,x});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // to the top edge of the board
                for(int iY = y+1; iY <= 7; iY++){
                    if(state[iY][x] == null){ // if nothing there
                        availMoves.add(new int[]{iY,x});
                    }
                    else if(state[iY][x] != null && state[iY][x].team == p.team){ // if ally piece there
                        break; // break out of the loop checking to the edge of the board
                    }
                    else if(state[iY][x] != null && state[iY][x].team != p.team){ // if enemy piece there
                        availMoves.add(new int[]{iY,x});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                break;
                
                
            case "bishop":
                // check all diagonal directions
                
                // to the bottom left edge of the board
                for(int iX = x-1, iY = y-1; iX >= 0 && iY >= 0; iX--, iY--){
                    if(state[iY][iX] == null){ // if nothing there
                        availMoves.add(new int[]{iY,iX});
                    }
                    else if(state[iY][iX] != null && state[iY][iX].team == p.team){ // if ally piece there
                        break; // break out of the loop checking to the edge of the board
                    }
                    else if(state[iY][iX] != null && state[iY][iX].team != p.team){ // if enemy piece there
                        availMoves.add(new int[]{iY,iX});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // to the top left edge of the board
                for(int iX = x-1, iY = y+1; iX >= 0 && iY <= 7; iX--, iY++){
                    if(state[iY][iX] == null){ // if nothing there
                        availMoves.add(new int[]{iY,iX});
                    }
                    else if(state[iY][iX] != null && state[iY][iX].team == p.team){ // if ally piece there
                        break; // break out of the loop checking to the edge of the board
                    }
                    else if(state[iY][iX] != null && state[iY][iX].team != p.team){ // if enemy piece there
                        availMoves.add(new int[]{iY,iX});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // to the top right edge of the board
                for(int iX = x+1, iY = y+1; iX <= 7 && iY <= 7; iX++, iY++){
                    if(state[iY][iX] == null){ // if nothing there
                        availMoves.add(new int[]{iY,iX});
                    }
                    else if(state[iY][iX] != null && state[iY][iX].team == p.team){ // if ally piece there
                        break; // break out of the loop checking to the edge of the board
                    }
                    else if(state[iY][iX] != null && state[iY][iX].team != p.team){ // if enemy piece there
                        availMoves.add(new int[]{iY,iX});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // to the bottom right edge of the board
                for(int iX = x+1, iY = y-1; iX >= 0 && iY <= 7; iX++, iY--){
                    if(state[iY][iX] == null){ // if nothing there
                        availMoves.add(new int[]{iY,iX});
                    }
                    else if(state[iY][iX] != null && state[iY][iX].team == p.team){ // if ally piece there
                        break; // break out of the loop checking to the edge of the board
                    }
                    else if(state[iY][iX] != null && state[iY][iX].team != p.team){ // if enemy piece there
                        availMoves.add(new int[]{iY,iX});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                break;
                
                
            case "knight":
                
                /*
                if(state[y+1][x-2] == null || state[y+1][x-2].team != p.team){ // left 2 up 1
                        availMoves.add(new int[]{y+1,x-2});
                }
                if(state[y+2][x-1] == null || state[y+2][x-1].team != p.team){ // left 1 up 2
                        availMoves.add(new int[]{y+2,x-1});
                }
                if(state[y+2][x+1] == null || state[y+2][x+1].team != p.team){ // right 1 up 2 
                        availMoves.add(new int[]{y+2,x+1});
                }
                if(state[y+1][x+2] == null || state[y+1][x+2].team != p.team){ // right 2 up 1
                        availMoves.add(new int[]{y+1,x+2});
                }
                if(state[y-1][x+2] == null || state[y-1][x+2].team != p.team){ // right 2 down 1
                        availMoves.add(new int[]{y-1,x+2});
                }
                if(state[y-2][x+1] == null || state[y-2][x+1].team != p.team){ // right 1 down 2
                        availMoves.add(new int[]{y-2,x+1});
                }
                if(state[y-2][x-1] == null || state[y-2][x-1].team != p.team){ // left 1 down 2
                        availMoves.add(new int[]{y-2,x-1});
                }
                if(state[y-1][x-2] == null || state[y-1][x-2].team != p.team){ // left 2 down 1
                        availMoves.add(new int[]{y-1,x-2});
                }*/
                
                break;
                
                
            case "queen":
                // combination of the rook and bishop move checks
                
                // --------- from rook -------------
                
                // to the left edge of the board
                for(int iX = x-1; iX >= 0; iX--){
                    if(state[y][iX] == null){ // if nothing there
                        availMoves.add(new int[]{y,iX});
                    }
                    else if(state[y][iX] != null && state[y][iX].team == p.team){ // if ally piece there
                        break; // break out of the loop checking to the edge of the board
                    }
                    else if(state[y][iX] != null && state[y][iX].team != p.team){ // if enemy piece there
                        availMoves.add(new int[]{y,iX});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // to the right edge of the board
                for(int iX = x+1; iX <= 7; iX++){
                    if(state[y][iX] == null){ // if nothing there
                        availMoves.add(new int[]{y,iX});
                    }
                    else if(state[y][iX] != null && state[y][iX].team == p.team){ // if ally piece there
                        break; // break out of the loop checking to the edge of the board
                    }
                    else if(state[y][iX] != null && state[y][iX].team != p.team){ // if enemy piece there
                        availMoves.add(new int[]{y,iX});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // to the bottom edge of the board
                for(int iY = y-1; iY >= 0; iY--){
                    if(state[iY][x] == null){ // if nothing there
                        availMoves.add(new int[]{iY,x});
                    }
                    else if(state[iY][x] != null && state[iY][x].team == p.team){ // if ally piece there
                        break; // break out of the loop checking to the edge of the board
                    }
                    else if(state[iY][x] != null && state[iY][x].team != p.team){ // if enemy piece there
                        availMoves.add(new int[]{iY,x});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // to the top edge of the board
                for(int iY = y+1; iY <= 7; iY++){
                    if(state[iY][x] == null){ // if nothing there
                        availMoves.add(new int[]{iY,x});
                    }
                    else if(state[iY][x] != null && state[iY][x].team == p.team){ // if ally piece there
                        break; // break out of the loop checking to the edge of the board
                    }
                    else if(state[iY][x] != null && state[iY][x].team != p.team){ // if enemy piece there
                        availMoves.add(new int[]{iY,x});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // --------- from bishop -------------
                
                // to the bottom left edge of the board
                for(int iX = x-1, iY = y-1; iX >= 0 && iY >= 0; iX--, iY--){
                    if(state[iY][iX] == null){ // if nothing there
                        availMoves.add(new int[]{iY,iX});
                    }
                    else if(state[iY][iX] != null && state[iY][iX].team == p.team){ // if ally piece there
                        break; // break out of the loop checking to the edge of the board
                    }
                    else if(state[iY][iX] != null && state[iY][iX].team != p.team){ // if enemy piece there
                        availMoves.add(new int[]{iY,iX});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // to the top left edge of the board
                for(int iX = x-1, iY = y+1; iX >= 0 && iY <= 7; iX--, iY++){
                    if(state[iY][iX] == null){ // if nothing there
                        availMoves.add(new int[]{iY,iX});
                    }
                    else if(state[iY][iX] != null && state[iY][iX].team == p.team){ // if ally piece there
                        break; // break out of the loop checking to the edge of the board
                    }
                    else if(state[iY][iX] != null && state[iY][iX].team != p.team){ // if enemy piece there
                        availMoves.add(new int[]{iY,iX});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // to the top right edge of the board
                for(int iX = x+1, iY = y+1; iX <= 7 && iY <= 7; iX++, iY++){
                    if(state[iY][iX] == null){ // if nothing there
                        availMoves.add(new int[]{iY,iX});
                    }
                    else if(state[iY][iX] != null && state[iY][iX].team == p.team){ // if ally piece there
                        break; // break out of the loop checking to the edge of the board
                    }
                    else if(state[iY][iX] != null && state[iY][iX].team != p.team){ // if enemy piece there
                        availMoves.add(new int[]{iY,iX});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // to the bottom right edge of the board
                for(int iX = x+1, iY = y-1; iX >= 0 && iY <= 7; iX++, iY--){
                    if(state[iY][iX] == null){ // if nothing there
                        availMoves.add(new int[]{iY,iX});
                    }
                    else if(state[iY][iX] != null && state[iY][iX].team == p.team){ // if ally piece there
                        break; // break out of the loop checking to the edge of the board
                    }
                    else if(state[iY][iX] != null && state[iY][iX].team != p.team){ // if enemy piece there
                        availMoves.add(new int[]{iY,iX});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                break;
                
                
            case "king":
                // check the immediate squares around the king
                
                if(state[y][x-1] == null || state[y][x-1].team != p.team){ // to the left
                    availMoves.add(new int[]{x-1,y});
                }
                else if(state[y+1][x-1] == null || state[y+1][x-1].team != p.team){ // up and to the left
                    availMoves.add(new int[]{x-1,y+1});
                }
                if(state[y+1][x] == null || state[y+1][x].team != p.team){ // up
                    availMoves.add(new int[]{x,y+1});
                }
                if(state[y+1][x+1] == null || state[y+1][x+1].team != p.team){ // up and to the right
                    availMoves.add(new int[]{x+1,y+1});
                }
                if(state[y][x+1] == null || state[y][x+1].team != p.team){ // to the right
                    availMoves.add(new int[]{x+1,y});
                }
                if(state[y-1][x+1] == null || state[y-1][x+1].team != p.team){ // down and to the right
                    availMoves.add(new int[]{x+1,y-1});
                }
                if(state[y-1][x] == null || state[y-1][x].team != p.team){ // down
                    availMoves.add(new int[]{x,y-1});
                }
                if(state[y-1][x-1] == null || state[y-1][x-1].team != p.team){ // down and to the left
                    availMoves.add(new int[]{x-1,y-1});
                }
                
                /* Requires castling check still */
                break;
        }
        
        
        return availMoves;
        
        // just a default return for now
        //return new int[][]{{3,0}};
    }
    
    public static Piece[][] getBoardAfterMove(Piece[][] b, Move m){
        // if piece has not been moved, it has now
        Piece[][] result = new Piece[8][8];
        
        // copy board state
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                result[i][j] = b[i][j].copy();
            }
        }
        
        // make move on the result board
        result[m.newX][m.newY] = result[m.oldX][m.oldY]; // make the destination space the moved piece
        result[m.oldX][m.oldY] = null; // remove pointer reference for old spot
        
        return result;
    }
    
    public static boolean stateCheck(Piece[][] p){
        return true;
    }
}
