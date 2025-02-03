//Shreya Shukla & Stanley Yu
//CSCI 185 Computer Programming II
//Professor Wenjia Li
//May 21 2022

import java.math.*;
import java.net.IDN;
import java.lang.*;
import java.util.*; //use list, arraylist, array and random from this package
import java.util.Scanner;
import java.lang.Integer;
import org.w3c.dom.UserDataHandler;
public class NewTicTacToe {
    //prints out grid of TicTacToe
    public static void printBoard (String [] grid){
        System.out.println();
        System.out.println();
        for (int i = 0; i< 9; i++){
            //prints a new line per row
            if (i % 3 == 0){
                System.out.println();
            }
            //prints a tab between tics
            if (i%3==1||i%3==2){
                System.out.print ("   ");
            }
            if (grid [i] == null||grid [i].equals("") ){
                System.out.print ("-");
            } 
            else System.out.print (grid[i]);
            }
        }
    //condition for winning
    public static boolean Win (String [] grid, String player){
        for (int i = 0; i < 3; i++){
            //check vertical
            if (grid[i] == player && grid[i+3] == player && grid[i+6] == player){
                return true;
            }
        }
        for (int i = 0; i < 7; i = i + 3){
            //check horizontal
            if (grid[i] == player && grid[i+1] == player && grid[i+2] == player){
                return true;
            }
        }
        //check diagonals 
        if (grid[0] == player && grid[4] == player && grid[8] == player){
            return true;
        }
        if (grid[2] == player && grid[4] == player && grid[6] == player){
            return true;
        }
        return false;
    }
    //returns the location of a random move on the array
    public static int randomMove (String [] grid, String player){
        Random rand = new Random();
        ArrayList <Integer> spaces = emptySpaces(grid);
        //index will not necessarily correspond to actual value in ArrayList spaces
        //this is because list elements will randomly be removed
        //so we need random index, and random Value 
        int randomIndex = rand.nextInt(spaces.size());
        int randomValue = spaces.get(randomIndex);
        return randomValue;
    }
    //method that returns the list of remaining empty spaces on the board
    public static ArrayList <Integer> emptySpaces (String [] grid){
        ArrayList <Integer> spaces =  new ArrayList <>();
        for (int i = 0; i < 9; i++){
            if (grid[i] == null || grid[i].equals("")){
                spaces.add (i);
            }
        }
        return spaces;
    }
    //Questions if the computer can win. If so, it returns the index of the winning move.
    public static int tryWin(String [] grid, String c){
        //tries horizontal wins
        for (int i = 0; i < 7; i = i + 3){
            if (grid[i] == c && grid [i+1] == c && (grid [i+2] == null || grid [i+2].equals(""))){
                return i+2;
            }
            if (grid[i+1] == c && grid [i+2] == c && (grid [i] == null || grid [i].equals(""))){
                return i;
            }
            if (grid[i+0] == c && grid [i+2] == c && (grid [i+1] == null || grid [i+1].equals(""))){
                return i+1;
            }
        }
 
        //tries vertical wins
        for (int j = 0; j < 3; j++){
            if (grid[j] == c && grid [j+3] == c && (grid [j+6] == null || grid [j+6].equals(""))){
                return j+6;
            }
            if (grid[j+3] == c && grid [j+6] == c && (grid [j] == null || grid [j].equals(""))){
                return j;
            }
            if (grid[j] == c && grid [j+6] == c &&(grid [j+3] == null || grid [j+3].equals(""))){
                return j+3;
            }
        }
        
        //blocks diagonal wins
        if (grid[0] == c && grid [4] == c && (grid [8] == null || grid [8].equals(""))){
            return 8;
        }
        if (grid[8] == c && grid [4] == c && (grid [0] == null || grid [0].equals(""))){
            return 0;
        }
        if (grid[8] == c && grid [0] == c && (grid [4] == null || grid [4].equals(""))){
            return 4;
        }
        if (grid[2] == c && grid [6] == c && (grid [4] == null || grid [4].equals(""))){
            return 4;
        }
        if (grid[6] == c && grid [4] == c && (grid [2] == null || grid [2].equals(""))){
            return 2;
        }
        if (grid[2] == c && grid [4] == c && (grid [6] == null || grid [6].equals(""))){
            return 6;
        }
        return -1;
    }

