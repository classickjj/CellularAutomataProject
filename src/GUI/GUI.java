package GUI;

import drawupdate.Draw;
import gamelogic.GameClock;
import gamelogic.GameMaster;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI extends JFrame implements ActionListener, ChangeListener {                                             // graphical user interface class

    JFrame jframe;                                                                                                      // using JFrame for the GUI / window
    JButton startButton;
    JButton pauseButton;
    JButton restartButton;
    Integer startingCells;
    Integer ms;
    String val;
    String val2;
    JLabel sliderValue;
    JLabel sliderValue2;
    public static JLabel sliderValue3;
    public static JLabel sliderValue4;
    JLabel aliveValue;
    JLabel sliderName;
    JLabel sliderName2;
    JLabel generationValue;
    JSlider howManyStarters;
    JSlider delay;


    public static Draw draw;                                                                                            // initiate draw class object, so we can draw "duh"
    public static final int WIDTH = GameMaster.PIXEL, HEIGHT = GameMaster.PIXEL, XOFF = 10, YOFF = 10;                  // set size of game board in the window (Spielfeldgröße)
    public static final int BUTTONS = 60, XFRAME = 36, YFRAME = 57, SLIDER = 75;
    public static int STARTVALUE = 25, DEFAULT_DELAY = 10;


    public void create(){                                                                                               // offset x / y, so board is not starting directly on window border

        //label
        generationValue = new JLabel("Generation");                                                                // LABEL MARKING GENERATION COUNTER
        generationValue.setBounds(WIDTH+20, 13, 67, 20);

        sliderValue3 = new JLabel();                                                                                    // DISPLAY GENERATION VALUE
        sliderValue3.setBounds(WIDTH+27,38, 65, 18);
        sliderValue3.setText(Integer.toString(GameMaster.generation));
        sliderValue3.setForeground(Color.DARK_GRAY);


        //***********************
        aliveValue = new JLabel("lebendig");                                                                       // LABEL MARKING LIVING CELLS
        aliveValue.setBounds(WIDTH+26, 76, 67, 20);

        sliderValue4 = new JLabel();                                                                                    // DISPLAY CELLS ALIVE VALUE
        sliderValue4.setBounds(WIDTH+27,100, 65, 18);
        sliderValue4.setText(Integer.toString(GameMaster.startCells));
        sliderValue4.setForeground(Color.DARK_GRAY);


        //***********************
        sliderName2 = new JLabel("Timer");                                                                         // LABEL FOR NAME OF DELAY SLIDER
        sliderName2.setBounds(WIDTH+30, (HEIGHT/2)-50, 60, 10);


        sliderValue2 = new JLabel();                                                                                    // DISPLAY ms VALUE
        sliderValue2.setBounds(WIDTH+27,(HEIGHT/2)-29, 60, 10);
        val2 = Integer.toString(DEFAULT_DELAY);
        sliderValue2.setText(val2 + " ms");
        sliderValue2.setForeground(Color.DARK_GRAY);


        //***********************
        sliderName = new JLabel();                                                                                      // STARTING CELLS (NAME) LABEL
        sliderName.setBounds(WIDTH+10,558,70,25);
        sliderName.setText("% Startcells");

        sliderValue = new JLabel();                                                                                     // DISPLAY % VALUE
        sliderValue.setBounds(WIDTH+34,535, 35, 20);
        val = Integer.toString(STARTVALUE);
        sliderValue.setText(val + " %");
        sliderValue.setForeground(Color.DARK_GRAY);


        //**************************************************************************************************************
        //slider
        howManyStarters = new JSlider(0,100, STARTVALUE);                                                     // ACTUAL SLIDER FOR STARTING PERCENTAGE
        howManyStarters.addChangeListener( this);
        howManyStarters.setBounds(255, 538,260,38);
        howManyStarters.setName("percentage of (randomly distributed) living cells based on number of all cells");
        howManyStarters.setVisible(true);
        howManyStarters.setPaintTicks(true);
        howManyStarters.setMinorTickSpacing(2);
        howManyStarters.setMajorTickSpacing(10);
        howManyStarters.setPaintLabels(true);
        howManyStarters.createStandardLabels(10,0);
        howManyStarters.setToolTipText("PERCENTAGE OF LIVING CELLS IN THE STARTING GRID AFTER A RESET");


        //***********************
        delay = new JSlider(JSlider.VERTICAL,0, 1000, DEFAULT_DELAY);                                         // GENERATIONEN-BERECHNUNGSDELAY SLIDER
        delay.addChangeListener(this);
        delay.setBounds(WIDTH+20,(HEIGHT/2)-10, 60, (HEIGHT/2)+15);
        delay.setPaintTicks(true);
        delay.setMinorTickSpacing(25);
        delay.setMajorTickSpacing(100);
        delay.setPaintLabels(true);
        delay.createStandardLabels(25,0);
        delay.setToolTipText("TIME BETWEEN GENERATIONS IN ms");
        delay.setName("TIMER");


        //**************************************************************************************************************
        //button(s)
        startButton = new JButton();                                                                                    // START BUTTON
        startButton.setBounds(15, 539, 75, 32);
        startButton.setText("Start!");
        startButton.setVisible(true);
        startButton.addActionListener(this);
        startButton.setFocusable(false);
        startButton.setBackground(Color.LIGHT_GRAY);
        startButton.setBorder(BorderFactory.createEtchedBorder());


        //***********************
        pauseButton = new JButton();                                                                                    // PAUSE / RESUME BUTTON
        pauseButton.setBounds(95, 539, 75, 32);
        pauseButton.setText("Pause");
        pauseButton.setVisible(true);
        pauseButton.setEnabled(false);
        pauseButton.setFocusable(false);
        pauseButton.setBackground(Color.LIGHT_GRAY);
        pauseButton.setBorder(BorderFactory.createEtchedBorder());
        pauseButton.addActionListener(this);


        //***********************
        restartButton = new JButton();                                                                                  // RESET BUTTON
        restartButton.setBounds(175, 539, 75, 32);
        restartButton.setText("Reset");
        restartButton.setVisible(true);
        restartButton.setFocusable(false);
        restartButton.setBackground(Color.LIGHT_GRAY);
        restartButton.setBorder(BorderFactory.createEtchedBorder());
        restartButton.addActionListener(this);


        //**************************************************************************************************************
        //displayed window
        jframe = new JFrame("Game of Life");                                                                       // initiating new JFrame + setting title of the window in top bar
        jframe.setSize(WIDTH + XFRAME + SLIDER, HEIGHT + YFRAME + BUTTONS);                               // JFrame size
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                                                          // end program and close window on closing operation
        jframe.setLocationRelativeTo(null);                                                                             // start in the middle of main monitor
        jframe.setResizable(false);                                                                                     // window will not be resizable

        draw = new Draw();                                                                                              // new draw object
        draw.setBounds(0,0,WIDTH + XFRAME + SLIDER,HEIGHT + YFRAME + BUTTONS);                      // same size as the JFrame
        draw.setVisible(true);                                                                                          // visibility function set to true -> we can see it then :)

        jframe.add(sliderValue);                                                                                        // ADDING  EVERY SLIDER, BUTTON, LABEL
        jframe.add(sliderValue2);
        jframe.add(sliderValue3);
        jframe.add(sliderValue4);
        jframe.add(aliveValue);
        jframe.add(generationValue);
        jframe.add(sliderName2);
        jframe.add(delay);
        jframe.add(sliderName);
        jframe.add(howManyStarters);
        jframe.add(restartButton);
        jframe.add(pauseButton);
        jframe.add(startButton);
        jframe.add(draw);                                                                                               // adding draw-class object "draw" to the JFrame "jframe"
        jframe.setVisible(true);                                                                                        // visibility function set to true -> we can see this then :)
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            delay.setValue(DEFAULT_DELAY);
            GameClock.unpause();
            startButton.setEnabled(false);
            pauseButton.setEnabled(true);
            System.out.println("******************** STARTING ********************");

        }else if(e.getSource()==pauseButton) {
            delay.setValue(DEFAULT_DELAY);
            if(!GameClock.paused) {
                GameClock.pause();
                pauseButton.setText("Resume");
                System.out.println("******************** PAUSING ********************");

            }else{
                GameClock.unpause();
                pauseButton.setText("Pause");
                System.out.println("****************** CONTINUING *******************");
            }
        }
        else if(e.getSource()==restartButton) {
            GameClock.pause();
            startButton.setEnabled(true);
            pauseButton.setEnabled(false);
            pauseButton.setText("Pause");
            GameMaster.generation = 0;
            sliderValue3.setText(Integer.toString(GameMaster.generation));
            DEFAULT_DELAY = 10;
            delay.setValue(DEFAULT_DELAY);
            startingCells = howManyStarters.getValue();
            GameMaster.startCells = (GameMaster.CELLCOUNT*GameMaster.CELLCOUNT*startingCells) / 100;
            sliderValue4.setText(Integer.toString(GameMaster.startCells));
            GameMaster.setup();
        }
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource()==howManyStarters){
            startingCells = howManyStarters.getValue();
            sliderValue.setText(startingCells + " %");
            STARTVALUE = startingCells;

        }else if(e.getSource()==delay){
            ms = delay.getValue();
            if(ms >= 1){
                sliderValue2.setText(ms + " ms");
                DEFAULT_DELAY = ms;
            }else{
                GameClock.pause();
                pauseButton.setEnabled(true);
                pauseButton.setText("Resume");
                sliderValue2.setText("PAUSED");
            }
        }
    }
}
