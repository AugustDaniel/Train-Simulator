package guiapplication.schedulePlanner.Simulator.npc;

import data.Schedule;
import data.ScheduleSubject;
import guiapplication.schedulePlanner.Simulator.Camera;
import guiapplication.schedulePlanner.Simulator.Clock;
import guiapplication.schedulePlanner.Simulator.pathfinding.PathFinding;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;
import util.graph.Node;

import java.util.ArrayList;
import java.util.List;

public class NPCController implements util.Observer {
    private List<NPC> npcs = new ArrayList<>();
    private Clock clock;
    private Schedule schedule;
    private ScheduleSubject subject;
    private double timer = 0;
    private int populairity = 5;

    public NPCController(Clock clock, ScheduleSubject subject, ResizableCanvas canvas, Camera camera) {
        this.subject = subject;
        this.subject.attach(this);
        this.schedule = subject.getSchedule();
        this.clock = clock;

        canvas.setOnMouseClicked(e -> {
                    for (NPC npc : npcs) {
                        if (npc.contains(camera.getWorldPos(e.getX(), e.getY()))) {
                            Traveler tr = (Traveler) npc;
                            tr.toggleClicked();
                            return;
                        }
                    }
                }
        );

        // todo
        Node spawnPoint = checkSpawnPoint(PathFinding.spawnPoints.get((int) (Math.random() * (PathFinding.spawnPoints.size() - 1))));
        npcs.add(new Traveler(spawnPoint, this.schedule.getJourneyList().get(0)));
    }

    public void update(double deltaTime) {
        timer += deltaTime;
        if (timer > (double) 10 / populairity) {
            npcs.add(new Traveler(PathFinding.spawnPoints.get((int) (Math.random() * (PathFinding.spawnPoints.size() - 1))), this.schedule.getJourneyList().get(0)));
            timer -= (double) 10 / populairity;
        }
        for (NPC npc : npcs) {
            npc.update(npcs);
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
}
