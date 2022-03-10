package nl.maastrichtuniversity.dke.explore;

public class Graphs<E> {

    public Node<E> root;

    public Graphs(Node<E> root) {
        this.root = root;
    }

    public Node<E> getRoot() {
        return root;
    }

    public void setRoot(Node<E> n)
    {
        root = n;
    }


}