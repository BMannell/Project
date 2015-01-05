package chess;

import java.util.ArrayList;

public class Engine {
   
    // fetches a list of which pieces on the board belong to the player specified
    // (used to know which pieces to make successors from)
    public ArrayList<int[]> getPieces(Piece[][] board, boolean team){
        ArrayList<int[]> piecesList = new ArrayList();
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(team = board[j][i].team){ // belongs to the player
                    piecesList.add(new int[]{j,i});
                }
            }
        }
        
        return piecesList;
    }
    
    public ArrayList<int[]> getMoves(Piece[][] state,int y, int x, boolean team, boolean inCheck){
        
        ArrayList<int[]> availMoves = new ArrayList<int[]>();
        Piece p = state[y][x]; // pointer to the piece needing moves
        Piece[][] boardToCheckForCheck; // will be checked for "check" later
        int checkCheckResult; // result of checking result board for check
        
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
                if((x-1 >= 0 && x-1 <= 7 && y+(1*direction) >= 0 && y+(1*direction) <= 7 ) && (state[y+(1*direction)][x-1] != null && state[y+(1*direction)][x-1].team != p.team)){ // diagonally ahead to the left not empty and piece there is not same team
                   availMoves.add(new int[]{y+(1*direction),x-1});
                }
                if((x+1 >= 0 && x+1 <= 7 && y+(1*direction) >= 0 && y+(1*direction) <= 7 ) && (state[y+(1*direction)][x+1] != null && state[y+(1*direction)][x+1].team != p.team)){ // diagonally ahead to the left not empty and piece there is not same team
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
                for(int iX = x-1, iY = y-1; iX >= 0 && iY <= 7 && iY >= 0 && iX <= 7; iX--, iY--){
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
                for(int iX = x-1, iY = y+1; iX >= 0 && iY <= 7 && iY >= 0 && iX <= 7; iX--, iY++){
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
                for(int iX = x+1, iY = y+1; iX >= 0 && iY <= 7 && iY >= 0 && iX <= 7; iX++, iY++){
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
                for(int iX = x+1, iY = y-1; iX >= 0 && iY <= 7 && iY >= 0 && iX <= 7; iX++, iY--){
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
                
                
                if((x-2 >= 0 && x-2 <= 7 && y+1 >= 0 && y+1 <= 7 ) && (state[y+1][x-2] == null || state[y+1][x-2].team != p.team)){ // left 2 up 1
                        availMoves.add(new int[]{y+1,x-2});
                }
                if((x-1 >= 0 && x-1 <= 7 && y+2 >= 0 && y+2 <= 7 ) && (state[y+2][x-1] == null || state[y+2][x-1].team != p.team)){ // left 1 up 2
                        availMoves.add(new int[]{y+2,x-1});
                }
                if((x+1 >= 0 && x+1 <= 7 && y+2 >= 0 && y+2 <= 7 ) && (state[y+2][x+1] == null || state[y+2][x+1].team != p.team)){ // right 1 up 2 
                        availMoves.add(new int[]{y+2,x+1});
                }
                if((x+2 >= 0 && x+2 <= 7 && y+1 >= 0 && y+1 <= 7 ) && (state[y+1][x+2] == null || state[y+1][x+2].team != p.team)){ // right 2 up 1
                        availMoves.add(new int[]{y+1,x+2});
                }
                if((x+2 >= 0 && x+2 <= 7 && y-1 >= 0 && y-1 <= 7 ) && (state[y-1][x+2] == null || state[y-1][x+2].team != p.team)){ // right 2 down 1
                        availMoves.add(new int[]{y-1,x+2});
                }
                if((x+1 >= 0 && x+1 <= 7 && y-2 >= 0 && y-2 <= 7 ) && (state[y-2][x+1] == null || state[y-2][x+1].team != p.team)){ // right 1 down 2
                        availMoves.add(new int[]{y-2,x+1});
                }
                if((x-1 >= 0 && x-1 <= 7 && y-2 >= 0 && y-2 <= 7 ) && (state[y-2][x-1] == null || state[y-2][x-1].team != p.team)){ // left 1 down 2
                        availMoves.add(new int[]{y-2,x-1});
                }
                if((x-2 >= 0 && x-2 <= 7 && y-1 >= 0 && y-1 <= 7 ) && (state[y-1][x-2] == null || state[y-1][x-2].team != p.team)){ // left 2 down 1
                        availMoves.add(new int[]{y-1,x-2});
                }
                
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
                for(int iX = x-1, iY = y-1; iX >= 0 && iY <= 7 && iY >= 0 && iX <= 7; iX--, iY--){
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
                for(int iX = x-1, iY = y+1; iX >= 0 && iY <= 7 && iY >= 0 && iX <= 7; iX--, iY++){
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
                for(int iX = x+1, iY = y+1; iX >= 0 && iY <= 7 && iY >= 0 && iX <= 7; iX++, iY++){
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
                for(int iX = x+1, iY = y-1; iX >= 0 && iY <= 7 && iY >= 0 && iX <= 7; iX++, iY--){
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
                
                if((x-1 >= 0 && x-1 <= 7 && y >= 0 && y <= 7 ) && (state[y][x-1] == null || state[y][x-1].team != p.team)){ // to the left
                    availMoves.add(new int[]{y,x-1});
                }
                else if((x-1 >= 0 && x-1 <= 7 && y+1 >= 0 && y+1 <= 7 ) && (state[y+1][x-1] == null || state[y+1][x-1].team != p.team)){ // up and to the left
                    availMoves.add(new int[]{y+1,x-1});
                }
                if((x >= 0 && x <= 7 && y+1 >= 0 && y+1 <= 7 ) && (state[y+1][x] == null || state[y+1][x].team != p.team)){ // up
                    availMoves.add(new int[]{y+1,x});
                }
                if((x+1 >= 0 && x+1 <= 7 && y+1 >= 0 && y+1 <= 7 ) && (state[y+1][x+1] == null || state[y+1][x+1].team != p.team)){ // up and to the right
                    availMoves.add(new int[]{y+1,x+1});
                }
                if((x+1 >= 0 && x+1 <= 7 && y >= 0 && y <= 7 ) && (state[y][x+1] == null || state[y][x+1].team != p.team)){ // to the right
                    availMoves.add(new int[]{y,x+1});
                }
                if((x+1 >= 0 && x+1 <= 7 && y-1 >= 0 && y-1 <= 7 ) && (state[y-1][x+1] == null || state[y-1][x+1].team != p.team)){ // down and to the right
                    availMoves.add(new int[]{y-1,x+1});
                }
                if((x >= 0 && x <= 7 && y-1 >= 0 && y-1 <= 7 ) && (state[y-1][x] == null || state[y-1][x].team != p.team)){ // down
                    availMoves.add(new int[]{y-1,x});
                }
                if((x-1 >= 0 && x-1 <= 7 && y-1 >= 0 && y-1 <= 7 ) && (state[y-1][x-1] == null || state[y-1][x-1].team != p.team)){ // down and to the left
                    availMoves.add(new int[]{y-1,x-1});
                }
                
                /* Requires castling check still */
                break;
        }
        
        // code for disallowing moving into check (check always now)
        /*if(inCheck){ // if in check*/
            // for each move yielded by availMoves
            // traverse move ArrayList in reverse so that removing doesn't change index of items iterated after the removed
            for(int i = availMoves.size()-1; i >= 0; i--){ 
                // check to see if executing the move results in check
                boardToCheckForCheck = getBoardAfterMove(state, new Move(y, x, availMoves.get(i)[0], availMoves.get(i)[1]));
                
                checkCheckResult = stateCheck(boardToCheckForCheck,team);
                if(checkCheckResult != 0){
                    // if so then remove from available moves
                    availMoves.remove(i);
                }
            }
            
        /*}*/
        return availMoves;
        
    }
    
    public Piece[][] getBoardAfterMove(Piece[][] b, Move m){
        // if piece has not been moved, it has now
        Piece[][] result = new Piece[8][8];
        
        // copy board state
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if (b[i][j] != null) {
                    result[i][j] = b[i][j].copy();
                }
            }
        }
        
        // make move on the result board
        result[m.newY][m.newX] = result[m.oldY][m.oldX]; // make the destination space the moved piece
        result[m.oldY][m.oldX] = null; // remove pointer reference for old spot
        
        return result;
    }
    
    //check if a player has been checked or checkmated
    public static int stateCheck(Piece[][] state, boolean team){
        int level = 0;
        
        ArrayList<int[]> enemyPositions = new ArrayList();
        ArrayList<int[]> availEnemyMoves = new ArrayList();
        ArrayList<ArrayList> enemyRoutes = new ArrayList();
        
        ArrayList<int[]> availFriendlyMoves = new ArrayList();
        
        int[] goodKingLeo = new int[]{0,0};
        ArrayList<int[]> availKingMoves = new ArrayList();
        
        /* Check */
        
        //find king
        for (int y = 0; y < 8; y++) {  //loop through every square on board to get pieces
            for (int x = 0; x < 8; x++) {
                if (state[y][x] != null && state[y][x].team == team && state[y][x].type.equals("king")) {    //if the unit is the good king
                    System.out.println("Found the king!");
                    goodKingLeo = new int[]{y, x};
                    availKingMoves = getMoves(state, y, x);
                    System.out.println(availKingMoves.size());
                }
            }
        }
        
        for(int y = 0; y <8; y++){  //loop through every square on board to get pieces
            for(int x = 0; x<8; x++){
                if(state[y][x] != null){    //if there's a piece there
                    if(state[y][x].team == team){ //friendly units
                        ArrayList<int[]> moves = getMoves(state, y, x); //get moves of current piece
                        for (int[] newMove : moves) {  //loop through new moves
                            boolean alreadyIn = false; //if move is already in 
                            for (int[] oldMove : availFriendlyMoves) { //loop through old moves
                                if (newMove == oldMove) { //check if moves of already there
                                    alreadyIn = true;   //change inAlready boolean
                                    break;              //move onto next piece
                                }
                            }
                            if (!alreadyIn) { //if move does not exist add it
                                availFriendlyMoves.add(newMove);
                            }
                        }
                    }else{
                        System.out.println("adding enemy moves");
                        ArrayList<int[]> moves = getMoves(state, y, x); //get moves of current piece
                        for(int[] newMove: moves){
                            boolean add = true;
                            for(int[] oldMove: availEnemyMoves){
                                if(newMove == oldMove){
                                    add = false;
                                    break;
                                }
                            }
                            if(add){
                                availEnemyMoves.add(newMove);
                            }
                        }
                        
                        if(moveListContains(moves, goodKingLeo)){   //if the piece is checking king add it to the list
                            System.out.println("adding kill route");
                            moves.add(0, new int[]{y, x});
                            ArrayList routes = getRouteToKing(moves, state[y][x].type, goodKingLeo);
                            enemyRoutes.add(routes);
                            level = 1;
                        }
                    }
                }
            }
        }
        
        if(level == 1){
           
            // check if king can move
            boolean canMove = false;
            //for(int[] aem: availEnemyMoves){
            //    System.out.println(aem[0] + ":" + aem[1]);
            //}
            
            for(int[] kMove: availKingMoves){
                if(!moveListContains(availEnemyMoves, kMove)){
                    canMove = true;
                    break;
                }
            }
            if(!canMove){ //if it cant move 
                if(enemyRoutes.size() >= 2){ //there are more than 2 pieces attacking -- checkmate
                    level = 2;
                }else{  //if a allied piece can take out or block
                    boolean canBlock = false;
                    ArrayList<int []> route = enemyRoutes.get(0);
                    for(int[] move: route){
                        if(moveListContains(availFriendlyMoves,move)){
                            canBlock = true;
                            break;
                        }
                    }
                    if(!canBlock){
                        level = 2;
                    }
                }
            }
        }
        
        /* Checkmate */
        
        return level;
    }
    
    private static boolean moveListContains(ArrayList<int[]> moves, int[] pos){
        boolean result = false;
        for(int[] move: moves){
            if(move[0] == pos[0] && move[1] == pos[1]){
                result = true;
                break;
            }
        }
        return result;
    }
    
    //get routes of all pieces that can attack king
    private static ArrayList<int[]> getRouteToKing(ArrayList<int[]> moves, String type, int[] king){
        ArrayList<int[]> route = new ArrayList();
        int[] origin = moves.get(0);
        route.add(origin);
           
        if (!type.equals("pawn") && !type.equals("knight")) {
            //if attacking along horizontal
            if (origin[0] == king[0]) {
                if (origin[1] < king[1]) {    //if rook is left
                    for (int[] move : moves) {
                        if (move[1] < king[1] && move[0] == king[0]) {
                            route.add(move);    //add all mvoes leading to king
                        }
                    }
                } else {  //if rook is right
                    for (int[] move : moves) {
                        if (move[1] > king[1] && move[0] == king[0]) {
                            route.add(move);    //add all moves leading to king
                        }
                    }
                }
            } else if (origin[1] == king[1]) {  //if rook is attacking from vertical
                if (origin[0] < king[0]) {    //if rook is above
                    for (int[] move : moves) {
                        if (move[0] < king[0] && move[1] == king[1]) {
                            route.add(move);    //add all mvoes leading to king
                        }
                    }
                } else {  //if rook is below
                    for (int[] move : moves) {
                        if (move[0] > king[0] && move[1] == king[1]) {
                            route.add(move);    //add all moves leading to king
                        }
                    }
                }
            } else if (origin[0] < king[0]) { //attacking diagonal from above
                int temp_x= origin[1];
                int temp_y=origin[0];
                if(origin[1] < king[1]){ //from upper left
                    temp_x++;
                    temp_y++;
                    do{
                        route.add(new int[]{temp_y++, temp_x++});
                    }while(temp_x != king[1] && temp_y != king[0]);
                    
                }else{  //from upper right
                    temp_x--;
                    temp_y++;
                    do{
                        route.add(new int[]{temp_y++, temp_x--});
                    }while(temp_x != king[1] && temp_y != king[0]);
                }
            }else{
                
                int temp_x= origin[1];
                int temp_y=origin[0];
                if(origin[1] < king[1]){ //from lower left
                    temp_x++;
                    temp_y--;
                    do{
                        route.add(new int[]{temp_y--, temp_x++});
                    }while(temp_x != king[1] && temp_y != king[0]);
                    
                }else{  //from lower right
                    temp_x--;
                    temp_y--;
                    do{
                        route.add(new int[]{temp_y--, temp_x--});
                    }while(temp_x != king[1] && temp_y != king[0]);
                }
            }
        }
        return route;
    }
}
