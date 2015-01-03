/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

/**
 *
 * @author Ben
 */
public class Move {

    //Piece[][] boardState; //boardstate

    Piece piece;          //piece that is moving

    int oldX;                // old x position
    int oldY;                // old y position
    
    int newX;                // new x position
    int newY;                // new y position
    
    public Move(){
        
    }
    public Move(Piece p, int oX, int oY, int nX, int nY) {
        //boardState = board;
        
        piece = p;
        this.oldX = oX;
        this.oldY = oY;

        this.newX = nX;
        this.newY = nY;
    }
}
