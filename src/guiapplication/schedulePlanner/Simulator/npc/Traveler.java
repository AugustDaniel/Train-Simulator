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
    private String name;
    private int age;

    public Traveler(Node node, Journey journey) {
        super(node.getPosition(), -2);
        this.journey = journey;
        this.currentNode = node;
        this.closestNode = node;
        this.age = (int) (Math.random() * 100);
        this.name = "Reiziger " + (int) (Math.random() * 1000);

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
        int rectX = (int) (this.position.getX() + 25);
        int rectY = (int) (this.position.getY() - 50);
        int rectWidth = 150;
        int rectHeight = 70;

        g.setClip(rectX, rectY, rectWidth, rectHeight);
        g.setColor(Color.WHITE);
        g.fill(new Rectangle2D.Double(rectX, rectY, rectWidth, rectHeight));

        g.setColor(Color.BLACK);

        String[] debugLines = {
                "Naam: " + this.name,
                "Leeftijd: " + this.age,
                "Perron: " + this.journey.getPlatform().toString(),
                "Trein aankomst: " + this.journey.getArrivalTime().toString(),
                "Trein vertrek: " + this.journey.getDepartureTime().toString(),
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
