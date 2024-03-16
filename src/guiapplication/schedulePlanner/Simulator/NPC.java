package guiapplication.schedulePlanner.Simulator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class NPC {

    private Point2D position;
    private double angle;
    private BufferedImage image;
    private double speed;
    private Point2D targetPosition;

    public NPC(Point2D position, double angle){
        this.position = position;
        this.angle = angle;
        this.speed = 2 + Math.random() * 2;

        try {
            this.image = ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/astronautHelmet.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.targetPosition = new Point2D.Double(100, 10);

    }

    public void update(ArrayList<NPC> npcs){
        double newAngle = Math.atan2(this.targetPosition.getY() - this.position.getY(), this.targetPosition.getX() - this.position.getX());

        double angleDifference = angle - newAngle;
        while(angleDifference > Math.PI)
            angleDifference -= 2 * Math.PI;
        while(angleDifference < -Math.PI)
            angleDifference += 2 * Math.PI;

        if(angleDifference < -0.1)
            angle += 0.1;
        else if(angleDifference > 0.1)
            angle -= 0.1;
        else
            angle = newAngle;

        Point2D newPosition = new Point2D.Double(
                this.position.getX() + speed * Math.cos(angle),
                this.position.getY() + speed * Math.sin(angle)
        );

        boolean hasCollision = false;

        for (NPC npc : npcs) {
            if(npc != this)
                if(npc.position.distance(newPosition) <= image.getHeight()/2)
                    hasCollision = true;
        }

        if(!hasCollision)
            this.position = newPosition;
        else
            this.angle += 0.2;
    }

    public void draw(Graphics2D g2d){
        AffineTransform tx = new AffineTransform();

        tx.translate(position.getX() - (double) image.getWidth() /2, position.getY() - (double) image.getHeight() / 2);
        tx.rotate(angle, (double) image.getWidth() /2, (double) image.getHeight()/2);
        g2d.drawImage(image, tx, null);

        g2d.setColor(Color.RED);
        g2d.fill(new Ellipse2D.Double(position.getX()-5, position.getY()-5, 10, 10));
    }

    public void setTargetPosition(Point2D targetPosition){
        this.targetPosition = targetPosition;
    }

    public Point2D getPosition(){
        return this.position;
    }
}
