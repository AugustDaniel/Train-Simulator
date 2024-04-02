package guiapplication.simulator;

import guiapplication.simulator.measuring.MeasureController;
import guiapplication.simulator.mouselistener.MouseListener;
import guiapplication.simulator.npc.traveler.controller.TravelerController;
import data.Journey;
import data.ScheduleSubject;
import guiapplication.simulator.tilehandlers.Map;
import guiapplication.View;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class MapView implements View {

    private ScheduleSubject subject;
    private final Map map;
    private final ResizableCanvas canvas;
    private BorderPane mainPane;
    ArrayList<TrainEntity> trains = new ArrayList<>();
    private final Camera camera;
    private Clock clock;
    private TravelerController npcController;
    private MeasureController measureController;
    private double timer = 0;

    public MapView(ScheduleSubject subject) throws IOException {
        mainPane = new BorderPane();
        this.subject = subject;
        this.clock = new Clock(1);
        this.canvas = new ResizableCanvas(this::draw, mainPane);
        this.camera = new Camera(canvas);
        this.map = new Map("/TrainStationPlannerMap.tmj");
        this.npcController = new TravelerController(clock, subject, camera);
        this.measureController = new MeasureController(this.npcController.getNPCs(), this.camera, this.clock);

        MouseListener ml = new MouseListener(canvas);
        ml.addCallback(this.camera);
        ml.addCallback(this.npcController);
        ml.addCallback(this.measureController);
    }

    public void update(double deltaTime) {
        if (clock.getCurrentTime().equals(LocalTime.MIDNIGHT)) {
            trains.clear();
            for (Journey journey : subject.getSchedule().getJourneyList()) {
                trains.add(new TrainEntity(journey, clock));
            }
        }

        //dit zorgt ervoor dat die een fps limit heeft op ongeveer 144 fps
        timer += deltaTime * 144;
        if (timer >= 1) {
            for (TrainEntity train : trains) {
                train.update();
            }
            clock.update(deltaTime);
            npcController.update(deltaTime);
            measureController.update();
            timer -= 1;
        }
    }

    @Override
    public Node getNode() {
        Button simulationStart = new Button("Start simulatie");
        mainPane.setCenter(simulationStart);
        simulationStart.setOnAction(e -> {
                    mainPane.setCenter(null);
                    initMapNode();
                }
        );

        return mainPane;
    }

    public void initMapNode() {
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

        mainPane.setTop(null);

        Button distasterButton = new Button("Calamiteiten oefening");
        mainPane.setBottom(distasterButton);
        distasterButton.setOnAction(e -> npcController.toggleDisaster());

        Slider slider = new Slider(this);
        VBox.setVgrow(mainPane, javafx.scene.layout.Priority.ALWAYS);
        mainPane.setBottom(slider);

        draw(g2d);
        init();
    }

    public void draw(FXGraphics2D g) {
        g.setBackground(Color.black);
        g.setTransform(new AffineTransform());
        g.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        g.setTransform(camera.getTransform());

        map.draw(g);

        measureController.draw(g);
        npcController.draw(g);

        for (TrainEntity train : trains) {
            train.draw(g);
        }

        g.setTransform(new AffineTransform());
        clock.draw(g);
    }

    public void init() {
        for (Journey journey : subject.getSchedule().getJourneyList()) {
            trains.add(new TrainEntity(journey, clock));
        }
    }

    public void updateClock(double timeSpeed) {
        clock.updateTimeSpeed(timeSpeed);
    }

    public void updatePeopleCount(int newPeopleCount) {
        npcController.setSpawnRate(newPeopleCount);
    }
}
