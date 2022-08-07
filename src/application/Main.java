package application;

import GUI.GUI;
import gamelogic.GameClock;
import gamelogic.GameMaster;


public class Main {
    public static void main(String[] args) {

        GUI g = new GUI();                                                                                              // initiation of new GUI object "g"
        GameMaster.setup();                                                                                             // call for setup()
        g.create();                                                                                                     // create() function from the GUI class

        GameClock gc = new GameClock();                                                                                 // new game clock "gc"
        gc.start();                                                                                                     // start the game clock
    }
}

//TODO: sometimes the living cell number is negative , find out why! ( --> problem occurs with big number of starting cells  /  probably time to compute all cells before next cycle is not enough