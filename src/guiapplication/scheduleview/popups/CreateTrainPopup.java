package guiapplication.scheduleview.popups;

import data.Schedule;
import data.ScheduleBuilder;
import guiapplication.PopupView;
import guiapplication.ReturnableView;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class CreateTrainPopup extends PopupView {

    private ScheduleBuilder scheduleBuilder;


    public CreateTrainPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.scheduleBuilder = new ScheduleBuilder(schedule);
    }

    @Override
    public Node getNode() {
        BorderPane pane = new BorderPane();

        Label infoLabel = new Label("Voer trein ID in (max 10 karakters):");
        TextField inputField = new TextField();
        VBox inputBox = new VBox(infoLabel, inputField);

        Button cancelButton = new Button("Annuleer");
        cancelButton.setOnAction(e -> super.callMainView());
        Button saveButton = new Button("Voeg toe");
        saveButton.setOnAction(e -> {
            if (inputField.getText().length() < 11){//heb er een limiet aan gezet
                this.scheduleBuilder.createTrain(inputField.getText());
                inputField.clear();
                super.callMainView();
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error, te veel nummers, verander de naam van de trein");
                alert.showAndWait();
            }

        });
        FlowPane buttonBar = new FlowPane(cancelButton, saveButton);
        buttonBar.setPadding(new Insets(10));

        pane.setCenter(inputBox);
        pane.setBottom(buttonBar);
        return pane;
    }
}
