package guiapplication.scheduleview.popups;

import data.*;
import guiapplication.PopupView;
import guiapplication.ReturnableView;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class DeleteTrainPopup extends PopupView {

    private Schedule schedule;
    private ScheduleBuilder scheduleBuilder;

    public DeleteTrainPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.schedule = schedule;
        this.scheduleBuilder = new ScheduleBuilder(schedule);
    }

    @Override
    public Node getNode() {
        BorderPane pane = new BorderPane();

        Label trainLabel = new Label("Kies uit de mogelijke treinen:");
        ComboBox<Train> trainComboBox = new ComboBox<>(FXCollections.observableList(this.schedule.getTrainList()));
        VBox trainBox = new VBox(trainLabel, trainComboBox);

        Button cancelButton = new Button("Annuleer");
        cancelButton.setOnAction(e -> super.callMainView());
        Button saveButton = new Button("Verwijder");
        saveButton.setOnAction(e -> {
            if (trainComboBox.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error, je bent data vergeten in te vullen");
                alert.showAndWait();
            } else {
                this.scheduleBuilder.deleteTrain(
                        trainComboBox.getValue()
                );
                super.callMainView();
            }
        });

        FlowPane buttonBar = new FlowPane(cancelButton, saveButton);

        VBox inputBox = new VBox(trainBox);
        pane.setCenter(inputBox);
        pane.setBottom(buttonBar);
        return pane;
    }
}
