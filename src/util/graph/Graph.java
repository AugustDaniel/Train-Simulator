package util.graph;

public class Graph {

    private Node[][] nodes;

    public Graph(int height, int width) {
        nodes = new Node[height][width];
    }

    public void addNode(int y, int x ,Node nodeA) {
        nodes[y][x] = nodeA;
    }

    public Node[][] getNodes() {
        return nodes;
    }
}