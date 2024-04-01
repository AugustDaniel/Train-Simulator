package guiapplication.schedulePlanner.scheduleview.components;

import guiapplication.schedulePlanner.PopupView;
import guiapplication.schedulePlanner.ReturnableView;
import guiapplication.schedulePlanner.View;
import guiapplication.schedulePlanner.scheduleview.popups.change.*;
import guiapplication.schedulePlanner.scheduleview.popups.create.*;
import guiapplication.schedulePlanner.scheduleview.popups.delete.*;
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

    enum MenuMode {CREATE, DELETE, CHANGE}

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
            case CHANGE:
                box = getChangeButtons();
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
                case CREATE:
                    menuToggle = MenuMode.DELETE;
                    break;
                case DELETE:
                    menuToggle = MenuMode.CHANGE;
                    break;
                case CHANGE:
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
                getButton("Creëer perron", new CreatePlatformPopup(this.mainView, this.subject.getSchedule())),
                getButton("Creëer wagon set", new CreateWagonSetPopup(this.mainView, this.subject.getSchedule())),
                getButton("Creëer machinist", new CreateMachinistPopup(this.mainView, this.subject.getSchedule()))
        );
        return box;
    }

    private VBox getDeleteButtons() {
        VBox box = new VBox(
                getButton("Verwijder reis", new DeleteJourneyPopup(this.mainView, this.subject.getSchedule())),
                getButton("Verwijder trein", new DeleteTrainPopup(this.mainView, this.subject.getSchedule())),
                getButton("Verwijder wagon", new DeleteWagonPopup(this.mainView, this.subject.getSchedule())),
                getButton("Verwijder perron", new DeletePlatformPopup(this.mainView, this.subject.getSchedule())),
                getButton("verwijder wagon set", new DeleteWagonSetPopup(this.mainView, this.subject.getSchedule())),
                getButton("verwijder machinist", new DeleteMachinistPopup(this.mainView, this.subject.getSchedule()))

        );
        return box;
    }

    private VBox getChangeButtons() {
        VBox box = new VBox(
                getButton("Verander reis", new ChangeJourneyPopup(this.mainView, this.subject.getSchedule())),
                getButton("Verander trein", new ChangeTrainPopup(this.mainView, this.subject.getSchedule())),
                getButton("Verander wagon", new ChangeWagonPopup(this.mainView, this.subject.getSchedule())),
                getButton("Verander perron", new ChangePlatformPopup(this.mainView, this.subject.getSchedule()))
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
