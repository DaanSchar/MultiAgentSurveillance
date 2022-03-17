package nl.maastrichtuniversity.dke.logic.agents.modules.smell;

import nl.maastrichtuniversity.dke.logic.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.communication.CommunicationMark;
import nl.maastrichtuniversity.dke.logic.agents.modules.communication.CommunicationType;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.Smell;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.List;

public class SmellModule extends AgentModule implements ISmellModule {

    public SmellModule(Scenario scenario) {
        super(scenario);
    }

    @Override
    public boolean getSmell(Position position) {
        List<Smell> smellMap = scenario.getSmellMap();
        if(smellMap.isEmpty())
            return false;

        for(Smell smell: smellMap){
            if(smell.getPosition().equals(position)){
                return true;
            }
        }
        return false;
    }


}
