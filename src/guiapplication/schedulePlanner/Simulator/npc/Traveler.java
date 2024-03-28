package guiapplication.schedulePlanner.Simulator.npc;

import data.Journey;
import guiapplication.schedulePlanner.Simulator.pathfinding.PathFinding;
import guiapplication.schedulePlanner.Simulator.pathfinding.Target;
import util.graph.Node;

import java.awt.*;
import java.util.List;
import java.awt.geom.Rectangle2D;

public class Traveler extends NPC {

    private Target target;
    private Node currentNode;
    private Node closestNode;
    private boolean clicked;
    private Journey journey;

    public Traveler(Node node, Journey journey) {
        super(node.getPosition(), -2);
        this.journey = journey;
        this.currentNode = node;
        this.closestNode = node;

        //todo
        List<Target> targets = PathFinding.platformTargets.get("Platform " + this.journey.getPlatform().getPlatformNumber());
        this.target = targets.get((int) (Math.random() * targets.size()));

        checkPosition();
    }

    @Override
    public void update(List<NPC> npcs) {
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
        if (clicked) {
            infoDraw(g);
        }

        super.draw(g);
    }

    public void infoDraw(Graphics2D g) {
        int rectX = (int) (this.position.getX() + 50);
        int rectY = (int) (this.position.getY() - 50);
        int rectWidth = 300;
        int rectHeight = 90;

        g.setClip(rectX, rectY, rectWidth, rectHeight);
        g.setColor(Color.WHITE);
        g.fill(new Rectangle2D.Double(rectX, rectY, rectWidth, rectHeight));

        g.setColor(Color.BLACK);

        String[] debugLines = {
                "Platform: " + this.journey.getPlatform().toString()
        };

        int lineHeight = g.getFontMetrics().getHeight();
        int textY = rectY + lineHeight;
        for (String line : debugLines) {
            g.drawString(line, rectX + 10, textY);
            textY += lineHeight;
        }

        g.setClip(null);
    }

    public void toggleClicked() {
        this.clicked = !this.clicked;
    }

    public Journey getJourney() {
        return this.journey;
    }

    public void setTarget(Target target) {
        this.target = target;
        checkPosition();
    }
}
