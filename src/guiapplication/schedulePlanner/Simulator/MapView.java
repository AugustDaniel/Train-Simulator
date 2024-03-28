package guiapplication.schedulePlanner.Simulator;

import guiapplication.schedulePlanner.Simulator.npc.NPC;
import guiapplication.schedulePlanner.Simulator.npc.Traveler;
import guiapplication.schedulePlanner.Simulator.pathfinding.PathFinding;
import data.Journey;
import data.ScheduleSubject;
import guiapplication.schedulePlanner.Simulator.tilehandlers.Map;
import guiapplication.schedulePlanner.View;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.ArrayList;

public class MapView implements View {

    private ScheduleSubject subject;
    private final Map map;
    private final ResizableCanvas canvas;
    private BorderPane mainPane;
    ArrayList<NPC> npcs = new ArrayList<>();
    ArrayList<TrainEntity> trains = new ArrayList<>();
    private final Camera camera;
    private Clock clock;

    public MapView(ScheduleSubject subject) throws IOException {
        mainPane = new BorderPane();
        this.subject = subject;
        this.clock = new Clock(0.5);
        this.canvas = new ResizableCanvas(this::draw, mainPane);
        this.camera = new Camera(canvas);
        this.map = new Map("/TrainStationPlannerMap.tmj");
    }

    public void update(double deltaTime) {
        for (NPC npc : npcs) {
            npc.update(npcs);
        }
        for (TrainEntity train : trains) {
            train.update();
        }
        clock.update(deltaTime);
    }

    @Override
    public Node getNode() {
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        draw(g2d);

        canvas.setOnMouseClicked(e -> {
            if (e.isShiftDown()) {
                npcs.clear();
                return;
            }

            //todo npc debug
            for (NPC npc : npcs) {
                if (npc.contains(camera.getWorldPos(e.getX(), e.getY()))) {
                    Traveler tr = (Traveler) npc;
                    tr.toggleClicked();
                    return;
                }
            }

            util.graph.Node spawnPoint = checkCollision(PathFinding.spawnPoints.get((int) (Math.random() * (PathFinding.spawnPoints.size() - 1))));
            int size = PathFinding.targets.size();

            npcs.add(new Traveler(spawnPoint, PathFinding.targets.get((int) (Math.random() * size))));
        });
        init();
        return mainPane;
    }

    private util.graph.Node checkCollision(util.graph.Node spawnPoint) {
        for (NPC npc : npcs) {
            if (npc.getPosition().distance(spawnPoint.getPosition()) <= npc.getImageSize()) {
                spawnPoint = checkCollision(PathFinding.spawnPoints.get((int) (Math.random() * (PathFinding.spawnPoints.size() - 1))));
            }
        }

        return spawnPoint;
    }

    public void draw(FXGraphics2D g) {
        g.setBackground(Color.black);
        g.setTransform(new AffineTransform());
        g.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        g.setTransform(camera.getTransform());

        map.draw(g);

        for (NPC npc : npcs) {
            npc.draw(g);
        }

//        //todo target debug
//        for (Target target : PathFinding.targets) {
//            Point2D p = target.getNode().getPosition();
//            g.draw(new Rectangle2D.Double(p.getX() - 16, p.getY() - 16, 32 ,32)); //todo change magic number 16 = half tilesize 32 tilesize
//        }
//
//        //todo spawnpoint debug
//        for (util.graph.Node node : PathFinding.spawnPoints) {
//            Point2D p = node.getPosition();
//            g.draw(new Rectangle2D.Double(p.getX() - 16, p.getY() - 16, 32 ,32)); //todo change magic number 16 = half tilesize 32 tilesize
//        }

        for (TrainEntity train : trains) {
            train.draw(g);
        }

    }

    public void init(){
        for (Journey journey : subject.getSchedule().getJourneyList()) {
            trains.add(new TrainEntity(journey,this));
        }
    }

    public ScheduleSubject getSubject() {
        return subject;
    }

    public Clock getClock() {
        return clock;
    }
}
