package nl.maastrichtuniversity.dke.agents.modules.listening;

import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.discrete.Scenario;

import java.util.Vector;

public class ListeningModule extends AgentModule implements IListeningModule{
    private final double hearingDistanceWalking;
    private final double hearingDistanceSprinting;

    public ListeningModule(Scenario scenario,double hearingDistanceWalking, double hearingDistanceSprinting) {
        super(scenario);
        this.hearingDistanceSprinting = hearingDistanceSprinting;
        this.hearingDistanceWalking = hearingDistanceWalking;
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
