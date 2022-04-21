package nl.maastrichtuniversity.dke.logic.agents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.logic.agents.util.Direction;
import nl.maastrichtuniversity.dke.logic.agents.util.MoveAction;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.*;


@Slf4j
public class Guard extends Agent {

    public Guard() {
        super();
    }

    @Override
    public void explore() {
        super.explore();
    }

    public void chasing(){
       for(Agent agent: getVisionModule().getAgents()){
           if(agent instanceof Intruder){
               agent.getDirection();
           }
       }
    }








}
