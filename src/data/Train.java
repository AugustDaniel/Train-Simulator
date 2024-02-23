package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Train implements Serializable {
    private List<Wagon> wagonList;
    private String TrainIDNumber;

    public Train(String trainIDNumber,List<Wagon> wagonList) {
        this.wagonList = wagonList;
        this.TrainIDNumber = trainIDNumber;
    }
    public Train(String trainIDNumber){
        this(trainIDNumber,new ArrayList<>());
    }

    public List<Wagon> getWagons(){
        return wagonList;
    }

    public int getCapacity() {
        int sum = 0;

        for (Wagon wagon : this.wagonList) {
            sum += wagon.getMaxCapacity();
        }

        return sum;
    }

    public String getTrainIDNumber() {
        return this.TrainIDNumber;
    }
}
