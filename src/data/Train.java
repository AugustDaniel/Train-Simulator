package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Train implements Serializable {
    private List<Wagon> wagonList;

    public Train(List<Wagon> wagonList) {
        this.wagonList = wagonList;
    }
    public Train(){
        this(new ArrayList<>());
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
}