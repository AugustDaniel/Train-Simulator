package guiApplication.scheduleView.popups;

import data.Schedule;
import guiApplication.PopupView;
import guiApplication.ReturnableView;
import javafx.scene.Node;

public class DeleteWagonPopup extends PopupView {

    private Schedule schedule;

    public DeleteWagonPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.schedule = schedule;
    }

    @Override
    public Node getNode() {
        return null;
    }
}
