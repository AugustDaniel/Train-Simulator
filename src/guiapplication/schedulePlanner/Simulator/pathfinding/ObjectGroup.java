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
        Node[][] graphNodes = graph.getNodes();
       GraphTargetDB instanceGraph = GraphTargetDB.getInstance();
        for (int i = 0; i < objects.size(); i++) {
            int xObject = objects.getJsonObject(i).getInt("x")/ 32;
            int yObject = objects.getJsonObject(i).getInt("y")/ 32;
            for (int y = 0; y < objects.getJsonObject(i).getInt("height") /32; y+=2) {
                for (int x = 0; x < objects.getJsonObject(i).getInt("width") /32 ; x+=2) {
                    Node nodeToAdd = graphNodes[yObject + y][xObject + x ];

                    if(nodeToAdd != null) {
                        instanceGraph.addTarget(new Target(nodeToAdd));
                    }
                }
            }


        }
    }
}
