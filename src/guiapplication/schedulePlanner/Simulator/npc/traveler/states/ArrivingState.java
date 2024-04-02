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
        if (time.isAfter(traveler.getJourney().getArrivalTime())
                || time.equals(traveler.getJourney().getArrivalTime())) {
            traveler.setState(new BoardingState(traveler));
        }

        if (time.isAfter(traveler.getJourney().getDepartureTime())
                || time.equals(traveler.getJourney().getDepartureTime())) {
            traveler.setState(new LeavingState(traveler));
        }
    }

    @Override
    public String toString() {
        return "Wachtend op trein";
    }
}
