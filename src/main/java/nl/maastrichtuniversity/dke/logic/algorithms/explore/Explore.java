package nl.maastrichtuniversity.dke.logic.algorithms.explore;

import lombok.Getter;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.agents.modules.vision.IVisionModule;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;

@Getter
public class Explore extends ExploreModule{
//    private List<MDFS> mdfs = new ArrayList<>();
    private MDFS mdfs ;
    public Explore(Scenario scenario) {
        super(scenario);

        for(Agent agent: scenario.getGuards()){
            mdfs = new MDFS(agent);
//            mdfs.add(new MDFS(agent));
        }
        mdfs.explore();
//        for(int ){
//            mdfs
//        }

    }


//    private VisionModule vision;

    private void update (IVisionModule vision){
//        for(MDFS md:mdfs){
//            md.explore();
//        }
//        for(Agent agent: scenario.getGuards()){
//            agent.getMemoryModule().update(agent.getVisionModule());
//            Explore(agent.getVisionModule());
//        }

//        for(Tile tile: vision.getObstacles()){
//            if (tile.getType() == TileType.TARGET){
//                //go to target
//            }
//
        }
//    }


}