    //Questions if the computer can block the other player's win. If so, it returns the index of the blocking move.
    public static int blockWin(String [] grid, String c){
        String other;
        if (c == "X"){
            other = "O";
        }
        else other = "X";
        //tries horizontal wins
        for (int i = 0; i < 7; i = i + 3){
            if (grid[i] == other && grid [i+1] == other && (grid [i+2] == null || grid [i+2].equals(""))){
                return i+2;
            }
            if (grid[i+1] == other && grid [i+2] == other && (grid [i] == null || grid [i].equals(""))){
                return i;
            }
            if (grid[i+0] == other && grid [i+2] == other && (grid [i+1] == null || grid [i+1].equals(""))){
                return i+1;
            }
        }
 
        //tries vertical wins
        for (int j = 0; j < 3; j++){
            if (grid[j] == other && grid [j+3] == other && (grid [j+6] == null || grid [j+6].equals(""))){
                return j+6;
            }
            if (grid[j+3] == other && grid [j+6] == other && (grid [j] == null || grid [j].equals(""))){
                return j;
            }
            if (grid[j] == other && grid [j+6] == other &&(grid [j+3] == null || grid [j+3].equals(""))){
                return j+3;
            }
        }
        
        //blocks diagonal wins
        if (grid[0] == other && grid [4] == other && (grid [8] == null || grid [8].equals(""))){
            return 8;
        }
        if (grid[8] == other && grid [4] == other && (grid [0] == null || grid [0].equals(""))){
            return 0;
        }
        if (grid[8] == other && grid [0] == other && (grid [4] == null || grid [4].equals(""))){
            return 4;
        }
        if (grid[2] == other && grid [6] == other && (grid [4] == null || grid [4].equals(""))){
            return 4;
        }
        if (grid[6] == other && grid [4] == other && (grid [2] == null || grid [2].equals(""))){
            return 2;
        }
        if (grid[2] == other && grid [4] == other && (grid [6] == null || grid [6].equals(""))){
            return 6;
        }
        return -1;
    }
    public static int easyMode(String [] grid, String tic){
            return randomMove(grid, tic);
    }
    //In Medium mode, the computer tries to win by strategizing
    public static int mediumMode(String grid [] , String player){
        int m = tryWin(grid, player);
        int n = blockWin(grid, player);
        //try to win the game
        if (!(m == -1)){
            return m;
        }
        //if you can't win, block the other player's win
        else if (!(n == -1)){
            return n;
        }
        else return randomMove(grid, player);
    }
    //returns true if the grid has any empty spaces, else returns false
    public static Boolean isMovesLeft(String [] grid){
        for (int i = 0; i < 9; i++){
            if (grid[i] == ""){
                return true;

            }
        }
        return false;
    }
    //returns +10, -10 or 0
    public static int evaluate (String [] grid, String c){
        String other;
        //c must be either "X" or "O"
        // if c is X other player is O
        if (c == "X"){
            other = "O";
        } // and if c is O, other player is X
        else other = "X";
        if (Win(grid, c) == true){
            return +10;
        }
        else if (Win(grid, other) == true){
            return -10;
        }
        else return 0;


    }
  //A minimax program that runs a search tree and finds the best possible outcome for the player
    public static int Minimax(String [] grid, String player, int depth, boolean isTurn){
        int score = evaluate(grid, player); //checks to see if either player won and returns the score
        String other;
        if (player == "X"){
            other = "O";
        }
        else other = "X";

        //if the current player or the opponent has won the game return their evaluated score
        if (score == 10 || score == -10) {
            return score;
        }
        
        // If there are no more moves and there is still no winner then it is a tie
        if (isMovesLeft(grid) == false){
            return 0;
        }
        // If it's the maximizer's move, we can predict the outcomes of our next possible moves
        if (isTurn == true){
            int maxScore = -1000; //a really low value
            //traverse all cells
            for (int i = 0; i < 9; i++){
                // reiterate through the grid, checking is each space is empty
                if (grid[i] == null||grid[i].equals("")){
                    // play the moves
                    grid[i] = player;
                    //recursive function calling, using parameter value !isTurn (triggers the comp to predict
                    //the opponent's move)
                    //calculates the maximum score that will benefit the player the most
                    maxScore = Math.max(maxScore, Minimax(grid, player, depth + 1, !isTurn));
                    //printBoard(grid);
                    //undos the move player
                    grid[i] = "";
                    }
                }
            return maxScore;
            }
 
        // If it's minimizer's move, we can predict the opponent's strategy
        else {
            int minScore = 1000; //a really big value
            //traverse all cells
            for (int i = 0; i < 9; i++){
                // reiterate through the grid, checking is each space is empty
                if (grid[i] == null||grid[i].equals("")){
                    // play the moves
                    grid[i] = other;
                    //calculates the minimum score that will benefit the other player the least
                    minScore = Math.min(minScore, Minimax (grid, player, depth + 1, !isTurn));
                    //printBoard(grid);
                    //undos the move player
                    grid[i] = "";
                    }
                }
            return minScore;
        }
    }
    

    
    //plays the best move in the grid
    public static int playBestMove (String [] grid, String player){
        int bestScore = -1000; // extremely low value
        int bestMoveIndex = 0; //the index will never be -1, because there will always be a move with a score that's better than -1000 (
        // even losing the game is only -10)
        int temp;
        for (int i = 0; i < 9; i++){
            if (grid[i].equals ("") || grid[i] == "" || grid[i] == null){
            //play the move
            grid[i] = player;
            //predict the outcome of playing that move, which will be either -10 (loss), 0 (tie)  or 10 (win)
            temp = Minimax(grid, player, 0, true);
            System.out.println();
            System.out.println ("index : " + i + " best score: " + temp);
            if (temp > bestScore){
                bestMoveIndex = i;
                bestScore = temp;
            }
            //remove the move
            grid[i] = "";
            }
        }
        //returns the index of the best possible move
        return bestMoveIndex;
    }

