package nl.maastrichtuniversity.dke.agents.modules.reward;

import nl.maastrichtuniversity.dke.agents.util.Direction;
import nl.maastrichtuniversity.dke.scenario.util.Position;

public interface IRewardModule {


    boolean isInTargetDirection(Position p, Direction d);
    double updateMoveReward(Position p, Direction d);
    double updateFleeingReward(Position p,Direction d,double[] inputs);
    double getReward();


}