package data;

import java.io.Serializable;

public class Journey implements Serializable {
    private int arrivalTime;
    private int departureTime;
    private Train train;
    private Platform platform;

    public Journey(int arrivalTime, int departureTime, Train train, Platform platform) {
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.train = train;
        this.platform = platform;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getDepartureTime() {
        return departureTime;
    }

    public Train getTrain() {
        return train;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setDepartureTime(int departureTime) {
        this.departureTime = departureTime;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return "platform: " + platform +
                "\n train: " + train;
    }
}
