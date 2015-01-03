package chess;

public class Player {
    boolean isHuman;
    GUI gui;
    public Player(boolean type, GUI g){
        isHuman = type;
        gui = g;
    }
    
    public Move getMove(){
        return new Move();
    }
}
