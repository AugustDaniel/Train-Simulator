package guiapplication.schedulePlanner.Simulator;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Camera {

    private Point2D target;
    private float zoom;

    public Camera(Point2D target, float zoom) {
        this.target = target;
        this.zoom = zoom;
    }

    public float getZoom() {
        return this.zoom;
    }

    public void setTarget(Point2D target) {
        this.target = target;
    }

    public Point2D getTarget() {
        return this.target;
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

    public AffineTransform getTransform() {
        AffineTransform transform = new AffineTransform();
        transform.scale(this.zoom, this.zoom);
        transform.translate(this.target.getX(), this.target.getY());
        return transform;
    }
}
