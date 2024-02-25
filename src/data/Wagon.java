package data;

import java.io.Serializable;

public class Wagon implements Serializable {
    private int maxCapacity;
    private String idNumber;

    public Wagon(String idNumber, int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public String getIdNumber() {
        return idNumber;
    }
}
