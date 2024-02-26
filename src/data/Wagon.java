package data;

import java.io.Serializable;

public class Wagon implements Serializable {
    private int maxCapacity;
    private String idNumber;

    public Wagon(String idNumber, int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.idNumber = idNumber;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    @Override
    public String toString() {
        return this.idNumber;
    }


}
