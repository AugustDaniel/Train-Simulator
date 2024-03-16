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

    public SpawnTrain(Platform platform){
        this.platform = platform;

        try {
            this.trainWagon = ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/wagontrain.png")));
            this.trainHeadLeft = ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/backtrain.png")));
            this.trainHeadRight = ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/fronttrain.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(){
    }

    private int traveling = 0;
    public void draw(Graphics2D g2d){
        AffineTransform tx = new AffineTransform();

        tx.translate(30 + traveling, -9 );
        g2d.drawImage(trainWagon, tx, null);
        traveling += 5;
    }
}
