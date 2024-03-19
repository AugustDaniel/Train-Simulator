package guiapplication.schedulePlanner.Simulator;

import guiapplication.schedulePlanner.Simulator.pathfinding.Target;
import util.graph.Graph;

import java.util.ArrayList;
import java.util.List;

public class GraphTargetDB {

    private static GraphTargetDB instance = new GraphTargetDB();
    private List<Target> targets;
    private Graph graphs;

    private GraphTargetDB() {
        this.targets = new ArrayList<>();
        this.graphs = new Graph(128,128);
    }

    public static GraphTargetDB getInstance() {
        return instance;
    }

    public List<Target> getTargets() {
        return targets;
    }

    public void addTarget(Target target){
        this.targets.add(target);
    }

    public Graph getGraph() {
        return graphs;
    }

    public void setTargets(List<Target> targets) {
        this.targets = targets;
    }

    public void setGraphs(Graph graphs) {
        this.graphs = graphs;
    }
}
