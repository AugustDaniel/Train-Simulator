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

    public void createTrain(String id) {
        this.schedule.addTrain(new Train(id));
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

    public void deleteJourney(Journey journey){
        this.schedule.deleteJourney(journey);
    }
    public void deletePlatform(Platform platform){
        this.schedule.deletePlatform(platform);
    }

    public void deleteTrain(Train train){
        this.schedule.deleteTrain(train);
    }

    public void deleteWagon(Wagon wagon){
        this.schedule.deleteWagon(wagon);
    }

    public Schedule getSchedule() {
        return schedule;
    }
}
