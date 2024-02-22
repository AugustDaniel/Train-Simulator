package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Schedule implements Serializable {

    private HashMap<String,Train> trainList;
    private List<Platform> platformList;
    private List<Journey> journeyList;

    private HashMap<String, List<Wagon>> wagonList;
    public Schedule(){
        this.trainList = new HashMap<>();
        this.journeyList = new ArrayList<>();
        this.platformList = new ArrayList<>();
        this.wagonList = new HashMap<>();
    }
    public void addTrain(String IdNumber ,Train train){
        trainList.put(IdNumber, train);
    }

    public void addWagons(String IdNumber, ArrayList<Wagon> wagons){
        wagonList.put(IdNumber,wagons);
    }

    public void addJourney(Journey journey){
        this.journeyList.add(journey);
    }

    public void addPlatform(Platform platform){
        this.platformList.add(platform);
    }

    public void changeTrain(){
        //todo needs body
    }
    public void deleteTrain(String IdNumber){
        for (Journey journey : journeyList) {
            if (journey.getTrain().equals(trainList.get(IdNumber))){
                deleteJourney(journey);
                trainList.remove(IdNumber, trainList.get(IdNumber));
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
        for (String trainKey : trainList.keySet()) {
            if (trainList.get(trainKey).getWagons().equals(wagonList.get(wagonsKey))){
                deleteTrain(trainKey);
                wagonList.remove(wagonsKey,wagonList.get(wagonsKey));
            }
        }
    }

    public List<Journey> getJourneyList(){
        return this.journeyList;
    }

}
