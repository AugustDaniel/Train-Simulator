package guiapplication.schedulePlanner.Simulator;

import javafx.geometry.Orientation;
import javafx.scene.layout.VBox;

public class Slider extends VBox {

    private javafx.scene.control.Slider amountOfPeopleSlider;
    private javafx.scene.control.Slider clockSpeedSlider;

    public Slider(MapView mapView) {
        //creates people slider
        amountOfPeopleSlider = new javafx.scene.control.Slider(0, 2000, 50);
        amountOfPeopleSlider.setOrientation(Orientation.HORIZONTAL);
        amountOfPeopleSlider.setShowTickMarks(true);
        amountOfPeopleSlider.setShowTickLabels(true);
        amountOfPeopleSlider.setMajorTickUnit(50);
        amountOfPeopleSlider.setBlockIncrement(10);

        amountOfPeopleSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int newPeopleCount = newValue.intValue();
            mapView.updatePeopleCount(newPeopleCount);
        });

        //creates clock slider
        clockSpeedSlider = new javafx.scene.control.Slider(1, 10, 1);
        clockSpeedSlider.setOrientation(Orientation.HORIZONTAL);
        clockSpeedSlider.setShowTickMarks(true);
        clockSpeedSlider.setShowTickLabels(true);
        clockSpeedSlider.setMajorTickUnit(1);
        clockSpeedSlider.setBlockIncrement(1);

        clockSpeedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double newClockSpeed = newValue.doubleValue();
            if ((newClockSpeed % 1) > 0.5){
                newClockSpeed += 1;
            }
            mapView.updateClock((int) newClockSpeed);
        });

        getChildren().add(amountOfPeopleSlider);
        getChildren().add(clockSpeedSlider);
    }
}
