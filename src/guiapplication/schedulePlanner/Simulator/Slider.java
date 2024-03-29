package guiapplication.schedulePlanner.Simulator;

import javafx.geometry.Orientation;
import javafx.scene.layout.VBox;

public class Slider extends VBox {

    private javafx.scene.control.Slider slider;

    public Slider(MapView mapView) {
        slider = new javafx.scene.control.Slider(0, 2000, 50);
        slider.setOrientation(Orientation.HORIZONTAL);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(50);
        slider.setBlockIncrement(10);

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int newPeopleCount = newValue.intValue();
            mapView.updatePeopleCount(newPeopleCount);
        });

        setSpacing(0); // Set spacing between children to zero
        getChildren().add(slider);
    }
}
