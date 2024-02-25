package guiapplication.scheduleview.popups;

import data.Journey;
import data.Platform;
import data.Schedule;
import data.Train;
import guiapplication.PopupView;
import guiapplication.ReturnableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class DeleteJourneyPopup extends PopupView {

    private Schedule schedule;

    public DeleteJourneyPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.schedule = schedule;
    }

    @Override
    public Node getNode() {
        BorderPane pane = new BorderPane();

        Label trainInfo = new Label("Kies uit trein:");
        ComboBox<Journey> trainComboBox = new ComboBox<>(FXCollections.observableList(this.schedule.getJourneyList()));
        VBox trainBox = new VBox(trainInfo, trainComboBox);

        Button cancelButton = new Button("Annuleer");
        cancelButton.setOnAction(e -> super.callMainView());
        Button saveButton = new Button("Voeg toe");
        saveButton.setOnAction(e -> {
            if () {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error, je bent data vergeten in te vullen");
                alert.showAndWait();
            } else {
                this.scheduleBuilder.;
                super.callMainView();
            }
        });

        FlowPane buttonBar = new FlowPane(cancelButton, saveButton);

        VBox inputBox = new VBox(arrivalTimeBox, departureTimeBox, trainBox, platformBox);
        pane.setCenter(inputBox);
        pane.setBottom(buttonBar);
        return pane;
    }
}
