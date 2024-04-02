package guiapplication.scheduleview.popups.create;

import data.Machinist;
import data.Schedule;
import guiapplication.ReturnableView;
import guiapplication.scheduleview.popups.SchedulePopupView;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class CreateMachinistPopup extends SchedulePopupView {

    public CreateMachinistPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView, schedule);
        this.schedule = schedule;
    }

    @Override
    public Node getNode() {
        BorderPane pane = new BorderPane();

        Label infoLabel = new Label("Voer een naam in:");
        TextField inputField = new TextField();
        VBox inputBox = new VBox(infoLabel, inputField);

        Button saveButton = getSaveButton(inputField);
        FlowPane buttonBar = new FlowPane(super.getCloseButton(), saveButton);
        buttonBar.setPadding(new Insets(10));

        pane.setCenter(inputBox);
        pane.setBottom(buttonBar);
        return pane;
    }

    private Button getSaveButton(TextField inputField) {
        Button saveButton = new Button("Voeg toe");
        saveButton.setOnAction(e -> {
            if (!inputField.getText().isEmpty()) {
                for (Machinist machinist : schedule.getMachinistsList()) {
                    if (machinist.getName().equals(inputField.getText())) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Error, deze machinist bestaat al");
                        alert.showAndWait();
                        return;
                    }
                }
            }

            schedule.addMachinist(new Machinist(inputField.getText()));
            super.callMainView();
        });

        return saveButton;
    }
}
