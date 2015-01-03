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

    Piece[][] boardState; //boardstate

    Piece piece;          //piece that is moving

    int x;                //new x position
    int y;                //new y position
    
    public Move(){
        
    }
    public Move(Piece[][] board, Piece p, int x, int y) {
        boardState = board;
        piece = p;
        this.x = x;
        this.y = y;
    }
}
