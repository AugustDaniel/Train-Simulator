package guiapplication.schedulePlanner.Simulator.npc.traveler.states;

import guiapplication.schedulePlanner.Simulator.npc.traveler.Traveler;
import guiapplication.schedulePlanner.Simulator.pathfinding.PathFinding;
import guiapplication.schedulePlanner.Simulator.pathfinding.Target;

import java.time.LocalTime;

public class LeavingState implements TravelerState{

    private Traveler traveler;

    public LeavingState(Traveler traveler) {
        this.traveler = traveler;
        traveler.setTarget(new Target(PathFinding.getRandomSpawnPoint()));
    }

    @Override
    public void handle(LocalTime time) {
        if (this.traveler.atTargetPosition()) {
            this.traveler.setState(new FinishedState());
        }
    }

    @Override
    public String toString() {
        return "Verlaat het station";
    }
}
