

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class Board implements MouseListener, ActionListener, Serializable {
    
    private static Pieces bStar1 = new Star();//Type 8
    private static Pieces bStar2 = new Star();//Type 8
    private static Pieces rStar1 = new Star();//Type 4
    private static Pieces rStar2 = new Star();//Type 4
    
    private static Pieces bArrow1 = new Arrow();//Type = 7
    private static Pieces bArrow2 = new Arrow();//Type = 7
    private static Pieces bArrow3 = new Arrow();//Type = 7
    private static Pieces rArrow1 = new Arrow();//Type = 3
    private static Pieces rArrow2 = new Arrow();//Type = 3
    private static Pieces rArrow3 = new Arrow();//Type = 3
    
    private static Pieces bCross1 = new Cross();//Type = 6
    private static Pieces bCross2 = new Cross();//Type = 6
    private static Pieces rCross1 = new Cross();//Type = 2
    private static Pieces rCross2 = new Cross();//Type = 2
    
    private static Pieces bHeart = new Heart();//Type = 5
    private static Pieces rHeart = new Heart();//Type = 1
    
    private static Helper oHelper = new Helper(); // an instance of Helper class
    private static ChessPlayer oChessPlayer = new ChessPlayer(); // an instance of ChessPlayer class
    
    private JFrame frame; //Main frame of the game
    private JButton squares[] = new JButton[40]; //Array of buttons where the player will play
    private JButton save = new JButton   ("Save");
    private JButton swap = new JButton   ("swap");
    private JButton St = new JButton("Start New Game");
    private JButton Lo = new JButton(" Load Game ");
    private JButton help = new JButton(" Help ");
    
    JLabel name = new JLabel("Barsoomian Chess");
    Font titleF = new Font("Sans Serif",Font.BOLD,30);
    private JLabel pic = new JLabel("Current Player: " + oChessPlayer.getCurrentPlayer()); // To display Current Player
    
    int redcounter = 0;             //
    int bluecounter = 0;            //
    int tempRedS2, tempRedS1;       // These variables are for the changing of stones from Star to Cross and vice versa
    int tempRedC2, tempRedC1;       //  
    int tempBlueS2, tempBlueS1;     //
    int tempBlueC2, tempBlueC1;     //
	boolean swap1 = false;
   
    JPanel main = new JPanel(new BorderLayout());
    JPanel center = new JPanel(new GridLayout(8,5));
    JPanel east = new JPanel (new GridLayout(5,0));
    JPanel west = new JPanel();
    JPanel north = new JPanel();
    JPanel south = new JPanel (new FlowLayout());
    
    @Override
    public void mouseClicked(MouseEvent e){
        JButton btn = (JButton) e.getSource();

        int owner = (int)btn.getClientProperty("owner"); // To know the owner of the stone
        int type = (int)btn.getClientProperty("type"); // Type is to know the type of the selected stone
        int coordinate = (int)btn.getClientProperty("coordinate"); // Stone Position in the array
        
        if (!oChessPlayer.bHasSelectedStone()){
            if(oHelper.bVerifySelectedStone(owner)){//To verify if the current player selected a correct stone
                btn.setBackground(Color.red);
                oChessPlayer.setHasSelectedStone(coordinate);
                oChessPlayer.setCurrentSelectedStoneType(type);            
            }
            else{
                System.out.println("wrong stone selection");
            }
        } 
        else{
            if(oHelper.bVerifySelectedStone(owner)){
            for(int i =0 ; i < 40; i++){
                squares[i].setBackground(Color.white);
            }
            if (oChessPlayer.bHasSelectedStone()){ 
                btn.setBackground(Color.red);  ////To verify if the current player selected a correct stone
                oChessPlayer.setHasSelectedStone(coordinate);
                oChessPlayer.setCurrentSelectedStoneType(type);                
            }   
            } 
            else{
                int base = oChessPlayer.getCurrentSelectedCoordinate();//Position of the selected stone
                if(oChessPlayer.getCurrentSelectedStoneType()==1){//Movement of the Red Heart
                    if (rHeart.bCheckForValidMovemement(oChessPlayer.getCurrentSelectedStoneType(), base, coordinate)){
                        boolean finish = false;
                        unsetStoneIcon(base);
                        if (owner == 0){ 
                            System.out.println("[target is empty] coord=" + coordinate + " new_owner=" + owner);
                        }else{
                            unsetStoneIcon(coordinate);
                            if (type == 5) {
                                finish = true; //If the target coordinate has the heart of the other player the game finishes
                            }
                            else if (type == 6){
                                if(coordinate == bCross1.getPosition()){
                                    bCross1.setStatus(false); //If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                                if(coordinate == bCross2.getPosition()){
                                    bCross2.setStatus(false); //If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                            }
                            else if (type == 7){
                                if(coordinate == bArrow1.getPosition()){
                                    bArrow1.setStatus(false); //If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                                if(coordinate == bArrow2.getPosition()){
                                    bArrow2.setStatus(false); //If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                                if(coordinate == bArrow3.getPosition()){
                                    bArrow3.setStatus(false); //If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                            }
                            else if (type == 8){
                                if(coordinate == bStar1.getPosition()){
                                    bStar1.setStatus(false); //If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                                if(coordinate == bStar2.getPosition()){
                                    bStar2.setStatus(false); //If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                            }
                        }
                        setStoneIcon(coordinate, oChessPlayer.getCurrentPlayer());
                        oChessPlayer.setCurrentSelectedCoordinate(0);
                        oChessPlayer.setCurrentSelectedStoneType(0);
                        oChessPlayer.unsetHasSelectedStone();
                        oChessPlayer.changePlayer();
                        if (finish) {
                            oChessPlayer.showGameFinished();
                        }
                    } 
                }
                else if(oChessPlayer.getCurrentSelectedStoneType()==2){
                    if (rCross1.bCheckForValidMovemement(oChessPlayer.getCurrentSelectedStoneType(), base, coordinate) && rCross2.bCheckForValidMovemement(oChessPlayer.getCurrentSelectedStoneType(), base, coordinate)) {
                        boolean finish = false;
                        unsetStoneIcon(base);
                        if(owner == 0){ 
                            System.out.println("[target is empty] coord=" + coordinate + " new_owner=" + owner);
                        } 
                        else{
                            unsetStoneIcon(coordinate);
                            if (type == 5) {
                                finish = true; //If the target coordinate has the heart of the other player the game finishes
                            }
                            else if (type == 6){
                                if(coordinate == bCross1.getPosition()){
                                    bCross1.setStatus(false); //If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                                if(coordinate == bCross2.getPosition()){
                                    bCross2.setStatus(false); //If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                            }
                            else if (type == 7){
                                if(coordinate == bArrow1.getPosition()){
                                    bArrow1.setStatus(false); //If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                                if(coordinate == bArrow2.getPosition()){
                                    bArrow2.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                                if(coordinate == bArrow3.getPosition()){
                                    bArrow3.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                            }
                            else if (type == 8){
                                if(coordinate == bStar1.getPosition()){
                                    bStar1.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                                if(coordinate == bStar2.getPosition()){
                                    bStar2.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                            }
                        }
                        setStoneIcon(coordinate, oChessPlayer.getCurrentPlayer());
                        oChessPlayer.setCurrentSelectedCoordinate(0);
                        oChessPlayer.setCurrentSelectedStoneType(0);
                        oChessPlayer.unsetHasSelectedStone();
                        oChessPlayer.changePlayer();

                        if(finish){
                            oChessPlayer.showGameFinished();
                        }
                    } 
                }
                else if(oChessPlayer.getCurrentSelectedStoneType()==3){
                    if (rArrow1.bCheckForValidMovemement(oChessPlayer.getCurrentSelectedStoneType(), base, coordinate) && rArrow2.bCheckForValidMovemement(oChessPlayer.getCurrentSelectedStoneType(), base, coordinate) && rArrow3.bCheckForValidMovemement(oChessPlayer.getCurrentSelectedStoneType(), base, coordinate)){
                        boolean finish = false;
                        unsetStoneIcon(base);
                        if (owner == 0) {
                            System.out.println("[target is empty] coord=" + coordinate + " new_owner=" + owner);
                        }else{
                            unsetStoneIcon(coordinate);
                            if (type == 5) {
                                finish = true;//If the target coordinate has the heart of the other player the game finishes
                            }                           
                            else if (type == 6){
                                if(coordinate == bCross1.getPosition()){
                                    bCross1.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                                if(coordinate == bCross2.getPosition()){
                                    bCross2.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                            }
                            else if (type == 7){
                                if(coordinate == bArrow1.getPosition()){
                                    bArrow1.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                                if(coordinate == bArrow2.getPosition()){
                                    bArrow2.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                                if(coordinate == bArrow3.getPosition()){
                                    bArrow3.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                            }
                            else if (type == 8){
                                if(coordinate == bStar1.getPosition()){
                                    bStar1.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                                if(coordinate == bStar2.getPosition()){
                                    bStar2.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                            }
                        }
                        setStoneIcon(coordinate, oChessPlayer.getCurrentPlayer());
                        oChessPlayer.setCurrentSelectedCoordinate(0);
                        oChessPlayer.setCurrentSelectedStoneType(0);
                        oChessPlayer.unsetHasSelectedStone();
                        oChessPlayer.changePlayer();
                        
                        if (finish) {
                            oChessPlayer.showGameFinished();
                        }

                    } 
                }
                else if(oChessPlayer.getCurrentSelectedStoneType()==4){
                    if (rStar1.bCheckForValidMovemement(oChessPlayer.getCurrentSelectedStoneType(), base, coordinate) && rStar2.bCheckForValidMovemement(oChessPlayer.getCurrentSelectedStoneType(), base, coordinate) ) {
                        boolean finish = false;
                        unsetStoneIcon(base);
                        if(owner == 0){
                            System.out.println("[target is empty] coord=" + coordinate + " new_owner=" + owner);
                        } 
                        else{
                            unsetStoneIcon(coordinate);
                            if (type == 1) {
                                finish = true; //If the target coordinate has the heart of the other player the game finishes
                            }
                            else if (type == 2){
                                if(coordinate == bCross1.getPosition()){
                                    bCross1.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                                if(coordinate == bCross2.getPosition()){
                                    bCross2.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                            }
                            else if (type == 3){
                                if(coordinate == bArrow1.getPosition()){
                                    bArrow1.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                                if(coordinate == bArrow2.getPosition()){
                                    bArrow2.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                                if(coordinate == bArrow3.getPosition()){
                                    bArrow3.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                            }
                            else if (type == 4){
                                if(coordinate == bStar1.getPosition()){
                                    bStar1.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                                if(coordinate == bStar2.getPosition()){
                                    bStar2.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                            }
                        }
                        setStoneIcon(coordinate, oChessPlayer.getCurrentPlayer());
                        oChessPlayer.setCurrentSelectedCoordinate(0);
                        oChessPlayer.setCurrentSelectedStoneType(0);
                        oChessPlayer.unsetHasSelectedStone();
                        oChessPlayer.changePlayer();

                        if (finish) {
                            oChessPlayer.showGameFinished();
                        }
                    } 
                }
                if(oChessPlayer.getCurrentSelectedStoneType()==5){
                    if (bHeart.bCheckForValidMovemement(oChessPlayer.getCurrentSelectedStoneType(), base, coordinate)){
                        boolean finish = false;
                        unsetStoneIcon(base);
                        if (owner == 0){ 
                            System.out.println("[target is empty] coord=" + coordinate + " new_owner=" + owner);
                        }else{
                            unsetStoneIcon(coordinate);
                            if (type == 1) {
                                finish = true; //If the target coordinate has the heart of the other player the game finishes
                            }
                            else if (type == 2){
                                if(coordinate == rCross1.getPosition()){
                                    rCross1.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                                if(coordinate == rCross2.getPosition()){
                                    rCross2.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                            }
                            else if (type == 3){
                                if(coordinate == rArrow1.getPosition()){
                                    rArrow1.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                                if(coordinate == rArrow2.getPosition()){
                                    rArrow2.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                                if(coordinate == rArrow3.getPosition()){
                                    rArrow3.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                            }
                            else if (type == 4){
                                if(coordinate == rStar1.getPosition()){
                                    rStar1.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                                if(coordinate == rStar2.getPosition()){
                                    rStar2.setStatus(false);//If the target coordinate has a piece of the other player the status of that piece goes to false(dead)
                                }
                            }
                        }
                        setStoneIcon(coordinate, oChessPlayer.getCurrentPlayer());
                        oChessPlayer.setCurrentSelectedCoordinate(0);
                        oChessPlayer.setCurrentSelectedStoneType(0);
                        oChessPlayer.unsetHasSelectedStone();
                        oChessPlayer.changePlayer();
                        if (finish) {
                            oChessPlayer.showGameFinished();
                        }
                    } 
                }
                else if(oChessPlayer.getCurrentSelectedStoneType()==6){
                    if (bCross1.bCheckForValidMovemement(oChessPlayer.getCurrentSelectedStoneType(), base, coordinate) && bCross2.bCheckForValidMovemement(oChessPlayer.getCurrentSelectedStoneType(), base, coordinate)) {
                        boolean finish = false;
                        unsetStoneIcon(base);
                        if(owner == 0){ 
                            System.out.println("[target is empty] coord=" + coordinate + " new_owner=" + owner);
                        } 
                        else{
                            unsetStoneIcon(coordinate);
                            if (type == 1) {
                                finish = true; //If the target coordinate has the heart of the other player the game finishes
                            }
                            else if (type == 2){
                                if(coordinate == rCross1.getPosition()){
                                    rCross1.setStatus(false);
                                }
                                if(coordinate == rCross2.getPosition()){
                                    rCross2.setStatus(false);
                                }
                            }
                            else if (type == 3){
                                if(coordinate == rArrow1.getPosition()){
                                    rArrow1.setStatus(false);
                                }
                                if(coordinate == rArrow2.getPosition()){
                                    rArrow2.setStatus(false);
                                }
                                if(coordinate == rArrow3.getPosition()){
                                    rArrow3.setStatus(false);
                                }
                            }
                            else if (type == 4){
                                if(coordinate == rStar1.getPosition()){
                                    rStar1.setStatus(false);
                                }
                                if(coordinate == rStar2.getPosition()){
                                    rStar2.setStatus(false);
                                }
                            }
                        }
                        setStoneIcon(coordinate, oChessPlayer.getCurrentPlayer());
                        oChessPlayer.setCurrentSelectedCoordinate(0);
                        oChessPlayer.setCurrentSelectedStoneType(0);
                        oChessPlayer.unsetHasSelectedStone();
                        oChessPlayer.changePlayer();

                        if(finish){
                            oChessPlayer.showGameFinished();
                        }
                    } 
                }
                else if(oChessPlayer.getCurrentSelectedStoneType()==7){
                    if (bArrow1.bCheckForValidMovemement(oChessPlayer.getCurrentSelectedStoneType(), base, coordinate) && bArrow2.bCheckForValidMovemement(oChessPlayer.getCurrentSelectedStoneType(), base, coordinate) && bArrow3.bCheckForValidMovemement(oChessPlayer.getCurrentSelectedStoneType(), base, coordinate)){
                        boolean finish = false;
                        unsetStoneIcon(base);
                        if (owner == 0) {
                            System.out.println("[target is empty] coord=" + coordinate + " new_owner=" + owner);
                        }else{
                            unsetStoneIcon(coordinate);
                            if (type == 1) {
                                finish = true; //If the target coordinate has the heart of the other player the game finishes
                            }                           
                            else if (type == 2){
                                if(coordinate == rCross1.getPosition()){
                                    rCross1.setStatus(false);
                                }
                                if(coordinate == rCross2.getPosition()){
                                    rCross2.setStatus(false);
                                }
                            }
                            else if (type == 3){
                                if(coordinate == rArrow1.getPosition()){
                                    rArrow1.setStatus(false);
                                }
                                if(coordinate == rArrow2.getPosition()){
                                    rArrow2.setStatus(false);
                                }
                                if(coordinate == rArrow3.getPosition()){
                                    rArrow3.setStatus(false);
                                }
                            }
                            else if (type == 4){
                                if(coordinate == rStar1.getPosition()){
                                    rStar1.setStatus(false);
                                }
                                if(coordinate == rStar2.getPosition()){
                                    rStar2.setStatus(false);
                                }
                            }
                        }
                        setStoneIcon(coordinate, oChessPlayer.getCurrentPlayer());
                        oChessPlayer.setCurrentSelectedCoordinate(0);
                        oChessPlayer.setCurrentSelectedStoneType(0);
                        oChessPlayer.unsetHasSelectedStone();
                        oChessPlayer.changePlayer();

                        if (finish) {
                            oChessPlayer.showGameFinished();
                        }

                    } 
                }
                else if(oChessPlayer.getCurrentSelectedStoneType()==8){
                    if (bStar1.bCheckForValidMovemement(oChessPlayer.getCurrentSelectedStoneType(), base, coordinate) && bStar2.bCheckForValidMovemement(oChessPlayer.getCurrentSelectedStoneType(), base, coordinate)) {
                        boolean finish = false;
                        unsetStoneIcon(base);
                        if(owner == 0){
                            System.out.println("[target is empty] coord=" + coordinate + " new_owner=" + owner);
                        } 
                        else{
                            unsetStoneIcon(coordinate);
                            if (type == 1) {
                                finish = true; //If the target coordinate has the heart of the other player the game finishes
                            }
                            else if (type == 2){
                                if(coordinate == rCross1.getPosition()){
                                    rCross1.setStatus(false);
                                }
                                if(coordinate == rCross2.getPosition()){
                                    rCross2.setStatus(false);
                                }
                            }
                            else if (type == 3){
                                if(coordinate == rArrow1.getPosition()){
                                    rArrow1.setStatus(false);
                                }
                                if(coordinate == rArrow2.getPosition()){
                                    rArrow2.setStatus(false);
                                }
                                if(coordinate == rArrow3.getPosition()){
                                    rArrow3.setStatus(false);
                                }
                            }
                            else if (type == 4){
                                if(coordinate == rStar1.getPosition()){
                                    rStar1.setStatus(false);
                                }
                                if(coordinate == rStar2.getPosition()){
                                    rStar2.setStatus(false);
                                }
                            }
                        }
                        setStoneIcon(coordinate, oChessPlayer.getCurrentPlayer());
                        oChessPlayer.setCurrentSelectedCoordinate(0);
                        oChessPlayer.setCurrentSelectedStoneType(0);
                        oChessPlayer.unsetHasSelectedStone();
                        oChessPlayer.changePlayer();

                        if (finish) {
                            oChessPlayer.showGameFinished();
                        }
                    } 
                }else{
                        System.out.println("invalid new coordinate");
                }
            }

        }
        System.out.println("\n");
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == St){
           new Board(); //Generate a new board to start a new game
           Helper oHelper = new Helper();
           ChessPlayer oChessPlayer = new ChessPlayer();
           frame.setVisible(false);
        }
        else if(e.getSource() == swap){
			swap1 = !swap1;
            flip(); // Function Flip to flip the board
        }
        else if(e.getSource() == save){
            try{
                FileOutputStream save = new FileOutputStream("game.txt");
                ObjectOutputStream obj = new ObjectOutputStream(save);
                obj.writeObject(bArrow1); //{
                obj.writeObject(bArrow2); //
                obj.writeObject(bArrow3); //
                obj.writeObject(bCross1); //
                obj.writeObject(bCross2); // 
                obj.writeObject(bStar1);  //
                obj.writeObject(bStar2);  //
                obj.writeObject(bHeart);  // **Save The position and the Status of each piece in a txt file
                obj.writeObject(rArrow1); //
                obj.writeObject(rArrow2); //
                obj.writeObject(rArrow3); //
                obj.writeObject(rCross1); //
                obj.writeObject(rCross2); //
                obj.writeObject(rStar1);  //
                obj.writeObject(rStar2);  //
                obj.writeObject(rHeart);  //}
                JOptionPane.showMessageDialog(null, "Saved Successfully");
                obj.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            
        }
        else if(e.getSource() == Lo){
            try{
                for (int i = 0; i <40; i++){
                    unsetStoneIcon(i);
                }
                ObjectInputStream load = new ObjectInputStream(new FileInputStream("game.txt"));
                bArrow1 = (Pieces)load.readObject(); //Load the stone information from txt file
                if(bArrow1.getStatus()){ // if the Stone status loaded from the txt file is true
                    squares[bArrow1.getPosition()].add((new JLabel(new ImageIcon("bluearrow1.png")))); //draw stone
                    squares[bArrow1.getPosition()].putClientProperty("type", 7); // set Type of the Stone          
                    squares[bArrow1.getPosition()].putClientProperty("owner", 2); // set Owner of the stone              
                }
                bArrow2 = (Pieces)load.readObject();//Load the stone information from txt file
                if(bArrow2.getStatus()){
                    squares[bArrow2.getPosition()].add((new JLabel(new ImageIcon("bluearrow1.png"))));
                    squares[bArrow2.getPosition()].putClientProperty("type", 7);
                    squares[bArrow2.getPosition()].putClientProperty("owner", 2);
                }
                bArrow3 = (Pieces)load.readObject();//Load the stone information from txt file
                if(bArrow3.getStatus()){
                    squares[bArrow3.getPosition()].add((new JLabel(new ImageIcon("bluearrow1.png"))));
                    squares[bArrow3.getPosition()].putClientProperty("type", 7);
                    squares[bArrow3.getPosition()].putClientProperty("owner", 2);
                }
                bCross1 = (Pieces)load.readObject();//Load the stone information from txt file
                if(bCross1.getStatus()){
                    squares[bCross1.getPosition()].add((new JLabel(new ImageIcon("crossblue.png"))));
                    squares[bCross1.getPosition()].putClientProperty("type", 6);
                    squares[bCross1.getPosition()].putClientProperty("owner", 2);
                }
                bCross2 = (Pieces)load.readObject();//Load the stone information from txt file
                if(bCross2.getStatus()){
                    squares[bCross2.getPosition()].add((new JLabel(new ImageIcon("crossblue.png"))));
                    squares[bCross2.getPosition()].putClientProperty("type", 6);
                    squares[bCross2.getPosition()].putClientProperty("owner", 2);
                }
                bStar1 = (Pieces)load.readObject();//Load the stone information from txt file
                if(bStar1.getStatus()){
                    squares[bStar1.getPosition()].add((new JLabel(new ImageIcon("bluestar.png"))));
                    squares[bStar1.getPosition()].putClientProperty("type", 8);
                    squares[bStar1.getPosition()].putClientProperty("owner", 2);
                }
                bStar2 = (Pieces)load.readObject();//Load the stone information from txt file
                if(bStar2.getStatus()){
                    squares[bStar2.getPosition()].add((new JLabel(new ImageIcon("bluestar.png"))));
                    squares[bStar2.getPosition()].putClientProperty("type", 8);
                    squares[bStar2.getPosition()].putClientProperty("owner", 2);
                }
                bHeart = (Pieces)load.readObject();//Load the stone information from txt file
                if(bHeart.getStatus()){
                    squares[bHeart.getPosition()].add((new JLabel(new ImageIcon("blueheart.png"))));
                    squares[bHeart.getPosition()].putClientProperty("type", 5);
                    squares[bHeart.getPosition()].putClientProperty("owner", 2);
                }
                rArrow1 = (Pieces)load.readObject();//Load the stone information from txt file
                if(rArrow1.getStatus()){
                    squares[rArrow1.getPosition()].add((new JLabel(new ImageIcon("redarrow1.png"))));
                    squares[rArrow1.getPosition()].putClientProperty("type", 3);
                    squares[rArrow1.getPosition()].putClientProperty("owner", 1);
                }
                rArrow2 = (Pieces)load.readObject();//Load the stone information from txt file
                if(rArrow2.getStatus()){
                    squares[rArrow2.getPosition()].add((new JLabel(new ImageIcon("redarrow1.png"))));
                    squares[rArrow2.getPosition()].putClientProperty("type", 3);
                    squares[rArrow2.getPosition()].putClientProperty("owner", 1);
                }
                rArrow3 = (Pieces)load.readObject();//Load the stone information from txt file
                if(rArrow3.getStatus()){
                    squares[rArrow3.getPosition()].add((new JLabel(new ImageIcon("redarrow1.png"))));
                    squares[rArrow3.getPosition()].putClientProperty("type", 3);
                    squares[rArrow3.getPosition()].putClientProperty("owner", 1);
                }
                rCross1 = (Pieces)load.readObject();//Load the stone information from txt file
                if(rCross1.getStatus()){
                    squares[rCross1.getPosition()].add((new JLabel(new ImageIcon("crossred.png"))));
                    squares[rCross1.getPosition()].putClientProperty("type", 2);
                    squares[rCross1.getPosition()].putClientProperty("owner", 1);
                }
                rCross2 = (Pieces)load.readObject();//Load the stone information from txt file
                if(rCross2.getStatus()){
                    squares[rCross2.getPosition()].add((new JLabel(new ImageIcon("crossred.png"))));
                    squares[rCross2.getPosition()].putClientProperty("type", 2);
                    squares[rCross2.getPosition()].putClientProperty("owner", 1);
                }
                rStar1 = (Pieces)load.readObject();//Load the stone information from txt file
                if(rStar1.getStatus()){
                    squares[rStar1.getPosition()].add((new JLabel(new ImageIcon("redstar.png"))));
                    squares[rStar1.getPosition()].putClientProperty("type", 4);
                    squares[rStar1.getPosition()].putClientProperty("owner", 1);
                }
                rStar2 = (Pieces)load.readObject();//Load the stone information from txt file
                if(rStar2.getStatus()){
                    squares[rStar2.getPosition()].add((new JLabel(new ImageIcon("redstar.png"))));
                    squares[rStar2.getPosition()].putClientProperty("type", 4);
                    squares[rStar2.getPosition()].putClientProperty("owner", 1);
                }
                rHeart = (Pieces)load.readObject();//Load the stone information from txt file
                if(rHeart.getStatus()){
                    squares[rHeart.getPosition()].add((new JLabel(new ImageIcon("redheart.png"))));
                    squares[rHeart.getPosition()].putClientProperty("type", 1);
                    squares[rHeart.getPosition()].putClientProperty("owner", 1);
                }
                JOptionPane.showMessageDialog(null, "Load Successfully");
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        else if(e.getSource() == help){ // Show the instructions of the game
            JOptionPane.showMessageDialog(null, "1 - Arrow: 1 or 2 steps forward\n"
                   + "2 - Heart: 1 step any directon \n"
                   + "3 - star: 1 or 2 steps any direction\n"
                   + "4 - Cross: unlimited steps diagonally\n");
        }
    }


    public void setStoneIcon(int coord, int owner){ // To set the piece moved into the new cell that it moved to
        if (oChessPlayer.getCurrentSelectedStoneType() == 1 || oChessPlayer.getCurrentSelectedStoneType() == 5) {
            if (oChessPlayer.getCurrentPlayer() == 1){
                squares[coord].add((new JLabel(new ImageIcon("redheart.png"))));
                redcounter++;
            } 
            else if (oChessPlayer.getCurrentPlayer() == 2) {
                squares[coord].add((new JLabel(new ImageIcon("blueheart.png"))));
                bluecounter++;
            }
        } 
        else if (oChessPlayer.getCurrentSelectedStoneType() == 2 || oChessPlayer.getCurrentSelectedStoneType() == 6) {
            if (oChessPlayer.getCurrentPlayer() == 1) {
                squares[coord].add((new JLabel(new ImageIcon("crossred.png"))));
                redcounter++;
            } else if (oChessPlayer.getCurrentPlayer() == 2) {
                squares[coord].add((new JLabel(new ImageIcon("crossblue.png"))));
                bluecounter++;
            }
        } 
        else if (oChessPlayer.getCurrentSelectedStoneType() == 4 || oChessPlayer.getCurrentSelectedStoneType() == 8) {
            if (oChessPlayer.getCurrentPlayer() == 1) {
                squares[coord].add((new JLabel(new ImageIcon("redstar.png"))));
                redcounter++;
            } 
            else if (oChessPlayer.getCurrentPlayer() == 2) {
                squares[coord].add((new JLabel(new ImageIcon("bluestar.png"))));
                bluecounter++;
            }
        } 
        else if (oChessPlayer.getCurrentSelectedStoneType() == 3 || oChessPlayer.getCurrentSelectedStoneType() == 7) {
            if (oChessPlayer.getCurrentPlayer() == 1) {
                squares[coord].add((new JLabel(new ImageIcon("redarrow1.png"))));
                redcounter++;
            } 
            else if (oChessPlayer.getCurrentPlayer() == 2) {
                squares[coord].add((new JLabel(new ImageIcon("bluearrow1.png"))));
                bluecounter++;
            }
        }
        squares[coord].putClientProperty("type", oChessPlayer.getCurrentSelectedStoneType());
        squares[coord].putClientProperty("owner", oChessPlayer.getCurrentPlayer());
        

        squares[coord].revalidate();
        squares[coord].repaint();
        
        pic.setText("Current Player: " + oChessPlayer.getNextPlayer());
        if (redcounter == 4){ // if red counter is 4 after 4 movement from the player the cross gonna be star and vice versa
            tempRedS1 = rStar1.getPosition();
            tempRedC1 = rCross1.getPosition();
            tempRedS2 = rStar2.getPosition();
            tempRedC2 = rCross2.getPosition();
            if(rStar1.getStatus()){ // to know ig the stone is live to replace it and if the status flase the stone is dead it is not going to be replaced
                unsetStoneIcon(rStar1.getPosition());
                squares[rStar1.getPosition()].add((new JLabel(new ImageIcon("crossred.png"))));
                squares[rStar1.getPosition()].putClientProperty("type", 2);
                squares[rStar1.getPosition()].putClientProperty("owner", 1);
                rStar1.setPosition(tempRedC1);
            }
            if(rStar2.getStatus()){ // to know ig the stone is live to replace it and if the status flase the stone is dead it is not going to be replaced
                unsetStoneIcon(rStar2.getPosition());
                squares[rStar2.getPosition()].add((new JLabel(new ImageIcon("crossred.png"))));
                squares[rStar2.getPosition()].putClientProperty("type", 2);
                squares[rStar2.getPosition()].putClientProperty("owner", 1);
                rStar2.setPosition(tempRedC2);
            }
            if(rCross1.getStatus()){ // to know ig the stone is live to replace it and if the status flase the stone is dead it is not going to be replaced
               unsetStoneIcon(rCross1.getPosition());
               squares[rCross1.getPosition()].add((new JLabel(new ImageIcon("redstar.png"))));
               squares[rCross1.getPosition()].putClientProperty("type", 4);
               squares[rCross1.getPosition()].putClientProperty("owner", 1);
               rCross1.setPosition(tempRedS1); 
            }
            if(rCross2.getStatus()){ // to know ig the stone is live to replace it and if the status flase the stone is dead it is not going to be replaced
                unsetStoneIcon(rCross2.getPosition());
                squares[rCross2.getPosition()].add((new JLabel(new ImageIcon("redstar.png"))));
                squares[rCross2.getPosition()].putClientProperty("type", 4);
                squares[rCross2.getPosition()].putClientProperty("owner", 1);
                rCross2.setPosition(tempRedS2);
            }
            redcounter = 0;
        }
        if (bluecounter == 4){  //if blue counter is 4 after 4 movement from the player the cross gonna be star and vice versa
            tempBlueS1 = bStar1.getPosition();
            tempBlueC1 = bCross1.getPosition();   // to get the postions for the blue starts and crosses 
            tempBlueS2 = bStar2.getPosition();
            tempBlueC2 = bCross2.getPosition();
            if(bStar1.getStatus()){  // to know ig the stone is live to replace it and if the status flase the stone is dead it is not going to be replaced
               unsetStoneIcon(bStar1.getPosition());
               squares[bStar1.getPosition()].add((new JLabel(new ImageIcon("crossblue.png"))));
               squares[bStar1.getPosition()].putClientProperty("type", 6);
               squares[bStar1.getPosition()].putClientProperty("owner", 2);
               bStar1.setPosition(tempBlueC1); 
            }
            if(bStar2.getStatus()){// to know ig the stone is live to replace it and if the status flase the stone is dead it is not going to be replaced
                unsetStoneIcon(bStar2.getPosition());
                squares[bStar2.getPosition()].add((new JLabel(new ImageIcon("crossblue.png"))));
                squares[bStar2.getPosition()].putClientProperty("type", 6);
                squares[bStar2.getPosition()].putClientProperty("owner", 2);
                bStar2.setPosition(tempBlueC2);
            }
            if(bCross1.getStatus()){// to know ig the stone is live to replace it and if the status flase the stone is dead it is not going to be replaced
                unsetStoneIcon(bCross1.getPosition());
                squares[bCross1.getPosition()].add((new JLabel(new ImageIcon("bluestar.png"))));
                squares[bCross1.getPosition()].putClientProperty("type", 8);
                squares[bCross1.getPosition()].putClientProperty("owner", 2);
                bCross1.setPosition(tempBlueS1);
            }
            if(bCross2.getStatus()){ // to know ig the stone is live to replace it and if the status flase the stone is dead it is not going to be replaced
                unsetStoneIcon(bCross2.getPosition());
                squares[bCross2.getPosition()].add((new JLabel(new ImageIcon("bluestar.png"))));
                squares[bCross2.getPosition()].putClientProperty("type", 8);
                squares[bCross2.getPosition()].putClientProperty("owner", 2);
                bCross2.setPosition(tempBlueS2);
            }
            bluecounter = 0;
        }
        return;
    }

    public void unsetStoneIcon(int coord) { // this function to remove stone from the board stones and set the type and the owner to 0
        squares[coord].removeAll();
        squares[coord].revalidate();
        squares[coord].repaint();
       
            squares[coord].setBackground(Color.white);
        
        squares[coord].putClientProperty("type", 0);
        squares[coord].putClientProperty("owner", 0);
        
        return;
    }

    public Board() {
      
        frame = new JFrame("Barsoomian Chess"); // name of the chess game
        frame.setSize(750,700); // frame size
        frame.setLocation(300,20);
        frame.setLayout(new FlowLayout());
        frame.add(main);
        main.add(center,BorderLayout.CENTER);
        main.add(west,BorderLayout.WEST);
        main.add(east,BorderLayout.EAST);
        main.add(south,BorderLayout.SOUTH);
        main.add(north,BorderLayout.NORTH);
        
        north.add(name);
        name.setFont(titleF);
        
        west.add(pic);
        
        east.add(St);
        east.add(Lo);
        east.add(save);
        east.add(swap);
        east.add(help);
        
        swap.addActionListener(this);
        St.addActionListener(this);
        save.addActionListener(this);
        Lo.addActionListener(this);
        help.addActionListener(this);
        
        for (int j = 0; j < 8; j++) { //loop to go through the lines of the array
            for (int i = 0; i < 5; i++) { // loop to go through the columns of each row and set stone images to their original position
                int coord = j * 5 + i;
                squares[coord] = new JButton();
                squares[coord].putClientProperty("coordinate", coord);
                squares[coord].addMouseListener(this);
                squares[coord].setBackground(Color.white);

                if ((coord) >= 31 && (coord) <= 39) {
                    if ((coord) == 31) {
                        squares[coord].add((new JLabel(new ImageIcon("redarrow1.png"))));
                        squares[coord].putClientProperty("type", 3);
                        squares[coord].putClientProperty("owner", 1);
                        rArrow1.setPosition(coord);
						rArrow1.setStatus(true);
                    }
                    if ((coord) == 32) {
                        squares[coord].add((new JLabel(new ImageIcon("redarrow1.png"))));
                        squares[coord].putClientProperty("type", 3);
                        squares[coord].putClientProperty("owner", 1);
                        rArrow2.setPosition(coord);
						rArrow2.setStatus(true);
                    }
                    if ((coord) == 33) {
                        squares[coord].add((new JLabel(new ImageIcon("redarrow1.png"))));
                        squares[coord].putClientProperty("type", 3);
                        squares[coord].putClientProperty("owner", 1);
                        rArrow3.setPosition(coord);
						rArrow3.setStatus(true);
                    }
                    if ((coord) == 35) {
                        squares[coord].add((new JLabel(new ImageIcon("redstar.png"))));
                        squares[coord].putClientProperty("type", 4);
                        squares[coord].putClientProperty("owner", 1);
                        rStar1.setPosition(coord);
						rStar1.setStatus(true);
                    }
                    if ((coord) == 36) {
                        squares[coord].add((new JLabel(new ImageIcon("crossred.png"))));
                        squares[coord].putClientProperty("type", 2);
                        squares[coord].putClientProperty("owner", 1);
                        rCross1.setPosition(coord);
						rCross1.setStatus(true);
                    }
                    if ((coord) == 37) {
                        squares[coord].add((new JLabel(new ImageIcon("redheart.png"))));
                        squares[coord].putClientProperty("type", 1);
                        squares[coord].putClientProperty("owner", 1);
                        rHeart.setPosition(coord);
						rHeart.setStatus(true);
                    }
                    if ((coord) == 38) {
                        squares[coord].add((new JLabel(new ImageIcon("crossred.png"))));
                        squares[coord].putClientProperty("type", 2);
                        squares[coord].putClientProperty("owner", 1);
                        rCross2.setPosition(coord);
						rCross2.setStatus(true);
                    }
                    if ((coord) == 39) {
                        squares[coord].add((new JLabel(new ImageIcon("redstar.png"))));
                        squares[coord].putClientProperty("type", 4);
                        squares[coord].putClientProperty("owner", 1);
                        rStar2.setPosition(coord);
						rStar2.setStatus(true);
                    }
                    if ((coord)== 34){
                         squares[coord].putClientProperty("type", 0);
                         squares[coord].putClientProperty("owner", 0);
                    }
                } 
                else if ((coord) >= 0 && (coord) <= 8) {
                    if ((coord) == 0) {
                        squares[coord].add((new JLabel(new ImageIcon("bluestar.png"))));
                        squares[coord].putClientProperty("type", 8);
                        squares[coord].putClientProperty("owner", 2);
                        bStar1.setPosition(coord);
						bStar1.setStatus(true);
                    }
                    if ((coord) == 1) {
                        squares[coord].add((new JLabel(new ImageIcon("crossblue.png"))));
                        squares[coord].putClientProperty("type", 6);
                        squares[coord].putClientProperty("owner", 2);
                        bCross1.setPosition(coord);
						bCross1.setStatus(true);
                    }
                    if ((coord) == 2) {
                        squares[coord].add((new JLabel(new ImageIcon("blueheart.png"))));
                        squares[coord].putClientProperty("type", 5);
                        squares[coord].putClientProperty("owner", 2);
                        bHeart.setPosition(coord);
						bHeart.setStatus(true);
                    }
                    if ((coord) == 3) {
                        squares[coord].add((new JLabel(new ImageIcon("crossblue.png"))));
                        squares[coord].putClientProperty("type", 6);
                        squares[coord].putClientProperty("owner", 2);
                        bCross2.setPosition(coord);
						bCross2.setStatus(true);
                    }
                    if ((coord) == 4) {
                        squares[coord].add((new JLabel(new ImageIcon("bluestar.png"))));
                        squares[coord].putClientProperty("type", 8);
                        squares[coord].putClientProperty("owner", 2);
                        bStar2.setPosition(coord);
						bStar2.setStatus(true);
                    }
                    if ((coord) == 6) {
                        squares[coord].add((new JLabel(new ImageIcon("bluearrow2.png"))));
                        squares[coord].putClientProperty("type", 7);
                        squares[coord].putClientProperty("owner", 2);
                        bArrow1.setPosition(coord);
						bArrow1.setStatus(true);
                    }
                    if ((coord) == 7) {
                        squares[coord].add((new JLabel(new ImageIcon("bluearrow2.png"))));
                        squares[coord].putClientProperty("type", 7);
                        squares[coord].putClientProperty("owner", 2);
                        bArrow2.setPosition(coord);
						bArrow2.setStatus(true);
                    }
                    if ((coord) == 8) { // starting position for 'Add'
                        squares[coord].add((new JLabel(new ImageIcon("bluearrow2.png"))));
                        squares[coord].putClientProperty("type", 7);
                        squares[coord].putClientProperty("owner", 2);
                        bArrow3.setPosition(coord);
						bArrow3.setStatus(true);
                    }
                    if ((coord)== 5){
                         squares[coord].putClientProperty("type", 0);
                         squares[coord].putClientProperty("owner", 0);
                    }
                    center.add(squares[coord]);
                } 
                else {
                    squares[coord].putClientProperty("type", 0);
                    squares[coord].putClientProperty("owner", 0);
                }
                center.add(squares[coord]);

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
      }
        
      
    }
      
    void flip(){
        rStar1.setPosition(39 - rStar1.getPosition());      //{
        rStar2.setPosition(39 - rStar2.getPosition());      //
        bStar1.setPosition(39 - bStar1.getPosition());      //
        bStar2.setPosition(39 - bStar2.getPosition());      //
        rCross1.setPosition(39 - rCross1.getPosition());    //
        rCross2.setPosition(39 - rCross2.getPosition());    //
        bCross1.setPosition(39 - bCross1.getPosition());    //
        bCross2.setPosition(39 - bCross2.getPosition());    //Flip the position of each piece 
        rArrow1.setPosition(39 - rArrow1.getPosition());    //
        rArrow2.setPosition(39 - rArrow2.getPosition());    //
        rArrow3.setPosition(39 - rArrow3.getPosition());    //
        bArrow1.setPosition(39 - bArrow1.getPosition());    //
        bArrow2.setPosition(39 - bArrow2.getPosition());    //
        bArrow3.setPosition(39 - bArrow3.getPosition());    //
        rHeart.setPosition(39 - rHeart.getPosition());      //
        bHeart.setPosition(39 - bHeart.getPosition());      //}
        for(int i =0 ; i < 40; i++){
            unsetStoneIcon(i);
        }
            if(!swap1){
                if(rArrow1.getStatus()){
                    squares[rArrow1.getPosition()].add((new JLabel(new ImageIcon("redarrow1.png"))));
                    squares[rArrow1.getPosition()].putClientProperty("type", 3);
                    squares[rArrow1.getPosition()].putClientProperty("owner", 1);
                }
                if(rArrow2.getStatus()){
                    squares[rArrow2.getPosition()].add((new JLabel(new ImageIcon("redarrow1.png"))));
                    squares[rArrow2.getPosition()].putClientProperty("type", 3);
                    squares[rArrow2.getPosition()].putClientProperty("owner", 1);
                }
                if(rArrow3.getStatus()){
                    squares[rArrow3.getPosition()].add((new JLabel(new ImageIcon("redarrow1.png"))));
                    squares[rArrow3.getPosition()].putClientProperty("type", 3);
                    squares[rArrow3.getPosition()].putClientProperty("owner", 1);
                }
                if(bArrow1.getStatus()){
                    squares[bArrow1.getPosition()].add((new JLabel(new ImageIcon("bluearrow2.png"))));
                    squares[bArrow1.getPosition()].putClientProperty("type", 7);
                    squares[bArrow1.getPosition()].putClientProperty("owner", 2);
                }
                if(bArrow2.getStatus()){
                    squares[bArrow2.getPosition()].add((new JLabel(new ImageIcon("bluearrow2.png"))));
                    squares[bArrow2.getPosition()].putClientProperty("type", 7);
                    squares[bArrow2.getPosition()].putClientProperty("owner", 2);
                }
                if(bArrow3.getStatus()){
                    squares[bArrow3.getPosition()].add((new JLabel(new ImageIcon("bluearrow2.png"))));
                    squares[bArrow3.getPosition()].putClientProperty("type", 7);
                    squares[bArrow3.getPosition()].putClientProperty("owner", 2);
                }
                if(rStar1.getStatus()){
                    squares[rStar1.getPosition()].add((new JLabel(new ImageIcon("redstar.png"))));
                    squares[rStar1.getPosition()].putClientProperty("type", 4);
                    squares[rStar1.getPosition()].putClientProperty("owner", 1);
                }
                if(rStar2.getStatus()){
                    squares[rStar2.getPosition()].add((new JLabel(new ImageIcon("redstar.png"))));
                    squares[rStar2.getPosition()].putClientProperty("type", 4);
                    squares[rStar2.getPosition()].putClientProperty("owner", 1);
                }
                if(bStar1.getStatus()){
                    squares[bStar1.getPosition()].add((new JLabel(new ImageIcon("bluestar.png"))));
                    squares[bStar1.getPosition()].putClientProperty("type", 8);
                    squares[bStar1.getPosition()].putClientProperty("owner", 2);
                }
                if(bStar2.getStatus()){
                    squares[bStar2.getPosition()].add((new JLabel(new ImageIcon("bluestar.png"))));
                    squares[bStar2.getPosition()].putClientProperty("type", 8);
                    squares[bStar2.getPosition()].putClientProperty("owner", 2);
                }
                squares[rHeart.getPosition()].add((new JLabel(new ImageIcon("redheart.png"))));
                squares[rHeart.getPosition()].putClientProperty("type", 1);
                squares[rHeart.getPosition()].putClientProperty("owner", 1);
                squares[bHeart.getPosition()].add((new JLabel(new ImageIcon("blueheart.png"))));
                squares[bHeart.getPosition()].putClientProperty("type", 5);
                squares[bHeart.getPosition()].putClientProperty("owner", 2);
                if(rCross1.getStatus()){
                    squares[rCross1.getPosition()].add((new JLabel(new ImageIcon("crossred.png"))));
                    squares[rCross1.getPosition()].putClientProperty("type", 2);
                    squares[rCross1.getPosition()].putClientProperty("owner", 1);
                }
                if(rCross2.getStatus()){
                    squares[rCross2.getPosition()].add((new JLabel(new ImageIcon("crossred.png"))));
                    squares[rCross2.getPosition()].putClientProperty("type", 2);
                    squares[rCross2.getPosition()].putClientProperty("owner", 1);
                }
                if(bCross1.getStatus()){
                    squares[bCross1.getPosition()].add((new JLabel(new ImageIcon("crossblue.png"))));
                    squares[bCross1.getPosition()].putClientProperty("type", 6);
                    squares[bCross1.getPosition()].putClientProperty("owner", 2);
                }
                if(bCross2.getStatus()){
                    squares[bCross2.getPosition()].add((new JLabel(new ImageIcon("crossblue.png"))));
                    squares[bCross2.getPosition()].putClientProperty("type", 6);
                    squares[bCross2.getPosition()].putClientProperty("owner", 2);
                }
            }
        
            if(swap1){
                if(rArrow1.getStatus()){
                    squares[rArrow1.getPosition()].add((new JLabel(new ImageIcon("redarrow2.png"))));
                    squares[rArrow1.getPosition()].putClientProperty("type", 3);
                    squares[rArrow1.getPosition()].putClientProperty("owner", 1);
                }
                if(rArrow2.getStatus()){
                    squares[rArrow2.getPosition()].add((new JLabel(new ImageIcon("redarrow2.png"))));
                    squares[rArrow2.getPosition()].putClientProperty("type", 3);
                    squares[rArrow2.getPosition()].putClientProperty("owner", 1);
                }
                if(rArrow3.getStatus()){
                    squares[rArrow3.getPosition()].add((new JLabel(new ImageIcon("redarrow2.png"))));
                    squares[rArrow3.getPosition()].putClientProperty("type", 3);
                    squares[rArrow3.getPosition()].putClientProperty("owner", 1);
                }
                if(bArrow1.getStatus()){
                    squares[bArrow1.getPosition()].add((new JLabel(new ImageIcon("bluearrow1.png"))));
                    squares[bArrow1.getPosition()].putClientProperty("type", 7);
                    squares[bArrow1.getPosition()].putClientProperty("owner", 2);
                }
                if(bArrow2.getStatus()){
                    squares[bArrow2.getPosition()].add((new JLabel(new ImageIcon("bluearrow1.png"))));
                    squares[bArrow2.getPosition()].putClientProperty("type", 7);
                    squares[bArrow2.getPosition()].putClientProperty("owner", 2);
                }
                if(bArrow3.getStatus()){
                    squares[bArrow3.getPosition()].add((new JLabel(new ImageIcon("bluearrow1.png"))));
                    squares[bArrow3.getPosition()].putClientProperty("type", 7);
                    squares[bArrow3.getPosition()].putClientProperty("owner", 2);
                }
                if(rStar1.getStatus()){
                    squares[rStar1.getPosition()].add((new JLabel(new ImageIcon("redstar.png"))));
                    squares[rStar1.getPosition()].putClientProperty("type", 4);
                    squares[rStar1.getPosition()].putClientProperty("owner", 1);
                }
                if(rStar2.getStatus()){
                    squares[rStar2.getPosition()].add((new JLabel(new ImageIcon("redstar.png"))));
                    squares[rStar2.getPosition()].putClientProperty("type", 4);
                    squares[rStar2.getPosition()].putClientProperty("owner", 1);
                }
                if(bStar1.getStatus()){
                    squares[bStar1.getPosition()].add((new JLabel(new ImageIcon("bluestar.png"))));
                    squares[bStar1.getPosition()].putClientProperty("type", 8);
                    squares[bStar1.getPosition()].putClientProperty("owner", 2);
                }
                if(bStar2.getStatus()){
                    squares[bStar2.getPosition()].add((new JLabel(new ImageIcon("bluestar.png"))));
                    squares[bStar2.getPosition()].putClientProperty("type", 8);
                    squares[bStar2.getPosition()].putClientProperty("owner", 2);
                }
                squares[rHeart.getPosition()].add((new JLabel(new ImageIcon("redheart.png"))));
                squares[rHeart.getPosition()].putClientProperty("type", 1);
                squares[rHeart.getPosition()].putClientProperty("owner", 1);
                squares[bHeart.getPosition()].add((new JLabel(new ImageIcon("blueheart.png"))));
                squares[bHeart.getPosition()].putClientProperty("type", 5);
                squares[bHeart.getPosition()].putClientProperty("owner", 2);
                if(rCross1.getStatus()){
                    squares[rCross1.getPosition()].add((new JLabel(new ImageIcon("crossred.png"))));
                    squares[rCross1.getPosition()].putClientProperty("type", 2);
                    squares[rCross1.getPosition()].putClientProperty("owner", 1);
                }
                if(rCross2.getStatus()){
                    squares[rCross2.getPosition()].add((new JLabel(new ImageIcon("crossred.png"))));
                    squares[rCross2.getPosition()].putClientProperty("type", 2);
                    squares[rCross2.getPosition()].putClientProperty("owner", 1);
                }
                if(bCross1.getStatus()){
                    squares[bCross1.getPosition()].add((new JLabel(new ImageIcon("crossblue.png"))));
                    squares[bCross1.getPosition()].putClientProperty("type", 6);
                    squares[bCross1.getPosition()].putClientProperty("owner", 2);
                }
                if(bCross2.getStatus()){
                    squares[bCross2.getPosition()].add((new JLabel(new ImageIcon("crossblue.png"))));
                    squares[bCross2.getPosition()].putClientProperty("type", 6);
                    squares[bCross2.getPosition()].putClientProperty("owner", 2);
                }
        }
    }

    public static void main(String[] args){
        new Board();   // start the game        
    }
}