package guiapplication.simulator.npc.traveler.controller;

import data.Journey;
import data.ScheduleSubject;
import guiapplication.simulator.Camera;
import guiapplication.simulator.Clock;
import guiapplication.simulator.Sounds;
import guiapplication.simulator.mouselistener.MouseCallback;
import guiapplication.simulator.npc.NPC;
import guiapplication.simulator.npc.traveler.Traveler;
import guiapplication.simulator.npc.traveler.states.ArrivingState;
import guiapplication.simulator.npc.traveler.states.FinishedState;
import guiapplication.simulator.npc.traveler.states.LeavingState;
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
            for (Journey journey : subject.getSchedule().getJourneyList()) {
                if (journey.getArrivalTime().minusMinutes(30).equals(clock.getCurrentTime())) {
                    spawner.addToQueue(journey);
                }
            }

            spawner.update(deltaTime);
        }

        updateNPCs();
    }

    private void updateNPCs() {
        Iterator<Traveler> iterator = travelers.iterator();

        while (iterator.hasNext()) {
            Traveler traveler = iterator.next();
            traveler.update(travelers);
            traveler.handle(clock.getCurrentTime());

            if (traveler.getState() instanceof FinishedState) {
                iterator.remove();
            }
        }
    }

    public void draw(FXGraphics2D g) {
        for (Traveler traveler : travelers) {
            traveler.draw(g);
        }
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        if (!e.isPrimaryButtonDown()) {
            return;
        }

        for (Traveler traveler : travelers) {
            if (traveler.contains(camera.getWorldPos(e.getX(), e.getY()))) {
                traveler.toggleClicked();
                return;
            }
        }
    }

    public void setSpawnRate(int newPeopleCount) {
        spawner.setSpawnRate(newPeopleCount);
    }

    @Override
    public void update() {
        double speed = 1 / clock.getTimeSpeed();

        for (Traveler traveler : travelers) {
            traveler.setStandardSpeed(speed);
        }
    }

    public List<Traveler> getNPCs() {
        return travelers;
    }

    public void toggleDisaster() {
        disaster = !disaster;

        if (disaster) {
            for (Traveler traveler : travelers) {
                traveler.setState(new LeavingState(traveler));
            }
        } else {
            for (Traveler traveler : travelers) {
                traveler.setState(new ArrivingState(traveler));
            }
        }
    }
}