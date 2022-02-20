package nl.maastrichtuniversity.dke.explore;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.agents.Direction;

import java.util.ArrayList;
@Getter
public class Node {
    public @Setter
    AgentTile agentTile;
    public Node parent;
    public Node[] children;
    private @Setter boolean visited;

    public Node(AgentTile t, Node parent, boolean visited) {
        this.visited = visited;
        this.agentTile = t;
        this.parent = parent;
        children = new Node[4];
    }

    public boolean hasChild() {
        int count = 0;
        for (Node child : children) {
            if (child != null && child.getAgentTile() != null) {
                count++;
            }
        }
        return count != 0;
    }

    public void addChildren(AgentTile agentTile, Direction direction) {
        if(direction == Direction.NORTH)
            children[0] = new Node(agentTile,  this, false);
        else if(direction == Direction.EAST)
            children[1] = new Node(agentTile,  this,false);
        else if(direction == Direction.SOUTH)
            children[2] = new Node(agentTile, this, false);
        else if(direction == Direction.WEST)
            children[3] = new Node(agentTile, this, false);

    }
    public AgentTile getDirection(Direction direction){
        if(direction == Direction.NORTH)
            return children[0].getAgentTile();
        else if(direction == Direction.EAST)
            return children[1].getAgentTile();
        else if(direction == Direction.SOUTH)
            return children[2].getAgentTile() ;
        else if(direction == Direction.WEST)
            return children[3].getAgentTile();
        return null;
    }

    public Node getChild(int index) {
        return children[index];
    }


}