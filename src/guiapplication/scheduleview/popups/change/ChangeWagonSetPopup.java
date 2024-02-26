package guiapplication.scheduleview.popups.change;

import data.Schedule;
import data.ScheduleBuilder;
import guiapplication.ReturnableView;
import guiapplication.scheduleview.popups.SchedulePopupView;
import javafx.scene.Node;

public class ChangeWagonSetPopup extends SchedulePopupView {

    private Schedule schedule;
    private ScheduleBuilder scheduleBuilder;
    public ChangeWagonSetPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.schedule = schedule;
        this.scheduleBuilder = new ScheduleBuilder(schedule);
    }

    @Override
    public Node getNode() {

        return null;
    }
}
