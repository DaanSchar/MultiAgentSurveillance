package nl.maastrichtuniversity.dke.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.util.*;
import java.util.List;

import nl.maastrichtuniversity.dke.*;
import nl.maastrichtuniversity.dke.areas.*;
import nl.maastrichtuniversity.dke.areas.Rectangle;


/**
 * GameSettings class implemnts Action Listener .
 */

public class GameSettings implements ActionListener  {

    /**
     * Creating new Frame (window)
     */

    JFrame window = new JFrame("GROUP 14");

    /**
     * Declaring the images of the buttons and the app icon
     */
    ImageIcon nextImage = new ImageIcon(Menu.class.getResource("/images/next.jpg"));
    ImageIcon startImage = new ImageIcon(Menu.class.getResource("/images/start.jpg"));
    ImageIcon uploudFileImage = new ImageIcon(Menu.class.getResource("/images/uploudFile.png"));
    ImageIcon setting1Image = new ImageIcon(Menu.class.getResource("/images/Settings1.jpg"));
    ImageIcon setting2Image = new ImageIcon(Menu.class.getResource("/images/Settings2.jpg"));
    ImageIcon setting3Image = new ImageIcon(Menu.class.getResource("/images/Settings3.jpg"));

    ImageIcon icon = new ImageIcon(Menu.class.getResource("/images/icon.png"));

    /**
     * Declaring the JTextField for the settings of the game
     */

    JTextField gameMode = new JTextField("0");
    JTextField height = new JTextField("80");
    JTextField width = new JTextField("120");
    JTextField scaling = new JTextField("0.1");
    JTextField numguards = new JTextField("3");
    JTextField numintruders = new JTextField("0");
    JTextField basespeedintruder = new JTextField("14.0");
    JTextField sprintspeedintruder = new JTextField("20.0");
    JTextField basespeedguard = new JTextField("14.0");
    JTextField timestep = new JTextField("0.1");
    JTextField targetarea = new JTextField("20 40 25 45");
    JTextField spawnareaintruders = new JTextField("2 2 20 10");
    JTextField spawnareaguards = new JTextField("2 2 20 10");

    JTextField walls = new JTextField("50 0 51 20 , 0 0 1 80 , 0 79 120 80 , 119 0 120 1");
    JTextField teleport = new JTextField("20 70 25 75 90 50 0.0");
    JTextField shaded = new JTextField("10 20 20 40");
    JTextField texture = new JTextField("10 20 20 40 0 0");
    JTextField windows = new JTextField("");
    JTextField doors = new JTextField("");
    JTextField sentrytower = new JTextField("");



    /**
     * Create a general label
     * Creating 3 Buttons and add thier icons (start , uploudFile ).
     * create new color
     * create a double for the frame size number
     */
    JLabel settings3 = new JLabel();
    JLabel settings2 = new JLabel();
    JLabel settings1 = new JLabel();
    JButton next = new JButton(nextImage);
    JButton start = new JButton(startImage);
    JButton uploudFile = new JButton(uploudFileImage);

    Color color1 = new Color(189,228,247);

    int nextInt = 0;

