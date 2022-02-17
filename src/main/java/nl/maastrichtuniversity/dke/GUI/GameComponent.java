package nl.maastrichtuniversity.dke.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.Timer;

import nl.maastrichtuniversity.dke.discrete.GameRepository;
import nl.maastrichtuniversity.dke.discrete.Scenario;
import nl.maastrichtuniversity.dke.discrete.Tile;
import nl.maastrichtuniversity.dke.discrete.TileType;

public class GameComponent extends JComponent{

	private final Scenario scenario;
	private int textureSize;

	private int guardY = 100;
	private int guardX = 0;
	private int panningX=0;
	private int panningY=0;

	public GameComponent(Scenario scenario){
		this.scenario = scenario;
		double scale = scenario.getScaling()*100;
		textureSize = (int) scale;
	}

	public void paintComponent(Graphics g) {
		var environment = scenario.getEnvironment();
		var agent = scenario.getIntruders().get(0);

		g.drawImage(ImageFactory.get("guard1"),panningX+agent.getPosition().getX(),panningY+ agent.getPosition().getY(), textureSize, textureSize,null);
		drawAreas(g, environment.get(TileType.WALL), ImageFactory.get("wallTexture"));
		drawAreas(g, environment.get(TileType.TELEPORT), ImageFactory.get("teleportTexture"));
		drawAreas(g, environment.get(TileType.SPAWN_GUARDS), ImageFactory.get("spawnAreaTexture"));
		drawAreas(g, environment.get(TileType.SPAWN_INTRUDERS), ImageFactory.get("spawnAreaTexture"));
		drawAreas(g, environment.get(TileType.WINDOW), ImageFactory.get("windowTexture"));
		drawAreas(g, environment.get(TileType.DOOR), ImageFactory.get("doorTexture"));
		drawAreas(g, environment.get(TileType.SENTRY), ImageFactory.get("sentryTowerTexture"));
		drawAreas(g, environment.get(TileType.TARGET), ImageFactory.get("targetTexture"));
		drawAreas(g, environment.get(TileType.SHADED), ImageFactory.get("shadedTexture"));

		GameRepository.run();
	}


	private void drawAreas(Graphics g, List<Tile> tiles, BufferedImage image ) {
		for (Tile tile : tiles) {
			drawArea(g, tile, image);
		}
	}

	private void drawArea(Graphics g, Tile tile, BufferedImage image) {
			g.drawImage(
					image,
					panningX +  tile.getPosition().getX() * textureSize,
					panningY +  tile.getPosition().getY() * textureSize,
					textureSize,
					textureSize,
					null
			);

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
		textureSize = (int) (scenario.getScaling()*100);
		panningX = 0;
		panningY = 0; 		
	}
}
