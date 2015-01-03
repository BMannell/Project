package chess;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Ben
 */
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
        myPicture = resize(myPicture,40,40);
        image = new JLabel(new ImageIcon(myPicture));
        }
        catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
        image.setVisible(true);
    }
    
    // returns a newly created exact duplicate of this object
    public Piece copy(){
        Piece p = new Piece(this.colour,this.team,this.type,this.moved);
        return p;
    }
    
    
    //stolen code need to find new way or cite
    public static BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return bi;
    }
}
