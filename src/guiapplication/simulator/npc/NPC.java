package guiapplication.simulator.npc;

import guiapplication.simulator.npc.traveler.Traveler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.List;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class NPC {

    protected Point2D position;
    protected double angle;
    protected BufferedImage image;
    protected double standardSpeed;
    protected double currentSpeed;
    protected Point2D targetPosition;
    protected int scale;
    protected boolean draw;
    protected boolean clicked;

    public NPC(Point2D position, double angle) {
        this.position = position;
        this.targetPosition = position;
        this.angle = angle;
        this.draw = true;
        this.scale = 8;
        this.currentSpeed = this.standardSpeed;
        this.standardSpeed = 1;
        try {
            this.image = ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/astronautHelmet.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(List<? extends NPC> npcs) {
        if (atTargetPosition()) {
            return;
        }

        draw = true;
        double newAngle = Math.atan2(targetPosition.getY() - position.getY(), targetPosition.getX() - position.getX());

        double angleDifference = angle - newAngle;

        while (angleDifference > Math.PI) {
            angleDifference -= 2 * Math.PI;
        }

        while (angleDifference < -Math.PI) {
            angleDifference += 2 * Math.PI;
        }

        if (angleDifference < -0.1 * standardSpeed) {
            angle += 0.1 * standardSpeed;
        } else if (angleDifference > 0.1 * standardSpeed) {
            angle -= 0.1 * standardSpeed;
        } else {
            angle = newAngle;
        }

        if (((Traveler) this).getJourney().getPlatform().getPlatformNumber() >= 10) {
            currentSpeed *= (1 + (double) ((Traveler) this).getJourney().getPlatform().getPlatformNumber() /10);
        }

        Point2D newPosition = new Point2D.Double(
                position.getX() + currentSpeed * Math.cos(angle),
                position.getY() + currentSpeed * Math.sin(angle)
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
            currentSpeed = standardSpeed;
            position = newPosition;
        } else {
            currentSpeed *= 0.5;
            angle += 0.18 * standardSpeed;
        }

        // clipping performance is terrible with large amounts of npcs, this is way better
        if (position.getY() < 4096 - 800 && position.getX() < 4096 - 416 && position.getX() > 4096 - 528) {
            draw = false;
        }
    }

    public void draw(Graphics2D g2d) {
        if (!draw) {
            return;
        }

        AffineTransform tx = new AffineTransform();
        tx.translate(position.getX() - (double) image.getWidth() / scale, position.getY() - (double) image.getHeight() / scale);
        tx.rotate(angle, (double) image.getWidth() / scale, (double) image.getHeight() / scale);
        tx.scale((double) 2 / scale, (double) 2 / scale);
        g2d.drawImage(image, tx, null);
    }

    public Point2D getPosition() {
        return position;
    }

    public int getImageSize() {
        return image.getHeight() / scale;
    }

    public Point2D getTargetPosition() {
        return targetPosition;
    }

    public boolean contains(Point2D p) {
        int size = getImageSize();
        return new Rectangle2D.Double(position.getX() - (double) size / 2, position.getY() - (double) size / 2, size, size).contains(p);
    }

    public void setStandardSpeed(double standardSpeed) {
        this.standardSpeed = standardSpeed * ((Traveler) this).getJourney().getPlatform().getPlatformNumber() * 2;
    }

    public void toggleClicked() {
        clicked = !clicked;
    }

    public boolean atTargetPosition() {
        if (standardSpeed >= 4) {
            return position.distance(targetPosition) <= 7 * standardSpeed / 8;
        }

        return position.distance(targetPosition) <= 7;
    }
}
