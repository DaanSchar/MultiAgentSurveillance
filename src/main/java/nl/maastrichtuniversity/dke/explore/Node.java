package nl.maastrichtuniversity.dke.explore;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.discrete.Tile;

import java.util.ArrayList;
@Getter
public class Node {
    public @Setter
    Tile tile;
    public Node parent;
    public ArrayList<Node> children;

    public Node(Tile t, Node p) {
        this.tile = t;
        parent = p;
        children = new ArrayList<>();
    }

    public boolean hasChild() {
        int count = 0;
        for (Node child : children) {
            if (child != null && child.getTile() != null) {
                count++;
            }
        }
        return count != 0;
    }

    public void addChildren(Tile tile) {
        children.add(new Node(tile,  this));

    }

    public Node getChild() {
        return(children.get(children.size() - 1));
    }

    public Node getChild(int index) {
        return children.get(index);
    }


}