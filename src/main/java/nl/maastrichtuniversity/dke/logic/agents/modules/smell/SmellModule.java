package nl.maastrichtuniversity.dke.logic.agents.modules.smell;

import lombok.Getter;
import nl.maastrichtuniversity.dke.logic.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.Smell;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.ArrayList;
import java.util.List;

public class SmellModule extends AgentModule implements ISmellModule {
    private int smellingDistance;

    public SmellModule(Scenario scenario, int smellingDistance) {
        super(scenario);
        this.smellingDistance=smellingDistance;
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
