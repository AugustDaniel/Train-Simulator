package data;

import java.io.Serializable;
import java.time.LocalTime;

public class Journey implements Serializable {
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private Train train;
    private int popularity;
    private Platform platform;
    private Machinist machinist;

    public Journey(LocalTime arrivalTime, LocalTime departureTime, Train train, int popularity, Platform platform, Machinist machinist) {
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.train = train;
        this.popularity = popularity;
        this.platform = platform;
        this.machinist = machinist;
    }
    
    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
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

    public Machinist getMachinist() {
        return machinist;
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

    public void setMachinist(Machinist machinist) {
        this.machinist = machinist;
    }
}
