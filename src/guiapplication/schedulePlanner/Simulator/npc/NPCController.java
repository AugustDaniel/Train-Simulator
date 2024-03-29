package guiapplication.schedulePlanner.Simulator.npc;

import data.Journey;
import data.Schedule;
import data.ScheduleSubject;
import guiapplication.schedulePlanner.Simulator.mouselistener.MouseCallback;
import guiapplication.schedulePlanner.Simulator.Camera;
import guiapplication.schedulePlanner.Simulator.Clock;
import guiapplication.schedulePlanner.Simulator.pathfinding.PathFinding;
import guiapplication.schedulePlanner.Simulator.pathfinding.Target;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import org.jfree.fx.FXGraphics2D;
import util.graph.Node;

import java.util.*;

public class NPCController implements util.Observer, MouseCallback {
    private List<NPC> npcs = new ArrayList<>();
    private Clock clock;
    private Schedule schedule;
    private ScheduleSubject subject;
    private Camera camera;
    private Queue<Map.Entry<Journey, Double>> journeysToSpawn;
    private double timer;

    public NPCController(Clock clock, ScheduleSubject subject, Camera camera) {
        this.subject = subject;
        this.subject.attach(this);
        this.schedule = subject.getSchedule();
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

        this.schedule.getJourneyList()
                .stream()
                .filter(journey -> journey.getArrivalTime().minusMinutes(30).equals(this.clock.getCurrentTime()))
                .forEach(journey -> this.journeysToSpawn.offer(new AbstractMap.SimpleEntry<>(journey, timer + (double) journey.getTrainPopularity() / 10))); //todo magic number for popularity

        updateNPCs();
        spawnNPCs();
    }

    private void updateNPCs() {
        Iterator<NPC> iterator = npcs.iterator();

        while (iterator.hasNext()) {
            NPC npc = iterator.next();
            npc.update(npcs);

            Traveler tr = (Traveler) npc;

            if (clock.getCurrentTime().isAfter(tr.getJourney().getDepartureTime())
                    || clock.getCurrentTime().equals(tr.getJourney().getDepartureTime())) {

                if (tr.isBoarding()) {
                    tr.setBoarding(false);
                    tr.setTarget(new Target(PathFinding.spawnPoints.get((int) (Math.random() * (PathFinding.spawnPoints.size() - 1)))));
                }

                if (npc.atTargetPosition()) {
                    iterator.remove();
                }

                continue;
            }

            if (clock.getCurrentTime().isAfter(tr.getJourney().getArrivalTime())
                    || clock.getCurrentTime().equals(tr.getJourney().getArrivalTime())) {

                if (!tr.isBoarding()) {
                    tr.setBoarding(true);
                    int size = PathFinding.trainTargets.get("Train " + tr.getJourney().getPlatform()).size();
                    tr.setTarget(PathFinding.trainTargets.get("Train " + tr.getJourney().getPlatform()).get((int) (Math.random() * size)));
                }

                if (npc.atTargetPosition()) {
                    iterator.remove();
                }
            }
        }
    }

    private void spawnNPCs() {
        if (this.journeysToSpawn.isEmpty())
                 { //todo magic number for spawn rate
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

    public void draw(FXGraphics2D g) {
        for (NPC npc : npcs) {
            npc.draw(g);
        }
    }

    @Override
    public void update() {
        this.schedule = this.subject.getSchedule();
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

    @Override
    public void onMouseReleased(MouseEvent e) {
        return;
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        return;
    }

    @Override
    public void onMouseScrolled(ScrollEvent e) {
        return;
    }
}