package guiapplication.scheduleview.popups;

import data.Schedule;
import guiapplication.ReturnableView;
import javafx.scene.Node;

public class DeleteJourneyPopup extends SchedulePopupView {

    private Schedule schedule;

    public DeleteJourneyPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.schedule = schedule;
    }

    @Override
    public Node getNode() {
        return null;
    }
}
