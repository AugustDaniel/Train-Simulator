package guiapplication.schedulePlanner.Simulator.npc.traveler.states;

import guiapplication.schedulePlanner.Simulator.npc.traveler.Traveler;
import guiapplication.schedulePlanner.Simulator.pathfinding.PathFinding;

import java.time.LocalTime;

public class ShoppingState implements TravelerState {

    private Traveler traveler;

    public ShoppingState(Traveler traveler) {
        this.traveler = traveler;
        this.traveler.setTarget(PathFinding.getRandomShoppingTarget());
    }

    @Override
    public void handle(LocalTime time) {
        if (time.isAfter(this.traveler.getJourney().getArrivalTime().minusMinutes(10))) {
            this.traveler.setState(new ArrivingState(this.traveler));
        }

        if (time.isAfter(this.traveler.getJourney().getDepartureTime())
                || time.equals(this.traveler.getJourney().getDepartureTime())) {
            this.traveler.setState(new LeavingState(this.traveler));
        }
    }

    @Override
    public String toString() {
        return "Winkellen";
    }
}
