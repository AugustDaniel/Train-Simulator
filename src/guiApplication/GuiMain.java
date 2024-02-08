package guiApplication;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GuiMain extends Application {
    public static void main(String[] args) {
        launch(GuiMain.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new BorderPane();
        primaryStage.setScene(new Scene(pane));
        primaryStage.setTitle("Trein planner");
        primaryStage.show();
    }
}
