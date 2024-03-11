package guiapplication.schedulePlanner.Simulator;

import guiapplication.schedulePlanner.Simulator.tilehandlers.Map;
import guiapplication.schedulePlanner.View;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.function.BiFunction;

public class MapView implements View {

    private Map map;
    private ResizableCanvas canvas;
    private BorderPane mainPane;
    ArrayList<NPC> npcs = new ArrayList<>();
    private Camera camera;
    private Point2D screenMousePos;
    private Point2D worldMousePos;
    private Point2D distance;

    public MapView() {
        mainPane = new BorderPane();
        canvas = new ResizableCanvas(this::draw, mainPane);
        camera = new Camera(canvas, this::draw, new FXGraphics2D(canvas.getGraphicsContext2D()));
        map = new Map("/TrainStationPlannerMap.tmj");
        Point2D nullpoint = new Point2D.Double(0, 0);
        worldMousePos = nullpoint;
        screenMousePos = nullpoint;
        distance = nullpoint;
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
            worldMousePos = getWorldPos(e.getX(), e.getY());
            screenMousePos = new Point2D.Double(e.getX() / camera.getZoom(), e.getY() / camera.getZoom());
            npcs.add(new NPC(screenMousePos, 0));
        });
        canvas.setOnMouseMoved(e -> {
            for (NPC npc : npcs) {
                worldMousePos = getWorldPos(e.getX(), e.getY());
                screenMousePos = new Point2D.Double(e.getX() / camera.getZoom(), e.getY() / camera.getZoom());
                npc.setTargetPosition(screenMousePos);

            }
        });

        return mainPane;
        /*fixme de npcs werken soortvan want ze spawnen op de muis als je klikt alleen de astonout helm is iets te groot, dus die mmoet kleiner
       (fixme) worden. En ze lopen vloeiend ze happeren een beetje.  */
    }
    //todo je zou hier het kunnen plaatsen want dit is waar de tab gemaakt wordt voor de map

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

    private Point2D getDistancePoint(BiFunction<Double, Double, Double> operator, Point2D i, Point2D j) {
        return new Point2D.Double(operator.apply(i.getX(), j.getX()), operator.apply(i.getY(), j.getY()));
    }

    private Point2D getWorldPos(double x, double y) {
        try {
            return camera.getTransform().inverseTransform(new Point2D.Double(x, y), null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
