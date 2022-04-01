package nl.maastrichtuniversity.dke.logic.agents.modules.spawn;

import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.agents.util.Direction;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

/**
 * A spawn module determines how a new agent is spawned into the environment.
 */
public interface ISpawnModule {

    /**
     * Returns the position where the agent will spawn.
     */
    Position getSpawnPosition(Agent agent);

    Direction getSpawnDirection();

}
