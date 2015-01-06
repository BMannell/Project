/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chess;

import java.util.ArrayList;

/**
 *
 * @author Dylan
 */
public class Node {
    
    Move moveMade; // board state for the node
    ArrayList<Node> children; // list of child nodes
    double fitness; // evaluated fitness of this board
    
    public Node(Move m, double f){
        children = new ArrayList(); // initialize as empty
        fitness = f; // set fitness
        
        moveMade = new Move(m.oldY, m.oldX, m.newY, m.newX);
    }
    public Node(){
        children = new ArrayList(); // initialize as empty
        fitness = 0; // set fitness
        moveMade = null;
    }
}
