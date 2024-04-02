package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Schedule implements Serializable {

    private List<Train> trainList;
    private List<Platform> platformList;
    private List<Journey> journeyList;

    private ArrayList<List<Wagon>> wagonSetList;
    private List<Wagon> wagonList;
    private List<Machinist> machinists;

    public Schedule() {
        this.trainList = new ArrayList<>();
        this.journeyList = new ArrayList<>();
        this.platformList = new ArrayList<>();
        this.wagonSetList = new ArrayList<>();
        this.wagonList = new ArrayList<>();
        this.machinists = new ArrayList<>();
    }

    public void addTrain(Train train) {
        trainList.add(train);
    }

    public void addWagonSet(List<Wagon> wagons) {
        wagonSetList.add(wagons);
    }

    public void addJourney(Journey journey) {
        journeyList.add(journey);
    }

    public void addPlatform(Platform platform) {
        platformList.add(platform);
    }

    public void addWagon(Wagon newWagon) {
        wagonList.add(newWagon);
    }

    public void addMachinist(Machinist machinist) {
        machinists.add(machinist);
    }

    public void deleteTrain(Train train) {
        Iterator<Journey> journeyIterator = journeyList.iterator();

        while (journeyIterator.hasNext()) {
            Journey currentJourney = journeyIterator.next();
            if (currentJourney.getTrain().equals(train)) {
                journeyIterator.remove();
                deleteJourney(currentJourney);
            }
        }
        trainList.remove(train);
    }

    public void deleteJourney(Journey journey) {
        journeyList.remove(journey);
    }

    public void deletePlatform(Platform platform) {
        Iterator<Journey> journeyIterator = journeyList.iterator();

        while (journeyIterator.hasNext()) {
            Journey currentJourney = journeyIterator.next();
            if (currentJourney.getPlatform().equals(platform)) {
                journeyIterator.remove();
                deleteJourney(currentJourney);
            }
        }

        platformList.remove(platform);
    }

    public void deleteWagonSet(List<Wagon> wagonSet) {
        Iterator<Train> trainIterator = trainList.iterator();

        while (trainIterator.hasNext()) {
            Train currentTrain = trainIterator.next();
            if (currentTrain.getWagons().equals(wagonSet)) {
                trainIterator.remove();
                deleteTrain(currentTrain);
            }
        }
        wagonSetList.remove(wagonSet);
    }

    public void deleteWagon(Wagon wagon) {
        Iterator<List<Wagon>> wagonSetListItterator = wagonSetList.iterator();

        while (wagonSetListItterator.hasNext()) {
            List<Wagon> currentWagonSet = wagonSetListItterator.next();
            if (currentWagonSet.contains(wagon)) {
                wagonSetListItterator.remove();
                deleteWagonSet(currentWagonSet);
            }
        }

        wagonList.remove(wagon);
    }

    public void deleteMachinist(Machinist machinist) {
        Iterator<Journey> journeyIterator = journeyList.iterator();

        while (journeyIterator.hasNext()) {
            Journey currentJourney = journeyIterator.next();
            if (currentJourney.getMachinist().equals(machinist)) {
                journeyIterator.remove();
                deleteJourney(currentJourney);
            }
        }

        machinists.remove(machinist);
    }

    public List<Journey> getJourneyList() {
        return journeyList;
    }

    public List<Train> getTrainList() {
        return trainList;
    }

    public List<Platform> getPlatformList() {
        return platformList;
    }

    public ArrayList<List<Wagon>> getWagonSetList() {
        return wagonSetList;
    }

    public List<Wagon> getWagonList() {
        return wagonList;
    }

    public List<Machinist> getMachinistsList() {
        return machinists;
    }

}
