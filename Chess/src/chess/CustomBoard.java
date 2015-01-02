package chess;

import static chess.Piece.resize;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

public class CustomBoard extends JPanel{
    
    Square[][] board = new Square[8][8];
    Square[][] select = new Square[8][4];
    Square selectedPiece;
    boolean playerColour = true;
    boolean removePiece = false; //if the remove button is selected
    boolean addPiece = false;   //if piece has been selected to add
    JPanel remove;
    
    public CustomBoard(){
        setSize(512,512);
        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        add(createGameBoard());
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(createOptions());
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(createPieceSelect());
    }
    
    private JPanel createGameBoard(){
        boolean colour = true;
        JPanel boardPane = new JPanel(new GridLayout(8, 8));
        boardPane.setSize(new Dimension(512,512));
        boardPane.setPreferredSize(new Dimension(512,512));
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Square jp;
                if(colour){
                    jp = new Square(Color.white, x, y);
                }
                else{
                    jp = new Square(Color.black, x, y);
                }
                board[y][x] = jp;
                boardPane.add(jp);
                colour = !colour;
            }
            colour = !colour;
        }
        return boardPane;
    }
    
    private JPanel createOptions(){
        JPanel opts = new JPanel();
        opts.setLayout(new BoxLayout(opts, BoxLayout.Y_AXIS));
        opts.setVisible(true);
        opts.add(Box.createRigidArea(new Dimension(0,70)));
        //colour select for player
        JPanel pColor = new JPanel();
        pColor.setLayout(new BoxLayout(pColor, BoxLayout.Y_AXIS));
        pColor.setVisible(true);
        pColor.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //create border for radio colour select
        TitledBorder title = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), "Colour");
        title.setTitleJustification(TitledBorder.CENTER);
        pColor.setBorder(title);
        
        //create radio buttons
        JRadioButton whiteButton = new JRadioButton("White");
        whiteButton.setActionCommand("white");
        whiteButton.setSelected(true);
        JRadioButton blackButton = new JRadioButton("Black");
        blackButton.setActionCommand("black");
        
        //add action listener for the buttons
        ColourChanger changer = new ColourChanger();
        whiteButton.addActionListener(changer);
        blackButton.addActionListener(changer);
        
        //group radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(whiteButton);
        group.add(blackButton);
        
        //add buttons to panel
        pColor.add(whiteButton);
        pColor.add(blackButton);
        opts.add(pColor);
        opts.add(Box.createRigidArea(new Dimension(0,70)));
        
        /* remove a piece from the board */
        remove = new JPanel();
        Dimension size = new Dimension(80,40);
        remove.setPreferredSize(size);
        remove.setMinimumSize(size);
        remove.setMaximumSize(size);
        remove.setAlignmentX(Component.CENTER_ALIGNMENT);
        remove.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        remove.setVisible(true);
        
        /* remove text */
        JLabel label = new JLabel("Remove");
        remove.add(label);
        remove.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removePiece();
            }
        });
        
        opts.add(remove);
        opts.add(new JPanel());
        return opts;
    }
    
    class ColourChanger implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if("white" == e.getActionCommand()){
                playerColour = true;
            }
            else{
                playerColour = false;
            }
            System.out.println(playerColour);
        }
    }
    
    public void removePiece(){
        if(removePiece){
            remove.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        }
        else{
            remove.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        }
        removePiece = !removePiece;
    }

    private JPanel createPieceSelect(){
        JPanel pieceSelect = new JPanel();
        pieceSelect.setLayout(new GridLayout(8,4));
        
        //white pieces
        for(int i=0;i<8;i++)
            select[i][0] = new Square(new FakePiece("pawn-white",i,0));
        select[0][1] = new Square(new FakePiece("rook-white",0,1));
        select[1][1] = new Square(new FakePiece("knight-white",1,1));
        select[2][1] = new Square(new FakePiece("bishop-white",2,1));
        select[3][1] = new Square(new FakePiece("queen-white",3,1));
        select[4][1] = new Square(new FakePiece("king-white",4,1));
        select[5][1] = new Square(new FakePiece("bishop-white",5,1));
        select[6][1] = new Square(new FakePiece("knight-white",6,1));
        select[7][1] = new Square(new FakePiece("rook-white",7,1));
        
        //black pieces
        for(int i=0;i<8;i++)
            select[i][2] = new Square(new FakePiece("pawn-black",i,2));
        select[0][3] = new Square(new FakePiece("rook-black",0,3));
        select[1][3] = new Square(new FakePiece("knight-black",1,3));
        select[2][3] = new Square(new FakePiece("bishop-black",2,3));
        select[3][3] = new Square(new FakePiece("queen-black",3,3));
        select[4][3] = new Square(new FakePiece("king-black",4,3));
        select[5][3] = new Square(new FakePiece("bishop-black",5,3));
        select[6][3] = new Square(new FakePiece("knight-black",6,3));
        select[7][3] = new Square(new FakePiece("rook-black",7,3));
        
        for(Square[] s: select)
            for(Square sq: s){
                pieceSelect.add(sq);
            }
        pieceSelect.setVisible(true);
        pieceSelect.setPreferredSize(new Dimension(256,512));
        return pieceSelect;
    }
    
    class Square extends JPanel{
        FakePiece piece;
        Color color;
        int X;
        int Y;
        boolean isBoard;    //is part of main game board(true) or select board (false)
        
        public Square(Color c, int x, int y){
            setBackground(c);
            X = x;
            Y = y;
            isBoard = true;
            init();
        }
        public Square(FakePiece p){
            setBackground(Color.WHITE);
            add(p.image);
            piece = p;
            isBoard = false;
            init();
        }
        
        public void addPiece(FakePiece p){
            if(isBoard && piece != null){
                select[piece.Y][piece.X].addPiece(piece);
            }
            piece = p;
            removeAll();
            add(p.image);
            validate();
            repaint();
        }
        public void init(){
            setVisible(true);
            setSize(64, 64);
            setBorder(BorderFactory.createLineBorder(Color.black));
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    System.out.println("clicked");
                    if(piece != null && isBoard && removePiece){
                        removePiece();
                        revokePiece();
                    }else if(isBoard && selectedPiece != null){
                        addPiece(selectedPiece.piece);
                        selectedPiece.deselectPiece();
                    }
                    else if(!isBoard && piece !=null){
                        selectPiece();
                    }
                }
            });
        }
        public void selectPiece(){
            if(selectedPiece != null && selectedPiece == this){
                deselectPiece();
            }else{
                if(selectedPiece!=null){
                selectedPiece.deselectPiece();
                }
                selectedPiece = this;
                setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            }
        }
        
        public void deselectPiece(){
            selectedPiece =null;
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        
        public void revokePiece(){
            select[piece.X][piece.Y].addPiece(piece);
            piece = null;
            removeAll();
            validate();
            repaint();
        }
    }
    
    class FakePiece{
        
        public JLabel image;
        public int X,Y;
        public String color, type;
        public FakePiece(String type, int x, int y) {
            X=x;
            Y=y;
            String imagePath = "/image/".concat(type + ".gif");
            try{
            BufferedImage myPicture = ImageIO.read(this.getClass().getResource(imagePath));
            myPicture = resize(myPicture, 40, 40);
            image = new JLabel(new ImageIcon(myPicture));
            }
            catch(IOException ioe){
                System.out.println(ioe.getMessage());
            }
        }
        public BufferedImage resize(BufferedImage image, int width, int height) {
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
            Graphics2D g2d = (Graphics2D) bi.createGraphics();
            g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
            g2d.drawImage(image, 0, 0, width, height, null);
            g2d.dispose();
            return bi;
        }
    }
}
