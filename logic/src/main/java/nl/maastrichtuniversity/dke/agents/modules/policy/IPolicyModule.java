package nl.maastrichtuniversity.dke.agents.modules.policy;

import nl.maastrichtuniversity.dke.agents.util.MoveAction;

public interface IPolicyModule {
    MoveAction nextMove(double[] input);

    int getInputSize();
}