package nl.maastrichtuniversity.dke.GUI;

import nl.maastrichtuniversity.dke.discrete.MapParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;


public class Menu implements ActionListener  {


    private final JFrame window = new JFrame("GROUP 14");

    /*
     * Create a general label
     * Creating 3 Buttons and add their icons (play , settings , exit ).
     */
    private JLabel label = new JLabel();
    private JLabel label2 = new JLabel();

    private JButton upload = new JButton(new ImageIcon(ImageFactory.get("uploadButton")));
    private JButton design = new JButton(new ImageIcon(ImageFactory.get("designButton")));
    private JButton manually = new JButton(new ImageIcon(ImageFactory.get("manuallyButton")));
    private JButton select = new JButton(new ImageIcon(ImageFactory.get("selectButton")));

    private JButton play = new JButton(new ImageIcon(ImageFactory.get("playButton")));
    private JButton exit = new JButton(new ImageIcon(ImageFactory.get("exitButton")));
    private JButton back = new JButton(new ImageIcon(ImageFactory.get("backButton")));


    public Menu() {

        // Implement the button. set the bounds and add the action listener
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

        /*
         *  Set the label2 icon
         *  Set the label2 location
         *  Set the label2 background color.
         *  Add the buttons to one label2.
         */
        label2.setIcon(new ImageIcon(ImageFactory.get("mapSettingsBackground")));
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setVerticalAlignment(JLabel.CENTER);
        label2.setBackground(Color.WHITE);
        label2.setOpaque(true);
        label2.add(upload);
        label2.add(design);
        label2.add(manually);
        label2.add(select);
        label2.add(back);


        // Implement the play button. set the bounds and add the action listener
        play.setBounds(270,375,285,89);
        play.addActionListener(this);

        //Implement the exit button.set the bounds and add the action listener
        exit.setBounds(275,491,285,89);
        exit.addActionListener(this);

        /*
         *  Set the label icon
         *  Set the label location
         *  Set the label background color.
         *  Add the buttons to one label.
         */
        label.setIcon(new ImageIcon(ImageFactory.get("menuBackground")));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setBackground(Color.WHITE);
        label.setOpaque(true);
        label.add(play);
        label.add(exit);

        /*
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
        window.setIconImage(ImageFactory.get("gameIcon"));
        window.setVisible(true);

    }

    /**
     *  Create an Action listener method.
     *  @param e which is the buttons that have an ActionListener.
     */
    @Override
    public void actionPerformed(ActionEvent e){

        /*
         *  If Play button clicked create new GameSettings class.
         *  Dispose the current frame.
         */
        if(e.getSource()==play) {
            label.setVisible(false);
            label2.setVisible(true);
            window.add(label2);
        }
        //GameSettings GameSettings = new GameSettings();
        if(e.getSource()==back) {
            label.setVisible(true);
            label2.setVisible(false);
        }
        if(e.getSource()==manually) {
            window.dispose();
            GameSettings GameSettings = new GameSettings();
        }
        if(e.getSource()==upload){
            JFileChooser fileChooser = new JFileChooser();
//            fileChooser.setCurrentDirectory();
            int response = fileChooser.showOpenDialog(null);

            if (response==JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

                new GameWindow(new MapParser(file).createScenario());

                window.dispose();
            }

        }
        if(e.getSource()==select){
            JOptionPane.showMessageDialog(select,"NOT IMPLEMENTED YET! :(" );
        }
        if(e.getSource()==design){
            JOptionPane.showMessageDialog(design,"NOT IMPLEMENTED YET! :(" );
        }


        // If exit  clicked close the frame.
        if(e.getSource()==exit){
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        ImageFactory.init();
        Menu Menu = new Menu();
    }

}
