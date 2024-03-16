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
    private SpawnTrain train;

    public Clock(Schedule schedule, double timeSpeed) {
        this.schedule = schedule;
        this.timeSpeed = timeSpeed;
        this.currentTime = TimeFormatter.intToLocalTime(currentTimeInt);
        timePassed = 0;
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
        }

        for (Journey journey : this.schedule.getJourneyList()) {
            if (currentTime.equals(journey.getArrivalTime())){
                this.train = new SpawnTrain(journey.getPlatform());
//                this.train.draw();
                System.out.println(currentTimeInt);
                System.out.println("train number " + journey.getTrain() + " should now arrive at platform " + journey.getPlatform());
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
