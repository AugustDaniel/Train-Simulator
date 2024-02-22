package data;

import java.io.Serializable;

public class Wagon implements Serializable {
    private int maxCapacity;

    public Wagon(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

}
