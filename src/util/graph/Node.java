package util.graph;

import java.awt.geom.Point2D;
import java.util.*;

public class Node {

    Point2D position;

    List<Node> adjacentNodes = new ArrayList<>();

    public Node(Point2D position) {
        this.position = position;
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
