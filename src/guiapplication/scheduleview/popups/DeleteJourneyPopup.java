package guiapplication.scheduleview.popups;

import data.*;
import guiapplication.PopupView;
import guiapplication.ReturnableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class DeleteJourneyPopup extends SchedulePopupView {

    private Schedule schedule;
    private ScheduleBuilder scheduleBuilder;

    public DeleteJourneyPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.schedule = schedule;
        this.scheduleBuilder = new ScheduleBuilder(schedule);
    }

    @Override
    public Node getNode() {
        BorderPane pane = new BorderPane();

        Label journeyLable = new Label("Kies uit reis:");
        ComboBox<Journey> journeyComboBox = new ComboBox<>(FXCollections.observableList(this.schedule.getJourneyList()));
        VBox trainBox = new VBox(journeyLable, journeyComboBox);

        Button cancelButton = new Button("Annuleer");
        cancelButton.setOnAction(e -> super.callMainView());
        Button saveButton = new Button("Verwijder");
        saveButton.setOnAction(e -> {
            if (journeyComboBox.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error, je bent data vergeten in te vullen");
                alert.showAndWait();
            } else {
                this.schedule.deleteJourney(
                        journeyComboBox.getValue()
                );
                super.callMainView();
            }
        });

        FlowPane buttonBar = new FlowPane(cancelButton, saveButton);

        VBox inputBox = new VBox(trainBox);
        pane.setCenter(inputBox);
        pane.setBottom(buttonBar);
        return pane;
    }
}
