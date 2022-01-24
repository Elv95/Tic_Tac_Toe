package com.gambelli.tris;

import com.gambelli.utils.Utilities;

import java.util.ArrayList;
import java.util.Random;


public class Tris {

    private static Square [][] board;
    private static Player p1, p2;


    /** Populates a matrix of com.gambelli.tris.Square **/
    public Tris(){
        board = new Square[3][3];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Square(i, j);
            }
        }
    }


    /** Initialization of the players **/
    private void init_players(int mode) {
        if (mode == 1) { //player vs player mode
            p1 = new Player(Utilities.readPlayer(1), Status.X);
            p2 = new Player(Utilities.readPlayer(1), Status.O);
        } else { // player vs cpu mode
            p1 = new Player(Utilities.readPlayer(2), Utilities.firstOrSecond() == 1 ? Status.X : Status.O);
            p2 = new Player("cpu", p1.getStatusPlayer() == Status.X ? Status.O : Status.X);
        }
    }


    /** Starts the game depending on the game mode (player vs player or cpu vs player)**/
    public void start_game(int mode) {
        int square_available = 9;
        boolean winner = false;
        String result = "";

        init_players(mode);
        int difficulty = mode != 1 ? Utilities.chooseDifficulty() : 0;
        Player player = p1.getStatusPlayer() == Status.X ? p1 : p2;

        printBoard();

        //Until there are square available and there is no winner (tris on the board), keep playing
        while (square_available > 0 && !winner) {
            System.out.println("\nPlayer "+ (player.getStatusPlayer() == Status.X ? "(1) " : "(2) ")
                    + player.getName() + " Turn");

            if(mode == 1) { //player vs player mode
                //Until the square chosen is not valid (already occupied), keep asking to choose one
                while (!setSquare(Utilities.readSquare(), Utilities.readSquare(), player)) {
                    printBoard();
                    System.out.println("\nCell already occupied. Retry a new move, " + player.getName() + " !");
                }
            }else{ //player vs cpu mode
                if(!player.getName().equals("cpu")){ //player turn
                    //Until the square chosen is not valid (already occupied), keep asking to choose one
                    while (!setSquare(Utilities.readSquare(), Utilities.readSquare(), player)) {
                        printBoard();
                        System.out.println("\nCell already occupied. Retry a new move, " + player.getName() + " !");
                    }
                }else{ //cpu turn
                   cpuMove(player, difficulty);
                }
            }

            printBoard();

            //check if there is a winner after the move just made
            if(isWin() != Status.E){
                result = "THE WINNER IS : " + player;
                winner = true;
            }

            //change player for next turn
            player = player.getStatusPlayer() == p1.getStatusPlayer() ? p2 : p1;
            square_available--;
        }
        System.out.println("\n" + (result.equals("") ? "\nNOBODY WINS : DRAW" : result));
    }


    /** Returns true and sets the square with X or O if the square is empty, returns false otherwise **/
    private static boolean setSquare(int row, int column, Player player) {
        if(board[row][column].getStatus() == Status.E){
            board[row][column].setStatus(player.getStatusPlayer());
            System.out.println("\nPlayer "+ (player.getStatusPlayer() == Status.X ? "(1) " : "(2) ") + player.getName()
                    + " choose [" + row + "][" + column + "]");
            return true;
        }
        return false;
    }


    /** Decides the function of next choice based on the difficulty **/
    private void cpuMove(Player cpu, int difficulty) {
        if(difficulty == 1){
            randomMove(cpu);
        }else{
            bestMove(cpu);
        }
    }


    /** Chooses and sets the best possible move for the cpu **/
    private void bestMove(Player cpu) {
        int bestScore = Integer.MIN_VALUE;
        int [] bestMove = {-1,-1};
        Player other_player = cpu.getStatusPlayer() == p1.getStatusPlayer() ? p2 : p1;

        //if cpu is first to start, randomizes the move
        if(getEmptySquare().size() == 9){
            randomMove(cpu);
        }else {
            //considers every single move possible and choose the one with the highest score.
            for (Square square : getEmptySquare()) {
                board[square.getRow()][square.getColumn()].setStatus(cpu.getStatusPlayer());
                int score = minimax(false, other_player);
                board[square.getRow()][square.getColumn()].setStatus(Status.E);
                if (score > bestScore) {
                    bestMove[0] = square.getRow();
                    bestMove[1] = square.getColumn();
                    bestScore = score;
                }
            }
            setSquare(bestMove[0], bestMove[1], cpu);
        }
    }


    /** Recursive algorithm, which allows to identify the best move, analyzing backwards the game tree
     starting from the terminal nodes (i.e. from the possible situations in which the game can end)
     and progressively going up to the current position of the board **/
    private int minimax(boolean isMaximizing, Player player) {
        Player other_player = player.getStatusPlayer() == p1.getStatusPlayer() ? p2 : p1;

        // If is a leaf of tree, return the score
        if(isWin() != Status.E){ //if the previous was a winning move
            return !isMaximizing ? (getEmptySquare().size() + 1) : -1 * (getEmptySquare().size() + 1);
        }else if(getEmptySquare().size() == 0){ // or there are no more empty squares
            return 0;
        }

        if(isMaximizing){ //if the player is the "maximizer" ---> try to maximize the score
            int maxScore = Integer.MIN_VALUE;
            for (Square square : getEmptySquare()) {
                board[square.getRow()][square.getColumn()].setStatus(player.getStatusPlayer());
                maxScore = Math.max(maxScore, minimax(false, other_player));
                board[square.getRow()][square.getColumn()].setStatus(Status.E);
            }
            return maxScore;
        }else{ //if the player is the "minimizer" ---> try to minimize the score
            int minScore = Integer.MAX_VALUE;
            for (Square square : getEmptySquare()) {
                board[square.getRow()][square.getColumn()].setStatus(player.getStatusPlayer());
                minScore = Math.min(minScore, minimax(true, other_player));
                board[square.getRow()][square.getColumn()].setStatus(Status.E);
            }
            return minScore;
        }
    }


    /** Randoms the cpu move by choosing a random empty square **/
    private static void randomMove(Player cpu) {
        Random cpuMove = new Random();
        int i = cpuMove.nextInt(getEmptySquare().size());
        setSquare(getEmptySquare().get(i).getRow(), getEmptySquare().get(i).getColumn(), cpu);
    }


    /** Checks if there is a tris on the board **/
    private static Status isWin() {
        int x = 0, o = 0;

        // com.gambelli.tris.Tris on a row
        for (Square[] squares : board) {
            for (int i = 0; i < board[0].length; i++) {
                if (squares[i].getStatus() == Status.X) {
                    x++;
                    if (x == 3) return Status.X;
                } else {
                    if (squares[i].getStatus() == Status.O) {
                        o++;
                        if (o == 3) return Status.O;
                    }
                }
            }
            x = 0;
            o = 0;
        }

        // com.gambelli.tris.Tris on a column
        for(int i = 0; i < board[0].length; i++) {
            for (Square[] squares : board) {
                if (squares[i].getStatus() == Status.X) {
                    x++;
                    if (x == 3) return Status.X;
                } else {
                    if (squares[i].getStatus() == Status.O) {
                        o++;
                        if (o == 3) return Status.O;
                    }
                }
            }
            x = 0;
            o = 0;
        }
        // com.gambelli.tris.Tris on the diagonal [0][0] - [1][1] - [2][2]
        for(int i = 0;i < board.length;i++) {
            if(board[i][i].getStatus() == Status.X) {
                x++;
                if(x==3) return Status.X;
            } else {
                if(board[i][i].getStatus() == Status.O) {
                    o++;
                    if(o == 3) return Status.O;
                }
            }
        }
        x = 0;
        o = 0;
        int i = 2;
        // com.gambelli.tris.Tris on the diagonal [0][2] - [1][1] - [2][0]
        for (Square[] squares : board) {
            if (squares[i].getStatus() == Status.X) {
                x++;
                if (x == 3) return Status.X;
            } else {
                if (squares[i].getStatus() == Status.O) {
                    o++;
                    if (o == 3) return Status.O;
                }
            }
            i--;
        }
        return Status.E;
    }


    /** Returns a list of all the empty Squares on the board **/
    private static ArrayList<Square> getEmptySquare(){
        ArrayList<Square> emptySquare = new ArrayList<>();
        for (Square[] squares : board) {
            for (int i = 0; i < board[0].length; i++) {
                if (squares[i].getStatus() == Status.E) {
                    emptySquare.add(squares[i]);
                }
            }
        }
        return emptySquare;
    }


    /** Prints the chessboard status **/
    private static void printBoard(){
        int i, j;
        System.out.print("\n");

        for (i = 0; i < board.length + 5; i++) {
            if (i < 5) {
                System.out.print("  ");
            } else {
                System.out.print((i - 5) + "   ");
            }
        }

        for (i = 0; i < board.length; i++) {
            System.out.print("\n");

            for(j= 0; j< board.length + 8;j++){
                if(j< 8){
                    System.out.print(" ");
                }else{
                    System.out.print("----");
                }
            }
            System.out.println("-");
            System.out.print("      "+i+" ");

            for (j = 0; j < board[0].length; j++) {
                System.out.print("| ");
                switch (board[i][j].getStatus()) {
                    case E:
                        System.out.print(" " + " ");
                        break;
                    case X:
                        System.out.print("X");
                        System.out.print(" ");
                        break;
                    case O:
                        System.out.print("O");
                        System.out.print(" ");
                        break;
                }
            }
            System.out.print("|");
        }
        System.out.print("\n");
        for(j= 0; j< board.length + 8;j++){
            if(j<8){
                System.out.print(" ");
            }else{
                System.out.print("----");
            }
        }
        System.out.println("-");
    }

}

