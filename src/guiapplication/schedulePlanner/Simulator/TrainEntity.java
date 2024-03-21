package guiapplication.schedulePlanner.Simulator;

import data.Journey;
import data.Platform;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class TrainEntity {
    private Platform platform;
    private BufferedImage trainWagon;
    private BufferedImage trainHeadLeft;
    private BufferedImage trainHeadRight;
    private Point2D position;
    private Journey journey;
    private MapView mapView;
    private boolean draw;


    public TrainEntity(Journey journey, MapView mapView){
        this.journey = journey;
        this.mapView = mapView;
        this.draw = false;
        int yLocation;
        if (journey.getPlatform().getPlatformNumber() %2 == 0) {
            yLocation = (((journey.getPlatform().getPlatformNumber() / 2 -1) * 16 + 12) * 32) - 152;
        }
        else {
            yLocation = ((((journey.getPlatform().getPlatformNumber() - 1) / 2) * 16 + 8) * 32) - 152;
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
        if (mapView.getClock().getCurrentTime().isAfter(journey.getArrivalTime().minusMinutes(25)) &&
                mapView.getClock().getCurrentTime().isBefore(journey.getArrivalTime())){
            position = new Point2D.Double(position.getX() + 4, position.getY());
            draw = true;
        }
        else if (mapView.getClock().getCurrentTime().isAfter(journey.getDepartureTime())) {
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
