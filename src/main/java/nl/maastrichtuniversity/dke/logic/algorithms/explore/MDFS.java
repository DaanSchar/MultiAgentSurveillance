package nl.maastrichtuniversity.dke.logic.algorithms.explore;

import lombok.Getter;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;

@Getter
//public class MDFS extends ExploreModule {
public class MDFS {
    private Tile start;
    private Agent agent;

    public MDFS(Agent agent){

        start = new Tile(agent.getPosition(), TileType.EMPTY);
        this.agent = agent;
    }

    public void explore(){
        traversePreOrder(start);
    }

    public void traversePreOrder(Tile tile) {
//        System.out.println("here");
//        agent.updateMemory();
        if (tile.getType().isPassable()) {
//            agent can go to tiles
            agent.goForward();
            traversePreOrder(new Tile(agent.getVisionModule().getObstacles().get(0).getPosition())); // NORTH
            agent.rotate(1);
            traversePreOrder(new Tile(agent.getVisionModule().getObstacles().get(0).getPosition()));//WEST
            agent.rotate(1);
            traversePreOrder(new Tile(agent.getVisionModule().getObstacles().get(0).getPosition()));//SOUTH
            agent.rotate(1);
            traversePreOrder(new Tile(agent.getVisionModule().getObstacles().get(0).getPosition()));//EAST
        }
    }
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

//    public MDFS(List<Agent> agentList){
//        List<Tile> start = new LinkedList<>();
//        for(Agent agent:agentList){
//            start.add(new Tile(agent.getPosition(), TileType.EMPTY));
//        }
////        start = new Tile(new Position(0 ,0 ), TileType.EMPTY);
//        this.agent = agent;
//    }