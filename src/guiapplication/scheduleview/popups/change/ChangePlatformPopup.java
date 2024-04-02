package guiapplication.scheduleview.popups.change;

import data.Platform;
import data.Schedule;
import guiapplication.ReturnableView;
import guiapplication.scheduleview.popups.SchedulePopupView;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class ChangePlatformPopup extends SchedulePopupView {

    public ChangePlatformPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView, schedule);
        this.schedule = schedule;
    }

    @Override
    public Node getNode() {
        BorderPane pane = new BorderPane();

        Label platformSelectionLable = new Label("Kies uit perrons:");
        ComboBox<Platform> platformSelectionComboBox = new ComboBox<>(FXCollections.observableList(schedule.getPlatformList()));
        VBox platformSelectionBox = new VBox(platformSelectionLable, platformSelectionComboBox);

        Label toChangeLabel = new Label("waar wilt u het nummer naar veranderen?:");
        TextField toChangeTextField = new TextField();
        VBox toChangeBox = new VBox(toChangeLabel, toChangeTextField);

        Button saveButton = new Button("Voeg toe");
        saveButton.setOnAction(e -> {
            if (platformSelectionComboBox.getSelectionModel().isEmpty() || toChangeTextField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error, je bent data vergeten in te vullen");
                alert.showAndWait();
            } else {
                schedule.getPlatformList().get(
                        schedule.getPlatformList().indexOf(platformSelectionComboBox.getValue())
                ).setPlatformNumber(Integer.parseInt(toChangeTextField.getText()));
                super.callMainView();
            }
        });

        FlowPane buttonBar = new FlowPane(super.getCloseButton(), saveButton);

        VBox inputBox = new VBox(platformSelectionBox, toChangeBox);
        pane.setCenter(inputBox);
        platformSelectionComboBox.setOnAction((event) -> {

        });

        pane.setBottom(buttonBar);
        return pane;
    }
}
