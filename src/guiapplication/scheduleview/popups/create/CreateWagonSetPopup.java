package guiapplication.scheduleview.popups.create;

import data.Platform;
import data.Schedule;
import data.ScheduleBuilder;
import data.Wagon;
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
    private ScheduleBuilder scheduleBuilder;
    public CreateWagonSetPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.schedule = schedule;
        this.scheduleBuilder = new ScheduleBuilder(schedule);
    }

    @Override
    public Node getNode() {
        BorderPane pane = new BorderPane();


//        ArrayList<Integer> amount = new ArrayList<>();
//        amount.add(1);
//        amount.add(2);
//        amount.add(3);

//        Label amountLabel = new Label("Kies uit de mogelijke wagon set:");
//        ComboBox<Integer> amountComboBox = new ComboBox<>(FXCollections.observableList(amount));
//        VBox amountBox = new VBox(amountLabel, amountComboBox);

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
                addedWagons.add(wagon1ComboBox.getValue());
                addedWagons.add(wagon2ComboBox.getValue());
                addedWagons.add(wagon3ComboBox.getValue());
                this.scheduleBuilder.createWagonSet(addedWagons);
                super.callMainView();
            }
        });

        FlowPane buttonBar = new FlowPane(cancelButton, saveButton);

        VBox inputBox = new VBox(wagon1Box, wagon2Box, wagon3Box);
        pane.setCenter(inputBox);
        pane.setBottom(buttonBar);
        return pane;
    }
}
