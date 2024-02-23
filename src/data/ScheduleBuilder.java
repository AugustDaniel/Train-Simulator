package data;

import java.util.List;

public class ScheduleBuilder {

    private Schedule schedule;

    public ScheduleBuilder(Schedule schedule) {
        this.schedule = schedule;
    }

    public boolean createJourney(int arrivalTime, int departureTime, Train train, Platform platform) {
        this.schedule.addJourney(new Journey(arrivalTime, departureTime, train, platform));
        return true;
    }

    public boolean createTrain(String id) {
        this.schedule.addTrain(new Train(id));
        return true;
    }

    public boolean createTrain(String id, List<Wagon> wagons) {
        this.schedule.addTrain(new Train(id, wagons));
        return true;
    }

    public boolean createPlatform(int platformNumber) {
        this.schedule.addPlatform(new Platform(platformNumber));
        return true;
    }

    public boolean createWagon(String id, int capacity) {
        return true;
    }

}
