package nl.maastrichtuniversity.dke.agents.modules.spawn;

import nl.maastrichtuniversity.dke.util.Vector;

/**
 * A spawn module determines how a new agent is spawned into the environment.
 */
public interface ISpawnModule {

    Vector getSpawnPosition();

}
