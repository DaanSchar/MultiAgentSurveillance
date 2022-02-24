//package nl.maastrichtuniversity.dke.explore;
//
//import nl.maastrichtuniversity.dke.discrete.Tile;
//import nl.maastrichtuniversity.dke.discrete.TileType;
//
//import java.util.LinkedList;
//import java.util.Queue;
//
//public class BFS {
//    private LinkedList<Node> path;
//
//    public LinkedList<Tile> search(Node root){
//        Queue<Node> queue = new LinkedList<>();
//        queue.add(root);
//        while (!queue.isEmpty()) {
//            Node temp = queue.poll();
//            if(temp.getTile().getType() == TileType.TARGET)
//                return findPath(temp);
//            for(int i =0; i <temp.getChildren().size() ; i++ ){
//                if (temp.getChild(i) != null) {
//                    queue.add(temp.getChild(i));
//                }
//            }
//        }
//        return null;
//    }
//
//    private LinkedList findPath(Node node){
//        path.add(node);
//        if(node.getParent() == null){
//            return path;
//        }else{
//            return findPath(node.getParent());
//        }
//    }
//
//}
