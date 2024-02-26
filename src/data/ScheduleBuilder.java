package data;

import java.util.List;

public class ScheduleBuilder {

    private Schedule schedule;

    public ScheduleBuilder(Schedule schedule) {
        this.schedule = schedule;
    }

    public void createJourney(int arrivalTime, int departureTime, Train train, Platform platform) {
        this.schedule.addJourney(new Journey(arrivalTime, departureTime, train, platform));
    }

    public void createTrain(String id, List<Wagon> wagons) {
        this.schedule.addTrain(new Train(id, wagons));
    }

    public void createPlatform(int platformNumber) {
        this.schedule.addPlatform(new Platform(platformNumber));
    }

    public void createWagon(String id, int capacity) {
        this.schedule.addWagon(new Wagon(id, capacity));
    }


    public Schedule getSchedule() {
        return schedule;
    }

    public void createWagonSet(List<Wagon> addedWagons) {
        this.schedule.addWagonSet(addedWagons);
    }
}
