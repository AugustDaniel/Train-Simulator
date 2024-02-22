package guiapplication.scheduleview.popups;

import data.Schedule;
import guiapplication.PopupView;
import guiapplication.ReturnableView;
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
