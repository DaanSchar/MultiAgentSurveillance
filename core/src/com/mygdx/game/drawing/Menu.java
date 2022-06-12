package com.mygdx.game.drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu implements ActionListener {


    private final JFrame window = new JFrame();

    /*
     * Create a general label
     * Creating 3 Buttons and add their icons (play , settings , exit ).
     */
    private final JLabel label = new JLabel();
    private final JLabel label2 = new JLabel();

    private final ImageFactory imageFactory = ImageFactory.getInstance();

    private final JButton play = new JButton(new ImageIcon(imageFactory.get("playButton")));
    private final JButton exit = new JButton(new ImageIcon(imageFactory.get("exitButton")));
    private final JButton back = new JButton(new ImageIcon(imageFactory.get("backButton")));


    public Menu() {

        // Implement the button. set the bounds and add the action listener
        back.setBounds(760, 0, 40, 40);
        back.addActionListener(this);
        back.setBackground(new Color(155, 223, 232));

        /*
         *  Set the label2 icon
         *  Set the label2 location
         *  Set the label2 background color.
         *  Add the buttons to one label2.
         */


        // Implement the play button. set the bounds and add the action listener
        play.setBounds(270, 375, 285, 89);
        play.addActionListener(this);

        //Implement the exit button.set the bounds and add the action listener
        exit.setBounds(275, 491, 285, 89);
        exit.addActionListener(this);

        /*
         *  Set the label icon
         *  Set the label location
         *  Set the label background color.
         *  Add the buttons to one label.
         */
        label.setIcon(new ImageIcon(imageFactory.get("menuBackground")));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setBackground(Color.WHITE);
        label.setOpaque(true);
        label.add(play);
        label.add(exit);

        window.add(label);
        window.setUndecorated(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setLocationRelativeTo(null);
        window.setIconImage(ImageFactory.getInstance().get("gameIcon"));
        window.setVisible(true);

    }

    /**
     * Create an Action listener method.
     *
     * @param e which is the buttons that have an ActionListener.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        /*
         *  If Play button clicked create new GameSettings class.
         *  Dispose the current frame.
         */
        if (e.getSource() == play) {
            //label.setVisible(false);
            //label2.setVisible(true);
            //window.add(label2);
            new Main();

            window.dispose();

        }
        //GameSettings GameSettings = new GameSettings();
        if (e.getSource() == back) {
            label.setVisible(true);
            label2.setVisible(false);
        }


        // If exit  clicked close the frame.
        if (e.getSource() == exit) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Menu();
    }

}
