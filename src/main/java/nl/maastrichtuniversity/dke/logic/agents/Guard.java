package nl.maastrichtuniversity.dke.logic.agents;

import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.logic.agents.modules.exploration.ExplorationModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.exploration.IExplorationModule;
import nl.maastrichtuniversity.dke.logic.agents.util.MoveAction;


@Slf4j
public class Guard extends Agent {

    private IExplorationModule explorationModule;

    public Guard() {
        super();
    }

    public void explore() {
        if (explorationModule == null) { explorationModule = new ExplorationModule(getMemoryModule().getMap(), getMovement()); }

        if (!explorationModule.isDoneExploring()) {
            MoveAction nextMove = explorationModule.explore(getPosition(), getDirection());

            super.move(nextMove);
        }
    }

}
