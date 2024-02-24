package util;


import data.Schedule;

import java.util.ArrayList;
import java.util.List;

public class ScheduleSubject {

    private List<Observer> observers = new ArrayList<Observer>();

    private Schedule schedule;

    public void callMethod(MethodCall method) {
        method.call();
        notifyAllObservers();
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