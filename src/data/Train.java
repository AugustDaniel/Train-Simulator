package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Train implements Serializable {
    private String trainIDNumber;
    private List<Wagon> wagonList;

    public Train(String trainIDNumber, List<Wagon> wagonList) {
        this.wagonList = wagonList;
        this.trainIDNumber = trainIDNumber;
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
        return this.trainIDNumber;
    }

    public void setWagonList(List<Wagon> wagonList) {
        this.wagonList = wagonList;
    }

    @Override
    public String toString() {
        return trainIDNumber;
    }
}
