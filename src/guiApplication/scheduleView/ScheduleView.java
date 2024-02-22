package guiApplication.scheduleView;


import data.Schedule;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class ScheduleView {

    private Schedule schedule;

    public ScheduleView(Schedule schedule) {
        this.schedule = schedule;
    }

    public Node getNode() {
        return new Pane(new Label("Lorem ipsum")); //todo change view to right content
    }
}