    public GameSettings() {

        gameMode.setFont(new Font("Consolas",Font.PLAIN,20));
        gameMode.setForeground(Color.darkGray);
        gameMode.setBackground(color1);
        gameMode.setCaretColor(Color.blue);
        gameMode.setBounds(170,138,50,25);

        height.setFont(new Font("Consolas",Font.PLAIN,20));
        height.setForeground(Color.darkGray);
        height.setBackground(color1);
        height.setCaretColor(Color.blue);
        height.setBounds(153,180,50,25);

        width.setFont(new Font("Consolas",Font.PLAIN,20));
        width.setForeground(Color.darkGray);
        width.setBackground(color1);
        width.setCaretColor(Color.blue);
        width.setBounds(375,180,50,25);

        scaling.setFont(new Font("Consolas",Font.PLAIN,20));
        scaling.setForeground(Color.darkGray);
        scaling.setBackground(color1);
        scaling.setCaretColor(Color.blue);
        scaling.setBounds(175,223,50,25);

        numguards.setFont(new Font("Consolas",Font.PLAIN,20));
        numguards.setForeground(Color.darkGray);
        numguards.setBackground(color1);
        numguards.setCaretColor(Color.blue);
        numguards.setBounds(203,263,50,25);
        
        numintruders.setFont(new Font("Consolas",Font.PLAIN,20));
        numintruders.setForeground(Color.darkGray);
        numintruders.setBackground(color1);
        numintruders.setCaretColor(Color.blue);
        numintruders.setBounds(552,263,50,25);
        
        basespeedintruder.setFont(new Font("Consolas",Font.PLAIN,20));
        basespeedintruder.setForeground(Color.darkGray);
        basespeedintruder.setBackground(color1);
        basespeedintruder.setCaretColor(Color.blue);
        basespeedintruder.setBounds(345,307,50,25);

        sprintspeedintruder.setFont(new Font("Consolas",Font.PLAIN,20));
        sprintspeedintruder.setForeground(Color.darkGray);
        sprintspeedintruder.setBackground(color1);
        sprintspeedintruder.setCaretColor(Color.blue);
        sprintspeedintruder.setBounds(383,347,50,25);

        basespeedguard.setFont(new Font("Consolas",Font.PLAIN,20));
        basespeedguard.setForeground(Color.darkGray);
        basespeedguard.setBackground(color1);
        basespeedguard.setCaretColor(Color.blue);
        basespeedguard.setBounds(280,390,50,25);

        timestep.setFont(new Font("Consolas",Font.PLAIN,20));
        timestep.setForeground(Color.darkGray);
        timestep.setBackground(color1);
        timestep.setCaretColor(Color.blue);
        timestep.setBounds(187,431,50,25);

        targetarea.setFont(new Font("Consolas",Font.PLAIN,20));
        targetarea.setForeground(Color.darkGray);
        targetarea.setBackground(color1);
        targetarea.setCaretColor(Color.blue);
        targetarea.setBounds(224,472,200,25);

        spawnareaintruders.setFont(new Font("Consolas",Font.PLAIN,20));
        spawnareaintruders.setForeground(Color.darkGray);
        spawnareaintruders.setBackground(color1);
        spawnareaintruders.setCaretColor(Color.blue);
        spawnareaintruders.setBounds(367,514,200,25);

        spawnareaguards.setFont(new Font("Consolas",Font.PLAIN,20));
        spawnareaguards.setForeground(Color.darkGray);
        spawnareaguards.setBackground(color1);
        spawnareaguards.setCaretColor(Color.blue);
        spawnareaguards.setBounds(315,556,200,25);

        walls.setFont(new Font("Consolas",Font.PLAIN,20));
        walls.setForeground(Color.darkGray);
        walls.setBackground(color1);
        walls.setCaretColor(Color.blue);
        walls.setBounds(30,40,770,120);

        teleport.setFont(new Font("Consolas",Font.PLAIN,20));
        teleport.setForeground(Color.darkGray);
        teleport.setBackground(color1);
        teleport.setCaretColor(Color.blue);
        teleport.setBounds(30,210,770,120);

        shaded.setFont(new Font("Consolas",Font.PLAIN,20));
        shaded.setForeground(Color.darkGray);
        shaded.setBackground(color1);
        shaded.setCaretColor(Color.blue);
        shaded.setBounds(30,370,770,120);

        texture.setFont(new Font("Consolas",Font.PLAIN,20));
        texture.setForeground(Color.darkGray);
        texture.setBackground(color1);
        texture.setCaretColor(Color.blue);
        texture.setBounds(30,540,600,55);

        windows.setFont(new Font("Consolas",Font.PLAIN,20));
        windows.setForeground(Color.darkGray);
        windows.setBackground(color1);
        windows.setCaretColor(Color.blue);
        windows.setBounds(30,60,760,155);

        doors.setFont(new Font("Consolas",Font.PLAIN,20));
        doors.setForeground(Color.darkGray);
        doors.setBackground(color1);
        doors.setCaretColor(Color.blue);
        doors.setBounds(30,255,760,155);

        sentrytower.setFont(new Font("Consolas",Font.PLAIN,20));
        sentrytower.setForeground(Color.darkGray);
        sentrytower.setBackground(color1);
        sentrytower.setCaretColor(Color.blue);
        sentrytower.setBounds(30,460,760,75);

        /**
         * Implement the start ,next button. set the bounds and add the actionlistener
         */

        start.setBounds(637,545,162,51);
        start.addActionListener(this);

        next.setBounds(669,545,131,48);
        next.addActionListener(this);

        /**
         * Implement the uploudFile button. set the bounds,the background color, and add the actionlistener
         */

        uploudFile.setBounds(505,15,285,73);
        uploudFile.addActionListener(this);

        /**
         *  Set the settings1 icon
         *  Set the settings1 location
         *  Set the settings1 background color.
         *  Add the JTextField to one settings1.
         */
        settings1.setIcon(setting1Image);
        settings1.setHorizontalAlignment(JLabel.CENTER);
        settings1.setVerticalAlignment(JLabel.CENTER);
        settings1.setBackground(Color.WHITE);
        settings1.setOpaque(true);
        settings1.add(uploudFile);
        settings1.add(gameMode);
        settings1.add(height);
        settings1.add(width);
        settings1.add(scaling);
        settings1.add(numguards);
        settings1.add(numintruders);
        settings1.add(basespeedintruder);
        settings1.add(sprintspeedintruder);
        settings1.add(basespeedguard);
        settings1.add(timestep);
        settings1.add(targetarea);
        settings1.add(spawnareaintruders);
        settings1.add(spawnareaguards);      

        /**
         *  Set the settings2 icon
         *  Set the settings2 location
         *  Set the settings2 background color.
         *  Add the JTextField to one settings2.
         */
        settings2.setIcon(setting2Image);
        settings2.setHorizontalAlignment(JLabel.CENTER);
        settings2.setVerticalAlignment(JLabel.CENTER);
        settings2.setBackground(Color.WHITE);
        settings2.setOpaque(true);
        settings2.setVisible(false);    
        settings2.add(walls);
        settings2.add(teleport);
        settings2.add(texture);
        settings2.add(shaded);
        /**
         *  Set the settings3 icon
         *  Set the settings3 location
         *  Set the settings3 background color.
         *  Add the JTextField to one settings3.
         */
        settings3.setIcon(setting3Image);
        settings3.setHorizontalAlignment(JLabel.CENTER);
        settings3.setVerticalAlignment(JLabel.CENTER);
        settings3.setBackground(Color.WHITE);
        settings3.setOpaque(true);
        settings3.setVisible(false);   
        settings3.add(start); 
        settings3.add(windows); 
        settings3.add(doors); 
        settings3.add(sentrytower); 


        /**
         *  Set how to close the frame.
         *  Set the size of the frame.
         *  Adding the settings1 to the frame.
         *  Set the frame visible.
         */
        window.setUndecorated(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setLocationRelativeTo(null);
        window.add(next);
        window.add(settings1);
        window.setIconImage(icon.getImage());
        window.setVisible(true);

    }

    /**
     *  Creat an Action listener method.
     *  @param e which is the buttons that have an ActionListener.
     */


    @Override
    public void actionPerformed(ActionEvent e){
         window.repaint();  

        /**
         *  If next button clicked go to the next page.
         */

        if(e.getSource()==next){
            if (nextInt==0) {
            settings2.setVisible(true);
            settings1.setVisible(false);
            window.add(settings2);   
            }    
            nextInt++;
        }

        if(e.getSource()==next){
            if(nextInt==2){
                settings3.setVisible(true);
                settings2.setVisible(false);
                window.add(settings3);
                next.setVisible(false);
            }
        }
        /**
         *  If start clicked create new game window.
         */
        if(e.getSource()==start){
             double widthI = Double.parseDouble(width.getText());
             double heightI = Double.parseDouble(height.getText());
              Rectangle guard1 = new Rectangle(367,514,200,25);
              List<Area> spawnAreaGuardsI = new ArrayList<Area>();
              spawnAreaGuardsI.add(guard1);
            List<Area> spawnAreaIntrudersI = new ArrayList<Area>();
              Rectangle wall1 = new Rectangle(30,40,770,120);
            List<Area> wallsI = new ArrayList<>();
              wallsI.add(wall1);
            List<Area> shadedAreasI = new ArrayList<>();
            List<Area> teleportPortalsI = new ArrayList<>();
              Rectangle targetArea = new Rectangle(224,472,200,25);

            Environment Environment = new Environment(widthI,heightI,spawnAreaIntrudersI,spawnAreaGuardsI,wallsI,shadedAreasI,teleportPortalsI,targetArea);
            System.out.println(Environment.getHeight());
            GameWindow gameWindow = new GameWindow(Environment);
            window.dispose();
            
        }

        /**
         *  If uploudFile  clicked the user can select a file for the game settings
         */

        if(e.getSource()==uploudFile){
           JOptionPane.showMessageDialog(uploudFile,"NOT IMPLEMENTED YET! :(" );
        }


    }

}
