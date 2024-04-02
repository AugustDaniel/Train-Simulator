package guiapplication.simulator.npc.traveler.states;

import guiapplication.simulator.npc.traveler.Traveler;
import guiapplication.simulator.pathfinding.PathFinding;

import java.time.LocalTime;

public class ShoppingState implements TravelerState {

    private Traveler traveler;

    public ShoppingState(Traveler traveler) {
        this.traveler = traveler;
        this.traveler.setTarget(PathFinding.getRandomShoppingTarget());
    }

    @Override
    public void handle(LocalTime time) {
        if (time.isAfter(traveler.getJourney().getArrivalTime().minusMinutes(10))) {
            traveler.setState(new ArrivingState(traveler));
        }

        if (time.isAfter(traveler.getJourney().getDepartureTime())
                || time.equals(traveler.getJourney().getDepartureTime())) {
            traveler.setState(new LeavingState(traveler));
        }
    }

    @Override
    public String toString() {
        return "Winkellen";
    }
}
