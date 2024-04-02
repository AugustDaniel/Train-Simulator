package guiapplication.schedulePlanner.Simulator;

import guiapplication.schedulePlanner.Simulator.mouselistener.MouseCallback;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.function.BiFunction;

public class Camera implements MouseCallback {

    private Point2D target;
    private float zoom;
    private Canvas canvas;
    private Point2D screenMousePos;
    private Point2D distance;


    public Camera(Canvas canvas) {
        this.target = new Point2D.Double(-canvas.getWidth()*4, -canvas.getHeight()*7);
        this.zoom = 1;
        this.canvas = canvas;
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        screenMousePos = new Point2D.Double(e.getX() / zoom, e.getY() / zoom);

        if (e.isSecondaryButtonDown()) {
            distance = getDistancePoint((a, b) -> a - b, target, screenMousePos);
        }
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        screenMousePos = new Point2D.Double(e.getX() / zoom, e.getY() / zoom);

        if (e.isSecondaryButtonDown()) {
            target = getDistancePoint(Double::sum, screenMousePos, distance);
        }
    }

    @Override
    public void onMouseScrolled(ScrollEvent e) {
        incrementZoom((float) e.getDeltaY() / 1500);
    }

    public AffineTransform getTransform() {
        AffineTransform transform = new AffineTransform();
        transform.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        transform.scale(zoom, zoom);
        transform.translate(target.getX(), target.getY());
        return transform;
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        distance = null;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;

        if (this.zoom <= 0) {
            this.zoom = 0.1f;
        }
    }

    public void incrementZoom(float amount) {
        setZoom(zoom + amount);
    }

    public Point2D getDistancePoint(BiFunction<Double, Double, Double> operator, Point2D i, Point2D j) {
        return new Point2D.Double(operator.apply(i.getX(), j.getX()), operator.apply(i.getY(), j.getY()));
    }

    public Point2D getWorldPos(double x, double y) {
        try {
            return this.getTransform().inverseTransform(new Point2D.Double(x, y), null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
