package guiapplication.schedulePlanner.scheduleview.popups;

import data.Schedule;
import guiapplication.schedulePlanner.PopupView;
import guiapplication.schedulePlanner.ReturnableView;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

abstract public class SchedulePopupView extends PopupView {

    protected Schedule schedule;

    public SchedulePopupView(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.schedule = schedule;
    }

    @Override
    public Node getNode() {
        return new VBox(getCloseButton(), getSaveButton());
    }

    public Node getCloseButton() {
        Button closeButton = new Button("Annuleer");
        closeButton.setOnAction(e -> {
            super.setClosePopup(true);
            super.callMainView();
        });

        return closeButton;
    }

    public Node getSaveButton() {
        Button saveButton = new Button("Voeg toe");
        saveButton.setOnAction(e -> {
            super.callMainView();
        });

        return saveButton;
    }
}
