package chess;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Piece {

    boolean colour; // colour of the piece 
    boolean team; // whether owning player is human or AI
    boolean moved; // whether the piece has moved or not
    
    
    
    String type;
    public JLabel image;
    

    public Piece(boolean colour, boolean team, String t, boolean m) {

        //System.out.println(colour);
        //System.out.println(team);
        //System.out.println(t);
        
        this.colour= colour;
        this.team = team;
        type = t;
        moved = m; // whether the piece has been moved
                String imagePath;
        if (colour) {
            imagePath = "/image/".concat(type + "-white.gif");
        } else {
            imagePath = "/image/".concat(type + "-black.gif");
        }
        try{
        BufferedImage myPicture = ImageIO.read(this.getClass().getResource(imagePath));
        myPicture = resizeImage(myPicture);
        image = new JLabel(new ImageIcon(myPicture));
        }
        catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
        image.setVisible(true);
    }
    
    public Piece(boolean colour, boolean team, String t, boolean m, boolean doesntMatter) {
        this.colour = colour;
        this.team = team;
        type = t;
        moved = m; // whether the piece has been moved
    }
    
    // returns a newly created exact duplicate of this object
    public Piece copy(){
        Piece p = new Piece(this.colour,this.team,this.type,this.moved);
        return p;
    }
    
    //returns a piece without the image
    public Piece copyAI(){
        Piece p = new Piece(this.colour,this.team,this.type,this.moved, true);
        return p;
    }
    
    //changes a pawn to new piece
    public void promote(String type){
        this.type = type;
        String imagePath;
        if (colour) {
            imagePath = "/image/".concat(type + "-white.gif");
        } else {
            imagePath = "/image/".concat(type + "-black.gif");
        }
        try {
            BufferedImage myPicture = ImageIO.read(this.getClass().getResource(imagePath));
            myPicture = resizeImage(myPicture);
            image = new JLabel(new ImageIcon(myPicture));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        image.setVisible(true);
    }
    
    public static BufferedImage resizeImage(BufferedImage image) {
        BufferedImage buffedImg = new BufferedImage(40, 40, BufferedImage.TRANSLUCENT);
        Graphics2D twoDGraphs = (Graphics2D) buffedImg.createGraphics();
        twoDGraphs.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        twoDGraphs.drawImage(image, 0, 0, 40, 40, null);
        twoDGraphs.dispose();
        return buffedImg;
    }
}
