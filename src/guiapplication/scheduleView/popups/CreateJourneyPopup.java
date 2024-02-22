package guiapplication.scheduleView.popups;

import data.Schedule;
import guiapplication.PopupView;
import guiapplication.ReturnableView;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class CreateJourneyPopup extends PopupView {
    private Schedule schedule;

    public CreateJourneyPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.schedule = schedule;
    }

    @Override
    public Node getNode() {
        BorderPane pane = new BorderPane();

        Button cancelButton = new Button("Annuleer");
        cancelButton.setOnAction(e -> super.callMainView());
        Button saveButton = new Button("Voeg toe");
        FlowPane buttonBar = new FlowPane(cancelButton,saveButton);

        Label testLabel = new Label("Lorem Ipsum");
        TextField testTextField = new TextField();
        VBox inputBox = new VBox(testLabel, testTextField);

        pane.setCenter(inputBox);
        pane.setBottom(buttonBar);
        return pane; //todo add functionality to this popup
    }
}
