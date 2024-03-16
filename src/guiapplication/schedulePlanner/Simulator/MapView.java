package guiapplication.schedulePlanner.Simulator;

import data.ScheduleSubject;
import guiapplication.schedulePlanner.Simulator.tilehandlers.Map;
import guiapplication.schedulePlanner.View;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class MapView implements View {

    private ScheduleSubject subject;
    private final Map map;
    private final ResizableCanvas canvas;
    private final BorderPane mainPane = new BorderPane();
    ArrayList<NPC> npcs = new ArrayList<>();
    LinkedList<SpawnTrain> trains = new LinkedList<>();
    private final Camera camera;
    private Point2D worldMousePos;
    private Clock clock;

//    private BufferedImage image = ImageIO.read(this.getClass().getResourceAsStream("/astronautHelmet.png"));

    public MapView(ScheduleSubject subject) throws IOException {
        this.subject = subject;
        this.clock = new Clock(this.subject.getSchedule(), 1.0, this);
        this.canvas = new ResizableCanvas(this::draw, mainPane);
        this.camera = new Camera(canvas, this::draw, new FXGraphics2D(canvas.getGraphicsContext2D()));
        this.map = new Map("/TrainStationPlannerMap.tmj");
        Point2D nullpoint = new Point2D.Double(0, 0);
        this.worldMousePos = nullpoint;
    }

    public void update(double deltaTime) {
        for (NPC npc : npcs) {
            npc.update(this.npcs);
        }
        for (SpawnTrain train : trains) {
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
            worldMousePos = camera.getWorldPos(e.getX(), e.getY());
            npcs.add(new NPC(worldMousePos, 0));
        });
        canvas.setOnMouseMoved(e -> {
            for (NPC npc : npcs) {
                worldMousePos = camera.getWorldPos(e.getX(), e.getY());
                npc.setTargetPosition(worldMousePos);
            }
        });

        return mainPane;
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
        for (SpawnTrain train : trains) {
            train.draw(g);
        }
    }
}
