package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Schedule implements Serializable {

    private ArrayList<Train> trainList;
    private List<Platform> platformList;
    private List<Journey> journeyList;

    private HashMap<String, List<Wagon>> wagonSetList;
    private List<Wagon> wagonList;
    public Schedule(){
        this.trainList = new ArrayList<>();
        this.journeyList = new ArrayList<>();
        this.platformList = new ArrayList<>();
        this.wagonSetList = new HashMap<>();
        this.wagonList = new ArrayList<>();
    }
    public void addTrain(Train train){
        trainList.add(train);
    }

    public void addWagonSet(String setNumber, ArrayList<Wagon> wagons){
        wagonSetList.put(setNumber,wagons);
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
            if (journey.getTrain().equals(train)){
                deleteJourney(journey);
                trainList.remove(train);
            }
        }
    }

    private void deleteJourney(Journey journey) {
        journeyList.remove(journey);
    }

    public void deletePlatform(Platform platform){
        for (Journey journey : journeyList) {
            if (journey.getPlatform().equals(platform)){
                deleteJourney(journey);
                platformList.remove(platform);
            }
        }
    }

    public void deleteWagons(String wagonsKey){
        for (Train trainKey : trainList) {
            if (trainKey.getWagons().equals(wagonSetList.get(wagonsKey))){
                deleteTrain(trainKey);
                wagonSetList.remove(wagonsKey, wagonSetList.get(wagonsKey));
            }
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

}
