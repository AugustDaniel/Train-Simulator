package guiapplication.schedulePlanner.Simulator;

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
import java.util.ArrayList;

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
            npc.update(this.npcs);
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
            if(e.isShiftDown()) {
                npcs.clear();
                return;
            }

            int y = (int) (120 + Math.random()*8);
            int x = (int) (112 +  Math.random()*16);
            boolean hasCollision = false;
            for (NPC npc : npcs) {
                if (npc.getPosition().distance(new Point2D.Double(x* 32,y * 32)) <= npc.getImageSize()) {
                    hasCollision = true;
                }
            }
            if (!hasCollision) {
                npcs.add(new Traveler(GraphTargetDB.getInstance().getGraph().getNodes()[y][x], new Target(GraphTargetDB.getInstance().getGraph().getNodes()[30][120])));
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
    }
}
