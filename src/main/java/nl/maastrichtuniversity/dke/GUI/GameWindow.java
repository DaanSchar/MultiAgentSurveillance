package nl.maastrichtuniversity.dke.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import nl.maastrichtuniversity.dke.*;
import nl.maastrichtuniversity.dke.areas.*;
    /**
     * GUIboard class
     */

public class GameWindow  {
		

    /**
     * Create GameComponent name it game
     */	
	GameComponent game;

    /**
     * set the icon of the game
     */
    ImageIcon icon = new ImageIcon(GameWindow.class.getResource("/images/icon.png"));

    /**
     * Creating new Frame (window)
     */

    JFrame window = new JFrame("GROUP 14");

    /**
     * Create variables for the background image
     * the back button 
     */

    ImageIcon backImage = new ImageIcon(GameWindow.class.getResource("/images/next.jpg"));
    JButton back = new JButton("BACK");    

    Color color1 = new Color(230,230,230);
    Color color2 = new Color(173,237,153);

    JButton exit = new JButton("EXIT");    

    AnimationListener animationListener = new AnimationListener();

    Environment environment;

public GameWindow(Environment environment) {

    this.environment = environment;
    System.out.println(environment.getHeight());

    game = new GameComponent(environment);


   /**
    * Implement the back button
    * set the bounds,the background color,the border and add the actionlistener
    */

    back.setBounds(0 ,0,75,40);
    back.addActionListener(animationListener);
    back.setBackground(color1);   
    back.setFocusable(false);   
    back.setBorder(BorderFactory.createBevelBorder(0, Color.red , Color.blue));
    /**
     * Implement the exit button. set the bounds,the background color,the border and add the actionlistener
     */

    exit.setBounds(80,0,75,40);
    exit.addActionListener(animationListener);
    exit.setBackground(color1);   
    exit.setFocusable(false);   
    exit.setBorder(BorderFactory.createBevelBorder(0, Color.red , Color.blue));  



             
    /**
     *  Set how to close the frame.
     *  Set the size of the frame.
     *  Adding the label to the frame.
     *  Set the frame visible.
     *  Add back button to the frame
     *  Add the gamecomponent to the frame   
     */
    window.setUndecorated(true);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setSize((int) environment.getWidth()*10, (int)environment.getHeight()*10);
    window.setLocationRelativeTo(null);
    window.getContentPane().setBackground(color2);
    //window.add(back);    
    //window.add(exit);         
    window.add(game);    
    window.setIconImage(icon.getImage());    
    window.setVisible(true);
}



 /**
   *  Creat an Action listener class.
   *  @param e which is the buttons that have an ActionListener.
   */


class AnimationListener implements ActionListener {
    public void actionPerformed(ActionEvent e){       

   /**
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

                  
    window.repaint();             

}
    }

}

