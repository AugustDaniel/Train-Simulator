package guiapplication.scheduleview.popups.change;

import data.*;
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

public class ChangePlatformPopup extends SchedulePopupView {

    private Schedule schedule;

    public ChangePlatformPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.schedule = schedule;
    }

    @Override
    public Node getNode() {
        BorderPane pane = new BorderPane();

        Label infoLabel = new Label("Voer platform nummer in:");
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
            try {//try-catch zodat er geen karakters of letters kunnen worden gebruikt. Het geeft dan een warning
                this.schedule.addPlatform(new Platform(Integer.parseInt(inputField.getText())));
                inputField.clear();
                super.callMainView();
            }catch (Exception numberNotFound){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error, Het kan zijn dat je iets anders hebt neergezet dan een nummer");
                alert.showAndWait();
            }
        });
        return saveButton;
    }
}
