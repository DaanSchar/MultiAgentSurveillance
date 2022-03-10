package nl.maastrichtuniversity.dke.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.Timer;

import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.agents.Direction;
import nl.maastrichtuniversity.dke.discrete.*;
import nl.maastrichtuniversity.dke.gui.ImageFactory;
import nl.maastrichtuniversity.dke.util.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameComponent extends JComponent{

	private final Scenario scenario;
	private Environment environment;
	private int textureSize;

	private static final Logger logger = LoggerFactory.getLogger(GameComponent.class);

	private int panningX=0;
	private int panningY=0;

	public GameComponent(Scenario scenario ,Environment environment){
		this.scenario = scenario;
		double scale = scenario.getScaling()*100;
		textureSize = (int) scale;
		this.environment = environment;
		moveGuards();

	}


	public void paintComponent(Graphics g) {

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
		drawAreas(g, environment.get(TileType.UNKNOWN), ImageFactory.get("unknownTexture"));
		drawAgents(g);
		
	}

	private void drawAgents(Graphics g) {
		for (Agent agent : scenario.getGuards()) {
            drawAgent(g, agent);
        }
	}

	private void drawAgent(Graphics g, Agent agent) {
		switch (agent.getDirection()) {
			case NORTH -> drawAgent(g, agent, ImageFactory.get("guardNorth"));
			case SOUTH -> drawAgent(g, agent, ImageFactory.get("guardSouth"));
			case EAST -> drawAgent(g, agent, ImageFactory.get("guardWest"));
			case WEST -> drawAgent(g, agent, ImageFactory.get("guardEast"));
			default -> logger.error("Unknown direction given for agent!");
		}
	}

	private void drawAgent(Graphics g, Agent agent, BufferedImage texture) {
            g.drawImage(
                    texture,
                    panningX + agent.getPosition().getX() * textureSize,
                    panningY + agent.getPosition().getY() * textureSize,
                    textureSize,
                    textureSize,
                    null
            );
	}

	private void drawAreas(Graphics g, List<Tile> tiles, BufferedImage image ) {

			for (Tile tile : tiles) {
				drawArea(g, tile, image);
				if(tile.getCommunicationMarks().size()>0){
					drawMark(g, tile);
				}
			}


	}

	private void drawArea(Graphics g, Tile tile, BufferedImage image) {

			g.drawImage(
					image,
					panningX + tile.getPosition().getX() * (textureSize),
					panningY + tile.getPosition().getY() * (textureSize),
					textureSize,
					textureSize,
					null
			);



		}
	private void drawMark(Graphics g, Tile tile) {
		g.setColor(tile.getCommunicationMarks().get(0).getColor());
		g.fillOval(
				panningX + tile.getPosition().getX() * (textureSize),
				panningY + tile.getPosition().getY() * (textureSize),
				textureSize,
				textureSize
		);
	}





	public void moveGuards(){
		GameSystem system = new GameSystem(scenario);
		AtomicReference<Double> time = new AtomicReference<>((double) 0);

		Timer timer = new Timer(300, e -> {

			system.update(time.get());
			time.updateAndGet(v -> v + scenario.getTimeStep());
			repaint();
		});
		timer.start();
	}

	public void zoomIn(){
		textureSize = textureSize +1;
	}
	public void zoomOut(){
		textureSize = textureSize -1;
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
	public void isAgentMap(){
		textureSize = textureSize-7;
	}
	public Environment getAgentMap(){
		return scenario.getGuards().get(0).getMemoryModule().getMap();
	}
	public void setAgentMap(Environment environment){
		this.environment = environment;

	}



}
