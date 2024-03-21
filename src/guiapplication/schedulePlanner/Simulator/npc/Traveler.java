package guiapplication.schedulePlanner.Simulator.npc;

import guiapplication.schedulePlanner.Simulator.pathfinding.Target;
import org.jfree.fx.FXGraphics2D;
import util.graph.Node;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Traveler extends NPC {

    private Target target;
    private Node currentNode;
    private Node closestNode;

    public Traveler(Node node, Target target) {
        super(node.getPosition(), 0);
        node.setOccupied(true);
        this.currentNode = node;
        this.closestNode = node;
        this.target = target;
        checkPosition();
    }

    @Override
    public void update(ArrayList<NPC> npcs) {
        super.update(npcs);

        if (this.position.distance(this.targetPosition) < 100) {
            checkPosition();
        }
    }

    private void checkPosition() {
        currentNode.setOccupied(false);
        currentNode = closestNode;

        for (Node node : currentNode.getAdjacentNodes()) {

            if (node.isOccupied()) {
                continue;
            }

            if (target.getDistance(node) < target.getDistance(closestNode)) {
                node.setOccupied(true);
                closestNode = node;
            }
        }

        this.targetPosition = closestNode.getPosition();
    }

    public void debugDraw(FXGraphics2D g) {
        target.getShortestPath().forEach((k, v) -> {
            g.draw(new Rectangle2D.Double(k.getPosition().getX() - 16, k.getPosition().getY() - 16, 32, 32)); //todo change magic number 16 = half tilesize 32 tilesize
            g.drawString(v.toString(), (int) k.getPosition().getX() - 16, (int) k.getPosition().getY() - 16 + 20); //todo change magic number 16 = half tilesize 32 tilesize 20 is random offset number
        });
    }
}
