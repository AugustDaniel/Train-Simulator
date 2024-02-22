package guiApplication.scheduleView;

import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Renderable {
    private Shape shape;
    private Point2D position;

    public Renderable(Shape shape, Point2D position) {
        this.shape = shape;
        this.position = position;
    }

    public void draw(FXGraphics2D g2d) {
        g2d.draw(getTransformedShape());
    }

    public Shape getTransformedShape() {
        return getTransform().createTransformedShape(shape);
    }

    public AffineTransform getTransform() {
        AffineTransform tx = new AffineTransform();
        tx.translate(position.getX(), position.getY());
        return tx;
    }
}

