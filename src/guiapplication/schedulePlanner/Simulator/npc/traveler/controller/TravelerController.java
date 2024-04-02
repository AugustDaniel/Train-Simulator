package guiapplication.schedulePlanner.Simulator.npc.traveler.controller;

import data.ScheduleSubject;
import guiapplication.schedulePlanner.Simulator.Camera;
import guiapplication.schedulePlanner.Simulator.Clock;
import guiapplication.schedulePlanner.Simulator.Sounds;
import guiapplication.schedulePlanner.Simulator.mouselistener.MouseCallback;
import guiapplication.schedulePlanner.Simulator.npc.NPC;
import guiapplication.schedulePlanner.Simulator.npc.traveler.Traveler;
import guiapplication.schedulePlanner.Simulator.npc.traveler.states.ArrivingState;
import guiapplication.schedulePlanner.Simulator.npc.traveler.states.FinishedState;
import guiapplication.schedulePlanner.Simulator.npc.traveler.states.LeavingState;
import guiapplication.schedulePlanner.Simulator.npc.traveler.states.TravelerState;
import javafx.scene.input.MouseEvent;
import org.jfree.fx.FXGraphics2D;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TravelerController implements MouseCallback, util.Observer {
    private List<Traveler> travelers;
    private Clock clock;
    private ScheduleSubject subject;
    private Camera camera;
    private TravelerSpawner spawner;
    private boolean disaster;
    public TravelerController(Clock clock, ScheduleSubject subject, Camera camera) {
        this.travelers = new ArrayList<>();
        this.subject = subject;
        this.clock = clock;
        this.clock.attach(this);
        this.camera = camera;
        this.spawner = new TravelerSpawner(this.travelers, this.clock);
        this.disaster = false;
    }

    public void update(double deltaTime) {
        Sounds.disasterSound(disaster);

        if (!disaster) {
            this.subject.getSchedule().getJourneyList().forEach(journey -> {
                        if (journey.getArrivalTime().minusMinutes(30).equals(this.clock.getCurrentTime())) {
                            this.spawner.addToQueue(journey);
                        }
                    }
            );

            spawner.update(deltaTime);
        }

        updateNPCs();
    }

    private void updateNPCs() {
        Iterator<Traveler> iterator = travelers.iterator();

        while (iterator.hasNext()) {
            Traveler tr = iterator.next();
            tr.update(travelers);
            tr.handle(clock.getCurrentTime());

            if (tr.getState() instanceof FinishedState) {
                iterator.remove();
            }
        }
    }

    public void draw(FXGraphics2D g) {
        this.travelers.forEach(t -> t.draw(g));
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        if (!e.isPrimaryButtonDown()) {
            return;
        }

        for (NPC npc : travelers) {
            if (npc.contains(camera.getWorldPos(e.getX(), e.getY()))) {
                npc.toggleClicked();
                return;
            }
        }
    }

    public void setSpawnRate(int newPeopleCount) {
        this.spawner.setSpawnRate(newPeopleCount);
    }

    @Override
    public void update() {
        double speed = 1 / this.clock.getTimeSpeed();

        for (NPC npc : travelers) {
            npc.setStandardSpeed(speed);
        }
    }

    public List<Traveler> getNPCs() {
        return travelers;
    }

    public void toggleDisaster() {
        this.disaster = !this.disaster;

        if (disaster) {
            this.travelers.forEach(t -> t.setState(new LeavingState(t)));
        } else {
            this.travelers.forEach(t -> t.setState(new ArrivingState(t)));
        }
    }
}