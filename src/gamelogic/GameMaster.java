package gamelogic;

import GUI.GUI;
import java.util.concurrent.ThreadLocalRandom;

public class GameMaster {

    public static final int PIXEL = 512;
    public static final int CELLWIDTH = 4;                                                                             // 2er potenzen
    public static final int CELLCOUNT = PIXEL/CELLWIDTH;                                                                // number of cells in rows / columns
    public static boolean[][] cells = new boolean[CELLCOUNT][CELLCOUNT];                                                // boolean 2D Array of "cells"
    public static boolean[][] temp = new boolean[CELLCOUNT][CELLCOUNT];                                                 // TEMPORARY 2D ARRAY FOR COMPUTATION OF NEXT GEN

    public static int startCells = (CELLCOUNT * CELLCOUNT * GUI.STARTVALUE) / 100;                                      // number of living cells at the start of the game
    public static int generation = 0;                                                                                   // initialize generation
    public static int livingCells;

    public static void setup(){                                                                                         // SETUP OF A NEW GAME
        for(int x = 0; x < CELLCOUNT; x++) {
            for (int y = 0; y < CELLCOUNT; y++) {
                cells[x][y] = false;                                                                                    // SET ALL CELLS TO FALSE (DEAD) -> NEEDED IF CALLED FROM RESET
            }
        }

        livingCells = 0;                                                                                                // SET CELL COUNTER 0

        for(int i = 0; i < startCells; i++){                                                                            // solange i < startcells :
            int x = random(0, CELLCOUNT);                                                                               // random number zwischen "0" und "CELLCOUNT" für x
            int y = random(0, CELLCOUNT);                                                                               // und nach demselben prinzip für y finden, und dann:

            if(cells[x][y]) {
                i--;
            }else {
                cells[x][y] = true;                                                                                     // wert von cells an dieser zufälligen stelle im Array auf true setzen
                livingCells++;                                                                                          // FOR EACH CELL SET TO TRUE (ALIVE) INCREMENT COUNTER
            }
        }
        //GUI.sliderValue4.setText(Integer.toString(startCells));
        System.out.println("********************* -= NEW GAME =- *********************");                               // SOME INFO DISPLAYED IN CONSOLE
        System.out.println("*** " + GUI.STARTVALUE  + " % (" + livingCells + " cells) start alive with random distribution ***");
    }


    public static void nextGen(){

        generation++;                                                                                                   // generation ++ mit jedem durchlauf


        for(int x = 0; x < CELLCOUNT; x++) {
            for (int y = 0; y < CELLCOUNT; y++) {
                int n = neighbours(x, y);                                                                               // in "n" wird Anzahl der nachbarn von betrachteter zelle gespeichert

                //RULE 1: BIRTH (DEAD CELL BECOMES ALIVE CELL)
                if ((n == 3) && (!cells[x][y])) {                                                                       // wenn betrachtete zelle genau 3 nachbarn hat und selber "tot" ist:
                    temp[x][y] = true;                                                                                  // erwache zum Leben → set true
                    livingCells++;
                }

                //RULE 2: DEATH OF LONELINESS (IF A LIVING CELL HAS LESS THAN TWO NEIGHBORS IT WILL DIE)
                else if ((n < 2) && (cells[x][y])) {                                                                    // wenn anzahl nachbarn betrachteter zelle (lebend) kleiner als 2 ist:
                    temp[x][y] = false;                                                                                 // zelle stirbt -> set false
                    livingCells--;
                }

                //RULE 3: SURVIVE (IF A LIVING CELL HAS TWO OR THREE NEIGHBORS IT WILL STAY ALIVE)
                else if ((n == 2 || n == 3) && (cells[x][y])) {                                                         // wenn anzahl nachbarn einer lebenden zelle genau 2 oder 3 ist:
                    temp[x][y] = true;                                                                                  // zelle bleibt unverändert auf true
                }

                //RULE 4: DEATH BECAUSE OF OVERCROWDING (IF A LIVING CELL HAS MORE THAN THREE NEIGHBORS IT DIES)
                else if ((n > 3) && (cells[x][y])) {                                                                    // wenn anzahl nachbarn einer lebenden zelle größer als 3 ist:
                    temp[x][y] = false;                                                                                 // die zelle stirbt -> set false
                    livingCells--;
                }
            }
        }


        for (int x = 0; x < CELLCOUNT; x++) {                                                                           // SAVE temp IN cells
            System.arraycopy(temp[x], 0, cells[x], 0, CELLCOUNT);                                        // USING ARRAY COPY INSTEAD OF MANUALLY DOING IT BECAUSE IT WAS RECOMMENDED
        }
        GUI.sliderValue3.setText(Integer.toString(generation));
        GUI.sliderValue4.setText(Integer.toString(livingCells));
        System.out.println("Generation: " + generation + " || lebende Zellen: " + livingCells);                         // außerdem ausgabe der aktuellen generation sowie anzahl lebender zellen in der konsole
    }


    public static int neighbours(int x, int y) {                                                                        // function to count number of neighbours for a given cell
        int count = 0;                                                                                                  // initialize count with 0

                                                                                                                        // STARTING TO COUNT NEIGHBORS TO THE RIGHT OF GIVEN CELL X/Y AND CONTINUING CLOCKWISE BY OFFSET
        int[] xoff_normal = {1, 1, 0, -1, -1, -1, 0, 1};                                                                //    x-1   x  x+1  ||    y+1  y+1  y+1
        int[] yoff_normal = {0, -1, -1, -1, 0, 1, 1, 1};                                                                //    x-1  "X" x+1  ||     y   "Y"   y
                                                                                                                        //    x-1   x  x+1  ||    y-1  y-1  y-1

        for (int i = 0; i < 8; i++) {                                                                                   // LOOP THROUGH ALL EIGHT NEIGHBORS POSITIONS
            try {
                int x1 = x + xoff_normal[i];                                                                            // LOOK FOR NEIGHBORS
                int y1 = y + yoff_normal[i];

                if (x1 == -1) {                                                                                         // IF EDGE CASE NEIGHBOR IS ON THE OTHER SIDE OF THE BOARD (LEFT TO RIGHT)
                    x1 = CELLCOUNT - 1;
                } else if (x1 == CELLCOUNT) {                                                                           // IF EDGE CASE NEIGHBOR IS ON THE OTHER SIDE OF THE BOARD (RIGHT TO LEFT)
                    x1 = 0;
                }

                if (y1 == -1) {                                                                                         // IF EDGE CASE NEIGHBOR IS ON THE OTHER SIDE OF THE BOARD (TOP TO BOTTOM)
                    y1 = CELLCOUNT - 1;
                } else if (y1 == CELLCOUNT) {                                                                           // IF EDGE CASE NEIGHBOR IS ON THE OTHER SIDE OF THE BOARD (BOTTOM TO TOP)
                    y1 = 0;
                }

                if (cells[x1][y1])                                                                                      // IF AT GIVEN POSITION THE NEIGHBOR IS ALIVE INCREMENT NEIGHBOR COUNTER
                    count++;

            } catch (Exception e) {                                                                                     // SIMPLE ERROR CATCH
                System.out.println("error in nachbar suche");
            }
        }
        return count;                                                                                                   // return number of found neighbours
    }


    public static int random(int min, int max){                                                                         // function that finds random number in given number space
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
