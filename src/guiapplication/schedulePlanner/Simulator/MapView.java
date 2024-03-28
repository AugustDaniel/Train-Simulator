package guiapplication.schedulePlanner.Simulator;

import guiapplication.schedulePlanner.Simulator.mouselistener.MouseListener;
import guiapplication.schedulePlanner.Simulator.npc.NPCController;
import data.Journey;
import data.ScheduleSubject;
import guiapplication.schedulePlanner.Simulator.pathfinding.PathFinding;
import guiapplication.schedulePlanner.Simulator.tilehandlers.Map;
import guiapplication.schedulePlanner.View;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.ArrayList;

public class MapView implements View {

    private ScheduleSubject subject;
    private final Map map;
    private final ResizableCanvas canvas;
    private BorderPane mainPane;
    ArrayList<TrainEntity> trains = new ArrayList<>();
    private final Camera camera;
    private Clock clock;
    private NPCController npcController;
    private double tijd = 0;


    public MapView(ScheduleSubject subject) throws IOException {
        this.timeSpeed = 0.5;
        mainPane = new BorderPane();
        this.subject = subject;
        this.clock = new Clock(this.timeSpeed);
        this.canvas = new ResizableCanvas(this::draw, mainPane);
        this.camera = new Camera(canvas);
        this.map = new Map("/TrainStationPlannerMap.tmj");
        this.npcController = new NPCController(clock,subject,camera);

        MouseListener ml = new MouseListener(canvas);
        ml.addCallback(this.camera);
        ml.addCallback(this.npcController);
    }

    public void update(double deltaTime) {
        //dit zorgt ervoor dat die een fps limit heeft op ongeveer 60 fps
        tijd += deltaTime;
        if (tijd > 0.016){
            for (TrainEntity train : trains) {
                train.update();
            }
            clock.update(deltaTime);
            npcController.update(deltaTime);
            tijd = 0;
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
        init();
        return mainPane;
    }

    public void draw(FXGraphics2D g) {
        g.setBackground(Color.black);
        g.setTransform(new AffineTransform());
        g.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        g.setTransform(camera.getTransform());

        map.draw(g);

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

        npcController.draw(g);
    }

    public void init(){
        for (Journey journey : subject.getSchedule().getJourneyList()) {
            trains.add(new TrainEntity(journey,this.clock));
        }
    }

    public ScheduleSubject getSubject() {
        return subject;
    }

    public Clock getClock() {
        return clock;
    }
}
