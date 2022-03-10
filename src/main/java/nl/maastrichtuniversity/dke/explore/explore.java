package nl.maastrichtuniversity.dke.explore;

import lombok.Getter;
import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.agents.modules.vision.IVisionModule;
import nl.maastrichtuniversity.dke.agents.modules.vision.VisionModule;
import nl.maastrichtuniversity.dke.discrete.Scenario;
import nl.maastrichtuniversity.dke.discrete.Tile;

import java.util.List;

@Getter
public class explore extends ExploreModule{
    private List<MDFS> mdfs;
    public explore(Scenario scenario) {
        super(scenario);
//        for(int ){
//            mdfs
//        }

    }


//    private VisionModule vision;

    private void update (IVisionModule vision){
        for(Agent agent: scenario.getGuards()){
            agent.getMemoryModule().update(agent.getVisionModule());
//            explore(agent.getVisionModule());
        }

//        for(Tile tile: vision.getObstacles()){
//            if (tile.getType() == TileType.TARGET){
//                //go to target
//            }
//
        }
//    }


}
