package util.graph;


import java.awt.geom.Point2D;
import java.util.*;

public class Node {

    private Point2D position;
    private List<Node> adjacentNodes;

    public Node(Point2D position) {
        this.position = position;
        this.adjacentNodes = new ArrayList<>();
    }

    public void addAdjacentNode(Node toAdd) {
        adjacentNodes.add(toAdd);
    }

    public List<Node> getAdjacentNodes() {
        return adjacentNodes;
    }

    public Point2D getPosition() {
        return position;
    }

}
