package guiapplication.schedulePlanner;


import data.*;
import guiapplication.schedulePlanner.Simulator.MapView;
import guiapplication.schedulePlanner.scheduleview.components.ScheduleView;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GuiMain extends Application {

    private ScheduleSubject subject;
    private ScheduleView scheduleView;
    private MapView mapView;

    public static void main(String[] args) {
        launch(GuiMain.class);
    }

    @Override
    public void init() {
        this.subject = new ScheduleSubject();
        this.subject.setState(new Schedule());
        this.scheduleView = new ScheduleView(this.subject);
        this.mapView = new MapView();
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