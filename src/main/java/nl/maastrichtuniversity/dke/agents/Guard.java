package nl.maastrichtuniversity.dke.agents;

import nl.maastrichtuniversity.dke.agents.modules.spawn.ISpawnModule;
import nl.maastrichtuniversity.dke.util.Vector;

public class Guard extends Agent {

    public Guard(ISpawnModule spawnModule, double baseSpeed) {
        super(spawnModule, baseSpeed, 90);
    }

}
