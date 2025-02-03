//Shreya Shukla & Stanley Yu
//CSCI 185 Computer Programming II
//Professor Wenjia Li
//May 21 2022

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

public class TicTacToeGrid extends JPanel {
    //objects
    private JButton tic1 = new JButton ("");
    private JButton tic2 = new JButton ("");
    private JButton tic3 = new JButton ("");
    private JButton tic4 = new JButton ("");
    private JButton tic5 = new JButton ("");
    private JButton tic6 = new JButton ("");
    private JButton tic7 = new JButton ("");
    private JButton tic8 = new JButton ("");
    private JButton tic9 = new JButton ("");
    private JButton[] jButtons = {tic1, tic2, tic3, tic4, tic5, tic6, tic7, tic8, tic9};
    private String [] grid = {"", "", "", "", "", "", "", "", ""};
    private Boolean gameOver = false;
    //panels
    private JPanel centerpanel = new JPanel ();
    private JPanel rightpanel = new JPanel ();
    //start and clear buttons
    private JButton start = new JButton ("Start");
    private JButton clear = new JButton ("Clear");
    //pieces
    private JRadioButton setX = new JRadioButton ("Piece X"); //user piece
    private JRadioButton setO = new JRadioButton ("Piece O"); //user piece
    //turn
    private JRadioButton player1 = new JRadioButton ("First Player"); //user goes first
    private JRadioButton player2 = new JRadioButton ("Second Player"); //second goes second
    //modes
    private JRadioButton setEasy = new JRadioButton ("Easy");
    private JRadioButton setMedium = new JRadioButton ("Medium");
    private JRadioButton setHard = new JRadioButton ("Hard");
    
    private JButton stats = new JButton ("Stats");
    private JButton exit = new JButton ("Exit");
    //default values
    private String mode = "easy"; //mode is easy by default
    private String user = "X"; //user is "X" by default
    private String comp = "O";//comp is "O" by default
    private Boolean userFirst =  true; //user goes first by default
    private Boolean gameBegan = false; //game does not begin untill gameBegan is set to true
    //counters
    int gamesPlayed = 0;
    int gamesWon = 0;
    int gamesLost = 0;
    int gamesTied = 0;
    
    //constructor
    public TicTacToeGrid () {
        add (centerpanel, BorderLayout.WEST);
        centerpanel.setPreferredSize (new Dimension (550, 500));
        add (rightpanel, BorderLayout.EAST);
        rightpanel.setPreferredSize (new Dimension (150, 550));
        //adds accessory buttons
        rightpanel.add (start);
        rightpanel.add (clear);
        rightpanel.add (setX);
        rightpanel.add (setO);
        rightpanel.add (player1);
        rightpanel.add (player2);
        rightpanel.add (setEasy);
        rightpanel.add (setMedium);
        rightpanel.add (setHard);
        rightpanel.add (stats);
        rightpanel.add (exit);
        //uses a layout manager to divide the centerpanel into a 3x3 grid
        GridLayout TicTacToeGridLayout = new GridLayout(3, 3);
        centerpanel.setLayout(TicTacToeGridLayout);
        //adds the buttons
        for (int i = 0; i < 9; i++){
            JButton tic = jButtons[i];
            tic.setFont(new Font("Tahoma", Font.PLAIN, 24));
            //disables button
            whatButton(i).setEnabled(false);
            centerpanel.add(tic);
            //add a action listener to each button
            tic.addActionListener(new ActionListener (){
                public void actionPerformed(ActionEvent e){
                    //if the button clicked is empty, set the button to the user's piece, and add it to grid []
                    if (isEmptySpot(whatIndex(tic)) == true){ 
                        tic.setText (user);
                        tic.setEnabled (false);
                        grid[whatIndex(tic)] = user;
                        NewTicTacToe.printBoard (grid);
                    }
                    //check if the user wins by clicking this button
                    Win ();
                    //plays the computer's move if the board isn't full
                    if (boardFull() == false && gameOver == false) {
                        //play the computer's move
                        int index = NewTicTacToe.nextCompMove(grid, comp, mode);
                        //if the button clicked is empty, set the button to the comp's piece, and add it to grid []
                        if (isEmptySpot(index) == true){
                            whatButton(index).setText(comp);
                            whatButton(index).setEnabled(false);
                            grid[index] = comp;
                        }
                        //NewTicTacToe.printBoard (grid);
                        //check if the comp wins by choosing this button
                        Win ();
                    }
                }
            });
        }
        //action listeners for each button
        clearBoard();
        choosePiece();
        chooseMode();
        chooseFirstPlayer();
        exitGame();
        displayStats();
        startGame();
    }
    
