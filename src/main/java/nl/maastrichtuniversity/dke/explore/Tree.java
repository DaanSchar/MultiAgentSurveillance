package nl.maastrichtuniversity.dke.explore;

public class Tree<Node> {

    public Node root;

    public Tree(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node n)
    {
        root = n;
    }


}