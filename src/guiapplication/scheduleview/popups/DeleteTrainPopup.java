package guiapplication.scheduleview.popups;

import data.Schedule;
import guiapplication.ReturnableView;
import javafx.scene.Node;

public class DeleteTrainPopup extends SchedulePopupView {

    private Schedule schedule;

    public DeleteTrainPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.schedule = schedule;
    }
}
