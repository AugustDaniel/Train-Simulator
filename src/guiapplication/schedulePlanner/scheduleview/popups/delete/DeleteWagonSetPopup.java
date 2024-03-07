package guiapplication.schedulePlanner.scheduleview.popups.delete;

import data.*;
import guiapplication.schedulePlanner.ReturnableView;
import guiapplication.schedulePlanner.scheduleview.popups.SchedulePopupView;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class DeleteWagonSetPopup extends SchedulePopupView {
    private Schedule schedule;
    public DeleteWagonSetPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.schedule = schedule;
    }

    public Node getNode() {
        BorderPane pane = new BorderPane();

        Label trainInfo = new Label("Kies uit de mogelijke Wagons sets:");
        ComboBox<List<Wagon>> platformComboBox = new ComboBox<>(FXCollections.observableList(this.schedule.getWagonSetList()));
        VBox wagonSetBox = new VBox(trainInfo, platformComboBox);

        Button saveButton = new Button("Verwijder");
        saveButton.setOnAction(e -> {
            if (platformComboBox.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error, je bent data vergeten in te vullen");
                alert.showAndWait();
            } else {
                this.schedule.deleteWagonSet(
                        platformComboBox.getValue()
                );
                super.callMainView();
            }
        });

        FlowPane buttonBar = new FlowPane(super.getCloseButton(), saveButton);

        VBox inputBox = new VBox(wagonSetBox);
        pane.setCenter(inputBox);
        pane.setBottom(buttonBar);
        return pane;
    }

}
