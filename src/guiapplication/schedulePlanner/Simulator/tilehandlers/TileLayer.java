package guiapplication.schedulePlanner.Simulator.tilehandlers;

import javax.json.JsonObject;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TileLayer {

    private JsonObject object;
    private TileSet tileSet;
    private int layerHeight;
    private int layerWidth;
    private BufferedImage layerImage;


    public TileLayer(JsonObject object, TileSet tileSet) {
        this.object = object;
        this.tileSet = tileSet;
        init();
    }

    private void init() {
        layerHeight = object.getInt("height");
        layerWidth = object.getInt("width");

        int index = 0;
        layerImage = new BufferedImage(layerHeight * 32, layerWidth * 32 ,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = layerImage.createGraphics();

        for (int y = 0; y < layerHeight; y++) {
            for (int x = 0; x < layerWidth; x++) {

                g.drawImage(
                        tileSet.getTile(object.getJsonArray("data").getInt(index)),
                        x * 32, y * 32,
                        null);

                index++;
            }
        }
    }

    public void draw(Graphics2D graphics) {
        graphics.drawImage(layerImage, null, null);
    }
}
