package guiapplication.simulator;

import data.Journey;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
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
    private double trainSpeed;
    private int tileDimentions;
    private boolean draw;
    private int trainlength;
    private boolean playedOnes = false;
    private int startBridge = 112;
    private int endBridge = 124;
    private int spacebetweenEndBridgeAndEndOfSimulation = 4;



    public TrainEntity(Journey journey, Clock clock) {
        this.journey = journey;
        this.clock = clock;
        this.draw = false;
        this.trainSpeed = 20;
        this.tileDimentions = 32;
        int yLocation;
        if (journey.getPlatform().getPlatformNumber() % 2 == 0) {
            yLocation = 106 * tileDimentions - (((journey.getPlatform().getPlatformNumber() / 2 - 1) * 14 + 10) * tileDimentions) - 152;
        } else {
            yLocation = 106 * tileDimentions - ((((journey.getPlatform().getPlatformNumber() - 1) / 2) * 14) * tileDimentions) - 152;
        }


        this.position = new Point2D.Double(-900, yLocation);

        try {
            this.trainWagon = ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/wagontrain.png")));
            this.trainHeadLeft = ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/backtrain.png")));
            this.trainHeadRight = ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/fronttrain.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.trainlength = trainHeadLeft.getWidth() + trainHeadRight.getWidth() + journey.getTrain().getWagons().size() * trainWagon.getWidth();

    }

    public void update() {
        double clockSpeed = 1 / clock.getTimeSpeed();

        if (clock.getCurrentTime().isAfter(journey.getArrivalTime().minusMinutes(5)) &&
                position.getX() + trainlength < 90 * tileDimentions) {
            position = new Point2D.Double(position.getX() + (trainSpeed * clockSpeed), position.getY());
            draw = true;
            if (position.getX() > 5 * tileDimentions)
                if (trainSpeed > 4)
                    trainSpeed -= 0.125;
                else
                    trainSpeed = 4;
        } else if (clock.getCurrentTime().isAfter(journey.getDepartureTime())) {
            if (trainSpeed < 25)
                trainSpeed += 0.5;
            position = new Point2D.Double(position.getX() + (trainSpeed * clockSpeed), position.getY());
            if (!playedOnes) {
                playedOnes = true;
                Sounds.whistleWhenTrainLeaving();
            }
        }
    }

    public void draw(Graphics2D g2d) {
        if (!draw) {
            return;
        }

        Area clipArea = new Area(new Rectangle(0, 0, startBridge * tileDimentions, 4000));
        clipArea.add(new Area(new Rectangle(endBridge * tileDimentions, 0, spacebetweenEndBridgeAndEndOfSimulation * tileDimentions, 4000)));
        g2d.setClip(clipArea);

        AffineTransform tx = new AffineTransform();
        tx.translate(position.getX(), position.getY());
        g2d.drawImage(trainHeadRight, tx, null);

        for (int i = 0; i < journey.getTrain().getWagons().size(); i++) {
            if (i == 0) {
                tx.translate(trainHeadRight.getWidth(), -4);
            } else {
                tx.translate(trainWagon.getWidth(), 0);
            }
            g2d.drawImage(trainWagon, tx, null);
        }

        tx.translate(trainWagon.getWidth(), 2);
        g2d.drawImage(trainHeadLeft, tx, null);
        g2d.setClip(null);
    }
}
