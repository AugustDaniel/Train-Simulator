package guiapplication.scheduleview.popups.change;

import data.*;
import guiapplication.PopupView;
import guiapplication.ReturnableView;
import guiapplication.scheduleview.popups.SchedulePopupView;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

//TODO add change functionality
public class ChangeTrainPopup extends SchedulePopupView {

    private Schedule schedule;

    public ChangeTrainPopup(ReturnableView mainView, Schedule schedule) {
        super(mainView);
        this.schedule = schedule;
    }

    @Override
    public Node getNode() {
        BorderPane pane = new BorderPane();

        Label amountSelectionLable = new Label("Kies uit de hoeveelheid wagens:");
        ComboBox<ArrayList> amountSelectionComboBox = new ComboBox<>(FXCollections.observableList(new ArrayList<>()));
        VBox amountSelectionBox = new VBox(amountSelectionLable,amountSelectionComboBox);


        Button saveButton = new Button("Voeg toe");
        saveButton.setOnAction(e -> {
            if (false) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error, je bent data vergeten in te vullen");
                alert.showAndWait();
            } else {
                super.callMainView();
            }
        });

        FlowPane buttonBar = new FlowPane(super.getCloseButton(), saveButton);

        VBox inputBox = new VBox();
        pane.setCenter(inputBox);
        amountSelectionComboBox.setOnAction((event) -> {

        });

        pane.setBottom(buttonBar);
        return pane;
    }
}
