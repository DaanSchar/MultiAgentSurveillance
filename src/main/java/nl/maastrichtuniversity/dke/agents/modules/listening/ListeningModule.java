package nl.maastrichtuniversity.dke.agents.modules.listening;

import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.discrete.Scenario;

import java.util.Vector;

public class ListeningModule extends AgentModule implements IListeningModule{
    public ListeningModule(Scenario scenario) {
        super(scenario);
    }

    @Override
    public boolean getSound() {
        return false;
    }

    @Override
    public Vector getSoundDirection() {
        return null;
    }
}
