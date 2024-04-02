package guiapplication.schedulePlanner.Simulator.npc.traveler.states;

import guiapplication.schedulePlanner.Simulator.npc.traveler.Traveler;
import guiapplication.schedulePlanner.Simulator.pathfinding.PathFinding;

import java.time.LocalTime;

public class BoardingState implements TravelerState {

    private Traveler traveler;

    public BoardingState(Traveler traveler) {
        this.traveler = traveler;
        traveler.setTarget(PathFinding.getRandomTrainTarget(traveler.getJourney().getPlatform().getPlatformNumber()));
    }

    @Override
    public void handle(LocalTime time) {
        if (time.isAfter(this.traveler.getJourney().getDepartureTime())
                || time.equals(this.traveler.getJourney().getDepartureTime())) {
            this.traveler.setState(new LeavingState(this.traveler));
        }

        if (this.traveler.atTargetNode()) {
            this.traveler.setState(new FinishedState());
        }
    }

    @Override
    public String toString() {
        return "Betreed trein";
    }
}
