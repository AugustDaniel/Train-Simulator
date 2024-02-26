package guiapplication.scheduleview.components;

import guiapplication.PopupView;
import guiapplication.ReturnableView;
import guiapplication.View;
import guiapplication.scheduleview.popups.create.*;
import guiapplication.scheduleview.popups.delete.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import data.ScheduleSubject;

public class ScheduleBuilderView implements View {

    private ScheduleSubject subject;
    private ReturnableView mainView;
    private MenuMode menuToggle;

    enum MenuMode {CREATE, DELETE}

    public ScheduleBuilderView(ScheduleSubject subject, ReturnableView mainView) {
        this.subject = subject;
        this.mainView = mainView;
        this.menuToggle = MenuMode.CREATE;
    }

    @Override
    public Node getNode() {
        VBox box = new VBox();

        switch (menuToggle) {
            case CREATE:
                box = getAddButtons();
                break;
            case DELETE:
                box = getDeleteButtons();
                break;
        }

        Button switchModeButton = getSwitchModeButton();

        box.getChildren().addAll(switchModeButton);
        box.setPadding(new Insets(10));
        box.setSpacing(10);
        box.setAlignment(Pos.BASELINE_LEFT);
        return box;
    }

    private Button getSwitchModeButton() {
        Button switchModeButton = new Button("Verander modus");
        switchModeButton.setOnAction(event -> {
            switch (menuToggle) {
                //todo maybe this needs to be better idk
                case CREATE:
                    menuToggle = MenuMode.DELETE;
                    break;
                case DELETE:
                    menuToggle = MenuMode.CREATE;
                    break;
            }
            this.mainView.returnToView();
        });
        return switchModeButton;
    }

    private VBox getAddButtons() {
        VBox box = new VBox(
                getButton("Creëer reis", new CreateJourneyPopup(this.mainView, this.subject.getSchedule())),
                getButton("Creëer trein", new CreateTrainPopup(this.mainView, this.subject.getSchedule())),
                getButton("Creëer wagon", new CreateWagonPopup(this.mainView, this.subject.getSchedule())),
                getButton("Creëer perron", new CreatePlatformPopup(this.mainView, this.subject.getSchedule()))
        );
        return box;
    }

    private VBox getDeleteButtons() {
        VBox box = new VBox(
                getButton("Verwijder reis", new DeleteJourneyPopup(this.mainView, this.subject.getSchedule())),
                getButton("Verwijder trein", new DeleteTrainPopup(this.mainView, this.subject.getSchedule())),
                getButton("Verwijder wagon", new DeleteWagonPopup(this.mainView, this.subject.getSchedule())),
                getButton("Verwijder perron", new DeletePlatformPopup(this.mainView, this.subject.getSchedule()))
        );
        return box;
    }

    private Node getButton(String text, PopupView popup) {
        Button button = new Button(text);
        button.setOnAction(e -> {
            this.mainView.setPopup(popup);
            this.mainView.returnToView();
        });

        return button;
    }
}
