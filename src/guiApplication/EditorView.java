package guiApplication;

import data.Schedule;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

public class EditorView implements View{
    private Schedule schedule;

    public EditorView(Schedule schedule) {
        this.schedule = schedule;
    }

    @Override
    public Node getNode() {
        return new Pane(new Label("Lorem ipsum")); //todo change view to right content
    }
}
