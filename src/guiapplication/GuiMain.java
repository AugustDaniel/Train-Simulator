package guiapplication;


import data.*;
import guiapplication.scheduleview.components.ScheduleView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class GuiMain extends Application {

    private Schedule schedule;
    private ScheduleView scheduleView;

    public static void main(String[] args) {
        launch(GuiMain.class);
    }

    @Override
    public void init() {
        this.schedule = new Schedule();
//        this.scheduleView = new ScheduleView(this.schedule);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // create tabs
        TabPane tabPane = new TabPane();
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
