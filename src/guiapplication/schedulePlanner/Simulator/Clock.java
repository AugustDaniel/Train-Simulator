package guiapplication.schedulePlanner.Simulator;

import util.Subject;
import util.TimeFormatter;

import java.time.LocalDateTime;
import java.time.LocalTime;


public class Clock extends Subject<Clock> {
    private double timeSpeed;
    private int currentTimeInt;
    private LocalTime currentTime;
    private long previousTime;

    public Clock(double timeSpeed) {
        this.timeSpeed = timeSpeed;
        this.currentTime = TimeFormatter.intToLocalTime(currentTimeInt);
    }

    public void update(double deltaTime) {
        long now = System.currentTimeMillis();
        long deltaActualTime = now-previousTime;
        if (deltaActualTime > this.timeSpeed * 1000) {
            currentTimeInt++;
            if (currentTimeInt % 100 > 59) {
                currentTimeInt -= 60;
                currentTimeInt += 100;
            }
            if (currentTimeInt > 2359) {
                currentTimeInt -= 2400;
            }
            currentTime = TimeFormatter.intToLocalTime(currentTimeInt);
            previousTime = now;
        }
    }

    public void updateTimeSpeed(double timeSpeed){
        this.timeSpeed = 1.0 / timeSpeed;
        super.setState(this);
    }

    public double getTimeSpeed() {
        return timeSpeed;
    }

    public LocalTime getCurrentTime() {
        return currentTime;
    }
}
