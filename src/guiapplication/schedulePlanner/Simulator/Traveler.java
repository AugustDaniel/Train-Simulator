package guiapplication.schedulePlanner.Simulator;

import guiapplication.schedulePlanner.Simulator.pathfinding.Target;
import util.graph.Node;

import java.awt.geom.Point2D;
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
        double newAngle = Math.atan2(getTargetPosition().getY() - getPosition().getY(), getTargetPosition().getX() - getPosition().getX());

        double angleDifference = getAngle() - newAngle;
        while (angleDifference > Math.PI)
            angleDifference -= 2 * Math.PI;
        while (angleDifference < -Math.PI)
            angleDifference += 2 * Math.PI;

        if (angleDifference < -0.1)
            setAngle(getAngle() + 0.1);
        else if (angleDifference > 0.1)
            setAngle(getAngle() - 0.1);
        else
            setAngle(newAngle);

        Point2D newPosition = new Point2D.Double(
                getPosition().getX() + getSpeed() * Math.cos(getAngle()),
                getPosition().getY() + getSpeed() * Math.sin(getAngle())
        );

        setPosition(newPosition);

        if (getPosition().distance(getTargetPosition()) < 50) {
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
