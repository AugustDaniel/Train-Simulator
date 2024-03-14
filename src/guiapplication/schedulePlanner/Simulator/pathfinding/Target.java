package guiapplication.schedulePlanner.Simulator.pathfinding;

import util.graph.Graph;
import util.graph.Node;
import java.util.Map;

public class Target {

    private Map<Node, Integer> shortestPath;

    public Target(Node node) {
        this.shortestPath = PathFinding.getShortestPath(node);
    }

    public Integer getDistance(Node node) {
        return shortestPath.get(node);
    }

}
