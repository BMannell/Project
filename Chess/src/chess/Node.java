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
    
    Piece[][] boardState; // board state for the node
    ArrayList<Node> children; // list of child nodes
    double fitness; // evaluated fitness of this board
    
    
    
    public Node(Piece[][] b, double f){
        children = null; // initialize as empty
        fitness = f; // set fitness
        
        // copy board state of parameter board
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                boardState[i][j] = b[i][j].copy();
            }
        }
    }
}
