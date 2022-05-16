package com.mygdx.game.views.vision;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.util.TextureRepository;
import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public class VisionView extends Group {

    private @Getter @Setter boolean show;

    private final Scenario scenario;

    private final Color color;
    private final Texture texture;

    public VisionView(Scenario scenario, boolean show) {
        this(scenario);
        this.show = show;
    }

    public VisionView(Scenario scenario) {
        this.scenario = scenario;
        this.color = new Color(0.9f, 0.95f, 0f, 0.3f);
        this.texture = getPixmapTexture(color);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (show) {
            super.draw(batch, parentAlpha);
            drawTileFills(batch);
        }
    }

    public void draw(ShapeRenderer shapeRenderer, float parentAlpha) {
        if (show) {
            drawTileOutlines(shapeRenderer);
        }
    }

    private void drawTileFills(Batch batch) {
        for (Agent agent : scenario.getGuards()) {
            drawAgentTileFills(batch, agent);
        }

        for (Agent agent : scenario.getIntruders()) {
            drawAgentTileFills(batch, agent);
        }
    }

    private void drawAgentTileFills(Batch batch, Agent agent) {
        for (Tile tile : agent.getVisionModule().getVisibleTiles()) {
            drawTileFill(batch, tile.getPosition());
        }
    }

    private void drawTileFill(Batch batch, Position position) {
        batch.draw(
                texture,
                position.getX() * TextureRepository.TILE_WIDTH,
                position.getY() * TextureRepository.TILE_HEIGHT,
                TextureRepository.TILE_WIDTH,
                TextureRepository.TILE_HEIGHT
        );
    }

    private void drawTileOutlines(ShapeRenderer shapeRenderer) {
        for (Agent agent : scenario.getGuards()) {
            drawAgentTileOutlines(shapeRenderer, agent);
        }

        for (Agent agent : scenario.getIntruders()) {
            drawAgentTileOutlines(shapeRenderer, agent);
        }
    }

    private void drawAgentTileOutlines(ShapeRenderer shapeRenderer, Agent agent) {
        for (Tile tile : agent.getVisionModule().getVisibleTiles()) {
            drawTileOutline(shapeRenderer, tile.getPosition());
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

    private Texture getPixmapTexture(Color color) {
        return new Texture(getPixmapRectangle(color));
    }

    private static Pixmap getPixmapRectangle(Color color) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
        return pixmap;
    }

}
