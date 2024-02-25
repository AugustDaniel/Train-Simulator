package data;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Schedule implements Serializable {

    private List<Train> trainList;
    private List<Platform> platformList;
    private List<Journey> journeyList;

    private ArrayList<List<Wagon>> wagonSetList;
    private List<Wagon> wagonList;
    public Schedule(){
        this.trainList = new ArrayList<>();
        this.journeyList = new ArrayList<>();
        this.platformList = new ArrayList<>();
        this.wagonSetList = new ArrayList<>();
        this.wagonList = new ArrayList<>();

        this.initTestData();
    }
    public void addTrain(Train train){
        trainList.add(train);
    }

    public void addWagonSet(ArrayList<Wagon> wagons){
        wagonSetList.add(wagons);
    }

    public void addJourney(Journey journey){
        this.journeyList.add(journey);
    }

    public void addPlatform(Platform platform){
        this.platformList.add(platform);
    }

    public void addWagon(Wagon newWagon){
        this.wagonList.add(newWagon);
    }

    public void changeTrain(){
        //todo needs body
    }
    public void deleteTrain(Train train){
        for (Journey journey : journeyList) {
            if (journey.getTrain().equals(train)) {
                deleteJourney(journey);
                trainList.remove(train);
            }
        }
        if (trainList.contains(train)){
            trainList.remove(train);
        }
    }

    public void deleteJourney(Journey journey) {
        journeyList.remove(journey);
    }

    public void deletePlatform(Platform platform){
        for (Journey journey : journeyList) {
            if (journey.getPlatform().equals(platform)){
                deleteJourney(journey);
                platformList.remove(platform);
            }
        }
        if (platformList.contains(platform)){
            platformList.remove(platform);
        }
    }

    public void deleteWagonSet(ArrayList<Wagon> wagons){
        for (Train train : trainList) {
            if (train.getWagons().equals(wagons)){
                deleteTrain(train);
                wagonSetList.remove(wagons);
            }
        }
        if (wagonSetList.contains(wagons)){
            wagonSetList.remove(wagons);
        }
    }

    public void deleteWagon(Wagon wagon) {
        for (List<Wagon> wagonSet : wagonSetList) {
            if (wagonSet.contains(wagon)){
                deleteWagonSet((ArrayList<Wagon>) wagonSet);
                wagonList.remove(wagon);
            }
        }
        if (wagonList.contains(wagon)){
            wagonList.remove(wagon);
        }
    }

    public List<Journey> getJourneyList(){
        return this.journeyList;
    }

    public List<Train> getTrainList() {
        return this.trainList;
    }

    public List<Platform> getPlatformList() {
        return this.platformList;
    }

    public ArrayList<List<Wagon>> getWagonSetList() {
        return wagonSetList;
    }

    public List<Wagon> getWagonList() {
        return wagonList;
    }

    //TODO tescode
    public void initTestData(){

        //adding some wagons to list
        this.addWagon(new Wagon("01", 20));
        this.addWagon(new Wagon("02", 25));
        this.addWagon(new Wagon("03", 30));

        //adding some wagonSets to list
        ArrayList<Wagon> list1 = new ArrayList<>();
        list1.add(wagonList.get(0));
        list1.add(wagonList.get(0));
        list1.add(wagonList.get(0));

        ArrayList<Wagon> list2 = new ArrayList<>();
        list2.add(wagonList.get(2));
        list2.add(wagonList.get(2));
        list2.add(wagonList.get(1));

        ArrayList<Wagon> list3 = new ArrayList<>();
        list3.add(wagonList.get(1));
        list3.add(wagonList.get(1));
        list3.add(wagonList.get(1));

        this.addWagonSet(list1);
        this.addWagonSet(list2);
        this.addWagonSet(list3);


        //adding some trains to list
        Train train1 = new Train("21", wagonSetList.get(0));
        Train train2 = new Train("22", wagonSetList.get(1));
        Train train3 = new Train("23", wagonSetList.get(2));

        this.addTrain(train1);
        this.addTrain(train2);
        this.addTrain(train3);

        //adding some platforms to list
        this.addPlatform(new Platform(1));
        this.addPlatform(new Platform(2));
        this.addPlatform(new Platform(3));

        //adding some journeys to list
        this.addJourney(new Journey(1000, 1010, this.trainList.get(0), this.platformList.get(0)));
        this.addJourney(new Journey(1020, 1030, this.trainList.get(1), this.platformList.get(1)));
        this.addJourney(new Journey(1040, 1050, this.trainList.get(2), this.platformList.get(2)));
    }
}
