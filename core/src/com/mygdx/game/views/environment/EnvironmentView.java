package com.mygdx.game.views.environment;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.util.PerlinNoiseGenerator;
import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public class EnvironmentView extends Group {

    private Environment environment;
    private final Scenario scenario;
    private final double[][] heightMap;
    private @Getter @Setter boolean showMemoryMap;

    public EnvironmentView(Scenario scenario, boolean showMemoryMap) {
        this(scenario);
        this.showMemoryMap = showMemoryMap;
    }

    public EnvironmentView(Scenario scenario) {
        this.scenario = scenario;
        this.environment = scenario.getEnvironment();
        int octaveCount = 10;
        this.heightMap = generateHeightMap(octaveCount);
        this.showMemoryMap = false;

        addTileViews();
    }

    public void update() {
        super.clear();
        this.environment = getTileMap();
        addTileViews();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void draw(ShapeRenderer shapeRenderer, float parentAlpha) {
        for (Actor actor : getChildren()) {
            TileView tileView = (TileView) actor;
            tileView.draw(shapeRenderer, parentAlpha);
        }
    }

    private void addTileViews() {
        for (Tile tile : environment) {
            addTileView(tile);
        }
    }

    private Environment getTileMap() {
        if (showMemoryMap) {
            if (scenario.getGuards().size() != 0) {
                return scenario.getGuards().getMemoryMap();
            }
        }
        return scenario.getEnvironment();
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
