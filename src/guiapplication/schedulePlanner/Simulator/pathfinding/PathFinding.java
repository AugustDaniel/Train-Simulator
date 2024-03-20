package guiapplication.schedulePlanner.Simulator.pathfinding;

import util.graph.Graph;
import util.graph.Node;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.awt.geom.Point2D;
import java.util.*;

public class PathFinding {

    private static int COLLISIONTILE = 1;
    private static int TILESIZE = 32;
    private static int OFFSET = 2;

    public static List<Target> targets = new ArrayList<>();
    public static Graph graph = new Graph(0, 0);

    public static Map<Node, Integer> getShortestPath(Node source) {
        Queue<Node> nodes = new LinkedList<>();
        nodes.offer(source);
        Map<Node, Integer> distances = new HashMap<>();
        distances.put(source, 0);

        while (!nodes.isEmpty()) {
            Node current = nodes.poll();

            for (Node adjacent : current.getAdjacentNodes()) {
                if (!distances.containsKey(adjacent)) {
                    nodes.offer(adjacent);
                    distances.put(adjacent, 1 + distances.get(current));
                }
            }
        }

        return distances;
    }

    public static void addCollision(JsonObject object) {

        int layerHeight = object.getInt("height");
        int layerWidth = object.getInt("width");

        graph = new Graph(layerHeight, layerWidth);
        int index = 0;

        for (int y = 0; y < layerHeight; y++) {
            for (int x = 0; x < layerWidth; x++) {

                if (object.getJsonArray("data").getInt(index) == COLLISIONTILE) {
                    index++;
                    continue;
                }

                Node node = new Node(new Point2D.Double(x * 32, y * 32));

                if (!(x - 1 < 0)) {
                    addNodeAsAdjacent(node, graph.getNodes()[y][x - 1]);
                }

                if (!(x + 1 > layerWidth - 1)) {
                    addNodeAsAdjacent(node, graph.getNodes()[y][x + 1]);
                }

                if (!(y - 1 < 0)) {
                    addNodeAsAdjacent(node, graph.getNodes()[y - 1][x]);
                }

                if (!(y + 1 > layerHeight - 1)) {
                    addNodeAsAdjacent(node, graph.getNodes()[y + 1][x]);
                }

                graph.addNode(y, x, node);
                index++;
            }
        }
    }

    private static void addNodeAsAdjacent(Node currentNode, Node toAdd) {
        if (toAdd != null && currentNode != null) {
            currentNode.addAdjacentNode(toAdd);

            if (!toAdd.getAdjacentNodes().contains(currentNode)) {
                toAdd.addAdjacentNode(currentNode);
            }
        }
    }

    public static void addTargets(JsonObject jsonObject) {
        JsonArray objects = jsonObject.getJsonArray("objects");

        for (int i = 0; i < objects.size(); i++) {

            int xObject = objects.getJsonObject(i).getInt("x") / TILESIZE;
            int yObject = objects.getJsonObject(i).getInt("y") / TILESIZE;

            for (int y = 0; y < objects.getJsonObject(i).getInt("height") / TILESIZE; y += OFFSET) {
                for (int x = 0; x < objects.getJsonObject(i).getInt("width") / TILESIZE; x += OFFSET) {

                    Node nodeToAdd = graph.getNodes()[yObject + y][xObject + x];

                    if (nodeToAdd != null) {
                        targets.add(new Target(nodeToAdd));
                    }
                }
            }
        }
    }
}
