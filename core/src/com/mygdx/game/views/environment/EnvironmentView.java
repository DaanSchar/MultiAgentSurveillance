package com.mygdx.game.views.environment;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.util.PerlinNoiseGenerator;
import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public class EnvironmentView extends Group {

    private Environment environment;
    private final Scenario scenario;
    private final double[][] heightMap;
    @Getter
    @Setter
    public boolean memory;

    public EnvironmentView(Scenario scenario, boolean memory) {
        this.scenario = scenario;
        this.environment = scenario.getEnvironment();
        int octaveCount = 10;
        this.heightMap = generateHeightMap(octaveCount);
        this.memory = memory;

        addTileViews();
    }

    public void update() {
        if (memory) {
            super.clear();
            environment = scenario.getGuards().getMemoryMap();
            addTileViews();
        } else {
            super.clear();
            environment = scenario.getEnvironment();
            addTileViews();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        update();
    }

    private void addTileViews() {
        for (Tile tile : environment) {
            addTileView(tile);
        }
    }

    private void addTileView(Tile tile) {
        Position position = tile.getPosition();
        float height = (float) heightMap[position.getX()][position.getY()];
        this.addActor(new TileView(tile, height));
    }

    private double[][] generateHeightMap(int octaveCount) {
        return PerlinNoiseGenerator.generateNormalizedPerlinNoise(
                environment.getWidth(),
                environment.getHeight(),
                octaveCount
        );
    }

}
