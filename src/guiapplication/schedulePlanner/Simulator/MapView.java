package guiapplication.schedulePlanner.Simulator;

import guiapplication.schedulePlanner.Simulator.tilehandlers.Map;
import guiapplication.schedulePlanner.View;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.function.BiFunction;

public class MapView implements View {

    private Map map;
    private ResizableCanvas canvas;
    private Camera camera;
    private Point2D screenMousePos;
    private Point2D worldMousePos;
    private Point2D distance;

    public void update(double deltaTime) {}

    @Override
    public Node getNode() {
        BorderPane mainPane = new BorderPane();
        map = new Map("/TrainStationPlannerMap.tmj");
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        camera = new Camera(new Point2D.Double(0,0), 1, canvas.getWidth(), canvas.getHeight());
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();
        Point2D nullpoint = new Point2D.Double(0,0);
        worldMousePos = nullpoint;
        screenMousePos = nullpoint;
        distance = nullpoint;

        canvas.setOnMousePressed(this::mousePressed);
        canvas.setOnMouseReleased(this::mouseReleased);
        canvas.setOnMouseDragged(this::mouseDragged);
        canvas.setOnScroll(this::mouseScrolled);

        draw(g2d);

        return mainPane;
    }

    public void draw(Graphics2D g) {
        g.setBackground(Color.black);
        g.setTransform(new AffineTransform());
        g.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        g.setTransform(camera.getTransform());
        map.draw(g);
    }

    private void mousePressed(MouseEvent e) {
        worldMousePos = getWorldPos(e.getX(), e.getY());
        screenMousePos = new Point2D.Double(e.getX() / camera.getZoom(), e.getY() / camera.getZoom());

        if (e.isSecondaryButtonDown()) {
            distance = getDistancePoint((a, b) -> a - b, camera.getTarget(), screenMousePos);
        }
    }

    private void mouseDragged(MouseEvent e) {
        worldMousePos = getWorldPos(e.getX(), e.getY());
        screenMousePos = new Point2D.Double(e.getX() / camera.getZoom(), e.getY() / camera.getZoom());

        if (e.isSecondaryButtonDown()) {
            camera.setTarget(getDistancePoint(Double::sum, screenMousePos, distance));
        }
    }

    private void mouseScrolled(ScrollEvent e) {
        camera.incrementZoom((float) e.getDeltaY() / 1000);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }

    private void mouseReleased(MouseEvent e) {
        worldMousePos = null;
        distance = null;
    }

    private Point2D getDistancePoint(BiFunction<Double, Double, Double> operator, Point2D i, Point2D j) {
        return new Point2D.Double(operator.apply(i.getX(), j.getX()), operator.apply(i.getY(), j.getY()));
    }

    private Point2D getWorldPos(double x, double y) {
        try {
            return camera.getTransform().inverseTransform(new Point2D.Double(x, y), null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
