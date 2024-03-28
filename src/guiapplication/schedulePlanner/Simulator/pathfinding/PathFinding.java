package guiapplication.schedulePlanner.Simulator.pathfinding;

import util.graph.Graph;
import util.graph.Node;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.awt.geom.Point2D;
import java.util.*;

public class PathFinding {

    private final static int COLLISION_TILE = 1;
    private final static int TILE_SIZE = 32;
    private final static int OFFSET = 2;

    public static Map<String, List<Target>> targets = new HashMap<>();
    public static List<Node> spawnPoints = new ArrayList<>();
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

                if (object.getJsonArray("data").getInt(index) == COLLISION_TILE) {
                    index++;
                    continue;
                }

                Node node = new Node(new Point2D.Double(x * TILE_SIZE + ((double) TILE_SIZE / 2), y * TILE_SIZE + ((double) TILE_SIZE / 2)));

                if (x - 1 > 0) {
                    addNodeAsAdjacent(node, graph.getNodes()[y][x - 1]);

                }

                if (x + 1 < layerWidth - 1) {
                    addNodeAsAdjacent(node, graph.getNodes()[y][x + 1]);

                }

                if (y - 1 > 0) {
                    addNodeAsAdjacent(node, graph.getNodes()[y - 1][x]);

                }

                if (y + 1 < layerHeight - 1) {
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

            JsonObject o = objects.getJsonObject(i);

            if (o.getString("name").equals("spawn")) {
                createSpawnPoints(o);
                continue;
            }

            int xObject = o.getInt("x") / TILE_SIZE;
            int yObject = o.getInt("y") / TILE_SIZE;
            int xStartingPoint = 0;

            for (int y = 0; y < Math.ceil((double) o.getInt("height") / TILE_SIZE); y++) {
                for (int x = xStartingPoint; x < Math.ceil((double) o.getInt("width") / TILE_SIZE); x += OFFSET) {

                    Node nodeToAdd = graph.getNodes()[yObject + y][xObject + x];

                    if (nodeToAdd != null) {
                        if (!targets.containsKey(o.getString("name"))) {
                            targets.put(o.getString("name"), new ArrayList<>());
                        }

                        targets.get(o.getString("name")).add(new Target(nodeToAdd));
                    }
                }

                xStartingPoint++;
            }
        }
    }

    private static void createSpawnPoints(JsonObject o) {
        int xObject = o.getInt("x") / TILE_SIZE;
        int yObject = o.getInt("y") / TILE_SIZE;

        for (int y = 0; y < Math.ceil((double) o.getInt("height") / TILE_SIZE); y++) {
            for (int x = 0; x < o.getInt("width") / TILE_SIZE; x++) {
                spawnPoints.add(graph.getNodes()[yObject + y][xObject + x]);
            }
        }
    }
}
