package nl.maastrichtuniversity.dke.explore;

import lombok.Getter;
import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.agents.Direction;
import nl.maastrichtuniversity.dke.discrete.Scenario;
import nl.maastrichtuniversity.dke.util.Position;

import java.util.List;

@Getter
//public class MDFS extends ExploreModule {
public class MDFS {
//    private Node start;
    private AgentTile start;
    private Agent agent;

    public MDFS(Agent agent){
//        start = new Node(new AgentTile(new Position(0, 0),AgentTileType.START), null, true);
//        start = new AgentTile(new Position(0, 0),AgentTileType.START);
        start = new AgentTile(AgentTileType.START);
        this.agent = agent;
    }


    public void explore(){
        traversePreOrder(start);
    }

//    public void traversePreOrder(Node node) {
//        if (!node.isVisited() && node.getAgentTile().getType().isPassable()) {
//            //agent can go to tile
//            node.setVisited(true);
//
//            traversePreOrder(node.getChild(0));
//            traversePreOrder(node.getChild(1));
//            traversePreOrder(node.getChild(2));
//            traversePreOrder(node.getChild(3));
//        }
//    }
    public void traversePreOrder(AgentTile tile) {
        if (tile.getType().isPassable()) {
            //agent can go to tile
//            traversePreOrder(new AgentTile(agent.getVisionModule().getObstacles(Direction.NORTH).get(0)));
//            traversePreOrder(new AgentTile(agent.getVisionModule().getObstacles(Direction.WEST).get(0)));
//            traversePreOrder(new AgentTile(agent.getVisionModule().getObstacles(Direction.SOUTH).get(0)));
//            traversePreOrder(new AgentTile(agent.getVisionModule().getObstacles(Direction.EAST).get(0)));
        }
    }





}
