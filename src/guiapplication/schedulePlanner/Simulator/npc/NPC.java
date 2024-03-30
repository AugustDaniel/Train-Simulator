package guiapplication.schedulePlanner.Simulator.npc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.List;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class NPC {

    protected Point2D position;
    protected double angle;
    protected BufferedImage image;
    protected double speed;
    protected double currentSpeed;
    protected Point2D targetPosition;
    protected int scale;
    protected boolean draw;

    public NPC(Point2D position, double angle) {
        this.position = position;
        this.targetPosition = position;
        this.angle = angle;
        this.speed = 2;
        this.draw = true;
        this.scale = 8;
        this.currentSpeed = this.speed;

        try {
            this.image = ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/astronautHelmet.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(List<NPC> npcs) {
        if (atTargetPosition()) {
            return;
        }

        this.draw = true;
        double newAngle = Math.atan2(this.targetPosition.getY() - this.position.getY(), this.targetPosition.getX() - this.position.getX());

        double angleDifference = angle - newAngle;

        while (angleDifference > Math.PI) {
            angleDifference -= 2 * Math.PI;
        }

        while (angleDifference < -Math.PI) {
            angleDifference += 2 * Math.PI;
        }

        if (angleDifference < -0.1) {
            angle += 0.1;
        } else if (angleDifference > 0.1) {
            angle -= 0.1;
        } else {
            angle = newAngle;
        }

        Point2D newPosition = new Point2D.Double(
                this.position.getX() + currentSpeed * Math.cos(angle),
                this.position.getY() + currentSpeed * Math.sin(angle)
        );

        boolean hasCollision = false;

        for (NPC npc : npcs) {
            if (npc == this) {
                continue;
            }

            if (npc.position.distance(newPosition) <= (double) image.getHeight() / scale) {
                hasCollision = true;
            }
        }

        if (!hasCollision) {
            this.currentSpeed = speed;
            this.position = newPosition;
        } else {
            this.currentSpeed *= 0.5;
            this.angle += 0.18;
        }

        // clipping performance is terrible with large amounts of npcs, this is way better
        if (this.position.getY() < 4096 - 800 && this.position.getX() < 4096 - 416 && this.position.getX() > 4096 - 528) {
            this.draw = false;
        }
    }

    public boolean atTargetPosition() {
        return position.distance(targetPosition) <= 10;
    }

    public void draw(Graphics2D g2d) {
        if (!draw) {
            return;
        }

        AffineTransform tx = new AffineTransform();
        tx.translate(this.position.getX() - (double) image.getWidth() / scale, this.position.getY() - (double) image.getHeight() / scale);
        tx.rotate(angle, (double) image.getWidth() / scale, (double) image.getHeight() / scale);
        tx.scale((double) 2 / scale, (double) 2 / scale);
        g2d.drawImage(image, tx, null);

        g2d.setColor(Color.RED);
        g2d.fill(new Ellipse2D.Double(position.getX(), position.getY(), 10, 10));
    }

    public Point2D getPosition() {
        return this.position;
    }

    public int getImageSize() {
        return image.getHeight() / scale;
    }

    public Point2D getTargetPosition() {
        return this.targetPosition;
    }

    public boolean contains(Point2D p) {
        int size = getImageSize();
        return new Rectangle2D.Double(this.position.getX() - (double) size / 2, this.position.getY() - (double) size / 2, size, size).contains(p);
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
