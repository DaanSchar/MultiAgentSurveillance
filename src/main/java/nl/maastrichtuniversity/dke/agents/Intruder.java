package nl.maastrichtuniversity.dke.agents;

import nl.maastrichtuniversity.dke.agents.modules.movement.IMovement;
import nl.maastrichtuniversity.dke.agents.modules.spawn.ISpawnModule;

public class Intruder extends Agent {

    public Intruder(ISpawnModule spawnModule, IMovement movement, double baseSpeed) {
        super(spawnModule,movement, baseSpeed);
    }

}
