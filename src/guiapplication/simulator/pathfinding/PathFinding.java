package guiapplication.simulator.pathfinding;

import util.graph.Graph;
import util.graph.Node;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.awt.geom.Point2D;
import java.util.*;

public class PathFinding {

    private final static int COLLISION_TILE = 1;
    private final static int TILE_SIZE = 32;

    public static Map<Integer, List<Target>> platformTargets = new HashMap<>();
    public static Map<Integer, List<Target>> trainTargets = new HashMap<>();
    public static List<Node> shopPoints = new ArrayList<>();
    public static List<Node> spawnPoints = new ArrayList<>();
    public static Graph graph = new Graph(0, 0);

    /**
     * getShortestPath
     *
     * Find the shortest path for every node in Pathfinding.graph to the source node
     * It achieves this with a breadth first search and keeping count of the distance
     * @author Group A1
     * @param source node to find the shortest path to
     * @return a distance map with distance from all tiles to source
     */
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

    /**
     * addCollision
     *
     * fills Pathfinding.graph with nodes for every tile in the json object, it will skip an index in the graph when a collision tile is detected
     * this results in a graph with nodes in indices which can be walked upon and null on indices which have collision
     * it will add nodes as adjacent in a cross shape so every nodes is connected to the node above, to the right and left and the node below
     * @author Group A1
     * @param object json object which includes the collision layer
     */
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

    /**
     * addNodeAsAdjacent
     *
     * will add a node as adjacent to a current node
     * it will also check if that the node added as an adjacent has current node as adjacent
     * if not it will correct this by adding the current node as adjacent
     * @author Group A1
     * @param currentNode node to add adjacent to
     * @param toAdd adjacent node
     */
    private static void addNodeAsAdjacent(Node currentNode, Node toAdd) {
        if (toAdd != null && currentNode != null) {
            currentNode.addAdjacentNode(toAdd);

            if (!toAdd.getAdjacentNodes().contains(currentNode)) {
                toAdd.addAdjacentNode(currentNode);
            }
        }
    }

    /**
     * addTargets
     *
     * will walk through the width and height of the object layer and adds as targets or points
     * @author Group A1
     * @param jsonObject json object which contains the object layer
     */
    public static void addTargets(JsonObject jsonObject) {
        JsonArray objects = jsonObject.getJsonArray("objects");

        for (int i = 0; i < objects.size(); i++) {

            JsonObject o = objects.getJsonObject(i);

            if (o.getString("name").equals("spawn")) {
                createPoints(o, spawnPoints);
                continue;
            }
            if (o.getString("name").equals("Shops")) {
                createPoints(o, shopPoints);
                continue;
            }

            int xObject = o.getInt("x") / TILE_SIZE;
            int yObject = o.getInt("y") / TILE_SIZE;
            int xStartingPoint = 0;

            double yLimit = Math.ceil((double) o.getInt("height") / TILE_SIZE);
            double xLimit = Math.ceil((double) o.getInt("width") / TILE_SIZE);
            for (int y = 0; y < yLimit; y++) {
                for (int x = xStartingPoint; x < xLimit; x++) {

                    Node nodeToAdd = graph.getNodes()[yObject + y][xObject + x];

                    if (nodeToAdd != null) {
                        String name = o.getString("name");

                        if (name.contains("Platform")) {
                            addToMap(platformTargets, name, nodeToAdd);
                        } else if (name.contains("Train")) {
                            addToMap(trainTargets, name, nodeToAdd);
                        }
                    }
                }

                xStartingPoint++;
            }
        }
    }

    /**
     * addToMap
     *
     * extracts the number out of the name and adds the node to the map with that number as key
     * @author Group A1
     * @param map hashmap in pathfinding which to add to
     * @param name name of the entry in the hashmap
     * @param nodeToAdd node to aad to map
     */
    private static void addToMap(Map<Integer, List<Target>> map, String name, Node nodeToAdd) {
        int id = Integer.parseInt(name.split(" ")[1]);

        if (!map.containsKey(id)) {
            map.put(id, new LinkedList<>());
        }

        map.get(id).add(new Target(nodeToAdd));
    }

    private static void createPoints(JsonObject object, List<Node> pointList) {
        int xObject = object.getInt("x") / TILE_SIZE;
        int yObject = object.getInt("y") / TILE_SIZE;

        for (int y = 0; y < Math.ceil((double) object.getInt("height") / TILE_SIZE); y++) {
            for (int x = 0; x < object.getInt("width") / TILE_SIZE; x++) {
                pointList.add(graph.getNodes()[yObject + y][xObject + x]);
            }
        }
    }

    public static Target getRandomPlatformTarget(int platform) {
        int size = platformTargets.get(platform).size();
        return platformTargets.get(platform).get((int) (Math.random() * size));
    }

    public static Target getRandomTrainTarget(int train) {
        int size = trainTargets.get(train).size();
        return trainTargets.get(train).get((int) (Math.random() * size));
    }

    public static Target getRandomShoppingTarget() {
        return new Target(shopPoints.get((int) (Math.random() * shopPoints.size())));
    }

    public static Node getRandomSpawnPoint() {
        return spawnPoints.get((int) (Math.random() * spawnPoints.size()));
    }
}
