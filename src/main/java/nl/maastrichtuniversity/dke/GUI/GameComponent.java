package nl.maastrichtuniversity.dke.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.Timer;

import nl.maastrichtuniversity.dke.areas.Area;
import nl.maastrichtuniversity.dke.scenario.Environment;
import nl.maastrichtuniversity.dke.util.Vector;

public class GameComponent extends JComponent{

	private final Environment scenario;
	private int textureSize;

	private int guardY = 100;
	private int guardX = 0;
	private int panningX=0;
	private int panningY=0;

	public GameComponent(Environment scenario){
		this.scenario = scenario;
		double scale = scenario.getStaticEnvironment().getScaling()*100;
		textureSize = (int) scale;
	}


	public void paintComponent(Graphics g) {
		var staticEnv = scenario.getStaticEnvironment();
		var dynamicEnv = scenario.getDynamicEnvironment();

		g.drawImage(ImageFactory.get("guard1"),panningX+300,panningY+ guardY, textureSize, textureSize,null);
		drawAreas(g, staticEnv.get("wall"), ImageFactory.get("wallTexture"));
		drawAreas(g, staticEnv.get("teleport"), ImageFactory.get("teleportTexture"));
		drawAreas(g, staticEnv.get("spawnAreaIntruders"), ImageFactory.get("spawnAreaTexture"));
		drawAreas(g, staticEnv.get("spawnAreaGuards"), ImageFactory.get("spawnAreaTexture"));
		drawAreas(g, dynamicEnv.getWindows(), ImageFactory.get("windowTexture"));
		drawAreas(g, dynamicEnv.getDoors(), ImageFactory.get("doorTexture"));
		drawAreas(g, staticEnv.get("sentrytower"), ImageFactory.get("sentryTowerTexture"));
		drawAreas(g, staticEnv.get("targetArea"), ImageFactory.get("targetTexture"));
		drawAreas(g, staticEnv.get("shaded"), ImageFactory.get("shadedTexture"));
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
		textureSize = (int) (scenario.getStaticEnvironment().getScaling()*100);
		panningX = 0;
		panningY = 0; 		
	}
}
