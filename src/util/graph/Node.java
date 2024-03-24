package util.graph;

import guiapplication.schedulePlanner.Simulator.npc.NPC;
import guiapplication.schedulePlanner.Simulator.npc.Traveler;

import java.awt.geom.Point2D;
import java.io.StringReader;
import java.util.*;

public class Node {

    private Point2D position;
    private List<Node> adjacentNodes;
    private Queue<NPC> queue;

    public Node(Point2D position) {
        this.position = position;
        this.adjacentNodes = new ArrayList<>();
        this.queue = new ArrayDeque<>();
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

    public boolean occupy(NPC npc) {
        if (!queue.contains(npc)) {
            this.queue.offer(npc);
        }

        if (npc == this.queue.peek()) {
            this.queue.poll();
            return true;
        }

        return false;
    }

}
