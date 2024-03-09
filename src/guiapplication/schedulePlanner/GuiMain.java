package guiapplication.schedulePlanner;


import data.*;
import guiapplication.schedulePlanner.Simulator.MapView;
import guiapplication.schedulePlanner.Simulator.NPC;
import guiapplication.schedulePlanner.scheduleview.components.ScheduleView;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class GuiMain extends Application {

    private ScheduleSubject subject;
    private ScheduleView scheduleView;
    private MapView mapView;
    private ResizableCanvas canvas;

    public static void main(String[] args) {
        launch(GuiMain.class);
    }

    @Override
    public void init() {
        this.subject = new ScheduleSubject();
        this.subject.setState(new Schedule());
        this.scheduleView = new ScheduleView(this.subject);
        this.mapView = new MapView();
//        while(npcs.size() < 10) {
//            Point2D newPosition = new Point2D.Double(1900 + Math.random()*200, 1900 + Math.random()*200);
//            boolean hasCollision = false;
//            for (NPC visitor : npcs) {
//                if(visitor.getPosition().distance(newPosition) < 64)
//                    hasCollision = true;
//            }
//            if(!hasCollision) {
//                npcs.add(new NPC(newPosition, 0));
//                System.out.println("Toegevoegd");
//            }
//
//        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // create tabs
        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(new Tab("Trein tabel",scheduleView.getNode()));
        tabPane.getTabs().add(new Tab("Simulatie",mapView.getNode()));

        // to prevent flashing of screen when launching
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        // sets window to maximized
        primaryStage.setMaximized(true);

        // prevent removing of tabs
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // add tabs to scene and set stages parameters
        primaryStage.setScene(new Scene(tabPane));
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(900);
        primaryStage.setTitle("Trein planner");
        primaryStage.show();
    }

}
