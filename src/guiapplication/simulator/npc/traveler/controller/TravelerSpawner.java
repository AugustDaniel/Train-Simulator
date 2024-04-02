package guiapplication.simulator.npc.traveler.controller;

import data.Journey;
import guiapplication.simulator.Clock;
import guiapplication.simulator.npc.NPC;
import guiapplication.simulator.npc.traveler.Traveler;
import guiapplication.simulator.pathfinding.PathFinding;
import util.TimeFormatter;
import util.graph.Node;

import java.time.LocalTime;
import java.util.*;

public class TravelerSpawner implements util.Observer{

    private final Queue<Map.Entry<Journey, Integer>> journeysToSpawn;
    private final List<Traveler> npcs;
    private final Clock clock;
    private int spawnRate;
    private int counter;
    private double delay;
    private double timer;
    private double npcSpeed;
    private LocalTime previousTime;

    public TravelerSpawner(List<Traveler> npcs, Clock clock) {
        this.npcs = npcs;
        this.journeysToSpawn = new LinkedList<>();
        this.counter = 0;
        this.spawnRate = 50;
        this.npcSpeed = 1 / clock.getTimeSpeed();
        this.delay = clock.getTimeSpeed() / 10;
        this.clock = clock;
        this.clock.attach(this);
        this.previousTime = TimeFormatter.intToLocalTime(0);
    }

    public void update(double deltaTime) {
        if (timer < delay) {
            timer += deltaTime;
            return;
        }

        if (journeysToSpawn.isEmpty()) {
            return;
        }

        timer -= delay;
        Map.Entry<Journey, Integer> journey = journeysToSpawn.peek();

        if (journey.getValue() > counter) {
            Node spawnPoint = checkSpawnPoint(PathFinding.getRandomSpawnPoint());
            Traveler traveler = new Traveler(spawnPoint,journey.getKey(),npcSpeed);
            npcs.add(traveler);
            counter++;
        } else if (!clock.getCurrentTime().equals(previousTime)) {
            journeysToSpawn.poll();
            counter = 0;
        }

        previousTime = clock.getCurrentTime();
    }

    public void addToQueue(Journey journey) {
        if (journeysToSpawn.stream().anyMatch(e -> e.getKey().equals(journey))) {
            return;
        }

        int amountOfSpawns = (int) Math.ceil((journey.getTrain().getCapacity() * (journey.getTrainPopularity() / 10.0)) * (spawnRate / 100.0));
        journeysToSpawn.offer(new AbstractMap.SimpleEntry<>(journey, amountOfSpawns));
    }

    public util.graph.Node checkSpawnPoint(util.graph.Node spawnPoint) {
        for (NPC npc : npcs) {
            if (npc.getPosition().distance(spawnPoint.getPosition()) <= npc.getImageSize()) {
                spawnPoint = checkSpawnPoint(PathFinding.getRandomSpawnPoint());
            }
        }

        return spawnPoint;
    }

    public void setSpawnRate(int newPeopleCount) {
        spawnRate = newPeopleCount;
    }

    @Override
    public void update() {
        delay = clock.getTimeSpeed() / 10;
        npcSpeed = 1 / clock.getTimeSpeed();
    }
}
