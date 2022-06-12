package com.mygdx.game.drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private final JFrame window = new JFrame("Draw");
    private final Drawing drawing = new Drawing();
    private JLabel buttonContainer;
    private final Color backgroundColor = new Color(173, 237, 153);
    private JComboBox<String> textures;
    private final JButton button = new JButton("SAVE MAP");
    private final Color buttonColor = new Color(230, 230, 230);
    private final ButtonListener buttonListener;

    public Main() {
        buttonListener = new ButtonListener();
        setUpButtonContainer();
        window.add(buttonContainer);
        window.add(drawing);
        window.getContentPane().setBackground(Color.BLACK);
        window.setUndecorated(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1200, 800);
        // window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    private void setUpButtonContainer() {
        textures = new JComboBox<>();
        buttonContainer = new JLabel();
        buttonContainer.setBackground(backgroundColor);
        buttonContainer.setOpaque(true);
        buttonContainer.setBounds(0, 760, 1200, 40);
        textures.setBounds(350, 0, 250, 40);
        textures.addActionListener(buttonListener);
        textures.setBackground(buttonColor);
        textures.setFocusable(false);
        button.setBounds(650, 0, 100, 40);
        button.addActionListener(buttonListener);
        button.setBackground(buttonColor);
        button.setFocusable(false);
        button.setBorder(BorderFactory.createBevelBorder(0, Color.gray, Color.black));
        textures.setBorder(BorderFactory.createBevelBorder(0, Color.gray, Color.black));
        textures.addItem("WALL");
        textures.addItem("TARGET");
        textures.addItem("SHADED");
        textures.addItem("SPAWN_GUARDS");
        textures.addItem("SPAWN_INTRUDERS");
        textures.addItem("SENTRY");
        textures.addItem("DOOR");
        textures.addItem("WINDOW");
        textures.addItem("TELEPORT");
        buttonContainer.add(textures, 0, 0);
        buttonContainer.add(button, 0, 0);
    }

    class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            drawing.setTextureDrawing(textures.getSelectedItem());
            window.repaint();
            if (e.getSource() == button) {
                drawing.savemap();
            }
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
