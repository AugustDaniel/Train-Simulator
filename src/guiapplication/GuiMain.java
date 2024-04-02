package guiapplication;

import data.Schedule;
import data.ScheduleSubject;
import guiapplication.simulator.MapView;
import guiapplication.simulator.Slider;
import guiapplication.scheduleview.components.ScheduleView;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiMain extends Application {

    private ScheduleSubject subject;
    private ScheduleView scheduleView;
    private MapView mapView;

    public static void main(String[] args) {
        launch(GuiMain.class);
    }

    @Override
    public void init() throws IOException {
        subject = new ScheduleSubject();
        subject.setState(new Schedule());
        scheduleView = new ScheduleView(subject);
        mapView = new MapView(subject);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // create tabs
        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(new Tab("Trein tabel", scheduleView.getNode()));

        // Create simulation tab
        Tab simulationTab = new Tab("Simulatie", mapView.getNode());
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
        primaryStage.getIcons().add(new Image("spaceTrainIcon.jpg"));
        primaryStage.show();
    }

}
