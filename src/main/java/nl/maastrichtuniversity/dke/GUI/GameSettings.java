package nl.maastrichtuniversity.dke.GUI;

import nl.maastrichtuniversity.dke.areas.Rectangle;
import nl.maastrichtuniversity.dke.areas.Area;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;




/**
 * GameSettings class implemnts Action Listener .
 */

public class GameSettings implements ActionListener  {

    private final JFrame window = new JFrame("GROUP 14");

    /*
     * Declaring the JTextField for the settings of the game
     */
    private JTextField gameMode = new JTextField("0");
    private JTextField height = new JTextField("80");
    private JTextField width = new JTextField("120");
    private JTextField scaling = new JTextField("0.1");
    private JTextField numGuards = new JTextField("3");
    private JTextField numIntruders = new JTextField("0");
    private JTextField baseSpeedIntruder = new JTextField("14.0");
    private JTextField sprintSpeedIntruder = new JTextField("20.0");
    private JTextField baseSpeedGuard = new JTextField("14.0");
    private JTextField timeStep = new JTextField("0.1");
    private JTextField targetArea = new JTextField("20 40 25 45");
    private JTextField spawnAreaIntruders = new JTextField("2 2 20 10");
    private JTextField spawnAreaGuards = new JTextField("2 2 20 10");

    private JTextField walls = new JTextField("50 0 51 20,0 0 1 80,0 79 120 80,119 0 120 80,0 0 120 1");
    private JTextField teleport = new JTextField("20 70 25 75 90 50 0.0");
    private JTextField shaded = new JTextField("10 20 20 40");
    private JTextField texture = new JTextField("10 20 20 40 0 0");
    private JTextField windows = new JTextField("61 61 61 61");
    private JTextField doors = new JTextField("60 60 60 60");
    private JTextField sentryTower = new JTextField("50 50 50 50");



    /*
     * Create a general label
     * Creating 3 Buttons and add their icons (start ,  ).
     * create new color
     * create a double for the frame size number
     */
    private JLabel settings3 = new JLabel();
    private JLabel settings2 = new JLabel();
    private JLabel settings1 = new JLabel();
    private JButton next = new JButton(new ImageIcon(ImageFactory.get("nextButton")));
    private JButton start = new JButton(new ImageIcon(ImageFactory.get("startButton")));

    private Color color1 = new Color(189,228,247);
    private int nextInt = 0;
    private JButton back = new JButton(new ImageIcon(ImageFactory.get("backButton")));

