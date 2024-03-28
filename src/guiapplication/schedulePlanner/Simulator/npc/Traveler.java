package guiapplication.schedulePlanner.Simulator.npc;

import data.Journey;
import guiapplication.schedulePlanner.Simulator.pathfinding.PathFinding;
import guiapplication.schedulePlanner.Simulator.pathfinding.Target;
import util.graph.Node;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class Traveler extends NPC {

    private Target target;
    private Node currentNode;
    private Node closestNode;
    private boolean clicked;
    private Journey journey;
    private InfoScreen infoScreen;
    private boolean boarding;

    public Traveler(Node node, Journey journey) {
        super(node.getPosition(), -2);
        this.journey = journey;
        this.currentNode = node;
        this.closestNode = node;
        this.infoScreen = new InfoScreen(
                new String[]{
                        "Naam: " + (int) (Math.random() * 100),
                        "Leeftijd: " + (int) (Math.random() * 1000),
                        "Perron: " + this.journey.getPlatform().toString(),
                        "Trein aankomst: " + this.journey.getArrivalTime().toString(),
                        "Trein vertrek: " + this.journey.getDepartureTime().toString(),
                },
                new Point2D.Double(0, 0)
        );

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
            infoScreen.updatePosition(this.position.getX(), this.position.getY());
            infoScreen.draw(g);
        }

        super.draw(g);
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

    public void setBoarding(boolean boarding) {
        this.boarding = boarding;
    }

    public boolean isBoarding() {
        return this.boarding;
    }
}
