package guiapplication.schedulePlanner.Simulator.npc;

import data.Journey;
import data.ScheduleSubject;
import guiapplication.schedulePlanner.Simulator.mouselistener.MouseCallback;
import guiapplication.schedulePlanner.Simulator.Camera;
import guiapplication.schedulePlanner.Simulator.Clock;
import guiapplication.schedulePlanner.Simulator.pathfinding.PathFinding;
import guiapplication.schedulePlanner.Simulator.pathfinding.Target;
import javafx.scene.input.MouseEvent;
import org.jfree.fx.FXGraphics2D;
import util.graph.Node;

import java.util.*;

public class NPCController implements MouseCallback {
    private List<NPC> npcs = new ArrayList<>();
    private Clock clock;
    private ScheduleSubject subject;
    private Camera camera;
    private Queue<Map.Entry<Journey, Double>> journeysToSpawn;
    private double timer;

    public NPCController(Clock clock, ScheduleSubject subject, Camera camera) {
        this.subject = subject;
        this.clock = clock;
        this.camera = camera;
        this.journeysToSpawn = new ArrayDeque<>();
        this.timer = 0;
    }

    public void update(double deltaTime) {
        timer += deltaTime;

        if (timer > Double.MAX_VALUE - 100) {
            timer = 0;
        }

        this.subject.getSchedule().getJourneyList().forEach(journey -> {
                    if (journey.getArrivalTime().minusMinutes(30).equals(this.clock.getCurrentTime())) {
                        double timerEnd = timer + (double) journey.getTrainPopularity() / 10; //todo magic number for popularity
                        this.journeysToSpawn.offer(new AbstractMap.SimpleEntry<>(journey, timerEnd));
                    }
                }
        );

        updateNPCs();
        spawnNPCs();
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
                handleStatus(tr, Traveler.Status.LEAVING, new Target(PathFinding.getRandomSpawnPoint()));
                continue;
            }

            if (isArrivalTime(tr)) {
                handleStatus(tr, Traveler.Status.BOARDING, PathFinding.getRandomTrainTarget("Train " + tr.getJourney().getPlatform()));
            }
        }
    }

    private void handleStatus(Traveler tr, Traveler.Status status, Target target) {
        if (tr.getStatus() != status) {
            tr.setstatus(status);
            tr.setTarget(target);
        }
    }

    private void spawnNPCs() {
        if (this.journeysToSpawn.isEmpty()) { //todo magic number for spawn rate
            return;
        }

        Map.Entry<Journey, Double> journey = this.journeysToSpawn.peek();

        if (journey.getValue() >= timer) {
            Node spawnPoint = checkSpawnPoint(PathFinding.spawnPoints.get((int) (Math.random() * (PathFinding.spawnPoints.size() - 1))));
            npcs.add(new Traveler(spawnPoint, journey.getKey()));
        } else {
            this.journeysToSpawn.poll();
        }
    }

    private util.graph.Node checkSpawnPoint(util.graph.Node spawnPoint) {
        for (NPC npc : npcs) {
            if (npc.getPosition().distance(spawnPoint.getPosition()) <= npc.getImageSize()) {
                spawnPoint = checkSpawnPoint(PathFinding.spawnPoints.get((int) (Math.random() * (PathFinding.spawnPoints.size() - 1))));
            }
        }

        return spawnPoint;
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
}