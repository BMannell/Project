package chess;

import java.util.ArrayList;

public class Engine {
   
    public static ArrayList<int[]> getMoves(Piece[][] state,int x, int y){
        
        ArrayList<int[]> availMoves = new ArrayList<int[]>();
        Piece p = state[x][y]; // pointer to the piece needing moves
        
        // gets the game piece at the x and y coordinates and checks what type of piece it is as well as the colour
        // assuming the x and y coordinate scheme is correct
        switch (p.type) {
            case "pawn":
                int direction; // up or down depending on colour. Applied to any vertical positioning. only matters for pawn
        
                if(p.colour){ // assuming true means white
                    direction = 1; // up (y + movement*1 is upward movement)
                }
                else{
                    direction = -1; // down (y + movement*-1 is downward movement)
                }

                
                if(state[x][y+(1*direction)] == null){ // if square ahead is empty
                    availMoves.add(new int[]{x,y+(1*direction)});
                }
                else if(state[x-1][y+(1*direction)] != null && state[x+1][y+(1*direction)].colour != p.colour){ // diagonally ahead to the left not empty and piece there is not same colour
                   availMoves.add(new int[]{x-1,y+(1*direction)});
                }
                else if(state[x+1][y+(1*direction)] != null && state[x+1][y+(1*direction)].colour != p.colour){ // diagonally ahead to the left not empty and piece there is not same colour
                   availMoves.add(new int[]{x+1,y+(1*direction)});
                }
                break;
                
                
            case "rook":
                // loop in perpendicular directions adding empty spaces and the space of the first piece encountered if opposite colour
                // iX and iy are set to the x and y variables +/- 1 to prevent allowing providing moves that are no movement at all (not actually necessary? wouldnt be null nor an enemy piece)
                
                
                // to the left edge of the board
                for(int iX = x-1; iX >= 0; iX--){
                    if(state[iX][y] == null){ // if nothing there
                        availMoves.add(new int[]{iX,y});
                    }
                    else if(state[iX][y] != null && state[iX][y].colour != p.colour){ // if enemy piece there
                        availMoves.add(new int[]{iX,y});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // to the right edge of the board
                for(int iX = x+1; iX <= 7; iX++){
                    if(state[iX][y] == null){ // if nothing there
                        availMoves.add(new int[]{iX,y});
                    }
                    else if(state[iX][y] != null && state[iX][y].colour != p.colour){ // if enemy piece there
                        availMoves.add(new int[]{iX,y});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // to the bottom edge of the board
                for(int iY = y-1; iY >= 0; iY--){
                    if(state[x][iY] == null){ // if nothing there
                        availMoves.add(new int[]{x,iY});
                    }
                    else if(state[x][iY] != null && state[x][iY].colour != p.colour){ // if enemy piece there
                        availMoves.add(new int[]{x,iY});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // to the top edge of the board
                for(int iY = y+1; iY <= 7; iY++){
                    if(state[x][iY] == null){ // if nothing there
                        availMoves.add(new int[]{x,iY});
                    }
                    else if(state[x][iY] != null && state[x][iY].colour != p.colour){ // if enemy piece there
                        availMoves.add(new int[]{x,iY});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                
                break;
            case "bishop":
                // check all diagonal directions
                
                // to the bottom left edge of the board
                for(int iX = x-1, iY = y-1; iX >= 0 && iY >= 0; iX--, iY--){
                    if(state[iX][iY] == null){ // if nothing there
                        availMoves.add(new int[]{iX,iY});
                    }
                    else if(state[iX][iY] != null && state[iX][iY].colour != p.colour){ // if enemy piece there
                        availMoves.add(new int[]{iX,iY});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // to the top left edge of the board
                for(int iX = x-1, iY = y+1; iX >= 0 && iY <= 7; iX--, iY++){
                    if(state[iX][iY] == null){ // if nothing there
                        availMoves.add(new int[]{iX,iY});
                    }
                    else if(state[iX][iY] != null && state[iX][iY].colour != p.colour){ // if enemy piece there
                        availMoves.add(new int[]{iX,iY});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // to the top right edge of the board
                for(int iX = x+1, iY = y+1; iX <= 7 && iY <= 7; iX++, iY++){
                    if(state[iX][iY] == null){ // if nothing there
                        availMoves.add(new int[]{iX,iY});
                    }
                    else if(state[iX][iY] != null && state[iX][iY].colour != p.colour){ // if enemy piece there
                        availMoves.add(new int[]{iX,iY});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // to the bottom right edge of the board
                for(int iX = x+1, iY = y-1; iX >= 0 && iY <= 7; iX++, iY--){
                    if(state[iX][iY] == null){ // if nothing there
                        availMoves.add(new int[]{iX,iY});
                    }
                    else if(state[iX][iY] != null && state[iX][iY].colour != p.colour){ // if enemy piece there
                        availMoves.add(new int[]{iX,iY});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                break;
                
                
            case "knight":
                if(state[x-2][y+1] == null || state[x-2][y+1].colour != p.colour){ // left 2 up 1
                        availMoves.add(new int[]{x-2,y+1});
                }
                if(state[x-1][y+2] == null || state[x-1][y+2].colour != p.colour){ // left 1 up 2
                        availMoves.add(new int[]{x-1,y+2});
                }
                if(state[x+1][y+2] == null || state[x+1][y+2].colour != p.colour){ // right 1 up 2 
                        availMoves.add(new int[]{x+1,y+2});
                }
                if(state[x+2][y+1] == null || state[x+2][y+1].colour != p.colour){ // right 2 up 1
                        availMoves.add(new int[]{x+2,y+1});
                }
                if(state[x+2][y-1] == null || state[x+2][y-1].colour != p.colour){ // right 2 down 1
                        availMoves.add(new int[]{x+2,y-1});
                }
                if(state[x+1][y-2] == null || state[x+1][y-2].colour != p.colour){ // right 1 down 2
                        availMoves.add(new int[]{x+1,y-2});
                }
                if(state[x-1][y-2] == null || state[x-1][y-2].colour != p.colour){ // left 1 down 2
                        availMoves.add(new int[]{x-1,y-2});
                }
                if(state[x-2][y-1] == null || state[x-2][y-1].colour != p.colour){ // left 2 down 1
                        availMoves.add(new int[]{x-2,y-1});
                }
                
                break;
            case "queen":
                // combination of the rook and bishop move checks
                
                // --------- from rook -------------
                
                // to the left edge of the board
                for(int iX = x-1; iX >= 0; iX--){
                    if(state[iX][y] == null){ // if nothing there
                        availMoves.add(new int[]{iX,y});
                    }
                    else if(state[iX][y] != null && state[iX][y].colour != p.colour){ // if enemy piece there
                        availMoves.add(new int[]{iX,y});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // to the right edge of the board
                for(int iX = x+1; iX <= 7; iX++){
                    if(state[iX][y] == null){ // if nothing there
                        availMoves.add(new int[]{iX,y});
                    }
                    else if(state[iX][y] != null && state[iX][y].colour != p.colour){ // if enemy piece there
                        availMoves.add(new int[]{iX,y});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // to the bottom edge of the board
                for(int iY = y-1; iY >= 0; iY--){
                    if(state[x][iY] == null){ // if nothing there
                        availMoves.add(new int[]{x,iY});
                    }
                    else if(state[x][iY] != null && state[x][iY].colour != p.colour){ // if enemy piece there
                        availMoves.add(new int[]{x,iY});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // to the top edge of the board
                for(int iY = y+1; iY <= 7; iY++){
                    if(state[x][iY] == null){ // if nothing there
                        availMoves.add(new int[]{x,iY});
                    }
                    else if(state[x][iY] != null && state[x][iY].colour != p.colour){ // if enemy piece there
                        availMoves.add(new int[]{x,iY});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // --------- from bishop -------------
                
                // to the bottom left edge of the board
                for(int iX = x-1, iY = y-1; iX >= 0 && iY >= 0; iX--, iY--){
                    if(state[iX][iY] == null){ // if nothing there
                        availMoves.add(new int[]{iX,iY});
                    }
                    else if(state[iX][iY] != null && state[iX][iY].colour != p.colour){ // if enemy piece there
                        availMoves.add(new int[]{iX,iY});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // to the top left edge of the board
                for(int iX = x-1, iY = y+1; iX >= 0 && iY <= 7; iX--, iY++){
                    if(state[iX][iY] == null){ // if nothing there
                        availMoves.add(new int[]{iX,iY});
                    }
                    else if(state[iX][iY] != null && state[iX][iY].colour != p.colour){ // if enemy piece there
                        availMoves.add(new int[]{iX,iY});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // to the top right edge of the board
                for(int iX = x+1, iY = y+1; iX <= 7 && iY <= 7; iX++, iY++){
                    if(state[iX][iY] == null){ // if nothing there
                        availMoves.add(new int[]{iX,iY});
                    }
                    else if(state[iX][iY] != null && state[iX][iY].colour != p.colour){ // if enemy piece there
                        availMoves.add(new int[]{iX,iY});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                
                // to the bottom right edge of the board
                for(int iX = x+1, iY = y-1; iX >= 0 && iY <= 7; iX++, iY--){
                    if(state[iX][iY] == null){ // if nothing there
                        availMoves.add(new int[]{iX,iY});
                    }
                    else if(state[iX][iY] != null && state[iX][iY].colour != p.colour){ // if enemy piece there
                        availMoves.add(new int[]{iX,iY});
                        break; // break out of the loop checking to the edge of the board
                    }
                }
                break;
                
                
            case "king":
                // check the immediate squares around the king
                
                if(state[x-1][y] == null || state[x-1][y].colour != p.colour){ // to the left
                    availMoves.add(new int[]{x-1,y});
                }
                else if(state[x-1][y+1] == null || state[x-1][y+1].colour != p.colour){ // up and to the left
                    availMoves.add(new int[]{x-1,y+1});
                }
                if(state[x][y+1] == null || state[x][y+1].colour != p.colour){ // up
                    availMoves.add(new int[]{x,y+1});
                }
                if(state[x+1][y+1] == null || state[x+1][y+1].colour != p.colour){ // up and to the right
                    availMoves.add(new int[]{x+1,y+1});
                }
                if(state[x+1][y] == null || state[x+1][y].colour != p.colour){ // to the right
                    availMoves.add(new int[]{x+1,y});
                }
                if(state[x+1][y-1] == null || state[x+1][y-1].colour != p.colour){ // down and to the right
                    availMoves.add(new int[]{x+1,y-1});
                }
                if(state[x][y-1] == null || state[x][y-1].colour != p.colour){ // down
                    availMoves.add(new int[]{x,y-1});
                }
                if(state[x-1][y-1] == null || state[x-1][y-1].colour != p.colour){ // down and to the left
                    availMoves.add(new int[]{x-1,y-1});
                }
                
                /* Requires castling check still */
                break;
        }
        
        
        return availMoves;
        
        // just a default return for now
        //return new int[][]{{3,0}};
    }
    
    public static Piece[][] makeMove(Move m){
        // if piece has not been moved, it has now
        
        
        
        return null;
    }
    
    public static boolean stateCheck(Piece[][] p){
        return true;
    }
}
