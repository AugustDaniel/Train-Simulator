package guiapplication.schedulePlanner.Simulator.tilehandlers;

import javax.imageio.ImageIO;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TileSet {
    private List<BufferedImage> tiles;
    private JsonArray tileSets;

    public TileSet(JsonArray tileSets) {
        this.tiles = new ArrayList<>();
        this.tileSets = tileSets;
        init();
    }

    private void init() {
        tiles.add(null);

        try {
            for (int i = 0; i < tileSets.size(); i++) {
                JsonObject object = tileSets.getJsonObject(i);
                BufferedImage tilemap = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/" + object.getString("image"))));

                int tileHeight = object.getInt("tileheight");
                int tileWidth = object.getInt("tilewidth");
                int amountOfTileX = object.getInt("imagewidth") / tileWidth;

                for (int j = 0; j < object.getInt("tilecount"); j++) {
                    tiles.add(tilemap.getSubimage(tileWidth * (j % amountOfTileX), tileHeight * (j / amountOfTileX), tileWidth, tileHeight));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getTile(int gid) {
        return tiles.get(gid);
    }
}
