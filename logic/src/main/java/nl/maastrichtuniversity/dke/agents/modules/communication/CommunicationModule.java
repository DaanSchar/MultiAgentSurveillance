package nl.maastrichtuniversity.dke.agents.modules.communication;

import lombok.Getter;
import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.Smell;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.util.List;

@Getter
public class CommunicationModule extends AgentModule implements ICommunicationModule {

    private List<CommunicationType> marks;


    public CommunicationModule(Scenario scenario, List<CommunicationType> marks) {
        super(scenario);
        this.marks = marks;
    }

    @Override
    //TODO: Suspicious list.remove() in loop.
    // you can't remove an element from a list while iterating over it.
    // PLEASE FIX THIS.
    public void dropMark(Mark device) {
        boolean check = false;
        for (int i = 0; i < marks.size(); i++) {
            if (marks.get(i).equals(device.getType())) {
                if (device.getType().equals(CommunicationType.SMELL)) {
                    dropSmell(device.getPosition(), device.getAgentSource());
                }
                scenario.getMarks().add(device);
                marks.remove(i);
                check = true;
            }
            if (check) {
                break;
            }

        }


    }

    private void dropSmell(Position position, Agent source) {
        final int smellingDistance = 3;
        Tile[][] tileMap = scenario.getEnvironment().getTileMap();
        for (Tile[] tiles : tileMap) {
            for (Tile tile : tiles) {
                if (position.distanceEuclidean(tile.getPosition()) <= smellingDistance) {
                    Smell smell = new Smell(tile.getPosition(), position, source);
                    scenario.getSmellMap().add(smell);
                }
            }
        }
    }

    @Override
    public boolean hasMark(CommunicationType type) {
        for (CommunicationType m : marks) {
            if (m.equals(type)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void findMarker(CommunicationType marker) {
        marks.add(marker);
    }

    @Override
    public int countMark(CommunicationType type) {
        int count = 0;

        for (CommunicationType m : marks) {
            if (m == type) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean tileHasMark(Position position, CommunicationType type) {
        for (Mark i : scenario.getMarks()) {
            if (i.getPosition().equals(position)) {
                if (i.getType().equals(type)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Position getMark(CommunicationType type) {
        for (Mark i : scenario.getMarks()) {
            if (i.getType().equals(type)) {
                return i.getPosition();
            }
        }
        return null;
    }

}
