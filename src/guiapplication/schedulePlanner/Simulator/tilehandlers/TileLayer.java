package guiapplication.schedulePlanner.Simulator.tilehandlers;

import javax.json.JsonObject;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TileLayer implements TileHandler {

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
        this.layerHeight = object.getInt("height");
        this.layerWidth = object.getInt("width");

        int index = 0;
        layerImage = new BufferedImage(layerHeight * 32, layerWidth * 32 ,BufferedImage.TYPE_INT_ARGB); // todo change magic numbers
        Graphics2D g = layerImage.createGraphics();

        for (int y = 0; y < this.layerHeight; y++) {
            for (int x = 0; x < this.layerWidth; x++) {

                g.drawImage(
                        this.tileSet.getTile(object.getJsonArray("data").getInt(index)),
                        x * 32, y * 32, //todo change magic number to tileheight etc...
                        null);

                index++;
            }
        }
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.drawImage(layerImage, null, null);
    }
}
