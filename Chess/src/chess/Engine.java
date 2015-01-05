package chess;

import java.util.ArrayList;

public class Engine {
   
    // fetches a list of which pieces on the board belong to the player specified
    // (used to know which pieces to make successors from)

    public static ArrayList<int[]> getPieces(Piece[][] board, boolean team ){
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
    

    public static ArrayList<int[]> getMoves(Piece[][] state, int y, int x){
        return getMoves(state,y,x,false);
    }
    
    public static ArrayList<int[]> getMoves(Piece[][] state,int y, int x, boolean underCheck){

        
        ArrayList<int[]> availMoves = new ArrayList<int[]>(), blockingRoutes = new ArrayList();
        Piece p = state[y][x]; // pointer to the piece needing moves
        
        int[] kingPos = new int[]{-1,-1};
        //find king
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (state[i][j] != null && state[i][j].team == p.team && state[i][j].type.equals("king")) {
                    //System.out.println("king " + i + ":" + j);
                    kingPos = new int[]{i, j};
                    break;
                }
            }
        }
        
        //get moves to stay out of check
        if(p.type != "king"){
            blockingRoutes = getKingLine(state,y,x,kingPos);
        }
        
        //get available moves for each piece
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
                if((x-1 >= 0 && x-1 <= 7 && y+1 >= 0 && y+1 <= 7 ) && (state[y+1][x-1] == null || state[y+1][x-1].team != p.team)){ // up and to the left
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
                
                ArrayList newList = new ArrayList();
                for(int[] move:availMoves)
                    if(checkKingMove(state, move[0], move[1], y ,x))
                        newList.add(move);
                availMoves = newList;
                
                /* Requires castling check still */
                break;
        }
        
        //get moves if king is under check
        if(!underCheck && !p.type.equals("king")){
            
            //find all enemy pieces that can attack king
            ArrayList<int[]> enemyRoute = new ArrayList();
            for (int i =0;i<8;i++) {
                for (int j = 0; j < 8;j++) {
                    if (state[i][j] != null && state[i][j].team != p.team) {
                        ArrayList<int[]> moves = getMoves(state, i, j); //get moves of current piece
                        if (moveListContains(moves, kingPos)) {   //if the piece is checking king add it to the list
                            if(!enemyRoute.isEmpty()){ //if another piece checking king too
                                enemyRoute = new ArrayList();  //this piece cannot move
                                break;
                            }
                            moves.add(0, new int[]{i, j});
                            ArrayList<int[]> route = getRouteToKing(moves, state[i][j].type, kingPos);
                            enemyRoute.addAll(route);
                        }
                    }
                }
            }
            //intersect the possible moves and the checking pieces
            ArrayList<int[]> newList = new ArrayList();
            for (int[] move : availMoves) {
                if (moveListContains(enemyRoute, move)) {
                    newList.add(move);
                }
            }
            availMoves = newList;
        }
        
        if (!availMoves.isEmpty() && !blockingRoutes.isEmpty()) {
            ArrayList<int[]> newList = new ArrayList();
            for (int[] move: availMoves) {
                if (moveListContains(blockingRoutes, move)) {
                    newList.add(move);
                }
            }
            availMoves = newList;
        }
        
