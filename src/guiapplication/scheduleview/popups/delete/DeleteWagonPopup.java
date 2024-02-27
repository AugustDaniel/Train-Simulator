package guiapplication.scheduleview.popups.delete;

import data.*;
import guiapplication.PopupView;
import guiapplication.ReturnableView;
import guiapplication.scheduleview.popups.SchedulePopupView;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class DeleteWagonPopup extends SchedulePopupView {

    private Schedule schedule;

    public DeleteWagonPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.schedule = schedule;
    }

    @Override
    public Node getNode() {
        BorderPane pane = new BorderPane();

        Label trainInfo = new Label("Kies uit de mogelijke Wagons:");
        ComboBox<Wagon> platformComboBox = new ComboBox<>(FXCollections.observableList(this.schedule.getWagonList()));
        VBox trainBox = new VBox(trainInfo, platformComboBox);

        Button saveButton = new Button("Verwijder");
        saveButton.setOnAction(e -> {
            if (platformComboBox.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error, je bent data vergeten in te vullen");
                alert.showAndWait();
            } else {
                this.schedule.deleteWagon(
                        platformComboBox.getValue()
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
