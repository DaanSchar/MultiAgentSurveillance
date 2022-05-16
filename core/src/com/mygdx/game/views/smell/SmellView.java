package com.mygdx.game.views.smell;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.util.TextureRepository;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.Smell;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.util.List;

public class SmellView {
    private final List<Smell> smells;
    private final Color color;

    public SmellView(Scenario scenario) {
        this.smells = scenario.getSmellMap();
        color = new Color().RED;
    }

    public void draw(ShapeRenderer batch, float parentAlpha) {
        smells.forEach(smell -> {
            Position position = smell.getPosition();
            batch.rect(
                    position.getX(),
                    position.getY(),
                    TextureRepository.TILE_WIDTH,
                    TextureRepository.TILE_HEIGHT,
                    color, color, color, color
            );
        });
    }
}
