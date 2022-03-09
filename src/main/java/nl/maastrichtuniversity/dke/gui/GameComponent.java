package nl.maastrichtuniversity.dke.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.Timer;

import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.discrete.GameSystem;
import nl.maastrichtuniversity.dke.discrete.Scenario;
import nl.maastrichtuniversity.dke.discrete.Tile;
import nl.maastrichtuniversity.dke.discrete.TileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameComponent extends JComponent{

	private final Scenario scenario;
	private int textureSize;

	private int guardY = 100;
	private int guardX = 0;
	private int panningX=0;
	private int panningY=0;

	private static Logger logger = LoggerFactory.getLogger(GameComponent.class);

	public GameComponent(Scenario scenario){
		this.scenario = scenario;
		double scale = scenario.getScaling()*100;
		textureSize = (int) scale;
		moveGuards();
	}

	public void paintComponent(Graphics g) {
		var environment = scenario.getEnvironment();
		drawAreas(g, environment.get(TileType.WALL), ImageFactory.get("wallTexture"));
		drawAreas(g, environment.get(TileType.TELEPORT), ImageFactory.get("teleportTexture"));
		drawAreas(g, environment.get(TileType.SPAWN_GUARDS), ImageFactory.get("spawnAreaTexture"));
		drawAreas(g, environment.get(TileType.SPAWN_INTRUDERS), ImageFactory.get("spawnAreaTexture"));
		drawAreas(g, environment.get(TileType.WINDOW), ImageFactory.get("windowTexture"));
		drawAreas(g, environment.get(TileType.DOOR), ImageFactory.get("doorTexture"));
		drawAreas(g, environment.get(TileType.SENTRY), ImageFactory.get("sentryTowerTexture"));
		drawAreas(g, environment.get(TileType.TARGET), ImageFactory.get("targetTexture"));
		drawAreas(g, environment.get(TileType.SHADED), ImageFactory.get("shadedTexture"));
		drawAgents(g);
	}

	private void drawAgents(Graphics g) {
		var guards = scenario.getGuards();

		for (Agent agent : guards) {
			drawAgent(agent, g);
		}
	}

	private void drawAgent(Agent agent, Graphics g) {
		switch (agent.getDirection()) {
			case NORTH -> drawAgent(agent, ImageFactory.get("guardNorth"), g);
			case SOUTH -> drawAgent(agent, ImageFactory.get("guardSouth"), g);

			// west and east are swapped deliberately
			case EAST -> drawAgent(agent, ImageFactory.get("guardWest"), g);
			case WEST -> drawAgent(agent, ImageFactory.get("guardEast"), g);

			default -> logger.error("Unknown direction");
		}
	}

	private void drawAgent(Agent agent, BufferedImage texture, Graphics g) {
		g.drawImage(
				texture,
				panningX+agent.getPosition().getX() * textureSize,
				panningY+ agent.getPosition().getY()*textureSize,
				textureSize,
				textureSize,
				null
		);
	}


	private void drawAreas(Graphics g, List<Tile> tiles, BufferedImage image ) {
		for (Tile tile : tiles) {
			drawArea(g, tile, image);
		}
	}

	private void drawArea(Graphics g, Tile tile, BufferedImage image) {
			g.drawImage(
					image,
					panningX + (tile.getPosition().getX() * (textureSize)),
					panningY + (tile.getPosition().getY() * (textureSize)),
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
			time.updateAndGet(v -> (v + scenario.getTimeStep()));
			repaint();
		});

		timer.start();
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
