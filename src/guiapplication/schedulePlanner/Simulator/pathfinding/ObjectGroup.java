package guiapplication.schedulePlanner.Simulator.pathfinding;

import guiapplication.schedulePlanner.Simulator.GraphTargetDB;
import util.graph.Graph;

import javax.json.JsonArray;
import javax.json.JsonObject;

public class ObjectGroup {

    public ObjectGroup(JsonObject jsonObject){
        JsonArray objects = jsonObject.getJsonArray("objects");
        Graph graph = GraphTargetDB.getInstance().getGraph();
        for (int i = 0; i < objects.size(); i++) {

            int x = objects.getJsonObject(i).getInt("x")/ 32;
            int y = objects.getJsonObject(i).getInt("y")/ 32;
            GraphTargetDB.getInstance().addTarget(new Target(graph.getNodes()[y +1][x+1]));
        }
    }
}
