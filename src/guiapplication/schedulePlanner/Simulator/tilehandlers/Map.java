package guiapplication.schedulePlanner.Simulator.tilehandlers;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.*;

public class Map {
    private TileMap tileMap;

    public Map(String fileName) {
        JsonReader reader = Json.createReader(getClass().getResourceAsStream(fileName));
        JsonObject root = reader.readObject();
        this.tileMap = new TileMap(root.getJsonArray("layers"), root.getJsonArray("tilesets"));
    }

    public void draw(Graphics2D graphics) {
        tileMap.draw(graphics);
    }


}
