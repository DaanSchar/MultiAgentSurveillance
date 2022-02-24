package nl.maastrichtuniversity.dke.explore;

import lombok.Getter;
import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.agents.modules.vision.VisionModule;
import nl.maastrichtuniversity.dke.discrete.Scenario;

@Getter
public class explore extends ExploreModule{
    public explore(Scenario scenario) {
        super(scenario);
        for(Agent agent: scenario.getGuards()){

        }
    }


//    private VisionModule vision;

    private void explore (VisionModule vision){
//        for(Tile tile: vision){
//            if (tile.getType() == TileType.TARGET){
//                //go to target
//            }
//
//        }
    }


}
