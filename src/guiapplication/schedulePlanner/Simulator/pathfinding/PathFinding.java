package guiapplication.schedulePlanner.Simulator.pathfinding;

import util.graph.Graph;
import util.graph.Node;

import java.util.*;

public class PathFinding {


    public PathFinding() {

    }

    public static Map<Node, Integer> getShortestPath(Node source) {
        Queue<Node> nodes = new LinkedList<>();
        nodes.offer(source);
        Map<Node, Integer> distances = new HashMap<>();
        distances.put(source, 0);

        while (!nodes.isEmpty()) {
            Node current = nodes.poll();

            for(Node adjacent : current.getAdjacentNodes()) {
                if (!distances.containsKey(adjacent)) {
                    nodes.offer(adjacent);
                    distances.put(adjacent, 1 + distances.get(current));
                }
            }
        }

        return distances;
    }

}
