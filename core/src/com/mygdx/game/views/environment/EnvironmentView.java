package com.mygdx.game.views.environment;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.util.PerlinNoiseGenerator;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public class EnvironmentView extends Group {

    private final Environment environment;
    private final double[][] heightMap;

    public EnvironmentView(Environment environment) {
        this.environment = environment;
        this.heightMap = generateHeightMap(10);

        addTileViews();
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
