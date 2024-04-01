package guiapplication.schedulePlanner.Simulator.npc.traveler.states;

import java.time.LocalTime;

public interface TravelerState {

    void handle(LocalTime time);
}
