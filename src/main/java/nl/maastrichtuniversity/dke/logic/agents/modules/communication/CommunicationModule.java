package nl.maastrichtuniversity.dke.logic.agents.modules.communication;

import lombok.Getter;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.Smell;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.List;

public class CommunicationModule extends AgentModule implements ICommunicationModule {

    private @Getter List<CommunicationType> marks;
    private @Getter int smellingDistance;

    public CommunicationModule(Scenario scenario, List<CommunicationType> marks, int smellingDistance) {
        super(scenario);
        this.marks = marks;
        this.smellingDistance=smellingDistance;
    }

    @Override
    public void dropMark(CommunicationMark device) {
        for(int i = 0; i < marks.size(); i++){
            if(marks.get(i).equals(device.getType())){
                if(device.getType().equals(CommunicationType.SMELL)){
                    dropSmell(device.getPosition(), device.getAgentSource());
                }
                marks.remove(i);

            }
            break;
        }
        scenario.getCommunicationMarks().add(device);

    }

    private void dropSmell(Position position, Agent source){
        Tile[][] tileMap = scenario.getEnvironment().getTileMap();

        for (Tile tile : scenario.getEnvironment()) {
            if(position.distance(tile.getPosition()) <= smellingDistance){
                Smell smell = new Smell(tile.getPosition(),position, source );
                scenario.getSmellMap().add(smell);
            }
        }
    }

    @Override
    public boolean hasMark(CommunicationType type) {
        for (CommunicationType m : marks) {
            if(m.equals(type)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void findMarker(CommunicationType marker){
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
}
