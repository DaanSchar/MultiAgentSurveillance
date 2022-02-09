package nl.maastrichtuniversity.dke.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import nl.maastrichtuniversity.dke.scenario.Environment;

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
    BufferedImage spawnArea=null;   	

	Environment environment;
	int texturesize;

	int guardy = 100;
	int guardx = 0;
	int panningX=0;
	int panningY=0;

	public GameComponent(Environment environment){

		this.environment = environment;
		double scal = environment.getScaling()*100;
		texturesize = (int) scal;
 		  /**
   		   * Assign the images of the pieces
   		   */

        try
        {
			targetArea = ImageIO.read(GameComponent.class.getResource("/images/texture/targetarea.png"));
			spawnArea = ImageIO.read(GameComponent.class.getResource("/images/texture/spawnArea.png"));
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
		g.drawImage(guard1,panningX+300,panningY+guardy,texturesize,texturesize,null);


		
		//createWalls 
		for (int i =0;i<environment.getWalls().size() ;i++ ) {
			for (int j =0; j<environment.getWalls().get(i).getPositions().size() ;j++ ) {
				g.drawImage(wall,panningX+((int)environment.getWalls().get(i).getPositions().get(j).getX())*texturesize,panningY+((int)environment.getWalls().get(i).getPositions().get(j).getY())*texturesize,texturesize,texturesize,null);
			}
		}

		//CreateTeleportPortals
		 for (int i =0;i<environment.getTeleportPortals().size() ;i++ ) {
		 	for (int j =0; j<environment.getTeleportPortals().get(i).getPositions().size() ;j++ ) {
		 		g.drawImage(teleport,panningX+((int)environment.getTeleportPortals().get(i).getPositions().get(j).getX())*texturesize,panningY+((int)environment.getTeleportPortals().get(i).getPositions().get(j).getY())*texturesize,texturesize,texturesize,null);
		 	}
		 }	

		//createSpawnAreaIntruders
		for (int i =0;i<environment.getSpawnAreaIntruders().size() ;i++ ) {
			for (int j =0; j<environment.getSpawnAreaIntruders().get(i).getPositions().size() ;j++ ) {
				g.drawImage(spawnArea,panningX+((int)environment.getSpawnAreaIntruders().get(i).getPositions().get(j).getX())*texturesize,panningY+((int)environment.getSpawnAreaIntruders().get(i).getPositions().get(j).getY())*texturesize,texturesize,texturesize,null);
			}
		}	

		//createSpawnAreaGuards
		for (int i =0;i<environment.getSpawnAreaGuards().size() ;i++ ) {
			for (int j =0; j<environment.getSpawnAreaGuards().get(i).getPositions().size() ;j++ ) {
				g.drawImage(spawnArea,panningX+((int)environment.getSpawnAreaGuards().get(i).getPositions().get(j).getX())*texturesize,panningY+((int)environment.getSpawnAreaGuards().get(i).getPositions().get(j).getY())*texturesize,texturesize,texturesize,null);
			}
		}	

		//setTextures


		//createWindows
		for (int i =0;i<environment.getWindows().size() ;i++ ) {
			for (int j =0; j<environment.getWindows().get(i).getPositions().size() ;j++ ) {
				g.drawImage(window,panningX+((int)environment.getWindows().get(i).getPositions().get(j).getX())*texturesize,panningY+((int)environment.getWindows().get(i).getPositions().get(j).getY())*texturesize,texturesize,texturesize,null);
			}
		}

		//createDoors
		for (int i =0;i<environment.getDoors().size() ;i++ ) {
			for (int j =0; j<environment.getDoors().get(i).getPositions().size() ;j++ ) {
				g.drawImage(door,panningX+((int)environment.getDoors().get(i).getPositions().get(j).getX())*texturesize,panningY+((int)environment.getDoors().get(i).getPositions().get(j).getY())*texturesize,texturesize,texturesize,null);
			}
		}

		//createSentryTowers
		for (int i =0;i<environment.getSentrytowers().size() ;i++ ) {
			for (int j =0; j<environment.getSentrytowers().get(i).getPositions().size() ;j++ ) {
				g.drawImage(sentrytower,panningX+((int)environment.getSentrytowers().get(i).getPositions().get(j).getX())*texturesize,panningY+((int)environment.getSentrytowers().get(i).getPositions().get(j).getY())*texturesize,texturesize,texturesize,null);
			}
		}

		//createTargetArea
		for (int i =0;i<1 ;i++ ) {
			for (int j =0; j<environment.getTargetArea().getPositions().size() ;j++ ) {
				g.drawImage(targetArea,panningX+((int)environment.getTargetArea().getPositions().get(j).getX())*texturesize,panningY+((int)environment.getTargetArea().getPositions().get(j).getY())*texturesize,texturesize,texturesize,null);
			if ((environment.getTargetArea().getPositions().size())/2 + 2== j) {
				g.drawImage(target,panningX+((int)environment.getTargetArea().getPositions().get(j).getX())*texturesize,panningY+((int)environment.getTargetArea().getPositions().get(j).getY())*texturesize,texturesize,texturesize,null);
				}
			}
		}
		//createTrees
		for (int i =0;i<environment.getShadedAreas().size() ;i++ ) {
			for (int j =0; j<environment.getShadedAreas().get(i).getPositions().size() ;j++ ) {
				g.drawImage(tree,panningX+((int)environment.getShadedAreas().get(i).getPositions().get(j).getX())*texturesize,panningY+((int)environment.getShadedAreas().get(i).getPositions().get(j).getY())*texturesize,texturesize,texturesize,null);
			}
		}





		//move guard
		//moveGuards();


	}
	public void moveGuards(){
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guardy++;
				if (guardy>500) {
					((Timer)e.getSource()).stop();
				}
				repaint();
			}
		});
		timer.start();
	}
	public void moveIntuders(){
		
	}
	public void zoomIn(){
		texturesize=texturesize+5;
	}
	public void zoomOut(){
		texturesize=texturesize-5;
	}
	public void panning(int x,int y){
		System.out.println(x);
		panningX = x;
		panningY = y;
		
	}
	public void resize(){
		texturesize = (int) (environment.getScaling()*100);
		panningX = 0;
		panningY = 0; 		
	}
}
