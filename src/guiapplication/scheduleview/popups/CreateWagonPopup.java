package guiapplication.scheduleview.popups;

import data.Schedule;
import guiapplication.ReturnableView;
import javafx.scene.Node;

public class CreateWagonPopup extends SchedulePopupView {

    private Schedule schedule;

    public CreateWagonPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.schedule = schedule;
    }

}
