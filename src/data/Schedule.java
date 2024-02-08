package data;

import java.util.ArrayList;
import java.util.List;

public class Schedule {

    private List<Train> trainList;
    private List<Platform> platformList;
    private List<Journey> journeyList;
    public Schedule(){
        this.trainList = new ArrayList<>();
        this.journeyList = new ArrayList<>();
        this.platformList = new ArrayList<>();
    }
    public void addTrain(Train train){
        trainList.add(train);
    }
    public void changeTrain(){

    }
    public void deleteTrain(Train train){
        trainList.remove(train);
    }
}
