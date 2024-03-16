package guiapplication.schedulePlanner.Simulator;

import data.Journey;
import data.Schedule;
import util.TimeFormatter;

import java.time.LocalTime;


public class Clock {
    private double timeSpeed;
    private Schedule schedule;
    private double timePassed;
    private int currentTimeInt;
    private LocalTime currentTime;
    private MapView mapView;
    private boolean spawned = false;
    private LocalTime localTime;

    public Clock(Schedule schedule, double timeSpeed, MapView mapView) {
        this.schedule = schedule;
        this.timeSpeed = timeSpeed;
        this.currentTime = TimeFormatter.intToLocalTime(currentTimeInt);
        this.timePassed = 0;
        this.mapView = mapView;
    }

    public void update(double deltaTime) {
        this.timePassed += deltaTime;
        if (timePassed > this.timeSpeed){
            currentTimeInt++;
            if (currentTimeInt %100 > 59){
                currentTimeInt -= 60;
                currentTimeInt += 100;
            }
            if (currentTimeInt > 2359){
                currentTimeInt -= 2400;
            }
            timePassed-=timeSpeed;
            currentTime = TimeFormatter.intToLocalTime(currentTimeInt);
            System.out.println(currentTimeInt);
        }

        for (Journey journey : this.schedule.getJourneyList()) {
            if (currentTime.equals(journey.getArrivalTime()) && !spawned){
                this.mapView.trains.add(new SpawnTrain(journey.getPlatform()));
                spawned = true;
                this.localTime = this.currentTime;
                System.out.println("test3");
//                System.out.println(currentTimeInt);
//                System.out.println("train number " + journey.getTrain() + " should now arrive at platform " + journey.getPlatform());
            } if (spawned && !currentTime.equals(localTime)){
                spawned = false;
                System.out.println("dqqjfnfwe");
            } if (currentTime.equals(journey.getDepartureTime().plusMinutes(10)) && !this.mapView.trains.isEmpty()) {
                System.out.println("suiiii");
                this.mapView.trains.removeFirst();
            }
        }
    }

    public void setTimeSpeed(double timeSpeed) {
        this.timeSpeed = timeSpeed;
    }

    public LocalTime getCurrentTimeInt() {
        return currentTime;
    }
}
