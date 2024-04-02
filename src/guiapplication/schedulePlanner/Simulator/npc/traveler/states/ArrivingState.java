package guiapplication.schedulePlanner.Simulator.npc.traveler.states;

import guiapplication.schedulePlanner.Simulator.npc.traveler.Traveler;
import guiapplication.schedulePlanner.Simulator.pathfinding.PathFinding;

import java.time.LocalTime;

public class ArrivingState implements TravelerState {

    Traveler traveler;

    public ArrivingState(Traveler traveler) {
        this.traveler = traveler;
        this.traveler.setTarget(PathFinding.getRandomPlatformTarget(traveler.getJourney().getPlatform().getPlatformNumber()));
    }

    @Override
    public void handle(LocalTime time) {
        if (time.isAfter(this.traveler.getJourney().getArrivalTime())
                || time.equals(this.traveler.getJourney().getArrivalTime())) {
            this.traveler.setState(new BoardingState(this.traveler));
        }

        if (time.isAfter(this.traveler.getJourney().getDepartureTime())
                || time.equals(this.traveler.getJourney().getDepartureTime())) {
            this.traveler.setState(new LeavingState(this.traveler));
        }
    }

    @Override
    public String toString() {
        return "Wachtend op trein";
    }
}
