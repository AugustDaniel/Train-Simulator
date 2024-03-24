package guiapplication.schedulePlanner.Simulator.npc;

import guiapplication.schedulePlanner.Simulator.pathfinding.Target;
import util.graph.Node;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Traveler extends NPC {

    private Target target;
    private Node currentNode;
    private Node closestNode;
    private boolean debug;

    public Traveler(Node node, Target target) {
        super(node.getPosition(), -2);
        this.currentNode = node;
        this.closestNode = node;
        this.target = target;
        checkPosition();
    }

    @Override
    public void update(ArrayList<NPC> npcs) {
        if (this.position.distance(getTargetPosition()) < 110) {
            checkPosition();
        }

        super.update(npcs);
    }

    private void checkPosition() {
        currentNode = closestNode;

        for (Node node : currentNode.getAdjacentNodes()) {
            if (target.getDistance(node) < target.getDistance(closestNode)) {
                closestNode = node;
                break;
            }
        }

        this.targetPosition = closestNode.getPosition();
    }

    @Override
    public void draw(Graphics2D g) {
        if (debug) {
            debugDraw(g);
        }

        super.draw(g);
    }

    public void debugDraw(Graphics2D g) {
        //todo tile pathfinding debugging
//        target.getShortestPath().forEach((k, v) -> {
//            g.draw(new Rectangle2D.Double(k.getPosition().getX() - 16, k.getPosition().getY() - 16, 32, 32)); //todo change magic number 16 = half tilesize 32 tilesize
//            g.drawString(v.toString(), (int) k.getPosition().getX() - 16, (int) k.getPosition().getY() - 16 + 20); //todo change magic number 16 = half tilesize 32 tilesize 20 is random offset number
//        });

        int rectX = (int) (this.position.getX() + 50);
        int rectY = (int) (this.position.getY() - 50);
        int rectWidth = 300;
        int rectHeight = 90;

        g.setClip(rectX, rectY, rectWidth, rectHeight);
        g.setColor(Color.WHITE);
        g.fill(new Rectangle2D.Double(rectX, rectY, rectWidth, rectHeight));

        g.setColor(Color.BLACK);

        String[] debugLines = {
                "Current position: x = " + ((int) this.position.getX()) + ", y = " + ((int) this.position.getY()),
                "Target position: x = " + ((int) this.targetPosition.getX()) + ", y = " + ((int) this.targetPosition.getY()),
                "Current node: " + this.currentNode.toString(),
                "Closest node: " + this.closestNode.toString(),
                "Distance p2d: " + this.position.distance(this.targetPosition),
                "Current tile distance: " + target.getDistance(currentNode),
                "Closest tile distance: " + target.getDistance(closestNode)
        };

        int lineHeight = g.getFontMetrics().getHeight();
        int textY = rectY + lineHeight;
        for (String line : debugLines) {
            g.drawString(line, rectX + 10, textY);
            textY += lineHeight;
        }

        g.setClip(null);

        g.setColor(Color.BLACK);
        g.draw(new Rectangle2D.Double(closestNode.getPosition().getX() - 16, closestNode.getPosition().getY() - 16, 32, 32));

        for (Node node : currentNode.getAdjacentNodes()) {
            g.setColor(Color.GREEN);
            g.draw(new Rectangle2D.Double(node.getPosition().getX() - 16, node.getPosition().getY() - 16, 32, 32));
        }
    }

    public void setDebug(boolean b) {
        this.debug = b;
    }

    public void toggleDebug() {
        setDebug(!this.debug);
    }
}
