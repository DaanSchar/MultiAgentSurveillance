package com.mygdx.game.views.vision;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.util.TextureRepository;
import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.ArrayList;
import java.util.List;

public class VisionView extends Actor {

    private final List<Agent> agents;
    private final Color color;

    private final TextureRepository textureRepository;

    public VisionView(Scenario scenario, boolean visible) {
        this(scenario);
        setVisible(visible);
    }

    public VisionView(Scenario scenario) {
        this.agents = getAllAgents(scenario);
        this.color = new Color(0.9f, 0.95f, 0f, 0.3f);
        this.textureRepository = TextureRepository.getInstance();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        drawTiles(batch);
    }

    public void draw(ShapeRenderer shapeRenderer, float parentAlpha) {
        if (isVisible()) {
            drawTileOutlines(shapeRenderer);
        }
    }

    private List<Agent> getAllAgents(Scenario scenario) {
        List<Agent> agents = new ArrayList<>(scenario.getGuards());
        agents.addAll(scenario.getIntruders());
        return agents;
    }

    private void drawTiles(Batch batch) {
        for (Agent agent : agents) {
            List<Tile> visibleTiles = agent.getVisionModule().getVisibleTiles();

            for (Tile tile : visibleTiles) {
                drawTile(batch, tile.getPosition());
            }
        }
    }

    private void drawTile(Batch batch, Position position) {
        batch.draw(
                textureRepository.getTile(color),
                position.getX() * TextureRepository.TILE_WIDTH,
                position.getY() * TextureRepository.TILE_HEIGHT,
                TextureRepository.TILE_WIDTH,
                TextureRepository.TILE_HEIGHT
        );
    }

    private void drawTileOutlines(ShapeRenderer shapeRenderer) {
        for (Agent agent : agents) {
            List<Tile> visibleTiles = agent.getVisionModule().getVisibleTiles();

            for (Tile tile : visibleTiles) {
                drawTileOutline(shapeRenderer, tile.getPosition());
            }
        }
    }

    private void drawTileOutline(ShapeRenderer shapeRenderer, Position position) {
        shapeRenderer.rect(
                position.getX() * TextureRepository.TILE_WIDTH,
                position.getY() * TextureRepository.TILE_HEIGHT,
                TextureRepository.TILE_WIDTH,
                TextureRepository.TILE_HEIGHT,
                color, color, color, color
        );
    }

}
