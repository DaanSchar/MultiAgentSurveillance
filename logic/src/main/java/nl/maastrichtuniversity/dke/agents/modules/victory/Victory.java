package nl.maastrichtuniversity.dke.agents.modules.victory;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.agents.Intruder;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.TileType;

import java.util.List;

@Setter
@Getter
public class Victory implements IVictory {
    private Scenario scenario;
    private String winner = "";

    public Victory(Scenario scenario) {
        this.scenario = scenario;
    }

    public boolean intrudersHaveWon() {
        for (Intruder intruder : scenario.getIntruders()) {
            if (intruderIsOnTargetTile(intruder)) {
                return true;
            }
        }

        return false;
    }

    public boolean guardsHaveWon() {
        for (Intruder intruder : scenario.getIntruders()) {
            if (!intruder.isCaught()) {
                return false;
            }
        }
        return true;
    }

    private boolean intruderIsOnTargetTile(Intruder intruder) {
        List<Tile> tiles = scenario.getEnvironment().get(TileType.TARGET);

        for (Tile tile : tiles) {
            if (intruder.getPosition().equals(tile.getPosition())) {
                return true;
            }
        }

        return false;
    }


}
