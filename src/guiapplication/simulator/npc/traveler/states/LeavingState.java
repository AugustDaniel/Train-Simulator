package guiapplication.simulator.npc.traveler.states;

import guiapplication.simulator.npc.traveler.Traveler;
import guiapplication.simulator.pathfinding.PathFinding;
import guiapplication.simulator.pathfinding.Target;

import java.time.LocalTime;

public class LeavingState implements TravelerState {

    private Traveler traveler;

    public LeavingState(Traveler traveler) {
        this.traveler = traveler;
        traveler.setTarget(new Target(PathFinding.getRandomSpawnPoint()));
    }

    @Override
    public void handle(LocalTime time) {
        if (traveler.atTargetNode()) {
            traveler.setState(new FinishedState());
        }
    }

    @Override
    public String toString() {
        return "Verlaat het station";
    }
}
