package chess;


public class Move {

    int oldX;                // old x position
    int oldY;                // old y position
    
    int newX;                // new x position
    int newY;                // new y position
    
    public Move(){
        
    }
    public Move(int oX, int oY, int nX, int nY) {

        this.oldX = oX;
        this.oldY = oY;

        this.newX = nX;
        this.newY = nY;
    }
}
