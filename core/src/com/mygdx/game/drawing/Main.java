package com.mygdx.game.drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    JFrame window = new JFrame("Draw");
    Drawing drawing = new Drawing();
    JLabel buttonContainer;
    Color backgroundColor = new Color(173, 237, 153);
    JComboBox<String> textures;
    JButton button = new JButton("SAVE MAP");
    Color buttonColor = new Color(230, 230, 230);
    ButtonListener buttonListener;

    public Main() {
        buttonListener = new ButtonListener();
        setUpButtonContainer();
        window.add(buttonContainer);
        window.add(drawing);
        window.getContentPane().setBackground(Color.BLACK);
        window.setUndecorated(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(60 * 20, 40 * 20);
        // window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    private void setUpButtonContainer() {
        textures = new JComboBox<>();
        buttonContainer = new JLabel();
        buttonContainer.setBackground(backgroundColor);
        buttonContainer.setOpaque(true);
        buttonContainer.setBounds(0, 40 * 20 - 40, 60 * 40, 40);
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
            if (e.getSource()==button){
                drawing.savemap();
            }
        }
    }

    public static void main(String[] args) {
        Main Main = new Main();
    }
}
