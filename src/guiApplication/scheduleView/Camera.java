package guiApplication.scheduleView;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Camera {
    private Point2D target;
    private float zoom;
    private double screenWidth;
    private double screenHeight;

    public Camera(Point2D target, float zoom, double screenWidth, double screenHeight) {
        this.target = target;
        this.zoom = zoom;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
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
        transform.translate(this.screenWidth / 2, this.screenHeight / 2);
        transform.scale(this.zoom, this.zoom);
        transform.translate(this.target.getX(), this.target.getY());
        return transform;
    }
}
