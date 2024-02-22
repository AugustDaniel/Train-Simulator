package guiapplication.scheduleView.popups;

import data.Schedule;
import guiapplication.PopupView;
import guiapplication.ReturnableView;
import javafx.scene.Node;

public class DeleteJourneyPopup extends PopupView {

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
