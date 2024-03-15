package guiapplication.schedulePlanner.Simulator.pathfinding;

import guiapplication.schedulePlanner.Simulator.GraphTargetDB;
import util.graph.Graph;
import util.graph.Node;

import javax.json.JsonObject;
import java.awt.geom.Point2D;

public class CollisionLayer {

    private Node[][] nodePositions;
    private int layerHeight;
    private int layerWidth;
    private Graph graph;

    public CollisionLayer(JsonObject object) {

        this.layerHeight = object.getInt("height");
        this.layerWidth = object.getInt("width");

        graph = new Graph(layerHeight, layerWidth);
        int index = 0;

        this.nodePositions = new Node[this.layerHeight][this.layerWidth];
        for (int y = 0; y < this.layerHeight; y++) {
            for (int x = 0; x < this.layerWidth; x++) {

                if (object.getJsonArray("data").getInt(index) == 1) {
                    index++;
                    continue;
                }

                nodePositions[y][x] = new Node(new Point2D.Double(x * 32, y * 32));
                index++;
            }
        }

        for (int y = 0; y < this.layerHeight; y++) {
            for (int x = 0; x < this.layerWidth; x++) {
                Node currentNode = nodePositions[y][x];

                if (!(x - 1 < 0)) {
                    addNodeAsAdjacent(currentNode, nodePositions[y][x - 1]);
                }

                if (!(x + 1 > this.layerWidth - 1)) {
                    addNodeAsAdjacent(currentNode, nodePositions[y][x + 1]);
                }

                if (!(y - 1 < 0)) {
                    addNodeAsAdjacent(currentNode, nodePositions[y - 1][x]);
                }

                if (!(y + 1 > this.layerHeight - 1)) {
                    addNodeAsAdjacent(currentNode, nodePositions[y + 1][x]);
                }

                graph.addNode(y, x, currentNode);
                index++;
            }
        }
        GraphTargetDB.getInstance().setGraphs(graph);
    }

    public void addNodeAsAdjacent(Node currentNode, Node toAdd) {
        if (toAdd != null && currentNode != null) {
            currentNode.addAdjacentNode(toAdd);
        }
    }



}
