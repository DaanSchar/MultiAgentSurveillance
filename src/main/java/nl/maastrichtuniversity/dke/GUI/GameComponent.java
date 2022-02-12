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

    private BufferedImage targetArea;
    private BufferedImage wall;
    private BufferedImage teleport;
    private BufferedImage tree;
    private BufferedImage door;
    private BufferedImage window;
    private BufferedImage sentryTower;
    private BufferedImage target;
    private BufferedImage guard1;
    private BufferedImage guardLeft1;
    private BufferedImage spawnArea;

	private Environment environment;
	private int textureSize;

	private int guardY = 100;
	private int guardX = 0;
	private int panningX=0;
	private int panningY=0;

	public GameComponent(Environment environment){

		this.environment = environment;
		double scal = environment.getScaling()*100;
		textureSize = (int) scal;

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
        	sentryTower = ImageIO.read(Objects.requireNonNull(GameComponent.class.getResource("/images/texture/sentrytower.png")));
        	target = ImageIO.read(Objects.requireNonNull(GameComponent.class.getResource("/images/texture/target.png")));
        	guard1 = ImageIO.read(Objects.requireNonNull(GameComponent.class.getResource("/images/guard/guard1.png")));
        	guardLeft1 = ImageIO.read(Objects.requireNonNull(GameComponent.class.getResource("/images/guard/guardleft.png")));

        }
        catch(IOException e) {
			e.printStackTrace();
        }		

	}
	public void paintComponent(Graphics g){
		g.drawImage(guard1,panningX+300,panningY+ guardY, textureSize, textureSize,null);
		drawAreas(g, environment.getWalls(), wall);
		drawAreas(g, environment.getTeleportPortals(), teleport);
		drawAreas(g, environment.getSpawnAreaIntruders(), spawnArea);
		drawAreas(g, environment.getSpawnAreaGuards(), spawnArea);
		drawAreas(g, environment.getWindows(), window);
		drawAreas(g, environment.getDoors(), door);
		drawAreas(g, environment.getSentryTowers(), sentryTower);
		drawAreas(g, environment.getTargetArea(), target);
		drawAreas(g, environment.getShadedAreas(), tree);
	}

	/**
	 * Draws all the areas in a list on the screen
	 * @param g graphics given by the paintComponent
	 * @param areas list of areas to draw
	 * @param image image corresponding to the area to draw
	 */
	private void drawAreas(Graphics g, List<Area> areas, BufferedImage image ) {
		for (Area area : areas) {
			drawArea(g, area.getPositions(), image);
		}
	}

	/**
	 * Draws an area on the screen
	 * @param g graphics given by the paintComponent
	 * @param positions list of positions of the area
	 * @param image image of the area
	 */
	private void drawArea(Graphics g, List<Vector> positions, BufferedImage image) {
		for (Vector position : positions) {
			g.drawImage(
					image,
					panningX + ((int) position.getX()) * textureSize,
					panningY + ((int) position.getY()) * textureSize,
					textureSize,
					textureSize,
					null
			);
		}
	}


	public void moveGuards(){
		Timer timer = new Timer(1000, e -> {
			guardY++;
			if (guardY >500) {
				((Timer)e.getSource()).stop();
			}
			repaint();
		});
		timer.start();
	}
	public void moveIntuders(){
		
	}
	public void zoomIn(){
		textureSize = textureSize +5;
	}
	public void zoomOut(){
		textureSize = textureSize -5;
	}
	public void panning(int x,int y){
		System.out.println(x);
		panningX = x;
		panningY = y;
		
	}
	public void resize(){
		textureSize = (int) (environment.getScaling()*100);
		panningX = 0;
		panningY = 0; 		
	}
}
