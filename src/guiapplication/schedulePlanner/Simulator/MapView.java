package guiapplication.schedulePlanner.Simulator;

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
import java.util.ArrayList;

public class MapView implements View {

    private Map map;
    private ResizableCanvas canvas;
    ArrayList<NPC> npcs = new ArrayList<>();

    public void update(double deltaTime) {
        for (NPC npc : npcs) {
            npc.update(this.npcs);
        }
    }

    @Override
    public Node getNode() {
        BorderPane mainPane = new BorderPane();
        map = new Map("/TrainStationPlannerMap.tmj");
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
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
        canvas.setOnMouseClicked(event -> {
            npcs.add(new NPC(new Point2D.Double(event.getX(),event.getY()), 0));
        });
        canvas.setOnMouseMoved(event -> {
            for (NPC npc : npcs) {
                npc.setTargetPosition(new Point2D.Double(event.getX(), event.getY()));
            }
        });

        return mainPane;
        /*fixme de npcs werken soortvan want ze spawnen op de muis als je klikt alleen de astonout helm is iets te groot, dus die mmoet kleiner
       (fixme) worden. En ze lopen vloeiend ze happeren een beetje.  */
    }
    //todo je zou hier het kunnen plaatsen want dit is waar de tab gemaakt wordt voor de map

    public void draw(Graphics2D g) {
        g.setBackground(Color.black);
        g.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        map.draw(g);
        g.setTransform(new AffineTransform());
        for (NPC npc : npcs) {
            npc.draw(g);
        }
    }
}
