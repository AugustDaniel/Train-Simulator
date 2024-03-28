package guiapplication.schedulePlanner.Simulator;

import data.Journey;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class TrainEntity {
    private BufferedImage trainWagon;
    private BufferedImage trainHeadLeft;
    private BufferedImage trainHeadRight;
    private Point2D position;
    private Journey journey;
    private Clock clock;
    private boolean draw;


    public TrainEntity(Journey journey, Clock clock){
        this.journey = journey;
        this.clock = clock;
        this.draw = false;
        int yLocation;
        if (journey.getPlatform().getPlatformNumber() %2 == 0) {
            yLocation = 106 * 32 - (((journey.getPlatform().getPlatformNumber() / 2 -1) * 14 + 10) * 32) - 152;
        }
        else {
            yLocation = 106 * 32 -((((journey.getPlatform().getPlatformNumber() - 1 + 2) / 2) * 14) * 32) - 152;
        }


        this.position = new Point2D.Double(-900, yLocation);

        try {
            this.trainWagon = ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/wagontrain.png")));
            this.trainHeadLeft = ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/backtrain.png")));
            this.trainHeadRight = ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/fronttrain.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(){
        if (this.clock.getCurrentTime().isAfter(journey.getArrivalTime().minusMinutes(25)) &&
                this.clock.getCurrentTime().isBefore(journey.getArrivalTime())){
            position = new Point2D.Double(position.getX() + 4, position.getY());
            draw = true;
        }
        else if (this.clock.getCurrentTime().isAfter(journey.getDepartureTime())) {
            position  = new Point2D.Double(position.getX() + 4, position.getY());
        }

    }

    public void draw(Graphics2D g2d){
        if (!draw) {
            return;
        }
        g2d.setClip(0,0,3584,4000);
        AffineTransform tx = new AffineTransform();

        tx.translate(position.getX(), position.getY());
        g2d.drawImage(trainHeadRight, tx, null);

        tx.translate(trainHeadRight.getWidth(),-4);
        g2d.drawImage(trainWagon, tx, null);

        tx.translate(trainWagon.getWidth(),2);
        g2d.drawImage(trainHeadLeft, tx, null);
        g2d.setClip(null);
    }
}
