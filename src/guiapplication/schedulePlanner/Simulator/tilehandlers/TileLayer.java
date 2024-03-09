package guiapplication.schedulePlanner.Simulator.tilehandlers;

import javax.json.JsonObject;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class TileLayer implements TileHandler {

    private JsonObject object;
    private TileSet tileSet;
    private int[][] tilePositions;
    private int height;
    private int width;

    public TileLayer(JsonObject object, TileSet tileSet) {
        this.object = object;
        this.tileSet = tileSet;
        init();
    }

    private void init() {
        this.height = object.getInt("height");
        this.width = object.getInt("width");

        int index = 0;

        this.tilePositions = new int[this.height][this.width];
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                this.tilePositions[y][x] = object.getJsonArray("data").getInt(index);
                index++;
            }
        }
    }

    @Override
    public void draw(Graphics2D graphics) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (this.tilePositions[y][x] == 0) {
                    continue;
                }

                graphics.drawImage(
                        this.tileSet.getTile(this.tilePositions[y][x]),
                        AffineTransform.getTranslateInstance(x * 32, y * 32),
                        null);
            }
        }
    }
}
