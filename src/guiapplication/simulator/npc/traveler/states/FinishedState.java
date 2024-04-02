package guiapplication.simulator.npc.traveler.states;

import java.time.LocalTime;

public class FinishedState implements TravelerState {

    @Override
    public void handle(LocalTime time) {
    }

    @Override
    public String toString() {
        return "Heeft het station verlaten";
    }
}
