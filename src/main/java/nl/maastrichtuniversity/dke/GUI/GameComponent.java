package nl.maastrichtuniversity.dke.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.Timer;

import nl.maastrichtuniversity.dke.agents.Direction;
import nl.maastrichtuniversity.dke.discrete.GameSystem;
import nl.maastrichtuniversity.dke.discrete.Scenario;
import nl.maastrichtuniversity.dke.discrete.Tile;
import nl.maastrichtuniversity.dke.discrete.TileType;
import nl.maastrichtuniversity.dke.util.Position;

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
		moveGuards();
	}

	public void paintComponent(Graphics g) {
		var environment = scenario.getEnvironment();
		var agent = scenario.getGuards().get(0);
		var sound = scenario.getSoundMap();
		drawAreas(g, environment.get(TileType.WALL), ImageFactory.get("wallTexture"));
		drawAreas(g, environment.get(TileType.TELEPORT), ImageFactory.get("teleportTexture"));
		drawAreas(g, environment.get(TileType.SPAWN_GUARDS), ImageFactory.get("spawnAreaTexture"));
		drawAreas(g, environment.get(TileType.SPAWN_INTRUDERS), ImageFactory.get("spawnAreaTexture"));
		drawAreas(g, environment.get(TileType.WINDOW), ImageFactory.get("windowTexture"));
		drawAreas(g, environment.get(TileType.DOOR), ImageFactory.get("doorTexture"));
		drawAreas(g, environment.get(TileType.SENTRY), ImageFactory.get("sentryTowerTexture"));
		drawAreas(g, environment.get(TileType.TARGET), ImageFactory.get("targetTexture"));
		drawAreas(g, environment.get(TileType.SHADED), ImageFactory.get("shadedTexture"));
		if(agent.getDirection() == Direction.NORTH){
			g.drawImage(ImageFactory.get("guardNorth"),panningX+agent.getPosition().getX() * textureSize,panningY+ agent.getPosition().getY()*textureSize, textureSize, textureSize,null);
		}
		if(agent.getDirection() == Direction.SOUTH){
			g.drawImage(ImageFactory.get("guardSouth"),panningX+agent.getPosition().getX() * textureSize,panningY+ agent.getPosition().getY()*textureSize, textureSize, textureSize,null);
		}
		if(agent.getDirection() == Direction.EAST){
			g.drawImage(ImageFactory.get("guardWest"),panningX+agent.getPosition().getX() * textureSize,panningY+ agent.getPosition().getY()*textureSize, textureSize, textureSize,null);
		}
		if(agent.getDirection() == Direction.WEST){
			g.drawImage(ImageFactory.get("guardEast"),panningX+agent.getPosition().getX() * textureSize,panningY+ agent.getPosition().getY()*textureSize, textureSize, textureSize,null);
		}


	}


	private void drawAreas(Graphics g, List<Tile> tiles, BufferedImage image ) {
		for (Tile tile : tiles) {
			drawArea(g, tile, image);
		}
	}

	private void drawArea(Graphics g, Tile tile, BufferedImage image) {
			g.drawImage(
					image,
					panningX +  (int)(tile.getPosition().getX() * (textureSize)),
					panningY +  (int)(tile.getPosition().getY() * (textureSize)),
					textureSize,
					textureSize,
					null
			);

		}

	public void moveGuards(){
		GameSystem system = new GameSystem(scenario);
		AtomicReference<Double> time = new AtomicReference<>((double) 0);

		Timer timer = new Timer(300, e -> {

			system.update(time.get());
			time.updateAndGet(v -> new Double((double) (v + (double) scenario.getTimeStep())));
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
		panningX = x;
		panningY = y;
		
	}
	public void resize(){
		textureSize = (int) (scenario.getScaling()*100);
		panningX = 0;
		panningY = 0; 		
	}
}
