package data;

import util.Observer;

import java.util.ArrayList;
import java.util.List;

public class ScheduleSubject {

    private List<Observer> observers = new ArrayList<>();

    private Schedule schedule;

    public Schedule getSchedule() {
        return this.schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
        notifyAllObservers();
    }

    public void attach(Observer observer){
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for (Observer observer : observers) {
            observer.update();
        }
    }
}