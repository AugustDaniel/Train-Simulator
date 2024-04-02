package guiapplication.scheduleview.popups.create;

import data.*;
import guiapplication.ReturnableView;
import guiapplication.scheduleview.popups.SchedulePopupView;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class CreateWagonSetPopup extends SchedulePopupView {

    public CreateWagonSetPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView, schedule);
        this.schedule = schedule;
    }

    @Override
    public Node getNode() {
        BorderPane pane = new BorderPane();


        ArrayList<Integer> possibleAmounts = new ArrayList<>();
        possibleAmounts.add(1);
        possibleAmounts.add(2);
        possibleAmounts.add(3);
        Label amountSelectionLable = new Label("Kies uit de hoeveelheid wagens:");
        ComboBox<Integer> amountSelectionComboBox = new ComboBox<>(FXCollections.observableList(possibleAmounts));
        VBox amountSelectionBox = new VBox(amountSelectionLable, amountSelectionComboBox);

        Label wagon1Label = new Label("Kies uit de mogelijke wagon:");
        ComboBox<Wagon> wagon1ComboBox = new ComboBox<>(FXCollections.observableList(schedule.getWagonList()));
        VBox wagon1Box = new VBox(wagon1Label, wagon1ComboBox);

        Label wagon2Label = new Label("Kies uit de mogelijke wagon:");
        ComboBox<Wagon> wagon2ComboBox = new ComboBox<>(FXCollections.observableList(schedule.getWagonList()));
        VBox wagon2Box = new VBox(wagon2Label, wagon2ComboBox);

        Label wagon3Label = new Label("Kies uit de mogelijke wagon:");
        ;
        ComboBox<Wagon> wagon3ComboBox = new ComboBox<>(FXCollections.observableList(schedule.getWagonList()));
        ;
        VBox wagon3Box = new VBox(wagon3Label, wagon3ComboBox);

        Button saveButton = new Button("Voeg toe");
        saveButton.setOnAction(e -> {
            if (wagon1Label.getText().isEmpty() || wagon2Label.getText().isEmpty() || wagon3Label.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error, je bent data vergeten in te vullen");
                alert.showAndWait();
            } else {
                List<Wagon> addedWagons = new ArrayList<>();
                switch (amountSelectionComboBox.getValue()) {
                    case 1:
                        addedWagons.add(wagon1ComboBox.getValue());
                        break;
                    case 2:
                        addedWagons.add(wagon1ComboBox.getValue());
                        addedWagons.add(wagon2ComboBox.getValue());
                        break;
                    case 3:
                        addedWagons.add(wagon1ComboBox.getValue());
                        addedWagons.add(wagon2ComboBox.getValue());
                        addedWagons.add(wagon3ComboBox.getValue());
                        break;
                }

                schedule.addWagonSet(addedWagons);
                super.callMainView();
            }
        });

        FlowPane buttonBar = new FlowPane(super.getCloseButton(), saveButton);

        VBox inputBox = new VBox(amountSelectionBox);
        pane.setCenter(inputBox);
        pane.setBottom(buttonBar);

        amountSelectionComboBox.setOnAction((event) -> {
            if (amountSelectionComboBox.getValue().equals(1)) {
                VBox addedinputs = new VBox(amountSelectionBox, wagon1Box);
                pane.setCenter(addedinputs);
            } else if (amountSelectionComboBox.getValue().equals(2)) {
                VBox addedinputs = new VBox(amountSelectionBox, wagon1Box, wagon2Box);
                pane.setCenter(addedinputs);
            } else if (amountSelectionComboBox.getValue().equals(3)) {
                VBox addedinputs = new VBox(amountSelectionBox, wagon1Box, wagon2Box, wagon3Box);
                pane.setCenter(addedinputs);
            }
        });

        return pane;
    }
}
