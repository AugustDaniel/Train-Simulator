package guiapplication.schedulePlanner.Simulator.npc;

import guiapplication.schedulePlanner.Simulator.pathfinding.Target;
import org.jfree.fx.FXGraphics2D;
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
        int rectHeight = 50;

        g.setColor(Color.WHITE);
        g.setClip(rectX, rectY, rectWidth, rectHeight);
        g.fill(new Rectangle2D.Double(rectX, rectY, rectWidth, rectHeight));

        g.setColor(Color.BLACK);

        String[] debugLines = {
                "Current position: x = " + ((int)this.position.getX()) + ", y = " + ((int)this.position.getY()),
                "Target position: x = " +((int)this.targetPosition.getX()) + ", y = " + ((int)this.targetPosition.getY()),
                "Current node: " + this.currentNode.toString(),
                "Closest node: " + this.closestNode.toString(),
        };

        int lineHeight = g.getFontMetrics().getHeight();
        int textY =  rectY + lineHeight;
        for (String line : debugLines) {
            g.drawString(line, rectX + 10, textY);
            textY += lineHeight;
        }

        g.setClip(null);
    }

    public void setDebug(boolean b) {
        this.debug = b;
    }

    public void toggleDebug() {
        setDebug(!this.debug);
    }
}
