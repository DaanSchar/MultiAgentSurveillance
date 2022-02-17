package nl.maastrichtuniversity.dke.discrete;

import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.maastrichtuniversity.dke.agents.AgentFactory;
import nl.maastrichtuniversity.dke.util.DebugSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor
@Setter
public class EnvironmentFactory {

    private static final Logger logger = LoggerFactory.getLogger(EnvironmentFactory.class);

    private int width;
    private int height;

    private int numberOfIntruders;
    private int numberOfGuards;

    private double baseSpeedGuards;
    private double baseSpeedIntruders;
    private double sprintSpeedGuards;
    private double sprintSpeedIntruders;

    private int viewingDistance;
    private int hearingDistanceWalking;
    private int hearingDistanceSprinting;
    private int smellingDistance;

    private Tile[][] tileMap;

    public void addArea(int x1, int y1, int x2, int y2, TileType type) {
        if (DebugSettings.AREA_DEBUG) logger.info("adding area of type " + type + ": " + x1 + " " + y1 + " " + x2 + " " + y2);

        if (this.tileMap == null && width >0 && height > 0)
            this.tileMap = new Tile[width][height];

        for (int x = x1; x < x2; x++)
            for (int y = y1; y < y2; y++)
                tileMap[x][y] = new Tile(x, y, type);
    }

    public Environment build() {
        fillInEmptyTiles();

        var environment = new Environment(this.width, this.height, this.tileMap);

        environment.setGuards(AgentFactory.createGuards(
                numberOfGuards,
                environment,
                this.baseSpeedGuards,
                this.sprintSpeedGuards,
                this.viewingDistance,
                this.hearingDistanceWalking,
                this.hearingDistanceSprinting,
                this.smellingDistance
        ));
        environment.setIntruders(AgentFactory.createIntruders(
                numberOfIntruders,
                environment,
                this.baseSpeedIntruders,
                this.sprintSpeedIntruders,
                this.viewingDistance,
                this.hearingDistanceWalking,
                this.hearingDistanceSprinting,
                this.smellingDistance
        ));

        return environment;
    }

    private void fillInEmptyTiles() {
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                if (tileMap[x][y] == null)
                    tileMap[x][y] = new Tile(x, y, TileType.EMPTY);
    }


}
