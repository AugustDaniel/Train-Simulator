package guiapplication.simulator.measuring;

import guiapplication.simulator.Camera;
import guiapplication.simulator.Clock;
import guiapplication.simulator.mouselistener.MouseCallback;
import guiapplication.simulator.npc.traveler.Traveler;
import guiapplication.simulator.pathfinding.PathFinding;
import javafx.scene.input.MouseEvent;
import util.graph.Node;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

public class MeasureController implements MouseCallback {

    private List<MeasurePoint> measurePoints;
    private List<Traveler> travelers;
    private Camera camera;
    private Clock clock;

    public MeasureController(List<Traveler> travelers, Camera camera, Clock clock) {
        this.travelers = travelers;
        this.measurePoints = new LinkedList<>();
        this.camera = camera;
        this.clock = clock;
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        if (!(e.isControlDown() && e.isPrimaryButtonDown())) {
            return;
        }

        Point2D mousePos = camera.getWorldPos(e.getX(), e.getY());
        if (measurePoints.removeIf(current -> current.contains(mousePos))) {
            return;
        }

        Node clickedNode = PathFinding.graph.getNodes()[(int) (mousePos.getY()/ 32)][(int) (mousePos.getX() /32)];
        if (clickedNode != null) {
            measurePoints.add(new MeasurePoint(clickedNode, clock.getCurrentTime()));
        }
    }

    public void update() {
        measurePoints.forEach(e -> e.update(travelers));
    }

    public void draw(Graphics2D g) {
        measurePoints.forEach(e -> e.draw(g));
    }
}
