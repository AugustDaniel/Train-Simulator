package guiApplication.scheduleView.popups;

import data.Schedule;
import guiApplication.PopupView;
import guiApplication.ReturnableView;
import javafx.scene.Node;

public class CreateWagonPopup extends PopupView {

    private Schedule schedule;

    public CreateWagonPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.schedule = schedule;
    }

    @Override
    public Node getNode() {
        return null;
    }
}