    public void playGame (){
        //if comp goes first
        if (userFirst == false){
            //computer's first move
            int index = NewTicTacToe.nextCompMove(grid, comp, mode);
            whatButton(index).setText(comp);
            whatButton(index).setEnabled(false);
            grid[index] = comp;
            for (int i = 0; i < 9; i++){
                if (!(i == index)){
                    whatButton(i).setEnabled (true);
                }
            
            }
        }
        //if usr goes first
        else {
                for (int i = 0; i < 9; i++){
                    whatButton(i).setEnabled (true);
                }
            }
    }
    //returns a boolean if the space on the grid is empty
    public Boolean isEmptySpot (int i){
        if ((grid[i] == null) || (grid[i].equals(""))) return true;
        else return false;
    }
    //returns a message if the game is over
    public void Win (){
        //check if user has won
        if (NewTicTacToe.Win (grid, user) == true) {
            JOptionPane.showMessageDialog(this,"You won! Game over.");
            gameOver = true;
            for (int i = 0; i < 9; i++){
                whatButton(i).setEnabled (false);
            }
            gamesWon++;
            gamesPlayed++;
        }  
        //check if comp has won
        if (NewTicTacToe.Win (grid, comp) == true){
            JOptionPane.showMessageDialog(this,"Computer won! Game over.");
            gameOver = true;
            for (int i = 0; i < 9; i++){
                whatButton(i).setEnabled (false);
            }
            gamesLost++;
            gamesPlayed++;
        }
        //if neither has won and the grid is full
        if ((NewTicTacToe.Win (grid, user) == false)&& (NewTicTacToe.Win (grid, comp) == false) && (this.boardFull() == true)){
            JOptionPane.showMessageDialog(this,"Tie! Game over.");
            gameOver = true;
            gamesTied++;
            gamesPlayed++;
        }
    }
    //returns the index of the button in the JButton list jButtons 
    public int whatIndex (JButton e){
        if (e == tic1) return 0;
        if (e == tic2) return 1;
        if (e == tic3) return 2;
        if (e == tic4) return 3;
        if (e == tic5) return 4;
        if (e == tic6) return 5;
        if (e == tic7) return 6;
        if (e == tic8) return 7;
        if (e == tic9) return 8;
        return 0; //this shouldn't happen
    }
    //returns the button that the index refers to
    public JButton whatButton (int i){
        if (i == 0)return tic1;
        if (i == 1)return tic2;
        if (i == 2)return tic3;
        if (i == 3)return tic4;
        if (i == 4)return tic5;
        if (i == 5)return tic6;
        if (i == 6)return tic7;
        if (i == 7)return tic8;
        if (i == 8)return tic9;
        return tic1; //this shouldn't happen
    }
    //tells us if the board is full
    public boolean boardFull (){
        if ((tic1.isEnabled() == false) && (tic2.isEnabled() == false) && (tic3.isEnabled() == false) && (tic4.isEnabled() == false) && 
        (tic5.isEnabled() == false) && (tic6.isEnabled() == false) && (tic7.isEnabled() == false) && (tic8.isEnabled() == false)
        && (tic9.isEnabled() == false)){
            return true;
        }
        else return false;
    }
    //starts the game
    public void startGame (){
        start.addActionListener(new ActionListener (){
                public void actionPerformed(ActionEvent e){
                    gameBegan = true;
                    //disables all other buttons
                    setX.setEnabled (false);
                    setO.setEnabled (false);
                    player1.setEnabled (false);
                    player2.setEnabled (false);
                    setEasy.setEnabled (false);
                    setMedium.setEnabled (false);
                    setHard.setEnabled (false);
                    start.setEnabled (false);
                    //starts the game
                    playGame();
                }
            });
        }
    //clears the tic tac toe board
    public void clearBoard (){
        clear.addActionListener(new ActionListener (){
                public void actionPerformed(ActionEvent e){
                    //if the game was quit midway, add 1 to the number of games played
                    if ((NewTicTacToe.Win (grid, user) == false)&& (NewTicTacToe.Win (grid, comp) == false) && (boardFull() == false)){
                        gamesPlayed++;
                    }
                    for (int i = 0; i < 9; i++){
                        grid [i] = "";
                        whatButton(i).setEnabled(false);
                        whatButton(i).setText("");
                    }
                    //NewTicTacToe.printBoard (grid);
                    //resets the board
                    gameBegan = false;
                    //enables all the buttons
                    
                    setX.setEnabled (true);
                    setO.setEnabled (true);
                    
                    player1.setEnabled (true);
                    player2.setEnabled (true);
                    
                    setEasy.setEnabled (true);
                    setMedium.setEnabled (true);
                    setHard.setEnabled (true);
                    
                    start.setEnabled (true);
                    //sets gameOver to be false again
                    gameOver = false;
                    
                }
            });
        }
    //exits the game
    public void exitGame (){
        exit.addActionListener(new ActionListener (){
                public void actionPerformed(ActionEvent e){
                    int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the game?");
                    System.out.println(input);
                    if (input == 0){
                        System.exit (0);
                    }
                }
            });
        }
    //displays the stats of the game
    public void displayStats (){
        stats.addActionListener(new ActionListener (){
                public void actionPerformed(ActionEvent e){
                    String output = "";
                    output += "Number of games played: " + gamesPlayed;
                    output += "\nNumber of games won: " + gamesWon;
                    output += "\nNumber of games lost: " + gamesLost;
                    output += "\nNumber of games tied: " + gamesTied;
                    JOptionPane.showMessageDialog(null, output);
                }
            });
        }
    //choses the mode from the button group mode // default is easy mode
    public void chooseMode (){
        setEasy.setSelected (true);
        setEasy.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent e){
                setEasy.setSelected (true);
                setMedium.setSelected (false);
                setHard.setSelected (false);
                mode = "easy";
            }
        });
        setMedium.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent e){
                    setEasy.setSelected (false);
                    setMedium.setSelected (true);
                    setHard.setSelected (false);
                    mode = "medium";
            }
        });
        setHard.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent e){
                    setEasy.setSelected (false);
                    setMedium.setSelected (false);
                    setHard.setSelected (true);
                    mode = "hard";
            }
        });
    }
    //choses the mode from the button group mode // default is easy mode
    public void choosePiece (){
        setX.setSelected (true);
        setX.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent e){
                setX.setSelected (true);
                setO.setSelected (false);
                user = "X";
                comp = "O";
            }
        });
        setO.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent e){
                setX.setSelected (false);
                setO.setSelected (true);
                user = "O";
                comp = "X";
                    
            }
        });
    }
    //choses the first player from the user interface
    public void chooseFirstPlayer (){
        player1.setSelected (true);
        player1.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent e){
                player1.setSelected (true);
                player2.setSelected (false);
                userFirst = true;
            }
        });
        player2.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent e){
                player1.setSelected (false);
                player2.setSelected (true);
                userFirst = false;
                    
            }
        });
    }
    //main method
    public static void main (String[] args) {
        JFrame jFrame = new JFrame("Tic Tac Toe Game");
        jFrame.getContentPane().add(new TicTacToeGrid());
        jFrame.setBounds(500, 500, 800, 600);
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);
    }

} 