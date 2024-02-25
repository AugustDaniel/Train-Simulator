package guiapplication.scheduleview.popups;

import data.Schedule;
import guiapplication.ReturnableView;
import javafx.scene.Node;

public class DeleteWagonPopup extends SchedulePopupView {

    private Schedule schedule;

    public DeleteWagonPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.schedule = schedule;
    }

}
