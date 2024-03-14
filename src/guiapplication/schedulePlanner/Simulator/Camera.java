package guiapplication.schedulePlanner.Simulator;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.Resizable;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.function.BiFunction;

public class Camera {

    private Point2D target;
    private float zoom;
    private Canvas canvas;
    private Resizable resizable;
    private FXGraphics2D g2d;
    private Point2D screenMousePos;
    private Point2D distance;
    private Point2D zoomPoint;
    private Point2D offset;


    public Camera(Canvas canvas, Resizable resizable, FXGraphics2D g2d) {
        this.target = new Point2D.Double(-canvas.getWidth() * 4, -canvas.getHeight() * 7); //todo magic numbers is zoom for start of map
        this.zoom = 1;
        this.canvas = canvas;
        this.resizable = resizable;
        this.g2d = g2d;
        this.zoomPoint = new Point2D.Double(-canvas.getWidth() * 4, -canvas.getHeight() * 7); //todo magic numbers is zoom for start of map
        this.offset = new Point2D.Double(0, 0);
        canvas.setOnMousePressed(this::mousePressed);
        canvas.setOnMouseReleased(this::mouseReleased);
        canvas.setOnMouseDragged(this::mouseDragged);
        canvas.setOnScroll(this::mouseScrolled);
        canvas.setOnMouseMoved(this::mouseMoved);
    }

    private void mouseMoved(MouseEvent mouseEvent) {
        this.zoomPoint = new Point2D.Double(mouseEvent.getX(), mouseEvent.getY());
    }

    private void mousePressed(MouseEvent e) {
        screenMousePos = new Point2D.Double(e.getX() / this.zoom, e.getY() / this.zoom);

        if (e.isSecondaryButtonDown()) {
            distance = getDistancePoint((a, b) -> a - b, this.target, screenMousePos);
        }
    }

    private void mouseDragged(MouseEvent e) {
        screenMousePos = new Point2D.Double(e.getX() / this.zoom, e.getY() / this.zoom);

        if (e.isSecondaryButtonDown()) {
            this.target = getDistancePoint(Double::sum, screenMousePos, distance);
        }
    }

    private void mouseScrolled(ScrollEvent e) {
        Point2D worldPos = getWorldPos(e.getX(), e.getY());
        this.incrementZoom((float) e.getDeltaY() / 1500);
        Point2D oldWorldPos = getWorldPos(e.getX(), e.getY());
        offset = getDistancePoint((a, b) -> a - b, oldWorldPos, worldPos);

        this.zoomPoint = new Point2D.Double(e.getX()  + offset.getX(), e.getY() + offset.getY());
        this.target = new Point2D.Double(target.getX() + offset.getX(), target.getY() + offset.getY());
    }

    public AffineTransform getTransform() {
        AffineTransform transform = new AffineTransform();
        transform.translate(this.zoomPoint.getX(), this.zoomPoint.getY());
        transform.scale(this.zoom, this.zoom);
        transform.translate(-this.zoomPoint.getX(), -this.zoomPoint.getY());
        transform.translate(this.target.getX(), this.target.getY());
        return transform;
    }

    private void mouseReleased(MouseEvent e) {
        distance = null;
    }

    public float getZoom() {
        return this.zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;

        if (this.zoom <= 0) {
            this.zoom = 0.1f;
        }
    }

    public void incrementZoom(float amount) {
        setZoom(this.zoom + amount);
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
