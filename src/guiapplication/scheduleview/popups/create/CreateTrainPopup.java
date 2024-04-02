package guiapplication.scheduleview.popups.create;

import data.Schedule;
import data.Train;
import data.Wagon;
import guiapplication.ReturnableView;
import guiapplication.scheduleview.popups.SchedulePopupView;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.List;


public class CreateTrainPopup extends SchedulePopupView {
    private int maxCharacter = 11;

    public CreateTrainPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView, schedule);
        this.schedule = schedule;
    }

    @Override
    public Node getNode() {
        BorderPane pane = new BorderPane();

        Label infoLabel = new Label("Voer trein ID in (max 10 karakters):");
        TextField inputField = new TextField();
        VBox idBox = new VBox(infoLabel, inputField);

        Label platformInfo = new Label("Kies uit wagon sets:");
        ComboBox<List<Wagon>> trainComboBox = new ComboBox<>(FXCollections.observableList(schedule.getWagonSetList()));
        VBox WagonSetBox = new VBox(platformInfo, trainComboBox);

        Button saveButton = new Button("Voeg toe");
        saveButton.setOnAction(e -> {
            if (inputField.getText().isEmpty() || inputField.getText().length() >= maxCharacter || trainComboBox.getItems().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error, er is geen data of er zijn teveel karakters toegevoegd");
                alert.showAndWait();
            } else if (inputField.getText().length() < maxCharacter && !inputField.getText().isEmpty() && !trainComboBox.getItems().isEmpty()) {
                schedule.addTrain(new Train(
                        inputField.getText(),
                        trainComboBox.getValue()
                ));
                inputField.clear();
                super.callMainView();
            }
        });
        FlowPane buttonBar = new FlowPane(super.getCloseButton(), saveButton);
        buttonBar.setPadding(new Insets(10));

        VBox inputBox = new VBox(idBox, WagonSetBox);
        pane.setCenter(inputBox);
        pane.setBottom(buttonBar);
        return pane;
    }
}
