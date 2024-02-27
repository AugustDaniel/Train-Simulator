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

    private Schedule schedule;
    public CreateWagonSetPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.schedule = schedule;
    }

    @Override
    public Node getNode() {
        BorderPane pane = new BorderPane();


        ArrayList<Integer> possibleAmounts = new ArrayList<>();
        possibleAmounts.add(1);
        possibleAmounts.add(2);
        possibleAmounts.add(3);
        ComboBox<Integer> amountSelectionBox = new ComboBox<>(FXCollections.observableList(possibleAmounts));

        Label wagon1Label = new Label("Kies uit de mogelijke wagon:");
        ComboBox<Wagon> wagon1ComboBox = new ComboBox<>(FXCollections.observableList(this.schedule.getWagonList()));
        VBox wagon1Box = new VBox(wagon1Label, wagon1ComboBox);

        Label wagon2Label = new Label("Kies uit de mogelijke wagon:");
        ComboBox<Wagon> wagon2ComboBox = new ComboBox<>(FXCollections.observableList(this.schedule.getWagonList()));
        VBox wagon2Box = new VBox(wagon2Label, wagon2ComboBox);

        Label wagon3Label = new Label("Kies uit de mogelijke wagon:");;
        ComboBox<Wagon> wagon3ComboBox = new ComboBox<>(FXCollections.observableList(this.schedule.getWagonList()));;
        VBox wagon3Box = new VBox(wagon3Label, wagon3ComboBox);





        Button cancelButton = new Button("Annuleer");
        cancelButton.setOnAction(e -> super.callMainView());

        Button saveButton = new Button("Voeg toe");
        saveButton.setOnAction(e -> {
            if (wagon1Label.getText().isEmpty() || wagon2Label.getText().isEmpty() || wagon3Label.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error, je bent data vergeten in te vullen");
                alert.showAndWait();
            } else {
                //TODO add correct parameters
                List<Wagon> addedWagons = new ArrayList<>();
                switch ((int) amountSelectionBox.getValue()){
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

                this.schedule.addWagonSet(addedWagons);
                super.callMainView();
            }
        });

        FlowPane buttonBar = new FlowPane(cancelButton, saveButton);

        VBox inputBox = new VBox(amountSelectionBox);
        pane.setCenter(inputBox);
        pane.setBottom(buttonBar);

        amountSelectionBox.setOnAction((event) -> {
            if (amountSelectionBox.getValue().equals(1)){
                VBox addedinputs = new VBox(amountSelectionBox, wagon1Box);
                pane.setCenter(addedinputs);
            }
            else if (amountSelectionBox.getValue().equals(2)){
                VBox addedinputs = new VBox(amountSelectionBox, wagon1Box, wagon2Box);
                pane.setCenter(addedinputs);
            }
            else if (amountSelectionBox.getValue().equals(3)){
                VBox addedinputs = new VBox(amountSelectionBox, wagon1Box, wagon2Box, wagon3Box);
                pane.setCenter(addedinputs);
            }
        });

        return pane;
    }
}
