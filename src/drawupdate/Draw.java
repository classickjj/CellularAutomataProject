package drawupdate;

import GUI.GUI;
import gamelogic.GameMaster;
import javax.swing.*;
import java.awt.*;

public class Draw extends JLabel {                                                                                      // want to draw on a JLabel, there for extending the class with it
    protected void paintComponent(Graphics g){                                                                          // zum Überschreiben / zeichnen
        super.paintComponent(g);                                                                                        // paintComponent aufrufen und Graphics-object "g" übergeben
        Graphics2D g2d = (Graphics2D) g;                                                                                // g wird zu graphics2D object gecastet ( Graphics g -> Graphics2D g)
                                                                                                                        // (so kann man später zB. Anti-aliasing umgehen)
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);                      // < wie hier zu sehen

        g.setColor(Color.GRAY);                                                                                         // color of the border/frame for the game in window
        g.drawRect(9,9, GUI.WIDTH,GUI.HEIGHT);                                                                    // BOARD FRAME
        g.drawRect(GUI.WIDTH+16,GUI.HEIGHT/2-55, GUI.SLIDER-7,GUI.HEIGHT/3*2-20);                   // DELAY SLIDER
        g.drawRect(9,528,GUI.WIDTH + GUI.SLIDER, 52);                                               // STARTING CELLS SLIDER
        g.drawRect(GUI.WIDTH+22,535,56,20);                                                         // CURRENT PERCENTAGE WINDOW
        g.drawRect(GUI.WIDTH+22,GUI.HEIGHT/2-34,56,20);                                             // CURRENT ms DELAY WINDOW
        g.drawRect(GUI.WIDTH+16,9,GUI.SLIDER-7, 60);                                                // VALUES GENERATION WINDOW
        g.drawRect(GUI.WIDTH+22,37,56, 20);                                                         // CURRENT GENERATION
        g.drawRect(GUI.WIDTH+22,100,56, 20);                                                        // CURRENT NUMBER OF LIVING CELLS
        g.drawRect(GUI.WIDTH+16,72,GUI.SLIDER-7, 60);                                               // VALUES LIVING CELLS WINDOW


        for (int x = 0; x < GameMaster.CELLCOUNT; x++) {                                                                // das 2D array durchgehen
            for (int y = 0; y < GameMaster.CELLCOUNT; y++) {
                if(GameMaster.cells[x][y]){                                                                             // wenn an der position von cells[x][y] = true ist , zelle also lebt:
                    g.setColor(Color.BLACK);                                                                            // fülle die zelle mit einem schwarzen rechteck
                }else{                                                                                                  // wenn die zelle tot ist (cells[x][y] = false), dann:
                    g.setColor(Color.WHITE);                                                                            // fülle die zelle mit einem weißen rechteck
                }
                g.fillRect(x*GameMaster.CELLWIDTH+GUI.XOFF, y*GameMaster.CELLWIDTH+GUI.YOFF,GameMaster.CELLWIDTH-1,GameMaster.CELLWIDTH-1 );
            }
        }
        repaint();                                                                                                      // needed for it to be drawable -> kinda like an update function
    }
}
