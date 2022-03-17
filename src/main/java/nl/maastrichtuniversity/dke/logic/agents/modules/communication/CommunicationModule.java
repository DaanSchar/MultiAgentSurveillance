package nl.maastrichtuniversity.dke.logic.agents.modules.communication;

import lombok.Getter;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.Smell;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.List;
@Getter
public class CommunicationModule extends AgentModule implements ICommunicationModule {

    private List<CommunicationType> marks;

    public CommunicationModule(Scenario scenario, List<CommunicationType> marks) {
        super(scenario);
        this.marks = marks;
    }

    @Override
    public void dropMark(CommunicationMark device) {
        boolean check = false;
        for(int i = 0; i < marks.size(); i++){
            System.out.println(marks.get(i));
            if(marks.get(i).equals(device.getType())){
                System.out.println("here");
                if(device.getType().equals(CommunicationType.SMELL)){
                    dropSmell(device.getPosition(), device.getAgentSource());
                }
                scenario.getCommunicationMarks().add(device);
                marks.remove(i);
                check = true;
            }
            if(check)
                break;
        }
    }

    private void dropSmell(Position position, Agent source){
        Tile[][] tileMap = scenario.getEnvironment().getTileMap();
        for(Tile[] tiles:tileMap){
            for(Tile tile:tiles){
                if(position.distance(tile.getPosition()) <= 3){
                    Smell smell = new Smell(tile.getPosition(),position, source );
                    scenario.getSmellMap().add(smell);
                }
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
