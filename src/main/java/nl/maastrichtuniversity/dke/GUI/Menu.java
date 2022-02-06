package nl.maastrichtuniversity.dke.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;

    /**
     * Menu class implemnts Action Listener .
     */

public class Menu implements ActionListener  {

    /**
     * Creating new Frame (window)
     */

    JFrame window = new JFrame("GROUP 14");

    /**
     * Declaring the images of the buttons and the app icon
     */

    ImageIcon image2 = new ImageIcon(Menu.class.getResource("/images/play.jpg"));
    ImageIcon image4 = new ImageIcon(Menu.class.getResource("/images/exit.jpg"));
    ImageIcon image = new ImageIcon(Menu.class.getResource("/images/background.jpg"));

    ImageIcon icon = new ImageIcon(Menu.class.getResource("/images/icon.png"));


    /**
     * Create a general label
     * Creating 3 Buttons and add thier icons (play , settings , exit ).
     */

    JLabel label = new JLabel();

    JButton play = new JButton(image2);
    JButton exit = new JButton(image4);

    
public Menu() {



   /**
    * Implement the play button. set the bounds and add the actionlistener
    */

    play.setBounds(270,375,285,89);
    play.addActionListener(this);

   /**
    * Implement the exit button.set the bounds and add the actionlistener
    */

    exit.setBounds(275,491,285,89);
    exit.addActionListener(this);
  
   /**
    *  Set the label icon
    *  Set the label location
    *  Set the label background color.
    *  Add the buttons to one label.
    */
    label.setIcon(image);
    label.setHorizontalAlignment(JLabel.CENTER);
    label.setVerticalAlignment(JLabel.CENTER);
    label.setBackground(Color.WHITE);
    label.setOpaque(true);
    label.add(play);
    label.add(exit);

    /**
     *  Set how to close the frame.
     *  Set the size of the frame.
     *  Adding the label to the frame.
     *  Set the frame visible.
     */
    window.setUndecorated(true);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setSize(800, 600);
    window.setLocationRelativeTo(null);
    window.add(label);
    window.setIconImage(icon.getImage());
    window.setVisible(true);

}

 /**
   *  Creat an Action listener method.
   *  @param e which is the buttons that have an ActionListener.
   */


@Override
public void actionPerformed(ActionEvent e){

    /**
     *  If Play button clicked create new GameSettings class.
     *  Dispose the current frame.
     */

    if(e.getSource()==play){
     window.dispose();
     GameSettings GameSettings = new GameSettings();
    // GUIplay GUIplay = new GUIplay(frameSize);
 }


    /**
     *  If exit  clicked close the frame.
     */

  if(e.getSource()==exit){
        System.exit(0);
        }
 

}
   public static void main(String[] args) {
    Menu Menu = new Menu();
}
}
