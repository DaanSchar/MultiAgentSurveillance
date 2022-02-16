package nl.maastrichtuniversity.dke.agents;

import nl.maastrichtuniversity.dke.agents.modules.movement.IMovement;
import nl.maastrichtuniversity.dke.agents.modules.spawn.ISpawnModule;

public class Guard extends Agent {

    public Guard(ISpawnModule spawnModule, IMovement movement, double baseSpeed, double sprintSpeed) {
        super(spawnModule,movement, baseSpeed, sprintSpeed);
    }

}
