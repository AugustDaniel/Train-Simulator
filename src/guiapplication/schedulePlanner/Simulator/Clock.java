package guiapplication.schedulePlanner.Simulator;

import util.Subject;
import util.TimeFormatter;

import java.time.LocalTime;


public class Clock extends Subject<Clock> {
    private double timeSpeed;
    private double timePassed;
    private int currentTimeInt;
    private LocalTime currentTime;

    public Clock(double timeSpeed) {
        this.timeSpeed = timeSpeed;
        this.currentTime = TimeFormatter.intToLocalTime(currentTimeInt);
        this.timePassed = 0;
    }

    public void update(double deltaTime) {
        this.timePassed += deltaTime;
        if (timePassed > this.timeSpeed) {
            currentTimeInt++;
            if (currentTimeInt % 100 > 59) {
                currentTimeInt -= 60;
                currentTimeInt += 100;
            }
            if (currentTimeInt > 2359) {
                currentTimeInt -= 2400;
            }
            timePassed -= timeSpeed;
            currentTime = TimeFormatter.intToLocalTime(currentTimeInt);
        }
    }

    public void updateTimeSpeed(double timeSpeed){
        this.timeSpeed = (double) 1 / timeSpeed;
        super.setState(this);
    }

    public double getTimeSpeed() {
        return timeSpeed;
    }

    public LocalTime getCurrentTime() {
        return currentTime;
    }
}
