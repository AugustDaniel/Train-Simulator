package guiapplication.schedulePlanner.Simulator.npc.controller;

import data.ScheduleSubject;
import guiapplication.schedulePlanner.Simulator.mouselistener.MouseCallback;
import guiapplication.schedulePlanner.Simulator.Camera;
import guiapplication.schedulePlanner.Simulator.Clock;
import guiapplication.schedulePlanner.Simulator.npc.NPC;
import guiapplication.schedulePlanner.Simulator.npc.Traveler;
import guiapplication.schedulePlanner.Simulator.pathfinding.PathFinding;
import guiapplication.schedulePlanner.Simulator.pathfinding.Target;
import javafx.scene.input.MouseEvent;
import org.jfree.fx.FXGraphics2D;

import java.util.*;

public class NPCController implements MouseCallback, util.Observer {
    private List<NPC> npcs;
    private Clock clock;
    private ScheduleSubject subject;
    private Camera camera;
    private NPCSpawner spawner;

    public NPCController(Clock clock, ScheduleSubject subject, Camera camera) {
        this.npcs = new ArrayList<>();
        this.subject = subject;
        this.clock = clock;
        this.clock.attach(this);
        this.camera = camera;
        this.spawner = new NPCSpawner(this.npcs);
    }

    public void update(double deltaTime) {
        this.subject.getSchedule().getJourneyList().forEach(journey -> {
                    if (journey.getArrivalTime().minusMinutes(30).equals(this.clock.getCurrentTime())) {
                        this.spawner.addToQueue(journey);
                    }
                }
        );

        updateNPCs();
        spawner.update(deltaTime);
    }

    private void updateNPCs() {
        Iterator<NPC> iterator = npcs.iterator();

        while (iterator.hasNext()) {
            NPC npc = iterator.next();
            npc.update(npcs);

            Traveler tr = (Traveler) npc;
            if (npc.atTargetPosition() &&
                    (tr.getStatus() == Traveler.Status.BOARDING || tr.getStatus() == Traveler.Status.LEAVING)) {
                iterator.remove();
            }

            if (isDepartureTime(tr)) {
                handleStatus(tr, Traveler.Status.LEAVING);
                continue;
            }

            if (isArrivalTime(tr)) {
                handleStatus(tr, Traveler.Status.BOARDING);
            }
        }
    }

    private void handleStatus(Traveler tr, Traveler.Status status) {
        if (tr.getStatus() != status) {
            tr.setStatus(status);

            Target target = null;
            switch (status) {
                case BOARDING: target = PathFinding.getRandomTrainTarget("Train " + tr.getJourney().getPlatform()); break;
                case LEAVING: target = new Target(PathFinding.getRandomSpawnPoint()); break;
            }

            tr.setTarget(target);
        }
    }

    private boolean isDepartureTime(Traveler tr) {
        return clock.getCurrentTime().isAfter(tr.getJourney().getDepartureTime())
                || clock.getCurrentTime().equals(tr.getJourney().getDepartureTime());
    }

    private boolean isArrivalTime(Traveler tr) {
        return clock.getCurrentTime().isAfter(tr.getJourney().getArrivalTime())
                || clock.getCurrentTime().equals(tr.getJourney().getArrivalTime());
    }

    public void draw(FXGraphics2D g) {
        for (NPC npc : npcs) {
            npc.draw(g);
        }
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        if (!e.isPrimaryButtonDown()) {
            return;
        }

        for (NPC npc : npcs) {
            if (npc.contains(camera.getWorldPos(e.getX(), e.getY()))) {
                Traveler tr = (Traveler) npc;
                tr.toggleClicked();
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

        for (NPC npc : npcs) {
            npc.setStandardSpeed(speed);
        }
    }
}