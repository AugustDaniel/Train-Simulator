package guiapplication.simulator.npc.traveler;

import data.Journey;
import guiapplication.simulator.InfoScreen;
import guiapplication.simulator.npc.NPC;
import guiapplication.simulator.npc.traveler.states.ArrivingState;
import guiapplication.simulator.npc.traveler.states.ShoppingState;
import guiapplication.simulator.npc.traveler.states.TravelerState;
import guiapplication.simulator.pathfinding.PathFinding;
import guiapplication.simulator.pathfinding.Target;
import util.graph.Node;

import java.awt.*;
import java.awt.geom.Point2D;
import java.time.LocalTime;
import java.util.List;

public class Traveler extends NPC implements TravelerState {

    private Target target;
    private Node currentNode;
    private Node closestNode;
    private Journey journey;
    private InfoScreen infoScreen;
    private TravelerState state;

    public Traveler(Node node, Journey journey) {
        super(node.getPosition(), -2);
        this.journey = journey;
        this.currentNode = node;
        this.closestNode = node;
        this.state = null;
        this.infoScreen = new InfoScreen(
                new String[]{
                        "Naam: " + (int) (Math.random() * 100),
                        "Leeftijd: " + (int) (Math.random() * 100),
                        "Status: ",
                        "Perron: " + this.journey.getPlatform().toString(),
                        "Trein aankomst: " + this.journey.getArrivalTime().toString(),
                        "Trein vertrek: " + this.journey.getDepartureTime().toString(),
                },
                new Point2D.Double(0, 0)
        );


        this.target = PathFinding.getRandomPlatformTarget(this.journey.getPlatform().getPlatformNumber());
        this.target.calculateShortestPath();
        checkPosition();
        initState();
    }

    private void initState() {
        if (Math.random() > 0.9) {
            setState(new ShoppingState(this));
            return;
        }

        setState(new ArrivingState(this));
        setStandardSpeed(1);
    }

    public Traveler(Node node, Journey journey, double standardSpeed) {
        this(node, journey);
        this.standardSpeed = standardSpeed;
    }

    @Override
    public void update(List<? extends NPC> npcs) {
        double threshhold = 110 * standardSpeed;
        if (standardSpeed >= 4) {
                threshhold = 110 * standardSpeed/8;
        }

        if (position.distance(getTargetPosition()) < threshhold) {
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

        targetPosition = closestNode.getPosition();
    }

    @Override
    public void draw(Graphics2D g) {
        if (clicked) {
            infoScreen.updatePosition(position.getX(), position.getY());
            infoScreen.draw(g);
        }

        super.draw(g);
    }

    public Journey getJourney() {
        return journey;
    }

    public void setTarget(Target target) {
        this.target = target;
        this.target.calculateShortestPath();
        checkPosition();
    }

    public void setState(TravelerState state) {
        this.state = state;
        this.infoScreen.updateInfo("Status: " + this.state.toString(), 2);
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    public TravelerState getState() {
        return state;
    }

    @Override
    public void handle(LocalTime time) {
        state.handle(time);
    }

    public boolean atTargetNode() {
        if (standardSpeed >= 4) {
            return position.distance(target.getNode().getPosition()) <= 7 * standardSpeed/8;
        }
        return position.distance(target.getNode().getPosition()) <= 7;
    }

    @Override
    public void setStandardSpeed(double standardSpeed) {
        this.standardSpeed = standardSpeed * this.getJourney().getPlatform().getPlatformNumber() * 2;
    }
}

