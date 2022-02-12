package nl.maastrichtuniversity.dke.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import nl.maastrichtuniversity.dke.areas.Area;
import nl.maastrichtuniversity.dke.scenario.Environment;
import nl.maastrichtuniversity.dke.util.Vector;

public class GameComponent extends JComponent{

    /**
     * Create variables the images of te pieces
     */
    BufferedImage targetArea;
    BufferedImage wall;
    BufferedImage teleport;
    BufferedImage tree;
    BufferedImage door;
    BufferedImage window;
    BufferedImage sentrytower;
    BufferedImage target;

    BufferedImage guard1;
    BufferedImage guardleft1;
    BufferedImage spawnArea;

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

		/*
		 * Assign the images of the pieces
		 */
        try
        {
			targetArea = ImageIO.read(Objects.requireNonNull(GameComponent.class.getResource("/images/texture/targetarea.png")));
			spawnArea = ImageIO.read(Objects.requireNonNull(GameComponent.class.getResource("/images/texture/spawnArea.png")));
        	wall = ImageIO.read(Objects.requireNonNull(GameComponent.class.getResource("/images/texture/wall.png")));
        	teleport = ImageIO.read(Objects.requireNonNull(GameComponent.class.getResource("/images/texture/teleport.png")));
        	tree = ImageIO.read(Objects.requireNonNull(GameComponent.class.getResource("/images/texture/tree.png")));
        	door = ImageIO.read(Objects.requireNonNull(GameComponent.class.getResource("/images/texture/door.png")));
        	window = ImageIO.read(Objects.requireNonNull(GameComponent.class.getResource("/images/texture/window.png")));
        	sentrytower = ImageIO.read(Objects.requireNonNull(GameComponent.class.getResource("/images/texture/sentrytower.png")));
        	target = ImageIO.read(Objects.requireNonNull(GameComponent.class.getResource("/images/texture/target.png")));
        	guard1 = ImageIO.read(Objects.requireNonNull(GameComponent.class.getResource("/images/guard/guard1.png")));
        	guardleft1 = ImageIO.read(Objects.requireNonNull(GameComponent.class.getResource("/images/guard/guardleft.png")));

        }
        catch(IOException e) {
			e.printStackTrace();
        }		

	}
	public void paintComponent(Graphics g){
		g.drawImage(guard1,panningX+300,panningY+guardy,texturesize,texturesize,null);
		drawAreas(g, environment.getWalls(), wall);
		drawAreas(g, environment.getTeleportPortals(), teleport);
		drawAreas(g, environment.getSpawnAreaIntruders(), spawnArea);
		drawAreas(g, environment.getSpawnAreaGuards(), spawnArea);
		drawAreas(g, environment.getWindows(), window);
		drawAreas(g, environment.getDoors(), door);
		drawAreas(g, environment.getSentryTowers(), sentrytower);
		drawAreas(g, environment.getTargetArea(), target);
		drawAreas(g, environment.getShadedAreas(), tree);
	}

	private void drawAreas(Graphics g, List<Area> areas, BufferedImage image ) {
		for (Area area : areas) {
			drawArea(g, area.getPositions(), image);
		}
	}

	private void drawArea(Graphics g, List<Vector> positions, BufferedImage image) {
		for (Vector position : positions) {
			g.drawImage(
					image,
					panningX + ((int) position.getX()) * texturesize,
					panningY + ((int) position.getY()) * texturesize,
					texturesize,
					texturesize,
					null
			);
		}
	}


	public void moveGuards(){
		Timer timer = new Timer(1000, e -> {
			guardy++;
			if (guardy>500) {
				((Timer)e.getSource()).stop();
			}
			repaint();
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
