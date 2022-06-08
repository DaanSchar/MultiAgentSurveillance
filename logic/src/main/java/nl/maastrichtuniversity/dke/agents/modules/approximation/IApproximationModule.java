package nl.maastrichtuniversity.dke.agents.modules.approximation;

import nl.maastrichtuniversity.dke.scenario.util.Position;

public interface IApproximationModule {
    public Position getValidGuess(Position source);
    public Position getValidTargetGuess();
}
