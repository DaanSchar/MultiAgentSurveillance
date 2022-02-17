package nl.maastrichtuniversity.dke.explore;

import lombok.Getter;
import nl.maastrichtuniversity.dke.discrete.Scenario;
import nl.maastrichtuniversity.dke.util.Position;

import java.util.List;

@Getter
//public class MDFS extends ExploreModule {
public class MDFS {
    private Node start;
    private

    public MDFS(){
        start = new Node(new AgentTile(new Position(0, 0),AgentTileType.START), null, true);
    }


    public void explore(){
        traversePreOrder(start);
    }

    public void traversePreOrder(Node node) {
        if (node != null) {
            node.setVisited(true);
            traversePreOrder(node.getChild(0));
            traversePreOrder(node.getChild(1));
            traversePreOrder(node.getChild(2));
            traversePreOrder(node.getChild(3));
        }
    }




}
