package nl.maastrichtuniversity.dke.agents;

import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.agents.util.MoveAction;
import nl.maastrichtuniversity.dke.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Collection of Agents.
 *
 * @param <T> type of agent
 */
@Slf4j
public class Fleet<T extends Agent> extends ArrayList<T> {

    private int currentAgentIdx = 0;

    public Fleet() {
    }

    /**
     * @return environment explored by all agents.
     */
    public Environment getMemoryMap() {
        List<Environment> memories = new ArrayList<>();
        for (Agent agent : this) {
            memories.add(agent.getMemoryModule().getMap());
        }

        Tile[][] tileMap = new Tile[memories.get(0).getWidth()][memories.get(0).getHeight()];

        for (int i = 0; i < memories.get(0).getHeight(); i++) {
            for (int j = 0; j < memories.get(0).getWidth(); j++) {
                for (Environment environment : memories) {
                    boolean isUnknown = environment.getTileMap()[j][i].getType().equals(TileType.UNKNOWN);
                    if (!isUnknown) {
                        tileMap[j][i] = environment.getTileMap()[j][i];
                        break;
                    }
                    tileMap[j][i] = new Tile(new Position(j, i), TileType.UNKNOWN);
                }
            }
        }

        return new Environment(tileMap.length, tileMap[0].length, tileMap);
    }

    public T getAt(Position position) {
        for (T agent : this) {
            if (agent.getPosition().equals(position)) {
                return agent;
            }
        }

        return null;
    }

    @Override
    public T get(int index) {
        T agent = super.get(index);

        if (agent instanceof Intruder) {
            return checkIfIsCaughtAndGet(agent);
        }

        return agent;
    }

    private T checkIfIsCaughtAndGet(T agent) {
        Intruder intruder = (Intruder) agent;

        if (intruder.isCaught()) {
            return null;
        }

        return (T) intruder;
    }

    public void executeActions(int[] actionList) {
        for (int i = 0; i < actionList.length; i++) {
            Intruder intruder = (Intruder) this.get(i);

            if (intruder.isFleeing()) {
                MoveAction nextAction = MoveAction.values()[actionList[i]];
                intruder.move(nextAction);
            }
        }
    }

    public double getReward() {
        double totalMoveReward = 0;
        for (Agent agent : this) {
            totalMoveReward += agent.getRewardModule().updateMoveReward(agent.getPosition(), agent.getDirection());
        }
        return totalMoveReward;
    }

    public double getFleeReward() {
        double totalMoveReward = 0;
        for (Agent agent : this) {
            Intruder i = (Intruder) agent;
            if (i.isFleeing()) {
                totalMoveReward += agent.getRewardModule().updateFleeingReward(agent.getPosition(), agent.getDirection());
            }
        }
        return totalMoveReward;
    }


    public Agent getCurrentAgent() {

        if (currentAgentIdx == this.size()) {
            currentAgentIdx = 0;
        }
        int idx = currentAgentIdx;
        currentAgentIdx++;


        return this.get(idx);
    }
}
