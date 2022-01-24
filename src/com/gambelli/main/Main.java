package com.gambelli.main;

import com.gambelli.tris.Tris;
import com.gambelli.utils.Utilities;

public class Main {

    public static void main(String[] args) {

        System.out.println("\nTic Tac Toe is a game for two players who take turns marking the spaces in a three-by-three " +
                "grid with X or O. \nThe player who succeeds in placing three of their marks in a horizontal, vertical, " +
                "or diagonal row is the winner.");

        Tris tris = new Tris();
        tris.start_game(Utilities.mode());

    }
}
