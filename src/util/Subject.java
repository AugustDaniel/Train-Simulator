package util;

import java.util.ArrayList;
import java.util.List;

abstract public class Subject<T> {

    private List<Observer> observers = new ArrayList<>();
    private T state;

    public void setState(T state) {
        this.state = state;
        notifyAllObservers();
    }

    public T getState() {
        return state;
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
