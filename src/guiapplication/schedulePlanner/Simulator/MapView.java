package guiapplication.schedulePlanner.Simulator;

import guiapplication.schedulePlanner.Simulator.pathfinding.PathFinding;
import guiapplication.schedulePlanner.Simulator.pathfinding.Target;
import guiapplication.schedulePlanner.Simulator.tilehandlers.Map;
import guiapplication.schedulePlanner.View;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;

public class MapView implements View {

    private Map map;
    private ResizableCanvas canvas;
    private BorderPane mainPane;
    ArrayList<NPC> npcs = new ArrayList<>();
    private Camera camera;


    public MapView() throws IOException {
        mainPane = new BorderPane();
        canvas = new ResizableCanvas(this::draw, mainPane);
        camera = new Camera(canvas, this::draw, new FXGraphics2D(canvas.getGraphicsContext2D()));
        map = new Map("/TrainStationPlannerMap.tmj");
    }

    public void update(double deltaTime) {
        for (NPC npc : npcs) {
            npc.update(npcs);
        }
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

            boolean hasCollision = false;
            util.graph.Node spawnPoint = PathFinding.spawnPoints.get((int) (Math.random() * (PathFinding.spawnPoints.size() - 1)));

            for (NPC npc : npcs) {
                if (npc.getPosition().distance(spawnPoint.getPosition()) <= npc.getImageSize()) {
                    hasCollision = true;
                }
            }

            if (!hasCollision) {
                int size = PathFinding.targets.size();
                npcs.add(new Traveler(spawnPoint, PathFinding.targets.get((int) (Math.random() * size))));
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

        if (this.npcs.size() == 1) {
            Traveler tr = (Traveler) this.npcs.get(0);
            tr.debugDraw(g);
        }
    }
}
