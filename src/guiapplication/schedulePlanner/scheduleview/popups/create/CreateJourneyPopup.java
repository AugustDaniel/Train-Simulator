package guiapplication.schedulePlanner.scheduleview.popups.create;

import data.*;
import guiapplication.schedulePlanner.scheduleview.ReturnableView;
import guiapplication.schedulePlanner.scheduleview.popups.SchedulePopupView;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;
import util.TimeFormatter;

import java.time.LocalTime;

public class CreateJourneyPopup extends SchedulePopupView {
    private Schedule schedule;
    private TextField departureTimeInput;
    private TextField popularityInput;
    private ComboBox<Platform> platformComboBox;

    public CreateJourneyPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.schedule = schedule;
    }

    @Override
    public Node getNode() {
        BorderPane pane = new BorderPane();

        Label departureTimeLabel = new Label("Voer vertrektijd in(ie. 1030):");
        this.departureTimeInput = new TextField();
        VBox departureTimeBox = new VBox(departureTimeLabel, departureTimeInput);

        Label trainLabel = new Label("Kies uit trein:");
        ComboBox<Train> trainComboBox = new ComboBox<>(FXCollections.observableList(this.schedule.getTrainList()));
        VBox trainBox = new VBox(trainLabel, trainComboBox);

        Label popularityLabel = new Label("Voer trein populariteit in (1-10):");
        this.popularityInput = new TextField();
        VBox popularityBox = new VBox(popularityLabel, popularityInput);

        Label platformInfo = new Label("Kies uit perron:");
        ObservableList<Platform> platformList = FXCollections.observableArrayList(this.schedule.getPlatformList());
        this.platformComboBox = new ComboBox<>(platformList);
        VBox platformBox = new VBox(platformInfo, platformComboBox);

        Button saveButton = new Button("Voeg toe");
        saveButton.setOnAction(e -> {
            if (departureTimeInput.getText().isEmpty()
                    || trainComboBox.getSelectionModel().isEmpty() || platformComboBox.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error, je bent data vergeten in te vullen");
                alert.showAndWait();
            } else if (overlappingTrains()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error, 2 treinen overlappen qua tijd");
                alert.showAndWait();
            } else if (timeBetweenArriveAndDeparture()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Error, de aankomst- en vertrektijd moet tussen 0000 en 2359 zitten");
                    alert.showAndWait();
            } else if (timeHigherThan60()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error, 2 treinen overlappen qua tijd");
                alert.showAndWait();
            } else if (Integer.parseInt(popularityInput.getText()) <= 0 || Integer.parseInt(popularityInput.getText()) > 10) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error, er is geen trein populariteit toegevoegd of is niet tussen de 1 en 10");
                alert.showAndWait();
            } else {
                LocalTime inputTime = TimeFormatter.intToLocalTime(Integer.parseInt(departureTimeInput.getText()));
                this.schedule.addJourney(new Journey(
                        inputTime.minusMinutes(10),
                        inputTime,
                        trainComboBox.getValue(),
                        Integer.parseInt(popularityInput.getText()),
                        platformComboBox.getValue()
                ));
                super.callMainView();
            }
        });

        FlowPane buttonBar = new FlowPane(super.getCloseButton(), saveButton);

        VBox inputBox = new VBox(departureTimeBox, popularityBox, trainBox, platformBox);
        pane.setCenter(inputBox);
        pane.setBottom(buttonBar);
        return pane;
    }

    public Boolean overlappingTrains() {
        Boolean overlapping = false;
        for (Journey journey : schedule.getJourneyList()) {
            if (Integer.parseInt(departureTimeInput.getText()) - 10 <= TimeFormatter.localTimeToInt(journey.getDepartureTime())
                    && Integer.parseInt(departureTimeInput.getText()) >= TimeFormatter.localTimeToInt(journey.getArrivalTime())
                    && platformComboBox.getValue().equals(journey.getPlatform())) {
                overlapping = true;
            }
        }
        return overlapping;
    }

    public Boolean timeBetweenArriveAndDeparture() {
        Boolean isFourNumbers = false;
        if (departureTimeInput.getText().length() != 4 || Integer.parseInt(departureTimeInput.getText()) > 2359) {
            isFourNumbers = true;
        }
        return isFourNumbers;
    }

    public Boolean timeHigherThan60() {
        Boolean higherThan60 = false;
        if ((Integer.parseInt(departureTimeInput.getText()) % 100) > 59){
            higherThan60 = true;
        }
        return higherThan60;
    }
}
