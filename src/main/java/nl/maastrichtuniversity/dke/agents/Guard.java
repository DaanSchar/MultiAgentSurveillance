package nl.maastrichtuniversity.dke.agents;

import nl.maastrichtuniversity.dke.agents.modules.movement.IMovement;
import nl.maastrichtuniversity.dke.agents.modules.spawn.ISpawnModule;
import nl.maastrichtuniversity.dke.agents.modules.vision.IVisionModule;

public class Guard extends Agent {

    public Guard(ISpawnModule spawnModule, IMovement movement, IVisionModule visionModule, double baseSpeed) {
        super(spawnModule,movement, visionModule, baseSpeed);
    }

}