        return availMoves;
    }
    
    private static boolean checkKingMove(Piece[][] state, int newY, int newX, int oldY, int oldX){
        state[newY][newX] = state[oldY][oldX];
        state[oldY][oldX] = null;
        ArrayList<int[]> moves = new ArrayList();
        boolean safe = true;
        for(int i = 0; i<8;i++){
            for(int j = 0; j < 8; j++){
                if(state[i][j] != null && state[i][j].team != state[newY][newX].team)
                    moves.addAll(getMoves(state,i,j));
            }
        }
        if( moveListContains(moves, new int[]{newY,newX}))
            safe = false;
        state[oldY][oldX] = state[newY][newX];
        state[newY][newX] = null;
        return safe;
    }
    private static ArrayList<int[]> getKingLine(Piece[][] state, int y, int x, int[] kingPos){
        Piece piece = state[y][x];
        boolean danger = false;
        ArrayList<int[]> inlineRoute = new ArrayList();
        
        
        int diffY = y - kingPos[0];
        int diffX = x - kingPos[1];
        
        //iterate through lines of sight
        
        /* vertical */
        if( x == kingPos[1]){
            System.out.println("Vertical");
            /* above */
            if (y < kingPos[0]) {
                System.out.println("above");
                for (int i = kingPos[0] - 1; i >= 0; i--) {
                    if (state[i][x] != null && i != y) {
                        if (i < y) {
                            System.out.println("above2");
                            if (!state[i][x].team && (state[i][x].type.equals("rook") || state[i][x].type.equals("queen"))) { //if rook or queen inbetween
                                inlineRoute.add(new int[]{i, x});    //can attack that piece
                                danger = true;
                            }
                            break;

                        } else{                            
                            if (!state[i][x].team && (state[i][x].type.equals("rook") || state[i][x].type.equals("queen"))) { //if rook or queen 
                                inlineRoute = new ArrayList();
                                inlineRoute.add(new int[]{i, x});    //can  only attack that piece
                                danger = true;
                            }
                            break;
                        }
                    }
                    inlineRoute.add(new int[]{i,x});
                }
                if(!danger)
                    inlineRoute = new ArrayList(); //no pieces inline
            }
            /* below */
            else if (y > kingPos[0]) {
                for (int i = kingPos[0] + 1; i < 8; i++) {
                    if (i > y) {    //in between
                        if (state[i][x] != null) { //piece inbetween
                            if (!state[i][x].team && (state[i][x].type.equals("rook") || state[i][x].type.equals("queen"))) { //if rook or queen inbetween
                                inlineRoute = new ArrayList();
                                inlineRoute.add(new int[]{i, x});    //can only attack that piece
                                danger = true;
                            }
                            break;
                        }
                    } else if (i < y) {
                        if (state[i][x] != null) {    //first piece
                            if (!state[i][x].team && (state[i][x].type.equals("rook") || state[i][x].type.equals("queen"))) { //if rook or queen 
                                inlineRoute.add(new int[]{i, x});    //can attack that piece
                                danger = true;
                            }
                            break;
                        }
                    }
                }
                if(!danger)
                    inlineRoute = new ArrayList(); //no pieces inline
            }
        }
        
        /* horizontal */
        else if (y == kingPos[0]) {
            
            /* left */
            if (x < kingPos[1]) {
                for (int i = kingPos[1] - 1; i >= 0; i--) {
                    if (state[y][i] != null && i != x) {
                        if (i > x) {
                            if (!state[y][i].team && (state[y][i].type.equals("rook") || state[y][i].type.equals("queen"))) { //if rook or queen inbetween
                                inlineRoute = new ArrayList();
                                inlineRoute.add(new int[]{y, i});    //can only attack that piece
                                danger = true;
                            }
                            break;
                        } else if (i < x) {
                            //first piece
                            if (!state[y][i].team && (state[y][i].type.equals("rook") || state[y][i].type.equals("queen"))) { //if rook or queen 
                                inlineRoute.add(new int[]{y, i});    //can attack that piece
                                danger = true;
                            }
                            break;

                        }
                        
                    }else inlineRoute.add(new int[]{y, i});    //piece not there
                }
                if(!danger)
                    inlineRoute = new ArrayList(); //no pieces inline
            }
            
            /* right */ 
            
            else if (x > kingPos[1]) {
                for (int i = kingPos[1] + 1; i< 8; i++) {
                    if (state[y][i] != null && i != x) {
                        if (i < x) {
                            if (!state[y][i].team && (state[y][i].type.equals("rook") || state[y][i].type.equals("queen"))) { //if rook or queen inbetween
                                inlineRoute = new ArrayList();
                                inlineRoute.add(new int[]{y, i});    //can only attack that piece
                                danger = true;
                            }
                            break;
                        } else if (i > x) {
                            //first piece
                            if (!state[y][i].team && (state[y][i].type.equals("rook") || state[y][i].type.equals("queen"))) { //if rook or queen 
                                inlineRoute.add(new int[]{y, i});    //can attack that piece
                                danger = true;
                            }
                            break;

                        }
                        
                    }else inlineRoute.add(new int[]{y, i});    //piece not there
                }
                if(!danger)
                    inlineRoute = new ArrayList(); //no pieces inline
            }
        }

        /* upper left or lower right */
        
        else if(diffY == diffX){
            
            /* upper left */
            
            if (diffY < 0) {

                for (int i = kingPos[0] - 1, j = kingPos[1] - 1; i >= 0 && j >= 0; i--, j--) {  //move along diag
                    if (state[i][j] != null && i != y) {
                        if (i > y) {
                            //inbetween
                            if (!state[i][j].team && (state[i][j].type.equals("rook") || state[i][j].type.equals("queen"))) { //if rook or queen inbetween
                                inlineRoute = new ArrayList();
                                inlineRoute.add(new int[]{i, j});    //can only attack that piece
                                danger = true;
                            }
                            break;
                        } else if (i < y) {
                            //first piece beyond
                            if (!state[i][j].team && (state[i][j].type.equals("rook") || state[i][j].type.equals("queen"))) { //if rook or queen 
                                inlineRoute.add(new int[]{i, j});    //can attack that piece
                                danger = true;
                            }
                            break;
                        }
                    } else {
                        inlineRoute.add(new int[]{i, j});    //piece not there
                    }
                }
                if(!danger)
                    inlineRoute = new ArrayList(); //no pieces inline
            }else{  //lower right
                for (int i = kingPos[0] + 1, j = kingPos[1] + 1; i < 8 && j < 8; i++, j++) {  //move along diag
                    if (state[i][j] != null && i != y) {
                        if (i < y) {
                            //inbetween
                            if (!state[i][j].team && (state[i][j].type.equals("rook") || state[i][j].type.equals("queen"))) { //if rook or queen inbetween
                                inlineRoute = new ArrayList();
                                inlineRoute.add(new int[]{i, j});    //can only attack that piece
                                danger = true;
                            }
                            break;
                        } else if (i > y) {
                            //first piece beyond
                            if (!state[i][j].team && (state[i][j].type.equals("rook") || state[i][j].type.equals("queen"))) { //if rook or queen 
                                inlineRoute.add(new int[]{i, j});    //can attack that piece
                                danger = true;
                            }
                            break;
                        }
                    } else {
                        inlineRoute.add(new int[]{i, j});    //piece not there
                    }
                }
                if(!danger)
                    inlineRoute = new ArrayList(); //no pieces inline
            }
                
                
            
        }
        
        
        else if(diffY == diffX*(-1)){// lower left and upper right 
            
            if (diffY < 0) {    //upper right
                for (int i = kingPos[0] - 1, j = kingPos[1] + 1; i >= 0 && j< 8; i--, j++) {  //move along diag
                    if (state[i][j] != null && i != y) {
                        if (i > y) {
                            //inbetween
                            if (!state[i][j].team && (state[i][j].type.equals("rook") || state[i][j].type.equals("queen"))) { //if rook or queen inbetween
                                inlineRoute = new ArrayList();
                                inlineRoute.add(new int[]{i, j});    //can only attack that piece
                                danger = true;
                            }
                            break;
                        } else if (i < y) {
                            //first piece beyond
                            if (!state[i][j].team && (state[i][j].type.equals("rook") || state[i][j].type.equals("queen"))) { //if rook or queen 
                                inlineRoute.add(new int[]{i, j});    //can attack that piece
                                danger = true;
                            }
                            break;
                        }
                    } else {
                        inlineRoute.add(new int[]{i, j});    //piece not there
                    }
                }
                if (!danger) {
                    inlineRoute = new ArrayList(); //no pieces inline
                }
            } else {  //lower right
                for (int i = kingPos[0] + 1, j = kingPos[1] - 1; i < 8 && j >= 0; i++, j--) {  //move along diag
                    if (state[i][j] != null && i != y) {
                        if (i < y) {
                            //inbetween
                            if (!state[i][j].team && (state[i][j].type.equals("rook") || state[i][j].type.equals("queen"))) { //if rook or queen inbetween
                                inlineRoute = new ArrayList();
                                inlineRoute.add(new int[]{i, j});    //can only attack that piece
                                danger = true;
                            }
                            break;
                        } else if (i > y) {
                            //first piece beyond
                            if (!state[i][j].team && (state[i][j].type.equals("rook") || state[i][j].type.equals("queen"))) { //if rook or queen 
                                inlineRoute.add(new int[]{i, j});    //can attack that piece
                                danger = true;
                            }
                            break;
                        }
                    } else {
                        inlineRoute.add(new int[]{i, j});    //piece not there
                    }
                }
                if (!danger) {
                    inlineRoute = new ArrayList(); //no pieces inline
                }
            }
        }
        
        return inlineRoute;
    }
    
    public static Piece[][] getBoardAfterMove(Piece[][] b, Move m){
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
                    //System.out.println("Found the king!");
                    goodKingLeo = new int[]{y, x};
                    availKingMoves = getMoves(state, y, x);
                    //System.out.println(availKingMoves.size());
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
                        //System.out.println("adding enemy moves");
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
