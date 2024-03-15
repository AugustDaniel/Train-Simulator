package guiapplication.schedulePlanner.Simulator;

import guiapplication.schedulePlanner.Simulator.pathfinding.Target;
import util.graph.Node;
import java.util.ArrayList;

public class Traveler extends NPC {

    private Target target;
    private Node currentNode;
    private Node closestNode;

    public Traveler(Node node, Target target) {
        super(node.getPosition(), 0);
        this.currentNode = node;
        this.closestNode = node;
        this.target = target;
        checkPosition();
    }

    @Override
    public void update(ArrayList<NPC> npcs) {
        super.update(npcs);

        if (getPosition().distance(getTargetPosition()) < 100) {
            currentNode = closestNode;
            checkPosition();
        }
    }

    private void checkPosition() {
        for (Node node : currentNode.getAdjacentNodes()) {
            if (target.getDistance(node) < target.getDistance(closestNode)) {
                closestNode = node;
            }
        }

        setTargetPosition(closestNode.getPosition());
    }
}
