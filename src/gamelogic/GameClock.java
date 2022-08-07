package gamelogic;

import GUI.GUI;

public class GameClock extends Thread{                                                                                  // das hab ich so erstmal aus nem tutorial übernommen

    public static boolean running = true;                                                                               // erstmal dauer run modus, aber wenn man z.B. eine pause funktion etc.
    public static boolean paused = true;                                                                                // einbauen will, ist dies nützlich

    public static void pause(){                                                                                         // SET PAUSE TRUE
        paused = true;
    }

    public static void unpause(){                                                                                       // SET PAUSE FALSE
        paused = false;
    }

    public void run(){                                                                                                  // run function with parameters for the clock
        while(running){                                                                                                 // solange clock = running:
            try {
                //noinspection BusyWait
                sleep(GUI.DEFAULT_DELAY);
                if(!paused) {
                    GameMaster.nextGen();
                }
            } catch (Exception e){
                  System.out.println("run catch");                                                                      // falls fehler, sag bescheid und gib in console zurück
            }
        }
    }
}
