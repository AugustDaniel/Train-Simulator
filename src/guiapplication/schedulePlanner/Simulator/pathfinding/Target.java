package guiapplication.schedulePlanner.Simulator.pathfinding;

import util.graph.Node;

import java.util.Map;

public class Target {
    private Map<Node, Integer> shortestPath;
    private Node node;

    public Target(Node node) {
        this.shortestPath = PathFinding.getShortestPath(node);
        this.node = node;
    }

    public Integer getDistance(Node node) {
        return shortestPath.get(node);
    }

    public Map<Node, Integer> getShortestPath() {
        return this.shortestPath;
    }

    public Node getNode() {
        return node;
    }
}