    public GameSettings() {

        back.setBounds(760,0,40,40);
        back.addActionListener(this);
        back.setBackground(new Color(155,223,232));

        setProberites(gameMode,170,138,50,25);

        setProberites(height,153,180,50,25);

        setProberites(width,375,180,50,25);

        setProberites(scaling,175,223,50,25);

        setProberites(numGuards,203,263,50,25);

        setProberites(numIntruders,552,263,50,25);

        setProberites(baseSpeedIntruder,345,307,50,25);

        setProberites(sprintSpeedIntruder,383,347,50,25);

        setProberites(baseSpeedGuard,280,390,50,25);

        setProberites(timeStep,187,431,50,25);

        setProberites(targetArea,224,472,200,25);

        setProberites(spawnAreaIntruders,367,514,200,25);

        setProberites(spawnAreaGuards,315,556,200,25);

        setProberites(timeStep,187,431,50,25);

        setProberites(timeStep,187,431,50,25);

        setProberites(walls,30,40,770,120);

        setProberites(teleport,30,210,770,120);

        setProberites(shaded,30,370,770,120);

        setProberites(texture,30,540,600,55);

        setProberites(windows,30,60,760,155);

        setProberites(doors,30,255,760,155);

        setProberites(sentryTower,30,460,760,75);



        /*
         * Implement the start ,next button. set the bounds and add the actionlistener
         */

        start.setBounds(637,545,162,51);
        start.addActionListener(this);

        next.setBounds(669,545,131,48);
        next.addActionListener(this);


        /*
         *  Set the settings1 icon
         *  Set the settings1 location
         *  Set the settings1 background color.
         *  Add the JTextField to one settings1.
         */
        settings1.setIcon(new ImageIcon(ImageFactory.get("settingsBackground1")));
        settings1.setHorizontalAlignment(JLabel.CENTER);
        settings1.setVerticalAlignment(JLabel.CENTER);
        settings1.setBackground(Color.WHITE);
        settings1.setOpaque(true);
        settings1.add(gameMode);
        settings1.add(height);
        settings1.add(width);
        settings1.add(scaling);
        settings1.add(numGuards);
        settings1.add(numIntruders);
        settings1.add(baseSpeedIntruder);
        settings1.add(sprintSpeedIntruder);
        settings1.add(baseSpeedGuard);
        settings1.add(timeStep);
        settings1.add(targetArea);
        settings1.add(spawnAreaIntruders);
        settings1.add(spawnAreaGuards);

        /*
         *  Set the settings2 icon
         *  Set the settings2 location
         *  Set the settings2 background color.
         *  Add the JTextField to one settings2.
         */
        settings2.setIcon(new ImageIcon(ImageFactory.get("settingsBackground2")));
        settings2.setHorizontalAlignment(JLabel.CENTER);
        settings2.setVerticalAlignment(JLabel.CENTER);
        settings2.setBackground(Color.WHITE);
        settings2.setOpaque(true);
        settings2.setVisible(false);
        settings2.add(walls);
        settings2.add(teleport);
        settings2.add(texture);
        settings2.add(shaded);
        /*
         *  Set the settings3 icon
         *  Set the settings3 location
         *  Set the settings3 background color.
         *  Add the JTextField to one settings3.
         */
        settings3.setIcon(new ImageIcon(ImageFactory.get("settingsBackground3")));
        settings3.setHorizontalAlignment(JLabel.CENTER);
        settings3.setVerticalAlignment(JLabel.CENTER);
        settings3.setBackground(Color.WHITE);
        settings3.setOpaque(true);
        settings3.setVisible(false);
        settings3.add(start);
        settings3.add(windows);
        settings3.add(doors);
        settings3.add(sentryTower);


        /*
         *  Set how to close the frame.
         *  Set the size of the frame.
         *  Adding the settings1 to the frame.
         *  Set the frame visible.
         */
        window.setUndecorated(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setLocationRelativeTo(null);
        window.add(back);
        window.add(next);
        window.add(settings1);
        window.setIconImage(ImageFactory.get("gameIcon"));
        window.setVisible(true);

    }

    public void createEnvironment(){

        //width , height scaling
        double widthFromTextField = Double.parseDouble(width.getText());
        double heightFromTextField = Double.parseDouble(height.getText());
        double scalingFromTextField = Double.parseDouble(scaling.getText());

//        scenario = new Scenario(
//                widthFromTextField,
//                heightFromTextField,
//                scalingFromTextField,
//                new ArrayList<>(),
//                new ArrayList<>(),
//                createAreas(spawnAreaIntruders),
//                createAreas(spawnAreaGuards),
//                createAreas(walls),
//                createAreas(shaded),
//                createAreas(teleport),
//                createAreas(targetArea),
//                createAreas(windows),
//                createAreas(doors),
//                createAreas(sentryTower)
//        );
    }
    public void setProberites(JTextField text ,int x,int y,int width,int height){
        text.setFont(new Font("Consolas",Font.PLAIN,20));
        text.setForeground(Color.darkGray);
        text.setBackground(color1);
        text.setCaretColor(Color.blue);
        text.setBounds(x,y,width,height);
    };

    /**
     * creates a list of areas from a textField
     * @param textField field containing the data for the areas
     * @return a list of areas
     */
    private List<Area> createAreas(JTextField textField) {
        String[] text = textField.getText().split(",");
        List<Area> areas = new ArrayList<>();

        for (String value : text) {
            String[] coordinates = value.split(" ");
            Area area = new Rectangle(Integer.parseInt(
                    coordinates[0]),
                    Integer.parseInt(coordinates[1]),
                    Integer.parseInt(coordinates[2]),
                    Integer.parseInt(coordinates[3])
            );
            areas.add(area);
        }

        return areas;
    }

    /**
     *  Creat an Action listener method.
     *  @param e which is the buttons that have an ActionListener.
     */
    @Override
    public void actionPerformed(ActionEvent e){
        window.repaint();

        /*
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
        if(e.getSource()==back){
            window.dispose();
            Menu Menu = new Menu();
        }
        /*
         *  If start clicked create new game window.
         */
        if(e.getSource()==start){
            createEnvironment();
//            GameWindow gameWindow = new GameWindow(staticEnvironment);
            window.dispose();
        }

    }

}
