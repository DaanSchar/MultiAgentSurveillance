package nl.maastrichtuniversity.dke.agents.modules.smell;

import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.Smell;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.util.List;

public class SmellModule extends AgentModule implements ISmellModule {
    private int smellingDistance;

    public SmellModule(Scenario scenario, int smellingDistance) {
        super(scenario);
        this.smellingDistance = smellingDistance;
    }

    @Override
    public boolean getSmell(Position position) {
        List<Smell> smellMap = scenario.getSmellMap();
        if (smellMap.isEmpty())
            return false;

        for (Smell smell : smellMap) {
            if (smell.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }


}
