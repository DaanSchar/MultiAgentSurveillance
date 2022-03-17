package nl.maastrichtuniversity.dke.logic.agents.modules.communication;

import lombok.Getter;
import nl.maastrichtuniversity.dke.logic.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.Smell;
import nl.maastrichtuniversity.dke.logic.scenario.Sound;
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
                    dropSmell(device.getPosition());
                }
                marks.remove(i);
                break;
            }
        }
//        marks.remove(device.getType());
        scenario.getCommunicationMarks().add(device);
    }

    private void dropSmell(Position position){
        Tile[][] tileMap = scenario.getEnvironment().getTileMap();
        for(Tile[] tiles:tileMap){
            for(Tile tile:tiles){
                if(position.distance(tile.getPosition()) <= smellingDistance){
                    Smell smell = new Smell(tile.getPosition(),position);
                    scenario.getSmellMap().add(smell);
                }
            }
        }
    }

    @Override
    public boolean hasMark(CommunicationType type) {
        boolean check = false;
        for (CommunicationType m : marks) {
            check = m == type;
        }
        return check;
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

    /*
    @Override
    public List<CommunicationMark> getMarks(int x, int y) {
        var environment = scenario.getEnvironment();
        var tile = environment.getTileMap()[x][y];

        return tile.getCommunicationMarks();
    }
*/

}
