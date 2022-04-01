package nl.maastrichtuniversity.dke.gui;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class GameMenu implements Screen{

    private static GameMenu gameMenuInstance;

    /**
     * Singleton method to get the instance of the GameMenu
     * @return only instance of the GameMenu
     */
    public static GameMenu getInstance() {
        if (gameMenuInstance == null) { gameMenuInstance = new GameMenu(); }

        return gameMenuInstance;
    }



    private @Getter JPanel panel;
    private final ImageFactory imageFactory;

    private GameMenu() {
        imageFactory = ImageFactory.getInstance();
        createPanel();
    }

    private void createPanel() {
        panel = new JPanel();
        panel.setLayout(new OverlayLayout(panel));
        panel.add(getLabel());
    }

    private JLabel getLabel() {
        var label = new JLabel();

        label.setIcon(new ImageIcon(imageFactory.get("mapSettingsBackground")));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setBackground(Color.WHITE);
        label.setOpaque(true);
        label.add(getBackButton());
        label.add(getUploadButton());

        return label;
    }

    private JButton getBackButton() {
        var button = new JButton("Back");

        button.addActionListener(e -> onBackButtonClicked());
        button.setBounds(760,0,40,40);
        button.setBackground(new Color(155,223,232));
        button.setIcon(new ImageIcon(imageFactory.get("backButton")));

        return button;
    }

    private JButton getUploadButton() {
        var button = new JButton("Upload");
        button.setIcon(new ImageIcon(imageFactory.get("uploadButton")));
        button.addActionListener(e -> onUploadButtonClicked());
        button.setBounds(310,368,210,44);
        button.setBackground(new Color(155,223,232));

        return button;
    }

    private void onBackButtonClicked() {
        System.out.println("Back button clicked");
    }

    private void onUploadButtonClicked() {
        System.out.println("Upload button clicked");
    }





}
