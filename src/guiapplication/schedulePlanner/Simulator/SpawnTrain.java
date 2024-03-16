package guiapplication.schedulePlanner.Simulator;

import data.Platform;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class SpawnTrain {
    private Platform platform;
    private BufferedImage trainWagon;
    private BufferedImage trainHeadLeft;
    private BufferedImage trainHeadRight;
    private double traveling;

    public SpawnTrain(Platform platform){
        this.platform = platform;
        this.traveling = 0;

        try {
            this.trainWagon = ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/wagontrain.png")));
            this.trainHeadLeft = ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/backtrain.png")));
            this.trainHeadRight = ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/fronttrain.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(){
        traveling += 4;
    }

    public void draw(Graphics2D g2d){
        AffineTransform tx = new AffineTransform();

        tx.translate(30 + traveling, -9 );
        g2d.drawImage(trainHeadRight, tx, null);

        tx.translate(trainHeadRight.getWidth() - 20,0);
        g2d.drawImage(trainWagon, tx, null);

        tx.translate(trainWagon.getWidth() - 30,0);
        g2d.drawImage(trainHeadLeft, tx, null);
    }
}
