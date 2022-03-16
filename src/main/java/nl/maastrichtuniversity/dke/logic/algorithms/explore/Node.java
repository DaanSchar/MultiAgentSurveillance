package nl.maastrichtuniversity.dke.logic.algorithms.explore;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.agents.util.Direction;

@Getter
public class Node<E> {

    public @Setter E element;
    public Node<E>[] neighbors;

    public Node(E element) {
        this.neighbors = new Node[4];
        this.element = element;
    }

    public boolean hasChild() {
        for (Node<E> child : neighbors) {
            if (child != null) {
                if (child.getElement() != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addNeighbor(E element, Direction direction) {
        int index = getIndex(direction);

        if (index == -1)
            return;

        neighbors[getIndex(direction)] = new Node<>(element);
    }

    public Node<E> getNeighbor(Direction direction){
        int index = getIndex(direction);

        if (index == -1)
            return null;

        return neighbors[getIndex(direction)];
    }

    private int getIndex(Direction direction){
        if(direction == Direction.NORTH)
            return 0;
        else if(direction == Direction.EAST)
            return 1;
        else if(direction == Direction.SOUTH)
            return 2;
        else if(direction == Direction.WEST)
            return 3;
        return -1;
    }

}