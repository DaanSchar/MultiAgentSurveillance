package nl.maastrichtuniversity.dke.logic.scenario.factory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor
@Setter
@Getter
public class EnvironmentFactory {

    private static final Logger logger = LoggerFactory.getLogger(EnvironmentFactory.class);

    private int width;
    private int height;


    private Tile[][] tileMap;

    public void addArea(int x1, int y1, int x2, int y2, TileType type) {
        logger.info("adding area of type " + type + ": " + x1 + " " + y1 + " " + x2 + " " + y2);

        if (this.tileMap == null && width >0 && height > 0)
            this.tileMap = new Tile[width][height];

        for (int x = x1; x < x2; x++)
            for (int y = y1; y < y2; y++)
                tileMap[x][y] = new Tile(new Position(x, y), type);
    }

    public Environment build() {
        fillInEmptyTiles();

        return new Environment(this.width, this.height, this.tileMap);
    }

    private void fillInEmptyTiles() {
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                if (tileMap[x][y] == null)
                    tileMap[x][y] = new Tile(new Position(x, y), TileType.EMPTY);
    }


}
