package nl.maastrichtuniversity.dke.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

import nl.maastrichtuniversity.dke.logic.Game;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;

/**
 * GUIboard class
 */

public class GameWindow  {

    private final ImageIcon icon = new ImageIcon(Objects.requireNonNull(GameWindow.class.getResource("/images/settings/icon.png")));
    private final Color buttonColor = new Color(230,230,230);
    private final Color backgroundColor = new Color(173,237,153);

    private final int textureSize;

    private final Scenario scenario;

    private boolean showAgentMemoryMap;

    public GameWindow() {
        this.scenario = Game.getInstance().getScenario();
        this.textureSize = (int) (scenario.getScaling()*100);

        setUp();
        start();
    }

    private void setUp() {
        setUpGameComponents();
        setUpClock();
        setUpButtons();
        setUpButtonContainer();
        setUpWindow();
    }

    private void start() {
        clock.start();
        agentMemoryMap.setVisible(false);
        window.setVisible(true);
    }


    // ------------------------------------------- GameComponents ---------------------------------------------- \\


    private GameComponent gameComponent;
    private GameComponent agentMemoryMap;

    private void setUpGameComponents() {
        gameComponent = new GameComponent();
        agentMemoryMap = new GameComponent(scenario,scenario.getGuards().get(0).getMemoryModule().getMap());
        gameComponent.setPreferredSize(new Dimension(
                scenario.getEnvironment().getWidth()*textureSize,
                (scenario.getEnvironment().getHeight()*textureSize) + 40)
        );
        agentMemoryMap.setPreferredSize(new Dimension(
                scenario.getEnvironment().getWidth()*3,
                scenario.getEnvironment().getHeight()*3)
        );
    }


    // ----------------------------------------------- Window -------------------------------------------------- \\


    private JFrame window;

    private void setUpWindow() {
        window = new JFrame("GROUP 14");
        window.setLayout(new BorderLayout());
        MouseListener mouseListener = new MouseListener();
        window.addMouseWheelListener(mouseListener);
        window.addMouseListener(mouseListener);
        window.addMouseMotionListener(mouseListener);
        window.setUndecorated(true);
        window.setSize(
                scenario.getEnvironment().getWidth()*textureSize,
                scenario.getEnvironment().getHeight()*textureSize + 40
        );
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.getContentPane().setBackground(backgroundColor);
        window.add(buttonContainer);
        window.add(gameComponent);
        window.add(agentMemoryMap, BorderLayout.NORTH);
        window.setIconImage(icon.getImage());
    }


    // ------------------------------------------- ButtonContainer ---------------------------------------------- \\


    private JLabel buttonContainer;

    private void setUpButtonContainer() {
        buttonContainer = new JLabel();
        buttonContainer.setHorizontalAlignment(JLabel.CENTER);
        buttonContainer.setVerticalAlignment(JLabel.CENTER);
        buttonContainer.setBackground(backgroundColor);
        buttonContainer.setOpaque(true);
        buttonContainer.add(backButton);
        buttonContainer.add(exitButton);
        buttonContainer.add(zoomInButton);
        buttonContainer.add(zoomOutButton);
        buttonContainer.add(resizeButton);
        buttonContainer.add(agentMemoryMapButton);
        buttonContainer.add(clock.getTime());
        buttonContainer.setBounds(0,
                scenario.getEnvironment().getHeight()*textureSize,
                scenario.getEnvironment().getWidth()*textureSize, 40
        );
    }


    // ----------------------------------------------- Buttons -------------------------------------------------- \\


    private JButton backButton;
    private JButton exitButton;
    private JButton zoomInButton;
    private JButton zoomOutButton;
    private JButton resizeButton;
    private JButton agentMemoryMapButton;

    private ButtonListener buttonListener;

    private void setUpButtons() {
        backButton = new JButton("BACK");
        exitButton = new JButton("EXIT");
        zoomInButton = new JButton("zoomIn");
        zoomOutButton = new JButton("zoomOut");
        resizeButton = new JButton("resize");
        agentMemoryMapButton = new JButton("AgentMap");

        setButtonsProperties();
    }

    private void setButtonsProperties() {
        buttonListener = new ButtonListener();

        setButtonProperties(backButton,0 ,0);
        setButtonProperties(exitButton,(scenario.getEnvironment().getWidth()*textureSize)-75,0);
        setButtonProperties(zoomInButton,80,0);
        setButtonProperties(zoomOutButton,160,0);
        setButtonProperties(resizeButton,240,0);
        setButtonProperties(agentMemoryMapButton,320,0);
    }

    private void setButtonProperties(JButton button , int x, int y){
        int buttonWidth = 75;
        int buttonHeight = 40;

        button.setBounds(x,y,buttonWidth,buttonHeight);
        button.addActionListener(buttonListener);
        button.setBackground(buttonColor);
        button.setFocusable(false);
        button.setBorder(BorderFactory.createBevelBorder(0, Color.gray , Color.black));
    }

    /**
     * Listener for the buttons.
     * Declares button behavior.
     */
    class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            performButtonAction(e.getSource());
            window.repaint();
        }

        private void performButtonAction(Object button) {
            if (button == backButton) { onBackButtonClicked(); }
            if (button == exitButton) { onExitButtonClicked(); }
            if (button == zoomInButton) { onZoomInButtonClicked(); }
            if (button == zoomOutButton) { onZoomOutButtonClicked(); }
            if (button == resizeButton) { onResizeButtonClicked(); }
            if (button == agentMemoryMapButton) { onAgentMapButtonClicked(); }
        }

        private void onExitButtonClicked() { System.exit(0); }

        private void onZoomOutButtonClicked() { gameComponent.zoomOut(); }

        private void onZoomInButtonClicked() { gameComponent.zoomIn(); }

        private void onResizeButtonClicked() { gameComponent.resize(); }

        private void onBackButtonClicked() {
            window.dispose();
            new Menu();
        }

        private void onAgentMapButtonClicked() {
            showAgentMemoryMap = !showAgentMemoryMap;
            agentMemoryMap.setVisible(showAgentMemoryMap);
        }

    }


    // ------------------------------------------------- Mouse ---------------------------------------------------- \\


    /**
     * Listener for mouse.
     * Declares mouse behavior.
     */
    class MouseListener implements MouseWheelListener, MouseMotionListener, java.awt.event.MouseListener {
        Point point0;
        boolean released = false;

        @Override
        public void mouseWheelMoved(MouseWheelEvent e){
            if (e.getWheelRotation() < 0) gameComponent.zoomIn(); else gameComponent.zoomOut();
            window.repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Point point1 = e.getLocationOnScreen();

            if (released) {
                gameComponent.panning((point1.x-point0.x),(point1.y-point0.y));
            }
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


    // ------------------------------------------------- Clock ---------------------------------------------------- \\

    private Clock clock;

    private void setUpClock() {
        clock = new Clock();
        clock.getTime().setBounds(400, 0, 75, 40);
        clock.getTime().setBackground(buttonColor);
        clock.getTime().setFocusable(false);
        clock.getTime().setBorder(BorderFactory.createBevelBorder(0, Color.gray , Color.black));
    }
}

