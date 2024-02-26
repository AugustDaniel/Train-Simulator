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
import javafx.collections.ObservableList;

public class CreateJourneyPopup extends SchedulePopupView {
    private Schedule schedule;

    public CreateJourneyPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
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
        ComboBox<Train> trainComboBox = new ComboBox<>(FXCollections.observableList(this.schedule.getTrainList()));
        VBox trainBox = new VBox(trainInfo, trainComboBox);

        Label platformInfo = new Label("Kies uit perron:");
        ObservableList<Platform> platformList = FXCollections.observableArrayList(this.schedule.getPlatformList());
        ComboBox<Platform> platformComboBox = new ComboBox<>(platformList);
        VBox platformBox = new VBox(platformInfo, platformComboBox);

        Button saveButton = new Button("Voeg toe");
        saveButton.setOnAction(e -> {
            if (arrivalTimeInput.getText().isEmpty() || departureTimeInput.getText().isEmpty()
                    || trainComboBox.getSelectionModel().isEmpty() || platformComboBox.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error, je bent data vergeten in te vullen");
                alert.showAndWait();
            } else {
                this.schedule.addJourney(new Journey(Integer.parseInt(arrivalTimeInput.getText()),
                        Integer.parseInt(departureTimeInput.getText()),
                        trainComboBox.getValue(),
                        platformComboBox.getValue()
                ));
                super.callMainView();
            }
        });

        FlowPane buttonBar = new FlowPane(super.getCloseButton(), saveButton);

        VBox inputBox = new VBox(arrivalTimeBox, departureTimeBox, trainBox, platformBox);
        pane.setCenter(inputBox);
        pane.setBottom(buttonBar);
        return pane;
    }
}
