package com.gambelli.utils;

import java.util.Scanner;

public final class Utilities {
    private static int player = 1;
    private static int sq_cord = 0;

    /** Asks to choose the game mode through keyboard input request **/
    public static int mode() {

        //Until the input is not valid, keep asking to choose
        while (true) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("\n\tGame Modes : " +
                    "\n\t\t1 ----> Player vs Player" +
                    "\n\t\t2 ----> Player vs Cpu" +
                    "\n\n\tType '1' or '2' to choose game mode : ");

            if (scanner.hasNextInt()) {
                int mode = scanner.nextInt();
                if (mode == 1 || mode == 2) {
                    return mode;
                } else {
                    System.out.println("\nThis mode does not exist! Input allowed to choose a game mode : '1' - '2'");
                }
            } else {
                System.out.println("\nInput non valid! Input allowed to choose a game mode : '1' - '2'");
            }
        }

    }

    /** Sets players names **/
    public static String readPlayer(int mode) {
        Scanner scanner = new Scanner(System.in);

        switch (mode) {
            case 1 :
                System.out.print("\n\tPlayer " + (player == 1 ? "1 " : "2 ") + "Name : ");
                player++;
                break;
            case 2 :
                System.out.print("\n\tPlayer Name : ");
                break;
        }
        return scanner.next();
    }

    /** Asks row and column for player next move **/
    public static int readSquare() {

        //Until the input is not valid, keep asking to choose a row or a column
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Choose a "+ (sq_cord == 0 ? "row : " : "column: "));

            if (scanner.hasNextInt()) {
                int coordinate = scanner.nextInt();
                if (coordinate == 0 || coordinate == 1 || coordinate == 2) {
                    sq_cord = sq_cord == 0 ? 1 : 0;
                    return coordinate;
                } else {
                    System.out.println("\nThis " + (sq_cord == 0 ? "row" : "column") + " does not exist! Input allowed to choose a " + (sq_cord == 0 ? "row" : "column") + ": '0' - '1' - '2'");
                    sq_cord = sq_cord == 0 ? 0 : 1;
                }
            } else {
                System.out.println("\nInput non valid! Input allowed to choose a" + (sq_cord == 0 ? "row" : "column") + ": '0' - '1' - '2'");
                sq_cord = sq_cord == 0 ? 0 : 1;
            }
        }
    }

    /** Asks if a player want start first or second **/
    public static int firstOrSecond() {

        //Until the input is not valid, keep asking to choose
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("\n\tType :" +
                    "\n\t\t1 ----> to start first" +
                    "\n\t\t2 ----> to start second" +
                    "\n\n\tType '1' or '2' to start first or second : ");


            if (scanner.hasNextInt()) {
                int firstOrSecond = scanner.nextInt();
                if (firstOrSecond == 1 || firstOrSecond == 2) {
                    return firstOrSecond;
                }else{
                    System.out.println("\nInput not valid! Only input '1' - '2' allowed");
                }
            } else {
                System.out.println("\nInput not valid! Only input '1' - '2' allowed");
            }
        }
    }

    /** Asks to choose the difficulty of the cpu **/
    public static int chooseDifficulty() {

        //Until the input is not valid, keep asking to choose
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("\n\tCpu difficulty : " +
                    "\n\t\teasy ----> easy cpu difficulty " +
                    "\n\t\thard ----> hard cpu difficulty" +
                    "\n\n\tType 'easy' or 'hard' to choose cpu difficulty : ");
            if (scanner.hasNextLine()) {
                String difficulty = scanner.nextLine();
                if (difficulty.equalsIgnoreCase("easy") || difficulty.equalsIgnoreCase("hard")) {
                    return difficulty.equalsIgnoreCase("easy") ? 1 : 2;
                } else {
                    System.out.println("\nThis cpu difficulty does not exist! Input allowed to choose cpu difficulty" +
                            " : 'easy' - 'hard'");
                }
            } else {
                System.out.println("\nInput non valid! Input allowed to choose a cpu difficulty : 'easy' - 'hard'");
            }
        }
    }
}
