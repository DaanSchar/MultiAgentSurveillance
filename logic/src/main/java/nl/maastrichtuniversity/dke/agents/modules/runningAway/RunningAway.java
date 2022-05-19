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
        if(guardPosition.getX() == currentPosition.getX()){
            newPos.setX(newPos.getY());
            newPos.setY(newPos.getX());
        }else if(guardPosition.getY() == currentPosition.getY()){
            newPos.setX(newPos.getY());
            newPos.setY(newPos.getX());
        }


        newPos.setX(newPos.getX());
        newPos.setY(newPos.getY());
        int height = scenario.getEnvironment().getHeight();
        int width = scenario.getEnvironment().getWidth();
        if (newPos.getX() < 1) {
            newPos.setX(1);
        } else if (newPos.getX() > width - 1) {
            newPos.setX(width - 2);
        }
        if (newPos.getY() > height - 1) {
            newPos.setY(height - 2);
        } else if (newPos.getY() < 1) {
            newPos.setY(1);
        }
        while (!scenario.getEnvironment().getAt(newPos).isPassable()) {
            if (Math.random() < 0.5) {
                if (Math.random() < 0.5) {
                    newPos.setX(newPos.getX() + 1);
                } else {
                    newPos.setX(newPos.getX() - 1);
                }
            } else {
                if (Math.random() < 0.5) {
                    newPos.setY(newPos.getY() + 1);
                } else {
                    newPos.setY(newPos.getY() - 1);
                }

            }
        }
        return newPos;
    }

}
