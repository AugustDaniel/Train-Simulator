package guiapplication.scheduleview.popups.change;

import data.*;
import guiapplication.ReturnableView;
import guiapplication.scheduleview.popups.SchedulePopupView;
import javafx.scene.Node;

public class ChangeWagonSetPopup extends SchedulePopupView {

    private Schedule schedule;
    public ChangeWagonSetPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.schedule = schedule;
    }

    @Override
    public Node getNode() {

        return null;
    }
}
