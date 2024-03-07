package guiapplication.schedulePlanner.Simulator;

import guiapplication.schedulePlanner.Simulator.tilehandlers.TileHandler;
import guiapplication.schedulePlanner.Simulator.tilehandlers.TileMap;
import guiapplication.schedulePlanner.Simulator.tilehandlers.TileSet;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.*;

public class Map implements TileHandler {
    private TileSet tileSet;
    private TileMap tileMap;
    private JsonObject root;

    public Map(String fileName) {

        JsonReader reader = Json.createReader(getClass().getResourceAsStream(fileName));
        this.root = reader.readObject();
        this.tileSet = new TileSet(this.root.getJsonArray("tilesets"));
        this.tileMap = new TileMap(this.root.getJsonArray("layers"), this.tileSet);
    }

    @Override
    public void draw(Graphics2D graphics) {
        this.tileMap.draw(graphics);
    }
}
