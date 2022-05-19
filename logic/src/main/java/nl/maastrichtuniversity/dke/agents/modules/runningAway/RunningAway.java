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


        newPos.setX(newPos.getX() * 2);
        newPos.setY(newPos.getY() * 2);
        int height = scenario.getEnvironment().getHeight();
        int width = scenario.getEnvironment().getWidth();
        Position[] corners = {new Position(0,0), new Position(0,height)
        , new Position(width, 0), new Position(width, height) };
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
            if(newPos.equals(corners[0]) || newPos.equals(corners[2])){
                newPos.setY(10);
            }
            if(newPos.equals(corners[1]) || newPos.equals(corners[3])){
                newPos.setX(10);
            }

        return newPos;

    }

}
