package nl.maastrichtuniversity.dke.agents.modules.runningAway;

import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.agents.util.Direction;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.util.Position;

public class RunningAway extends AgentModule implements IRunningAway {


    public RunningAway(Scenario scenario) {
        super(scenario);
    }

    @Override
    public Position avoidGuard(Position guardPosition, Position currentPosition) {
        Position newPos = currentPosition.add(currentPosition.sub(guardPosition));
        newPos.setX(newPos.getX() * 4);
        newPos.setY(newPos.getY() * 4);
        int height = scenario.getEnvironment().getHeight();
        int width = scenario.getEnvironment().getWidth();
//        try {
            if(newPos.getX() < 1){
                newPos.setX(1);
            }
            else if(newPos.getX() > width-1){
                newPos.setX(width-2);
            }
            if(newPos.getY() > height-1){
                newPos.setY(height-2);
            }
            else if(newPos.getY() < 1){
                newPos.setY(1);
            }

//            if (!scenario.getEnvironment().getAt(newPos).isPassable()) {
//                newPos = new Position(newPos.getX(), newPos.getY());
//            }
//        } catch (Exception e) {
//            return currentPosition;
//        }
        return newPos;

    }

}
