package nl.maastrichtuniversity.dke.logic.agents.modules.smell;

import nl.maastrichtuniversity.dke.logic.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.communication.CommunicationMark;
import nl.maastrichtuniversity.dke.logic.agents.modules.communication.CommunicationType;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public class SmellModule extends AgentModule implements ISmellModule {

    public SmellModule(Scenario scenario) {
        super(scenario);
    }

    @Override
    public boolean getSmell(Position position) {
        for(CommunicationMark mark:scenario.getCommunicationMarks()){
            if(mark.getType().equals(CommunicationType.SMELL)){
                if (mark.getPosition().equals(position)) {
                    return true;
                }
            }
        }
        return false;
    }


}
