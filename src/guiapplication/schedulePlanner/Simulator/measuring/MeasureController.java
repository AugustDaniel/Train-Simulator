package guiapplication.schedulePlanner.Simulator.measuring;

import guiapplication.schedulePlanner.Simulator.Camera;
import guiapplication.schedulePlanner.Simulator.mouselistener.MouseCallback;
import guiapplication.schedulePlanner.Simulator.npc.NPC;
import guiapplication.schedulePlanner.Simulator.pathfinding.PathFinding;
import javafx.scene.input.MouseEvent;
import util.graph.Node;

import java.awt.*;
import java.awt.geom.Point2D;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class MeasureController implements MouseCallback {

    private List<MeasurePoint> measurePoints;
    private List<NPC> npcs;
    private Camera camera;

    public MeasureController(List<NPC> npcs, Camera camera) {
        this.npcs = npcs;
        this.measurePoints = new LinkedList<>();
        this.camera = camera;
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        if (!(e.isControlDown() && e.isPrimaryButtonDown())) {
            return;
        }

        Point2D mousePos = this.camera.getWorldPos(e.getX(), e.getY());
        if (measurePoints.removeIf(current -> current.contains(mousePos))) {
            return;
        }

        Node clickedNode = PathFinding.graph.getNodes()[(int) (mousePos.getY()/ 32)][(int) (mousePos.getX() /32)];
        if (clickedNode != null) {
            this.measurePoints.add(new MeasurePoint(clickedNode, LocalTime.now()));
        }
    }

    public void update() {
        this.measurePoints.forEach(e -> e.update(this.npcs));
    }

    public void draw(Graphics2D g) {
        measurePoints.forEach(e -> e.draw(g));
    }
}
