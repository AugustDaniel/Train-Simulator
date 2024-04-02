package guiapplication.schedulePlanner.scheduleview.popups.delete;

import data.Machinist;
import data.Platform;
import data.Schedule;
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

public class DeleteMachinistPopup extends SchedulePopupView {


    public DeleteMachinistPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView, schedule);
    }

    @Override
    public Node getNode() {
        BorderPane pane = new BorderPane();

        Label machinistLabel = new Label("Kies uit de mogelijke machinisten:");
        ComboBox<Machinist> machinistComboBox = new ComboBox<>(FXCollections.observableList(this.schedule.getMachinistsList()));
        VBox trainBox = new VBox(machinistLabel, machinistComboBox);

        Button saveButton = new Button("Verwijder");
        saveButton.setOnAction(e -> {
            if (machinistComboBox.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error, je bent data vergeten in te vullen");
                alert.showAndWait();
            } else {
                this.schedule.deleteMachinist(
                        machinistComboBox.getValue()
                );
                super.callMainView();
            }
        });

        FlowPane buttonBar = new FlowPane(super.getCloseButton(), saveButton);

        VBox inputBox = new VBox(trainBox);
        pane.setCenter(inputBox);
        pane.setBottom(buttonBar);
        return pane;
    }
}
