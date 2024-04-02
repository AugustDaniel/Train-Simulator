package guiapplication.scheduleview.components;

import data.ScheduleSubject;
import guiapplication.PopupView;
import guiapplication.ReturnableView;
import guiapplication.View;
import guiapplication.scheduleview.popups.change.ChangeJourneyPopup;
import guiapplication.scheduleview.popups.change.ChangePlatformPopup;
import guiapplication.scheduleview.popups.change.ChangeTrainPopup;
import guiapplication.scheduleview.popups.change.ChangeWagonPopup;
import guiapplication.scheduleview.popups.create.*;
import guiapplication.scheduleview.popups.delete.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

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
        box.setSpacing(10);
        box.setAlignment(Pos.BASELINE_LEFT);
        return box;
    }

    private Button getSwitchModeButton() {
        Button switchModeButton = new Button("Verander modus");
        switchModeButton.setPrefWidth(150);
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
            mainView.returnToView();
        });
        return switchModeButton;
    }

    private VBox getAddButtons() {
        VBox box = new VBox(
                getButton("Creëer wagon", new CreateWagonPopup(mainView, subject.getSchedule())),
                getButton("Creëer wagon set", new CreateWagonSetPopup(mainView, subject.getSchedule())),
                getButton("Creëer trein", new CreateTrainPopup(mainView, subject.getSchedule())),
                getButton("Creëer perron", new CreatePlatformPopup(mainView, subject.getSchedule())),
                getButton("Creëer machinist", new CreateMachinistPopup(mainView, subject.getSchedule())),
                getButton("Creëer reis", new CreateJourneyPopup(mainView, subject.getSchedule()))
        );
        return box;
    }

    private VBox getDeleteButtons() {
        VBox box = new VBox(
                getButton("Verwijder wagon", new DeleteWagonPopup(mainView, subject.getSchedule())),
                getButton("verwijder wagon set", new DeleteWagonSetPopup(mainView, subject.getSchedule())),
                getButton("Verwijder trein", new DeleteTrainPopup(mainView, subject.getSchedule())),
                getButton("Verwijder perron", new DeletePlatformPopup(mainView, subject.getSchedule())),
                getButton("verwijder machinist", new DeleteMachinistPopup(mainView, subject.getSchedule())),
                getButton("Verwijder reis", new DeleteJourneyPopup(mainView, subject.getSchedule()))
        );
        return box;
    }

    private VBox getChangeButtons() {
        VBox box = new VBox(
                getButton("Verander wagon", new ChangeWagonPopup(mainView, subject.getSchedule())),
                getButton("Verander trein", new ChangeTrainPopup(mainView, subject.getSchedule())),
                getButton("Verander perron", new ChangePlatformPopup(mainView, subject.getSchedule())),
                getButton("Verander reis", new ChangeJourneyPopup(mainView, subject.getSchedule()))
        );
        return box;
    }

    private Node getButton(String text, PopupView popup) {
        Button button = new Button(text);
        button.setPrefWidth(150);
        button.setOnAction(e -> {
            mainView.setPopup(popup);
            mainView.returnToView();
        });

        return button;
    }
}
