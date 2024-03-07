package guiapplication.schedulePlanner.Simulator;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Map implements TileHandler {
    private TileSet tileSet;
    private TileMap tileMap;
    private JsonObject root;

    public Map(String fileName) {

        JsonReader reader = null;
//        try {
//            reader = Json.createReader(new FileInputStream(fileName));
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }

      reader = Json.createReader(getClass().getResourceAsStream(fileName));

        this.root = reader.readObject();
        this.tileSet = new TileSet(this.root.getJsonArray("tilesets"));
        this.tileMap = new TileMap(this.root.getJsonArray("layers"), this.tileSet);
    }

    @Override
    public void draw(Graphics2D graphics) {
        this.tileMap.draw(graphics);
    }
}
