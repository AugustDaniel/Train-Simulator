package guiapplication.schedulePlanner.Simulator.tilehandlers;



import util.graph.Graph;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.*;

public class Map implements TileHandler {
    private TileMap tileMap;

    public Map(String fileName) {
        JsonReader reader = Json.createReader(getClass().getResourceAsStream(fileName));
        JsonObject root = reader.readObject();
        this.tileMap = new TileMap(root.getJsonArray("layers"), root.getJsonArray("tilesets"));
    }

    @Override
    public void draw(Graphics2D graphics) {
        this.tileMap.draw(graphics);
    }


}
