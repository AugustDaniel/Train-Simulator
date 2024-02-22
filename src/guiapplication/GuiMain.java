package guiapplication;


import data.*;
import guiapplication.scheduleView.ScheduleView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class GuiMain extends Application {

    private Schedule schedule;
    private ScheduleView scheduleView;
    private EditorView editorView;

    public static void main(String[] args) {
        launch(GuiMain.class);
    }

    @Override
    public void init() {
        this.schedule = new Schedule();
        this.schedule.addJourney(new Journey(10,
                10,
                new Train("36-fd"),
                new Platform(10))); // todo clear test
        this.schedule.addJourney(new Journey(11,
                11,
                new Train("123-r3"),
                new Platform(11))); // todo clear test
        this.scheduleView = new ScheduleView(this.schedule);
        this.editorView = new EditorView(this.schedule);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // create tabs
        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(new Tab("Rooster weergave",editorView.getNode()));
        tabPane.getTabs().add(new Tab("Trein tabel",scheduleView.getNode()));

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
