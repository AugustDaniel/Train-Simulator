package guiapplication.scheduleview.components;

import guiapplication.PopupView;
import guiapplication.ReturnableView;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import data.ScheduleSubject;

public class ScheduleView extends ReturnableView {

    private ScheduleSubject schedule;
    private final BorderPane mainPane;
    private PopupView popup;

    public ScheduleView(ScheduleSubject schedule) {
        this.schedule = schedule;
        this.mainPane = new BorderPane();
        this.popup = null;
    }

    public void setPopup(PopupView popup) {
        this.popup = popup;
    }

    @Override
    public Node getNode() {
        this.returnToView();
        return this.mainPane;
    }

    @Override
    public void returnToView() {
        this.mainPane.getChildren().clear();
//        this.mainPane.setLeft(getButtons()); //todo fix
        // todo fileview
        this.mainPane.setCenter(new ScheduleTableView(schedule).getNode());
    }
}
