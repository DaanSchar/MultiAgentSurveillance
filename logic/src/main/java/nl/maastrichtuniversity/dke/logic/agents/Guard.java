package nl.maastrichtuniversity.dke.logic.agents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;

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

    private List<Tile> findShortestPath(Tile start, Tile target){
        PriorityQueue<Node> queue= new PriorityQueue<Node>((o1, o2) -> o1.cost - o2.cost );
        List<Node> visited = new ArrayList<>();
        List<Tile> path = new ArrayList<>();
        for(Tile tile: this.getMemoryModule().getCoveredTiles()){
            if(tile.isPassable()){
                if(!tile.equals(start)){
                    queue.add(new Node(Integer.MAX_VALUE, tile, null));
                }else{
                    queue.add(new Node(0, start, null));
                }
            }
        }
        while(!queue.isEmpty()){
            Node U = queue.poll();
            visited.add(U);
            for(Node v:queue){
                if(!visited.contains(v)){
                    int tempDis = U.getCost();
                    if(tempDis < v.getCost()){
                        v.setCost(tempDis);
                        v.setPrevious(U);
                    }
                }
            }
        }
        Node t = null;
        for(Node x: visited){
            if(x.current.equals(target)){
                t = x;
                break;
            }
        }
        for (Node vertex = t; vertex != null; vertex = vertex.getPrevious())
            path.add(vertex.current);
        Collections.reverse(path);

        return path;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    private class Node{
        private int cost;
        private Tile current;
        private Node previous;
    }


}
