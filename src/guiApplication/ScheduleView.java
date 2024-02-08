package guiApplication;


import data.Schedule;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

public class ScheduleView extends TableView<Schedule> {

    public ScheduleView() {
        getNode();
    }

    public Node getNode() {
        return new Pane(new Label("Lorem ipsum")); //todo change view to right content
    }
}
