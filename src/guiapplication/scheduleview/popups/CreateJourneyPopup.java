package guiapplication.scheduleview.popups;

import data.Platform;
import data.Schedule;
import data.ScheduleBuilder;
import data.Train;
import guiapplication.PopupView;
import guiapplication.ReturnableView;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;

public class CreateJourneyPopup extends PopupView {
    private ScheduleBuilder scheduleBuilder;
    private Schedule schedule;

    public CreateJourneyPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.scheduleBuilder = new ScheduleBuilder(schedule);
        this.schedule = schedule;
    }

    @Override
    public Node getNode() {
        BorderPane pane = new BorderPane();

        Label arrivalTimeInfo = new Label("Voer aankomsttijd in(ie. 1030):");
        TextField arrivalTimeInput = new TextField();
        VBox arrivalTimeBox = new VBox(arrivalTimeInfo, arrivalTimeInput);

        Label departureTimeInfo = new Label("Voer vertrektijd in(ie. 1030):");
        TextField departureTimeInput = new TextField();
        VBox departureTimeBox = new VBox(departureTimeInfo, departureTimeInput);

        Label trainInfo = new Label("Kies uit trein:");
        // todo fix not updating of observable list
        ComboBox<Train> trainComboBox = new ComboBox<>(FXCollections.observableList(this.schedule.getTrainList()));
        VBox trainBox = new VBox(trainInfo, trainComboBox);

        Label platformInfo = new Label("Kies uit perron:");
        // todo fix not updating of observable list
        ObservableList<Platform> platformList = FXCollections.observableArrayList(this.schedule.getPlatformList());
        ComboBox<Platform> platformComboBox = new ComboBox<>(platformList);
        VBox platformBox = new VBox(platformInfo, platformComboBox);

        Button cancelButton = new Button("Annuleer");
        cancelButton.setOnAction(e -> super.callMainView());
        Button saveButton = new Button("Voeg toe");
        saveButton.setOnAction(e -> this.scheduleBuilder.createJourney(
                Integer.parseInt(arrivalTimeInput.getText()),
                Integer.parseInt(departureTimeInput.getText()),
                trainComboBox.getValue(),
                platformComboBox.getValue()
        ));

        FlowPane buttonBar = new FlowPane(cancelButton, saveButton);

        VBox inputBox = new VBox(arrivalTimeBox, departureTimeBox, trainBox, platformBox);
        pane.setCenter(inputBox);
        pane.setBottom(buttonBar);
        return pane;
    }
}
