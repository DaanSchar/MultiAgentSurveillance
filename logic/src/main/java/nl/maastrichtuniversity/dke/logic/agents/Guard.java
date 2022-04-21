package nl.maastrichtuniversity.dke.logic.agents;

import lombok.extern.slf4j.Slf4j;


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








    @Override
    public void update() {
        super.explore();
        super.update();
    }
}
