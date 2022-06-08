package nl.maastrichtuniversity.dke.agents.modules.approximation;

import nl.maastrichtuniversity.dke.scenario.util.Position;

public interface IApproximationModule {
    Position getValidGuess(Position source);

    Position getValidTargetGuess();
}
