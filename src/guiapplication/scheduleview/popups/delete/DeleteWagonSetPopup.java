package guiapplication.scheduleview.popups.delete;

import data.Schedule;
import data.ScheduleBuilder;
import guiapplication.ReturnableView;
import guiapplication.scheduleview.popups.SchedulePopupView;
import javafx.scene.Node;

public class DeleteWagonSetPopup extends SchedulePopupView {
    private Schedule schedule;
    private ScheduleBuilder scheduleBuilder;

    public DeleteWagonSetPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.schedule = schedule;
        this.scheduleBuilder = new ScheduleBuilder(schedule);
    }

    public Node getNode() {

        return null;
    }

}
