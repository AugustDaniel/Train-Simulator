package guiapplication.scheduleview.popups.change;

import data.*;
import guiapplication.ReturnableView;
import guiapplication.scheduleview.popups.SchedulePopupView;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class ChangeTrainPopup extends SchedulePopupView {

    public ChangeTrainPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView, schedule);
        this.schedule = schedule;
    }

    @Override
    public Node getNode() {
        BorderPane pane = new BorderPane();

        Label platformSelectionLable = new Label("Kies uit train:");
        ComboBox<Train> platformSelectionComboBox = new ComboBox<>(FXCollections.observableList(this.schedule.getTrainList()));
        VBox platformSelectionBox = new VBox(platformSelectionLable, platformSelectionComboBox);

        Label toChangeLabel = new Label("waar wilt u het nummer naar veranderen?:");
        ComboBox<List<Wagon>> toChangeTextField = new ComboBox<>(FXCollections.observableList(schedule.getWagonSetList()));
        VBox toChangeBox = new VBox(toChangeLabel, toChangeTextField);

        Button saveButton = new Button("Voeg toe");
        saveButton.setOnAction(e -> {
            if (platformSelectionComboBox.getSelectionModel().isEmpty() || toChangeTextField.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error, je bent data vergeten in te vullen");
                alert.showAndWait();
            } else {
                schedule.getTrainList().get(
                        schedule.getTrainList().indexOf(platformSelectionComboBox.getValue())).setWagonList(toChangeTextField.getValue());
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
