package guiapplication.schedulePlanner;

import data.Schedule;
import data.ScheduleSubject;
import guiapplication.schedulePlanner.Simulator.MapView;
import guiapplication.schedulePlanner.Simulator.Slider;
import guiapplication.schedulePlanner.scheduleview.components.ScheduleView;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.jfree.fx.ResizableCanvas;

import java.io.IOException;

public class GuiMain extends Application {

    private ScheduleSubject subject;
    private ScheduleView scheduleView;
    private MapView mapView;
    private ResizableCanvas canvas;

    public static void main(String[] args) {
        launch(GuiMain.class);
    }

    @Override
    public void init() throws IOException {
        this.subject = new ScheduleSubject();
        this.subject.setState(new Schedule());
        this.scheduleView = new ScheduleView(this.subject);
        this.mapView = new MapView(this.subject, 50);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // create tabs
        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(new Tab("Trein tabel", scheduleView.getNode()));

        // Create simulation tab
        Tab simulationTab = new Tab("Simulatie", mapView.getNode());
        // Add slider to the simulation tab
        Slider peopleSlider = new Slider(mapView);
//        simulationTab.setContent(new VBox(peopleSlider, mapView.getNode()));
        VBox.setVgrow(mapView.getNode(), javafx.scene.layout.Priority.ALWAYS);
        simulationTab.setContent(new VBox(mapView.getNode(), peopleSlider));
        tabPane.getTabs().add(simulationTab);

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
