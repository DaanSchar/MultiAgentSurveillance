package com.mygdx.game.views.brickandmortar;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.util.TextureRepository;
import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.environment.MemoryTile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public class BrickAndMortarView extends Group {

    private final Environment environment;

    private final Color exploredColor;
    private final Color visitedColor;

    private final TextureRepository textureRepository;

    public BrickAndMortarView(Agent agent, boolean visible) {
        this(agent);
        setVisible(visible);
    }

    public BrickAndMortarView(Agent agent) {
        this.environment = agent.getMemoryModule().getMap();
        this.textureRepository = TextureRepository.getInstance();
        this.visitedColor = new Color(0.2f, 0.2f, 0.2f, 0.7f);
        this.exploredColor = new Color(0.5f, 0.5f, 0.5f, 0.7f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        for (Tile tile : environment) {
            drawTile(batch, tile);
        }
    }

    public void draw(ShapeRenderer shapeRenderer, float parentAlpha) {
        if (isVisible()) {
            for (Tile tile : environment) {
                drawTileOutline(shapeRenderer, tile);
            }
        }
    }

    private void drawTile(Batch batch, Tile tile) {
        Texture texture = getTexture(tile);

        if (texture != null) {
            Position position = tile.getPosition();

            batch.draw(
                    getTexture(tile),
                    position.getX(),
                    position.getY(),
                    TextureRepository.TILE_WIDTH,
                    TextureRepository.TILE_HEIGHT
            );
        }
    }

    private void drawTileOutline(ShapeRenderer shapeRenderer, Tile tile) {
        Color color = getColor(tile);

        if (color != null) {
            Position position = tile.getPosition();

            shapeRenderer.rect(
                    position.getX(),
                    position.getY(),
                    TextureRepository.TILE_WIDTH,
                    TextureRepository.TILE_HEIGHT,
                    color, color, color, color
            );
        }
    }

    private Texture getTexture(Tile tile) {
        Color color = getColor(tile);

        if (color == null) {
            return null;
        }

        return textureRepository.getTile(color);
    }

    private Color getColor(Tile tile) {
        MemoryTile memoryTile = (MemoryTile) tile;

        if (memoryTile.isVisited()) {
            return visitedColor;
        }

        if (memoryTile.isExplored()) {
            return exploredColor;
        }

        return null;
    }


}
