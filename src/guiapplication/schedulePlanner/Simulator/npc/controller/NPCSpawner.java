package guiapplication.schedulePlanner.Simulator.npc.controller;

import data.Journey;
import guiapplication.schedulePlanner.Simulator.Clock;
import guiapplication.schedulePlanner.Simulator.npc.NPC;
import guiapplication.schedulePlanner.Simulator.npc.Traveler;
import guiapplication.schedulePlanner.Simulator.pathfinding.PathFinding;
import util.graph.Node;

import java.util.*;

public class NPCSpawner implements util.Observer{

    private final Queue<Map.Entry<Journey, Integer>> journeysToSpawn;
    private final List<NPC> npcs;
    private final Clock clock;
    private int spawnRate;
    private int counter;
    private double delay;
    private double timer;
    private double npcSpeed;

    public NPCSpawner(List<NPC> npcs, Clock clock) {
        this.npcs = npcs;
        this.journeysToSpawn = new LinkedList<>();
        this.counter = 0;
        this.spawnRate = 50;
        this.npcSpeed = 1 / clock.getTimeSpeed();
        this.delay = clock.getTimeSpeed() / 10;
        this.clock = clock;
        this.clock.attach(this);
    }

    public void update(double deltaTime) {
        if (this.journeysToSpawn.isEmpty()) {
            return;
        }

        if (timer < delay) {
            timer += deltaTime;
            return;
        }

        timer -= delay;
        Map.Entry<Journey, Integer> journey = this.journeysToSpawn.peek();

        if (journey.getValue() > counter) {
            Node spawnPoint = checkSpawnPoint(PathFinding.spawnPoints.get((int) (Math.random() * (PathFinding.spawnPoints.size() - 1))));
            npcs.add(new Traveler(spawnPoint, journey.getKey(), npcSpeed));
            counter++;
        } else if (this.journeysToSpawn.size() > 1) {
            this.journeysToSpawn.poll();
            counter = 0;
        }
    }

    public void addToQueue(Journey journey) {
        if (this.journeysToSpawn.stream().anyMatch(e -> e.getKey().equals(journey))) {
            return;
        }

        int amountOfSpawns = (int) Math.ceil((journey.getTrainPopularity() * 10) * ((double) spawnRate / 100));
        this.journeysToSpawn.offer(new AbstractMap.SimpleEntry<>(journey, amountOfSpawns));
    }

    public util.graph.Node checkSpawnPoint(util.graph.Node spawnPoint) {
        for (NPC npc : npcs) {
            if (npc.getPosition().distance(spawnPoint.getPosition()) <= npc.getImageSize()) {
                spawnPoint = checkSpawnPoint(PathFinding.spawnPoints.get((int) (Math.random() * (PathFinding.spawnPoints.size() - 1))));
            }
        }

        return spawnPoint;
    }

    public void setSpawnRate(int newPeopleCount) {
        this.spawnRate = newPeopleCount;
    }

    @Override
    public void update() {
        this.delay = this.clock.getTimeSpeed() / 10;
        this.npcSpeed = 1 / this.clock.getTimeSpeed();
    }
}
