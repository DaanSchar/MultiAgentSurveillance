package nl.maastrichtuniversity.dke.agents.modules.victory;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.agents.Intruder;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.TileType;

import java.util.List;

@Slf4j
@Setter
@Getter
public class Victory implements IVictory {
    private Scenario scenario;
    private String winner = "";
    private final int ogNumberOfIntruders;

    public Victory(Scenario scenario) {
        this.scenario = scenario;
        this.ogNumberOfIntruders = scenario.getIntruders().size();
    }

    public boolean intrudersHaveWon() {
        //GAME MODE 1
        if (scenario.getGameMode() == 1) {
            for (Intruder intruder : scenario.getIntruders()) {
                if (intruderIsOnTargetTile(intruder)) {
                    return true;
                }
            }

            return false;
        } else if (scenario.getGameMode() == 0) {
            int count = 0;
            for (Intruder intruder : scenario.getIntruders()) {
                if (intruderIsOnTargetTile(intruder)) {
                    count += 1;
                }
            }
            if (count == scenario.getIntruders().size()) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    public boolean guardsHaveWon() {
        // GAME MODE 1
        if (scenario.getGameMode() == 1) {
            for (Intruder intruder : scenario.getIntruders()) {
                if (!intruder.isCaught()) {
                    return false;
                }
            }
            return true;
        } else if (scenario.getGameMode() == 0) {
            if (scenario.getIntruders().size() < ogNumberOfIntruders) {
                return true;
            }
            return false;
        } else {
            return false;
        }
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
