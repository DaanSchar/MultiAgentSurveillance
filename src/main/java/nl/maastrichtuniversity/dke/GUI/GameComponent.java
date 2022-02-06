package nl.maastrichtuniversity.dke.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import javax.swing.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import nl.maastrichtuniversity.dke.*;
import nl.maastrichtuniversity.dke.areas.*;
public class GameComponent extends JComponent{

    /**
     * Create variables the images of te pieces
     */
    BufferedImage targetArea=null;
    BufferedImage wall=null;
    BufferedImage teleport=null;
    BufferedImage tree=null;
    BufferedImage door=null;   	
    BufferedImage window=null;   	
    BufferedImage sentrytower=null;   	
    BufferedImage target=null;   

    BufferedImage guard1=null;   	
    BufferedImage guardleft1=null;   	

	Environment environment;
	int TEXTURESIZE = 32;

	int guardy = 0;
	int guardx = 0;

	public GameComponent(Environment environment){

		environment = this.environment;

 		  /**
   		   * Assign the images of the pieces
   		   */

        try
        {
			targetArea = ImageIO.read(GameComponent.class.getResource("/images/texture/targetarea.png"));
        	wall = ImageIO.read(GameComponent.class.getResource("/images/texture/wall.png"));
        	teleport = ImageIO.read(GameComponent.class.getResource("/images/texture/teleport.png"));
        	tree = ImageIO.read(GameComponent.class.getResource("/images/texture/tree.png"));
        	door = ImageIO.read(GameComponent.class.getResource("/images/texture/door.png"));
        	window = ImageIO.read(GameComponent.class.getResource("/images/texture/window.png"));
        	sentrytower = ImageIO.read(GameComponent.class.getResource("/images/texture/sentrytower.png"));
        	target = ImageIO.read(GameComponent.class.getResource("/images/texture/target.png"));
        	guard1 = ImageIO.read(GameComponent.class.getResource("/images/guard/guard1.png"));
        	guardleft1 = ImageIO.read(GameComponent.class.getResource("/images/guard/guardleft.png"));

        }
        catch(IOException e) {
			e.printStackTrace();
        }		

	}
	public void paintComponent(Graphics g){

		//drawing textures
		g.drawImage(targetArea,100,100,TEXTURESIZE,TEXTURESIZE,null);
		g.drawImage(wall,50,50,TEXTURESIZE,TEXTURESIZE,null);
		g.drawImage(wall,82,50,TEXTURESIZE,TEXTURESIZE,null);
		g.drawImage(guard1,300,guardy,TEXTURESIZE,TEXTURESIZE,null);
		g.drawImage(guardleft1,200,200,TEXTURESIZE,TEXTURESIZE,null);



		//move guard
		moveGuards();


	}
	public void createTargetArea(){

	}
	public void createSpawnAreaIntruders(){
		
	}
	public void createSpawnAreaGuards(){
		
	}
	public void createWalls(){
		
	}
	public void createTeleports(){
		
	}
	public void createTrees(){
		
	}
	public void setTextures(){
		
	}
	public void createWindows(){
		
	}
	public void createDoors(){
		
	}
	public void createSentryTowers(){
		
	}
	public void moveGuards(){
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				guardy++;
				System.out.println(guardy);
				if (guardy>500) {
					System.out.println("sssssssss");
					((Timer)e.getSource()).stop();
				}
				repaint();
			}
		});
		timer.start();
	}
	public void moveIntuders(){
		
	}
}
