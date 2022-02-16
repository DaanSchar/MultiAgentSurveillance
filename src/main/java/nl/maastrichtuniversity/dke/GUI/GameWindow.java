package nl.maastrichtuniversity.dke.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.StaticEnvironment;

/**
 * GUIboard class
 */

public class GameWindow  {



    // Create GameComponent name it game
    private GameComponent game;

    // set the icon of the game
    private ImageIcon icon = new ImageIcon(Objects.requireNonNull(GameWindow.class.getResource("/images/settings/icon.png")));

    // creating new frame (window)
    private JFrame window = new JFrame("GROUP 14");

    /*
     * Create variables for the background image
     * the back button
     */
    private ImageIcon backImage = new ImageIcon(Objects.requireNonNull(GameWindow.class.getResource("/images/settings/next.jpg")));
    private JButton back = new JButton("BACK");

    private Color color1 = new Color(230,230,230);
    private Color color2 = new Color(173,237,153);

    private JButton exit = new JButton("EXIT");
    private JButton zoomIn = new JButton("zoomIn");
    private JButton zoomOut = new JButton("zoomOut");
    private JButton resize = new JButton("resize");

    private AnimationListener animationListener = new AnimationListener();

    private Scenario scenario;

    private JLabel gameLabel = new JLabel();
    private MouseSpy mouseListener = new MouseSpy();

    int textureSize;

    public GameWindow(Scenario scenario) {
        double scale = scenario.getStaticEnvironment().getScaling()*100;
        textureSize = (int) scale;

        this.scenario = scenario;
        System.out.println(scenario.getStaticEnvironment().getHeight());

        game = new GameComponent(scenario);



        /*
         * Implement the back button
         * set the bounds,the background color,the border and add the actionlistener
         */

        setProberites(back,0 ,0,75,40);
        /*
         * Implement the exit button. set the bounds,the background color,the border and add the action listener
         */
        setProberites(exit,((int) scenario.getStaticEnvironment().getWidth()*textureSize)-75,0,75,40);


        setProberites(zoomIn,80,0,75,40);


        setProberites(zoomOut,160,0,75,40);


        setProberites(resize,240,0,75,40);

        gameLabel.setHorizontalAlignment(JLabel.CENTER);
        gameLabel.setVerticalAlignment(JLabel.CENTER);
        gameLabel.setBackground(color2);
        gameLabel.setOpaque(true);
        gameLabel.setBounds(0,((int) scenario.getStaticEnvironment().getHeight()*textureSize),(int) scenario.getStaticEnvironment().getWidth()*textureSize, 40);
        gameLabel.add(back);
        gameLabel.add(exit);
        gameLabel.add(zoomIn);
        gameLabel.add(zoomOut);
        gameLabel.add(resize);

        /*
         *  Set how to close the frame.
         *  Set the size of the frame.
         *  Adding the label to the frame.
         *  Set the frame visible.
         *  Add back button to the frame
         *  Add the gameComponent to the frame
         */
        window.addMouseWheelListener(mouseListener);
        window.addMouseListener(mouseListener);
        window.addMouseMotionListener(mouseListener);
        window.setUndecorated(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize((int) scenario.getStaticEnvironment().getWidth()*textureSize, ((int) scenario.getStaticEnvironment().getHeight()*textureSize) + 40);
        window.setLocationRelativeTo(null);
        window.getContentPane().setBackground(color2);
        window.add(gameLabel);
        window.add(game);
        window.setIconImage(icon.getImage());
        window.setVisible(true);
    }
    public void setProberites(JButton button ,int x,int y,int width,int height){
        button.addActionListener(animationListener);
        button.setBackground(color1);
        button.setFocusable(false);
        button.setBorder(BorderFactory.createBevelBorder(0, Color.gray , Color.black));
        button.setBounds(x,y,width,height);
    };


    /**
     *  Creat an Action listener class.
     *  e: which is the buttons that have an ActionListener.
     */
    class AnimationListener implements ActionListener {

        public void actionPerformed(ActionEvent e){

            /*
             * If back button clicked close the frame and go back to GUIplay class
             * set the bounds
             */
            if(e.getSource()==back){
                window.dispose();
                Menu Menu = new Menu();
            }
            if(e.getSource()==exit){
                System.exit(0);
            }
            if(e.getSource()==zoomOut){
                game.zoomOut();
            }
            if(e.getSource()==zoomIn){
                game.zoomIn();
            }
            if(e.getSource()==resize){
                game.resize();
            }
            window.repaint();
        }

    }

    class MouseSpy implements MouseWheelListener, MouseMotionListener, MouseListener {
        Point point0;
        boolean released = false;

        @Override
        public void mouseWheelMoved(MouseWheelEvent e){
            if (e.getWheelRotation()<0) {
                game.zoomIn();
            }
            else {
                game.zoomOut();
            }

            window.repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Point point1 = e.getLocationOnScreen();
            if (released) {
                game.panning((point1.x-point0.x),(point1.y-point0.y));
            }
            window.repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            released =false;
            point0 = MouseInfo.getPointerInfo().getLocation();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            released = true;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
        }
    }
}

