package guiApplication.scheduleView.popups;

import data.Schedule;
import guiApplication.PopupView;
import guiApplication.ReturnableView;
import javafx.scene.Node;

public class CreateTrainPopup extends PopupView {

    private Schedule schedule;

    public CreateTrainPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.schedule = schedule;
    }

    @Override
    public Node getNode() {
        return null;
    }
}
