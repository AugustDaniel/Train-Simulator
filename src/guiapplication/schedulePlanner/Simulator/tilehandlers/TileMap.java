package guiapplication.schedulePlanner.Simulator.tilehandlers;

import guiapplication.schedulePlanner.Simulator.pathfinding.CollisionLayer;
import util.graph.Graph;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TileMap implements TileHandler {

    private JsonArray tileLayers;
    private List<TileLayer> layers;
    private TileSet tileSet;
    private CollisionLayer collisionLayer;

    public TileMap(JsonArray tileLayers, TileSet tileSet) {
        this.tileLayers = tileLayers;
        this.layers = new ArrayList<>();
        this.collisionLayer = null;
        this.tileSet = tileSet;

        for (int i = 0; i < this.tileLayers.size(); i++) {
            JsonObject object = this.tileLayers.getJsonObject(i);

            if (object.getString("type").equals("objectgroup")) {
                continue;
            }
            if (object.getString("name").equals("CollisionLayer")) {
                collisionLayer = new CollisionLayer(object);
                continue;
            }

            this.layers.add(new TileLayer(object, this.tileSet));

        }
    }

    @Override
    public void draw(Graphics2D graphics) {
        for (TileLayer layer : this.layers) {
            layer.draw(graphics);
        }
    }

    public Graph getGraph() {
        return collisionLayer.getGraph();
    }
}
