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
        Position[] corners = {new Position(1,1), new Position(1,height-2)
        , new Position(width-2, 1), new Position(width-2, height-2) };
//        try {
            if(newPos.getX() < 1){

                if(Math.random()>0.2){
                    newPos.setX(1);
                }else{
                    newPos.setX(5);
                }
            }
            else if(newPos.getX() > width-1){
//                newPos.setX(width-2);
                if(Math.random()>0.2){
                    newPos.setX(width-2);
                }else{
                    newPos.setX(width-7);
                }
            }
            if(newPos.getY() > height-1){

                if(Math.random()>0.2){
                    newPos.setY(height-2);
                }else{
                    newPos.setY(height-7);
                }
            }
            else if(newPos.getY() < 1){
//                newPos.setY(1);
                if(Math.random()>0.2){
                    newPos.setY(1);
                }else{
                    newPos.setY(5);
                }
            }

            while(!scenario.getEnvironment().getAt(newPos).isPassable()){
                if(Math.random() < 0.5){
                    if(Math.random() < 0.5){
                        newPos.setX(newPos.getX()+1);
                    }else{
                        newPos.setX(newPos.getX()-1);
                    }
                }else{
                    if(Math.random() < 0.5){
                        newPos.setY(newPos.getY()+1);
                    }else{
                        newPos.setY(newPos.getY()-1);
                    }

                }
            }
//            if(newPos.equals(corners[0]) || newPos.equals(corners[2])){
//                newPos.setY(newPos.getY() + 10);
//                newPos.setY(newPos.getY() + 10);
//            }
//            if(newPos.equals(corners[1]) || newPos.equals(corners[3])){
//                newPos.setY(newPos.getY()-10);
//            }

        return newPos;

    }

}
