package nl.maastrichtuniversity.dke.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.Timer;

import nl.maastrichtuniversity.dke.discrete.Scenario;
import nl.maastrichtuniversity.dke.settings.Clock;

/**
 * GUIboard class
 */

public class GameWindow  {

    private GameComponent game;
    private GameComponent agentMap;


    private ImageIcon icon = new ImageIcon(Objects.requireNonNull(GameWindow.class.getResource("/images/settings/icon.png")));

    private JFrame window = new JFrame("GROUP 14");

    private ImageIcon backImage = new ImageIcon(Objects.requireNonNull(GameWindow.class.getResource("/images/settings/next.jpg")));
    private JButton back = new JButton("BACK");

    private Color color1 = new Color(230,230,230);
    private Color color2 = new Color(173,237,153);

    private JButton exit = new JButton("EXIT");
    private JButton zoomIn = new JButton("zoomIn");
    private JButton zoomOut = new JButton("zoomOut");
    private JButton resize = new JButton("resize");
    private JButton agentMapB = new JButton("AgentMap");

    private Clock clock = new Clock();

    private AnimationListener animationListener = new AnimationListener();

    private Scenario scenario;

    private JLabel gameLabel = new JLabel();
    private MouseSpy mouseListener = new MouseSpy();

    int textureSize;
    boolean map=false;
    private JPanel agentLapel = new JPanel();




    public GameWindow(Scenario scenario) {
        double scale = scenario.getScaling()*100;
        textureSize = (int) scale;

        this.scenario = scenario;

        game = new GameComponent(scenario,scenario.getEnvironment());

        agentMap = new GameComponent(scenario,scenario.getGuards().get(0).getMemoryModule().getMap());
        agentMap.isAgentMap();




        /*
         * Implement the back button
         * set the bounds,the background color,the border and add the actionlistener
         */

        setProperties(back,0 ,0,75,40);
        /*
         * Implement the exit button. set the bounds,the background color,the border and add the action listener
         */
        setProperties(exit,(scenario.getEnvironment().getWidth()*textureSize)-75,0,75,40);


        setProperties(zoomIn,80,0,75,40);


        setProperties(zoomOut,160,0,75,40);


        setProperties(resize,240,0,75,40);

        setProperties(agentMapB,320,0,75,40);


        // Set bounds of clock
        clock.getTime().setBounds(400, 0, 75, 40);
        clock.getTime().setBackground(color1);
        clock.getTime().setFocusable(false);
        clock.getTime().setBorder(BorderFactory.createBevelBorder(0, Color.gray , Color.black));

        gameLabel.setHorizontalAlignment(JLabel.CENTER);
        gameLabel.setVerticalAlignment(JLabel.CENTER);
        gameLabel.setBackground(color2);
        gameLabel.setOpaque(true);
        gameLabel.setBounds(0,(scenario.getEnvironment().getHeight()*textureSize),scenario.getEnvironment().getWidth()*textureSize, 40);
        gameLabel.add(back);
        gameLabel.add(exit);
        gameLabel.add(zoomIn);
        gameLabel.add(zoomOut);
        gameLabel.add(resize);
        gameLabel.add(agentMapB);
        gameLabel.add(clock.getTime());

        clock.start();

        game.setPreferredSize(new Dimension(scenario.getEnvironment().getWidth()*textureSize, (scenario.getEnvironment().getHeight()*textureSize) + 40));
        agentMap.setPreferredSize(new Dimension(scenario.getEnvironment().getWidth()*3,scenario.getEnvironment().getHeight()*3));


        /*
         *  Set how to close the frame.
         *  Set the size of the frame.
         *  Adding the label to the frame.
         *  Set the frame visible.
         *  Add back button to the frame
         *  Add the gameComponent to the frame
         */
        window.setLayout(new BorderLayout());
        window.addMouseWheelListener(mouseListener);
        window.addMouseListener(mouseListener);
        window.addMouseMotionListener(mouseListener);
        window.setUndecorated(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(scenario.getEnvironment().getWidth()*textureSize, (scenario.getEnvironment().getHeight()*textureSize) + 40);
        window.setLocationRelativeTo(null);
        window.getContentPane().setBackground(color2);
        window.add(gameLabel);
        window.add(game);
        window.add(agentMap, BorderLayout.NORTH);
        agentMap.setVisible(false);

        window.setIconImage(icon.getImage());
        window.setVisible(true);
    }
    public void setProperties(JButton button , int x, int y, int width, int height){
        button.addActionListener(animationListener);
        button.setBackground(color1);
        button.setFocusable(false);
        button.setBorder(BorderFactory.createBevelBorder(0, Color.gray , Color.black));
        button.setBounds(x,y,width,height);
    }

    /**
     *  Creat an Action listener class.
     *  e: which is the buttons that have an ActionListener.
     */
    class AnimationListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            performButtonAction(e.getSource());
            window.repaint();
        }

        private void performButtonAction(Object button) {
            if (button == back) { onBackButtonClicked(); }
            if (button == exit) { onExitButtonClicked(); }
            if (button == zoomOut) { onZoomInButtonClicked(); }
            if (button == zoomIn) { onZoomOutButtonClicked(); }
            if (button == resize) { onResizeButtonClicked(); }
            if (button == agentMapB) { onAgentMapButtonClicked(); }
        }

        private void onExitButtonClicked() { System.exit(0); }

        private void onZoomOutButtonClicked() { game.zoomOut(); }

        private void onZoomInButtonClicked() { game.zoomIn(); }

        private void onResizeButtonClicked() { game.resize(); }

        private void onBackButtonClicked() {
            window.dispose();
            new Menu();
        }

        private void onAgentMapButtonClicked() {
            if (map){
                agentMap.setVisible(false);
                map = false;
            } else {
                agentMap.setVisible(true);
                map = true;
            }
        }

    }



    class MouseSpy implements MouseWheelListener, MouseMotionListener, MouseListener {
        Point point0;
        boolean released = false;

        @Override
        public void mouseWheelMoved(MouseWheelEvent e){
            if (e.getWheelRotation()<0) {
                game.zoomIn();
            } else {
                game.zoomOut();
            }

            window.repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Point point1 = e.getLocationOnScreen();

            if (released) { game.panning((point1.x-point0.x),(point1.y-point0.y)); }

            window.repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            point0 = MouseInfo.getPointerInfo().getLocation();
            released = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) { released = true; }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}

        @Override
        public void mouseMoved(MouseEvent e) {}

        @Override
        public void mouseClicked(MouseEvent e) {}
    }
}

