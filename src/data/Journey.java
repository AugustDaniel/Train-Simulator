package data;

import java.io.Serializable;
import java.time.LocalTime;

public class Journey implements Serializable {
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private Train train;
    private int popularity;
    private Platform platform;
    private int ID;

    public Journey(LocalTime arrivalTime, LocalTime departureTime, Train train, int popularity, Platform platform) {
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.train = train;
        this.popularity = popularity;
        this.platform = platform;
    }
    
    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public String getTrainID(){
        return this.train.getTrainIDNumber();
    }

    public Train getTrain() {
        return this.train;
    }

    public Platform getPlatform() {
        return this.platform;
    }

    public int getTrainPopularity(){
        return this.popularity;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
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
