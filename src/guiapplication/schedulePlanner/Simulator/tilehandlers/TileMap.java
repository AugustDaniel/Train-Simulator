package guiapplication.schedulePlanner.Simulator.tilehandlers;

import guiapplication.schedulePlanner.Simulator.pathfinding.PathFinding;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TileMap {

    private JsonArray tileLayers;
    private List<TileLayer> layers;
    private TileSet tileSet;

    public TileMap(JsonArray tileLayers, JsonArray tileSet) {
        this.tileLayers = tileLayers;
        this.layers = new ArrayList<>();
        this.tileSet = new TileSet(tileSet);

        for (int i = 0; i < this.tileLayers.size(); i++) {
            JsonObject object = this.tileLayers.getJsonObject(i);

            if (object.getString("type").equals("objectgroup")) {
                PathFinding.addTargets(object);
                continue;
            }

            if (object.getString("name").equals("CollisionLayer")) {
                PathFinding.addCollision(object);
                continue;
            }

            this.layers.add(new TileLayer(object, this.tileSet));
        }
    }

    public void draw(Graphics2D graphics) {
        for (TileLayer layer : this.layers) {
            layer.draw(graphics);
        }
    }


}
