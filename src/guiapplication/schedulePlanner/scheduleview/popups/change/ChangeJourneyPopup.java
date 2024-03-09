package guiapplication.schedulePlanner.scheduleview.popups.change;

import data.*;
import guiapplication.schedulePlanner.ReturnableView;
import guiapplication.schedulePlanner.scheduleview.popups.SchedulePopupView;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import util.TimeFormatter;

import java.util.ArrayList;


public class ChangeJourneyPopup extends SchedulePopupView {
    private Schedule schedule;

    public ChangeJourneyPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.schedule = schedule;
    }

    @Override
    public Node getNode() {
        BorderPane pane = new BorderPane();

        Label journeySelectionLable = new Label("Kies uit mogelijke reizen:");
        ComboBox<Journey> journeySelectionComboBox = new ComboBox<>(FXCollections.observableList(this.schedule.getJourneyList()));
        VBox journeySelectionBox = new VBox(journeySelectionLable,journeySelectionComboBox);

        ArrayList<String> toChange = new ArrayList<>();
        toChange.add("aankomst tijd");
        toChange.add("vertrek tijd");
        toChange.add("trein");
        toChange.add("perron");

        Label toChangeLabel = new Label("wat wilt u veranderen?:");
        ComboBox<String> toCangeComboBox = new ComboBox<>(FXCollections.observableList(toChange));
        VBox toChangeBox = new VBox(toChangeLabel,toCangeComboBox);



        Label changePlatformIntoLabel = new Label("kies voor welk perron het moet zijn:");
        ComboBox<Platform> changePlatformIntoComboBox = new ComboBox<>(FXCollections.observableList(this.schedule.getPlatformList()));
        VBox changePlatformIntoBox = new VBox(changePlatformIntoLabel,changePlatformIntoComboBox);

        Label changeTrainIntoLabel = new Label("kies voor welke trein het moet zijn:");
        ComboBox<Train> changeTrainIntoComboBox = new ComboBox<>(FXCollections.observableList(this.schedule.getTrainList()));
        VBox changeTrainIntoBox = new VBox(changeTrainIntoLabel,changeTrainIntoComboBox);

        Label changeArrivalTimeIntoLabel = new Label("kies voor welke aankomsttijd het moet zijn:");
        TextField changeArrivalTimeInput = new TextField();
        VBox changeArrivalTimeIntoBox = new VBox(changeArrivalTimeIntoLabel, changeArrivalTimeInput);

        Label ChangeDepartureTimeIntoLabel = new Label("kies voor welke vertrektijd het moet zijn:");
        TextField changeDepartureTimeInput = new TextField();
        VBox changeDepartureTimeIntoBox = new VBox(ChangeDepartureTimeIntoLabel,changeDepartureTimeInput);


        Button saveButton = new Button("Verander");
        saveButton.setOnAction(e -> {
            if (journeySelectionComboBox.getSelectionModel().isEmpty() || toCangeComboBox.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error, je bent data vergeten in te vullen");
                alert.showAndWait();
            } else {
                if (toCangeComboBox.getValue().equals("perron")){
                    this.schedule.getJourneyList().get(
                            this.schedule.getJourneyList().indexOf(journeySelectionComboBox.getValue())
                    ).setPlatform(changePlatformIntoComboBox.getValue());

                } else if (toCangeComboBox.getValue().equals("trein")) {
                    this.schedule.getJourneyList().get(
                            this.schedule.getJourneyList().indexOf(journeySelectionComboBox.getValue())
                    ).setTrain(changeTrainIntoComboBox.getValue());

                } else if (toCangeComboBox.getValue().equals("aankomst tijd")) {
                    this.schedule.getJourneyList().get(
                            this.schedule.getJourneyList().indexOf(journeySelectionComboBox.getValue())
                    ).setArrivalTime(TimeFormatter.intToLocalTime(Integer.parseInt(changeArrivalTimeInput.getText())));

                    this.schedule.getJourneyList().get(
                            this.schedule.getJourneyList().indexOf(journeySelectionComboBox.getValue())
                    ).setDepartureTime(TimeFormatter.intToLocalTime(Integer.parseInt(changeArrivalTimeInput.getText())).plusMinutes(10));

                } else if (toCangeComboBox.getValue().equals("vertrek tijd")) {
                    this.schedule.getJourneyList().get(
                            this.schedule.getJourneyList().indexOf(journeySelectionComboBox.getValue())
                    ).setDepartureTime(TimeFormatter.intToLocalTime(Integer.parseInt(changeDepartureTimeInput.getText())));

                    this.schedule.getJourneyList().get(
                            this.schedule.getJourneyList().indexOf(journeySelectionComboBox.getValue())
                    ).setArrivalTime(TimeFormatter.intToLocalTime(Integer.parseInt(changeDepartureTimeInput.getText())).minusMinutes(10));

                }
                super.callMainView();
            }
        });

        FlowPane buttonBar = new FlowPane(super.getCloseButton(), saveButton);

        VBox inputBox = new VBox(journeySelectionBox,toChangeBox);
        pane.setCenter(inputBox);

        toCangeComboBox.setOnAction((event) -> {
            if (toCangeComboBox.getValue().equals("perron")){
                VBox addedinputBox = new VBox(journeySelectionBox,toChangeBox, changePlatformIntoBox);
                pane.setCenter(addedinputBox);
            } else if (toCangeComboBox.getValue().equals("trein")) {
                VBox addedinputBox = new VBox(journeySelectionBox,toChangeBox, changeTrainIntoBox);
                pane.setCenter(addedinputBox);
            } else if (toCangeComboBox.getValue().equals("aankomst tijd")) {
                VBox addedinputBox = new VBox(journeySelectionBox,toChangeBox, changeArrivalTimeIntoBox);
                pane.setCenter(addedinputBox);
            } else if (toCangeComboBox.getValue().equals("vertrek tijd")) {
                VBox addedinputBox = new VBox(journeySelectionBox,toChangeBox, changeDepartureTimeIntoBox);
                pane.setCenter(addedinputBox);
            }
        });

        pane.setBottom(buttonBar);
        return pane;
    }
}