package guiapplication.simulator.pathfinding;

import util.graph.Node;

import java.util.HashMap;
import java.util.Map;

public class Target {
    private Map<Node, Integer> shortestPath;
    private Node node;

    public Target(Node node) {
        this.shortestPath = new HashMap<>();
        this.node = node;
    }

    public Integer getDistance(Node node) {
        return shortestPath.get(node);
    }

    public Node getNode() {
        return node;
    }

    public void calculateShortestPath() {
        shortestPath = PathFinding.getShortestPath(node);
    }
}
