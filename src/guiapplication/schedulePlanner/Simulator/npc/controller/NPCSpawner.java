package guiapplication.schedulePlanner.Simulator.npc.controller;

import data.Journey;
import guiapplication.schedulePlanner.Simulator.npc.NPC;
import guiapplication.schedulePlanner.Simulator.npc.Traveler;
import guiapplication.schedulePlanner.Simulator.pathfinding.PathFinding;
import util.graph.Node;

import java.util.*;

public class NPCSpawner {

    private final Queue<Map.Entry<Journey, Double>> journeysToSpawn;
    private final List<NPC> npcs;
    private int spawnRate;
    private double timer;

    public NPCSpawner(List<NPC> npcs) {
        this.npcs = npcs;
        this.journeysToSpawn = new ArrayDeque<>();
    }

    public void update(double deltaTime) {
        this.timer += deltaTime;

        if (timer > Double.MAX_VALUE - 100) {
            timer = 0;
        }

        spawnNPCs();
    }

    private void spawnNPCs() {
        if (this.journeysToSpawn.isEmpty()) {
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

    public void addToQueue(Journey journey) {
        double timerEnd = timer + (double) journey.getTrainPopularity() / (2001 - spawnRate); //todo magic number is max spawnrate in slider
        this.journeysToSpawn.offer(new AbstractMap.SimpleEntry<>(journey, timerEnd));
    }
}
