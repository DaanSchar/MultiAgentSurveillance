package nl.maastrichtuniversity.dke.logic.agents.modules.communication;

import lombok.Getter;
import nl.maastrichtuniversity.dke.logic.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.List;

public class CommunicationModule extends AgentModule implements ICommunicationModule {

    private @Getter List<CommunicationType> marks;

    public CommunicationModule(Scenario scenario, List<CommunicationType> marks) {
        super(scenario);
//        this.numberOfMarkers = numberOfMarkers;
        this.marks = marks;
    }

    @Override
    public void dropMark(CommunicationMark device) {
        for(int i = 0; i < marks.size(); i++){
            if(marks.get(i).equals(device.getType())){
                marks.remove(i);
                break;
            }
        }
        marks.remove(device.getType());
        scenario.getCommunicationMarks().add(device);
    }

    @Override
    public boolean hasMark(int x, int y) {
        Position p = new Position(x, y);
        for (CommunicationMark cm : scenario.getCommunicationMarks()) {
            if (cm.getPosition().equals(p)) {
                return true;
            }
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
