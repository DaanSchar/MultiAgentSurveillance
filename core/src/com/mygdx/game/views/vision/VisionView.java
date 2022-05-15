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
    private final Agent agent;

    private final Color color;

    public VisionView(Scenario scenario, boolean show) {
        this(scenario);
        this.show = show;
    }

    public VisionView(Scenario scenario) {
        this.scenario = scenario;
        this.agent = scenario.getGuards().get(0);
        this.color = new Color(0.9f, 0.95f, 0f, 0.7f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        drawVisionTilesFill(batch);
    }

    public void draw(ShapeRenderer shapeRenderer, float parentAlpha) {
        drawVisionTilesOutline(shapeRenderer);
    }

    private void drawVisionTilesOutline(ShapeRenderer shapeRenderer) {
        if (show) {
            for (Tile tile : agent.getVisionModule().getVisibleTiles()) {
                drawRectangleOutline(shapeRenderer, tile.getPosition());
            }
        }
    }

    private void drawRectangleOutline(ShapeRenderer shapeRenderer, Position position) {
        shapeRenderer.rect(
                position.getX() * TextureRepository.TILE_WIDTH,
                position.getY() * TextureRepository.TILE_HEIGHT,
                TextureRepository.TILE_WIDTH,
                TextureRepository.TILE_HEIGHT,
                color, color, color, color
        );
    }

    private void drawVisionTilesFill(Batch batch) {
        if (show) {
            for (Tile tile : agent.getVisionModule().getVisibleTiles()) {
                drawRectangle(batch, tile.getPosition(), color);
            }
        }
    }

    private void drawRectangle(Batch batch, Position position, Color color) {
        batch.draw(
                getPixmapTexture(color),
                position.getX() * TextureRepository.TILE_WIDTH,
                position.getY() * TextureRepository.TILE_HEIGHT,
                TextureRepository.TILE_WIDTH,
                TextureRepository.TILE_HEIGHT
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
