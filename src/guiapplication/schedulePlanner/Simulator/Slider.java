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
            System.out.println("new people " + newPeopleCount);
            mapView.updatePeopleCount(newPeopleCount);
        });

        //creates clock slider
        clockSpeedSlider = new javafx.scene.control.Slider(1, 10, 1);
        clockSpeedSlider.setOrientation(Orientation.HORIZONTAL);
        clockSpeedSlider.setShowTickMarks(true);
//        clockSpeedSlider.setShowTickLabels(true);
        clockSpeedSlider.setMajorTickUnit(1);
        clockSpeedSlider.setBlockIncrement(1);


        clockSpeedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double newClockSpeed = newValue.doubleValue();
            if ((newClockSpeed % 1) > 0.5){
                newClockSpeed += 1;
            }

            switch ((int)newClockSpeed){
                case 1:
                    newClockSpeed = 0.0625;
                    break;
                case 2:
                    newClockSpeed = 0.125;
                    break;
                case 3:
                    newClockSpeed = 0.25;
                    break;
                case 4:
                    newClockSpeed = 0.5;
                    break;
                case 5:
                    newClockSpeed = 1;
                    break;
                case 6:
                    newClockSpeed = 2;
                    break;
                case 7:
                    newClockSpeed = 4;
                    break;
                case 8:
                    newClockSpeed = 8;
                    break;
                case 9:
                    newClockSpeed = 16;
                    break;
                case 10:
                    newClockSpeed = 32;
                    break;
            }

            System.out.println("new speed " + newClockSpeed);
            mapView.updateClock( newClockSpeed);
        });

        getChildren().add(amountOfPeopleSlider);
        getChildren().add(clockSpeedSlider);
    }
}
