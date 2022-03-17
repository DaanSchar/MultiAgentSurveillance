package nl.maastrichtuniversity.dke.gui;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SplashMenu implements Screen{

    private static SplashMenu splashMenuInstance;

    /**
     * Singleton method to get the instance of the SplashMenu
     * @return only instance of the SplashMenu
     */
    public static SplashMenu getInstance() {
        if (splashMenuInstance == null) { splashMenuInstance = new SplashMenu(); }

        return splashMenuInstance;
    }



    private @Getter JPanel panel;
    private final ImageFactory imageFactory;

    private SplashMenu() {
        imageFactory = ImageFactory.getInstance();
        createPanel();
    }

    /**
     * Creates the panel for the splash screen.
     * The panel contains everything that is needed for the splash screen.
     */
    private void createPanel() {
        panel = new JPanel();
        panel.setLayout(new OverlayLayout(panel));
        panel.add(getLabel());
    }

    private JLabel getLabel() {
        var label = new JLabel();
        label.setIcon(new ImageIcon(imageFactory.get("menuBackground")));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setBackground(Color.WHITE);
        label.setOpaque(true);
        label.add(getPlayButton());
        label.add(getExitButton());

        return label;
    }

    private JButton getPlayButton() {
        var playButton = new JButton();

        playButton.addActionListener(e -> onPlayButtonClicked());
        playButton.setBounds(270,375,285,89);
        playButton.setIcon(new ImageIcon(imageFactory.get("playButton")));

        return playButton;
    }

    private JButton getExitButton() {
        var exitButton = new JButton();

        exitButton.addActionListener(e -> onExitButtonClicked());
        exitButton.setBounds(275,491,285,89);
        exitButton.setIcon(new ImageIcon(imageFactory.get("exitButton")));

        return exitButton;
    }

    private void onPlayButtonClicked() {
        var window = Window.getInstance();
        window.setContentPane(GameMenu.getInstance().getPanel());
    }

    private void onExitButtonClicked() {
        System.exit(0);
    }
}




