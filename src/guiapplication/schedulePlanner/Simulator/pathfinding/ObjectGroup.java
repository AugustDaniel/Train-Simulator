package guiapplication.schedulePlanner.Simulator.pathfinding;

import guiapplication.schedulePlanner.Simulator.GraphTargetDB;
import util.graph.Graph;
import util.graph.Node;

import javax.json.JsonArray;
import javax.json.JsonObject;

public class ObjectGroup {

    public ObjectGroup(JsonObject jsonObject){
        JsonArray objects = jsonObject.getJsonArray("objects");
        Graph graph = GraphTargetDB.getInstance().getGraph();
        for (int i = 0; i < objects.size(); i++) {
            int xObject = objects.getJsonObject(i).getInt("x")/ 32;
            int yObject = objects.getJsonObject(i).getInt("y")/ 32;
            for (int y = 0; y < objects.getJsonObject(i).getInt("height") /32; y++) {
                for (int x = 0; x < objects.getJsonObject(i).getInt("width") /32 ; x++) {
                    Node nodeToAdd =graph.getNodes()[yObject + y][xObject + x ];

                    if(nodeToAdd != null) {
                        GraphTargetDB.getInstance().addTarget(new Target(nodeToAdd));
                    }
                }
            }


        }
    }
}