    //In Hard mode, the computer calculates the best possible move and put tics accordingly on the board
    public static int hardMode(String [] grid, String player){
        return playBestMove(grid, player);
    }
    //plays the Computer's next move
    public static int nextCompMove (String [] grid, String player, String mode){
        if (mode.equals("easy")){
            return easyMode(grid, player);
        }
        if (mode.equals("medium")){
            return mediumMode(grid, player);
        }
        if (mode.equals("hard")){
            return hardMode(grid, player);
       }
       //this shouldn't happen
       return -1;
    }
    //plays a move given a index
    public static String [] playMove (String [] grid, String player, int index){
        if (grid [index] == null || grid [index].equals ("")){
            grid [index] = player;
        }
        return grid;
    }
    //test the game from the console
    public static void playGame (String [] grid, String user, String mode){
        Scanner input = new Scanner(System.in);
        System.out.println();
        int move = 0;
        String comp;
        if (user == "X"){
                comp = "O";
            }
            else {
               comp = "X"; 
            }
        while ((move < 9) && (Win (grid, user) == false) && (Win (grid, comp) == false)){
            //user's turn //you can switch this so that the comp goes first and the user second
            if (move % 2 == 0){
                int index  = input.nextInt();
                grid [index] = user;
            }
            else{
                grid = playMove (grid, comp, nextCompMove (grid, comp, mode));
            }
            printBoard (grid);
            move++;
        }
        if (Win (grid, user) == true){
            System.out.println("You win!");
        }
        else if (Win (grid, comp) == true){
            System.out.println("Computer wins!");
        }
        else {
            System.out.println("Tie!");
        }
    }
    //main method
    public static void main (String [] args){
        //String [] grid = {"O", "", "X", "X","","", "X","O","O"};
        //you can test the methods here by calling them on grid
        //nextCompMove (grid, "X", "hard");        
        }
}
