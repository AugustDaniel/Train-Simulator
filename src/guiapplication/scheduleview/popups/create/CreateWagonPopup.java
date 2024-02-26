package guiapplication.scheduleview.popups.create;

import data.*;
import guiapplication.PopupView;
import guiapplication.ReturnableView;
import guiapplication.scheduleview.popups.SchedulePopupView;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class CreateWagonPopup extends SchedulePopupView {

    private Schedule schedule;
    private ScheduleBuilder scheduleBuilder;

    public CreateWagonPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.schedule = schedule;
        this.scheduleBuilder = new ScheduleBuilder(schedule);
    }

    @Override
    public Node getNode() {
        BorderPane pane = new BorderPane();


        Label idNumberWagon = new Label("Voer het ID nummer van de wagon in:");
        TextField idNumberWagonInput = new TextField();
        VBox idNumberWagonBox = new VBox(idNumberWagon, idNumberWagonInput);

        Label wagonCapacity = new Label("Voer maximum capaciteit in:");
        TextField wagonCapacityInput = new TextField();
        VBox wagonCapacityBox = new VBox(wagonCapacity,wagonCapacityInput);

        Button cancelButton = new Button("Annuleer");
        cancelButton.setOnAction(e -> super.callMainView());

        Button saveButton = new Button("Voeg toe");
        saveButton.setOnAction(e -> {
            if (wagonCapacity.getText().isEmpty() || idNumberWagonInput.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error, je bent data vergeten in te vullen");
                alert.showAndWait();
            } else {
                //TODO add correct parameters
                this.scheduleBuilder.createWagon(
                        idNumberWagonInput.getText(),
                        Integer.parseInt(wagonCapacityInput.getText())
                        );
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
