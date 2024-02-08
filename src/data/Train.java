package data;

import java.util.ArrayList;
import java.util.List;

public class Train {
    private List<Wagon> wagonList;

    public Train(List<Wagon> wagonList) {
        this.wagonList = wagonList;
    }
    public Train(){
        this(new ArrayList<>());
    }

    public void addWagon(Wagon wagon){
        wagonList.add(wagon);
    }
}
