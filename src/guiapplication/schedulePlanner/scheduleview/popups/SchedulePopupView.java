package guiapplication.schedulePlanner.scheduleview.popups;

import guiapplication.schedulePlanner.scheduleview.PopupView;
import guiapplication.schedulePlanner.scheduleview.ReturnableView;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

abstract public class SchedulePopupView extends PopupView {

    public SchedulePopupView(ReturnableView mainView) {
        super(mainView);
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
