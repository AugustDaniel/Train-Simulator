package guiapplication.scheduleview.popups;

import data.Schedule;
import data.ScheduleBuilder;
import guiapplication.PopupView;
import guiapplication.ReturnableView;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class CreateWagonSetPopup extends PopupView {

    private Schedule schedule;
    private ScheduleBuilder scheduleBuilder;

    public CreateWagonSetPopup(ReturnableView mainView) {
        super(mainView);
        this.schedule = schedule;
        this.scheduleBuilder = new ScheduleBuilder(schedule);
    }

    @Override
    public Node getNode() {

        BorderPane pane = new BorderPane();


        Button cancelButton = new Button("Annuleer");
        cancelButton.setOnAction(e -> super.callMainView());

        Button saveButton = new Button("Voeg toe");
        saveButton.setOnAction(e -> {
            if () {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error, je bent data vergeten in te vullen");
                alert.showAndWait();
            } else {
                //TODO add correct parameters
                this.scheduleBuilder.();
                super.callMainView();
            }
        });

        FlowPane buttonBar = new FlowPane(cancelButton, saveButton);

        VBox inputBox = new VBox(idNumberWagonBox, wagonCapacityBox);
        pane.setCenter(inputBox);
        pane.setBottom(buttonBar);
        return pane;
    }
}
