package nl.maastrichtuniversity.dke.GUI;

import nl.maastrichtuniversity.dke.scenario.MapParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
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

    ImageIcon image2 = new ImageIcon(Menu.class.getResource("/images/settings/play.jpg"));
    ImageIcon image4 = new ImageIcon(Menu.class.getResource("/images/settings/exit.jpg"));
    ImageIcon image = new ImageIcon(Menu.class.getResource("/images/settings/background.jpg"));

    ImageIcon icon = new ImageIcon(Menu.class.getResource("/images/settings/icon.png"));

    ImageIcon uploadI = new ImageIcon(Menu.class.getResource("/images/settings/upload.png"));
    ImageIcon designI = new ImageIcon(Menu.class.getResource("/images/settings/design.png"));
    ImageIcon manuallyI = new ImageIcon(Menu.class.getResource("/images/settings/manually.png"));
    ImageIcon selectI = new ImageIcon(Menu.class.getResource("/images/settings/select.png"));
    ImageIcon label2I = new ImageIcon(Menu.class.getResource("/images/settings/mapsettings.jpg"));
    ImageIcon backI = new ImageIcon(Menu.class.getResource("/images/settings/bback.png"));


    /**
     * Create a general label
     * Creating 3 Buttons and add thier icons (play , settings , exit ).
     */

    JLabel label = new JLabel();
    JLabel label2 = new JLabel();

    JButton upload = new JButton(uploadI);
    JButton design = new JButton(designI);
    JButton manually = new JButton(manuallyI);
    JButton select = new JButton(selectI);


    JButton play = new JButton(image2);
    JButton exit = new JButton(image4);
    JButton back = new JButton(backI);

    
public Menu() {

   /**
    * Implement the button. set the bounds and add the actionlistener
    */

    back.setBounds(760,0,40,40);
    back.addActionListener(this);
    back.setBackground(new Color(155,223,232));

    upload.setBounds(310,368,210,44);
    upload.addActionListener(this);

    design.setBounds(310,425,210,44);
    design.addActionListener(this);

    manually.setBounds(310,483,210,44);
    manually.addActionListener(this);

    select.setBounds(310,535,210,44);
    select.addActionListener(this);
  
   /**
    *  Set the label2 icon
    *  Set the label2 location
    *  Set the label2 background color.
    *  Add the buttons to one label2.
    */
    label2.setIcon(label2I);
    label2.setHorizontalAlignment(JLabel.CENTER);
    label2.setVerticalAlignment(JLabel.CENTER);
    label2.setBackground(Color.WHITE);
    label2.setOpaque(true);
    label2.add(upload);
    label2.add(design);
    label2.add(manually);
    label2.add(select);
    label2.add(back);




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
     label.setVisible(false);
     label2.setVisible(true);
     window.add(label2);
     //GameSettings GameSettings = new GameSettings();
 }
    if(e.getSource()==back){
     label.setVisible(true);
     label2.setVisible(false);
 }
    if(e.getSource()==manually){
     window.dispose();
     GameSettings GameSettings = new GameSettings();
 }
        if(e.getSource()==upload){
           JOptionPane.showMessageDialog(upload,"SELECT THE MAP FILE" );
           JFileChooser fileChooser = new JFileChooser();
           fileChooser.setCurrentDirectory(new File("C:"));
           int response = fileChooser.showOpenDialog(null);
           if (response==JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            System.out.println(file);
            //MapParser mapParser = new MapParser(file);
            //Scenario senario = mapParser.parse();

              
           }
        }
        if(e.getSource()==select){
           JOptionPane.showMessageDialog(select,"NOT IMPLEMENTED YET! :(" );
        } 
        if(e.getSource()==design){
           JOptionPane.showMessageDialog(design,"NOT IMPLEMENTED YET! :(" );
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
