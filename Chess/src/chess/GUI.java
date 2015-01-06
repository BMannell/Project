package chess;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;


public class GUI extends JFrame{
    
    private BoardSquare[][] board = new BoardSquare[8][8];
    Chess chess;        //chess controller
    JPanel boardPane;   //game board display
    JRadioButton whiteButton;       //colour select
    JFormattedTextField plySelect;  //ply input
    BoardSquare selectedPiece;      //piece to move
    boolean underCheck = false;  //if play is being checked
    public GUI(Chess c){
        super("Chess");
        chess = c;
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        displayMainMenu();
    }
    
    public void displayMainMenu(){
        
        setSize(180,200);
        
        JPanel main = new JPanel();
        
        main.setLayout(new BoxLayout(main, 1));
        
        // button to start new game
        JButton start = new JButton("New Game");
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(Box.createRigidArea(new Dimension(0, 10)));
        start.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
               displayGameOptions();
            }
        });
        main.add(start);
        
        //button to load game from file
        JButton load = new JButton("Load Game");
        load.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(Box.createRigidArea(new Dimension(0, 10)));
        load.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
               System.out.println("Load");
            }
        });
        load.setEnabled(false);
        main.add(load);
        
        //button to whitetate game file
        JButton spec = new JButton("Spectate Game");
        spec.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(Box.createRigidArea(new Dimension(0, 10)));
        spec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
               System.out.println("Spec");
            }
        });
        spec.setEnabled(false);
        main.add(spec);
        
        JButton test = new JButton("Custom Board");
        test.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(Box.createRigidArea(new Dimension(0, 10)));
        test.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
              customGame();
            }
        });
        main.add(test);
        
        getContentPane().add(main);
        validate();
        repaint();
    }
    
    public void customGame(){
        getContentPane().removeAll();
        setSize(798,512);
        getContentPane().add(new CustomBoard(chess));
        validate();
        repaint();
    }
    
    public void displayGameOptions() {
        setSize(180,250);
        getContentPane().removeAll();
        JPanel colors = new JPanel();
        colors.setLayout(new BoxLayout(colors, BoxLayout.Y_AXIS));
        colors.setVisible(true);
        
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
        whiteButton = new JRadioButton("White");
        whiteButton.setSelected(true);
        JRadioButton blackButton = new JRadioButton("Black");

        //group radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(whiteButton);
        group.add(blackButton);

        //add buttons to panel
        pColor.add(whiteButton);
        pColor.add(blackButton);

        colors.add(Box.createRigidArea(new Dimension(0, 10)));
        colors.add(pColor);
        colors.add(Box.createRigidArea(new Dimension(0, 10)));
        
        /* Ply input panel*/
        JPanel ply = new JPanel();
        ply.setVisible(true);
        title = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), "Ply");
        title.setTitleJustification(TitledBorder.CENTER);
        ply.setBorder(title);
        Dimension plyD = new Dimension(80,60);
        ply.setMaximumSize(plyD);
        ply.setMinimumSize(plyD);
        ply.setPreferredSize(plyD);
        ply.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        /* Ply input field */
        plySelect = new JFormattedTextField(NumberFormat.getIntegerInstance());
        plySelect.setValue((long)3);
        plySelect.setColumns(3);
        
        ply.add(plySelect);
        
        colors.add(ply);
        
        /* Start */
        JButton white = new JButton("Start");
        white.setAlignmentX(Component.CENTER_ALIGNMENT);
        white.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int ply = (int)(long)plySelect.getValue();
                if(ply < 1){
                    ply = 1;
                }
                chess.newGame(ply,whiteButton.isSelected());
                boardSetup();
            }
        });
        
        colors.add(Box.createRigidArea(new Dimension(0, 10)));
        colors.add(white);
        colors.add(Box.createRigidArea(new Dimension(0, 10)));
        
        getContentPane().add(colors);//Adding to content pane, not to Frame
        validate();
        repaint();
    }
    
    public void boardSetup(){
        initGameBoard();
        drawBoard();
        setSize(512,512);
        getContentPane().removeAll();
        getContentPane().add(boardPane);
        validate();
        repaint();
    }
    
    /* Initializes GameBoard */
    private void initGameBoard() {
        boolean colour = true;
        boardPane = new JPanel(new GridLayout(8, 8));
        boardPane.setSize(512,512);
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                BoardSquare jp;
                if(colour){
                    jp = new BoardSquare(Color.white, x, y);
                }
                else{
                    jp = new BoardSquare(Color.GRAY, x, y);
                }
                board[y][x] = jp;
                boardPane.add(jp);
                colour = !colour;
            }
            colour = !colour;
        }
    }
    
    /* Draws current gameboard */
    public void drawBoard(){
        for(int y = 0; y<8;y++){
            for(int x=0;x<8;x++){
                board[y][x].removeAll();
                board[y][x].piece = null;
                board[y][x].revertColour();
                if(chess.currentState[y][x] != null){
                    board[y][x].addPiece(chess.currentState[y][x]);
                }
            }
        }
    }
    
    public void cleanBoard(){
        for(BoardSquare[] bs: board){
            for(BoardSquare b: bs){
                b.clean();
            }
        }
    }
    
    public void lock(){
        boardPane.setEnabled(false);
    }
    
    public void unlock(){
        boardPane.setEnabled(true);
    }
    
    public void makeMove(BoardSquare bs){
        if(selectedPiece.piece.type.equalsIgnoreCase("pawn") && bs.Y == 0){
            getContentPane().add(new PawnPromotion(bs.X,bs.Y),0);
            validate();
            repaint();
        }else{
            chess.makeMove(new Move(selectedPiece.Y,selectedPiece.X,bs.Y,bs.X));
        }
    }
    
    public void promotePawn(String type, int x, int y){
        selectedPiece.piece.promote(type);
        getContentPane().remove(0);
        chess.makeMove(new Move(selectedPiece.X,selectedPiece.Y,x,y));
        validate();
        repaint();
    }
    
    class PawnPromotion extends JPanel implements ActionListener {

        String type = "queen";
        int X,Y;
        public PawnPromotion(int x, int y) {
            X=x;
            Y=y;
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setVisible(true);
            setAlignmentX(Component.CENTER_ALIGNMENT);
            //pawn upgrade panel
            JPanel innerPanel = new JPanel();
            innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
            innerPanel.setVisible(true);
            innerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            Dimension newD = new Dimension(120, 180);
            innerPanel.setMaximumSize(newD);
            innerPanel.setMinimumSize(newD);
            innerPanel.setPreferredSize(newD);
            //create border for radio colour select
            TitledBorder title = BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.BLACK), "Promotion");
            title.setTitleJustification(TitledBorder.CENTER);
            innerPanel.setBorder(title);

            //create radio buttons
            JRadioButton queenButton = new JRadioButton("Queen");
            queenButton.setActionCommand("queen");
            queenButton.addActionListener(this);
            queenButton.setSelected(true);
            queenButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            JRadioButton bishopButton = new JRadioButton("Bishop");
            bishopButton.setActionCommand("bishop");
            bishopButton.addActionListener(this);
            bishopButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            JRadioButton knightButton = new JRadioButton("Knight");
            knightButton.setActionCommand("knight");
            knightButton.addActionListener(this);
            knightButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            JRadioButton rookButton = new JRadioButton("Rook");
            rookButton.setActionCommand("rook");
            rookButton.addActionListener(this);
            rookButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            //group radio buttons
            ButtonGroup group = new ButtonGroup();
            group.add(queenButton);
            group.add(bishopButton);
            group.add(knightButton);
            group.add(rookButton);

            //add buttons to panel
            innerPanel.add(queenButton);
            innerPanel.add(bishopButton);
            innerPanel.add(knightButton);
            innerPanel.add(rookButton);

            //create continue button
            JButton cont = new JButton("Select");
            cont.setAlignmentX(Component.CENTER_ALIGNMENT);
            cont.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    promotePawn(type,X,Y);
                }
            });
            innerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            innerPanel.add(cont);
            add(innerPanel);
        }

        public void actionPerformed(ActionEvent e) {
            type = e.getActionCommand();
        }
    }
    /* Initializes menu bar 
    private void initMenuBar(){
        //Where the GUI is created:
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("A Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);
        
        setJMenuBar(menuBar);
    }
    */
    
    /*
    public void showPaths(){
        for(int y = 0; y < 8; y++){
            for (int x= 0; x < 8; x++) {
                if (board[y][x].piece != null && !board[y][x].piece.team) {
                    ArrayList<int[]> moves = Engine.getMoves(chess.currentState, y, x);
                    moves.add(0,new int[]{y,x});
                    ArrayList<int[]> coords = Engine.getRouteToKing(moves, board[y][x].piece.type, new int[]{5,5});
                    for(int[] num: coords){
                        board[num[0]][num[1]].setRed();
                    }
                }
            }
        }
    }
    */
    /**
     * BoardSquare
     * 
     * Variables
     * -position
     * -piece
     * -initial color (black, white)
     */
    
    public class BoardSquare extends JPanel{
        
        Color initialColor;
        int X;
        int Y;
        Piece piece;
        
        boolean toMove = false; //set to true if red square
        
        public BoardSquare(Color bg, int x, int y){
            initialColor = bg;
            X = x;
            Y = y;
            setBackground(bg);
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    //showPaths();
                  selectSquare();
                }
            });
            setSize(64, 64);
            setBorder(BorderFactory.createLineBorder(Color.black));
        }
        
        public void revertColour(){
            setBackground(initialColor);
        }
        
        public void setBlue(){
            setBackground(Color.CYAN);
        }
        
        public void setRed(){
            setBackground(Color.RED);
        }
        
        public void setGreen(){
            setBackground(Color.GREEN);
        }
        

        public void selectSquare() {
            if (piece != null && piece.team) {
                cleanBoard();
                if (selectedPiece != null && selectedPiece == this) {
                    selectedPiece = null;
                } else {
                    selectedPiece = this;
                    setBlue();
                    ArrayList<int[]> moves = Engine.getMoves(chess.currentState, Y, X, underCheck);
                    for (int[] move : moves) {
                        board[move[0]][move[1]].setToMove();
                    }
                }
            } else if (toMove) {
                makeMove(this);
                cleanBoard();
            }
        }
        
        public void setToMove(){
            if(piece != null){
                setRed();
            }else{
                setGreen();
            }
            toMove = true;
        }
        public void addPiece(Piece p){
            removeAll();
            piece = p;
            add(piece.image);
            validate();
            repaint();
        }
        
        public void clean(){
            toMove = false;
            revertColour();
        }
    }
}
