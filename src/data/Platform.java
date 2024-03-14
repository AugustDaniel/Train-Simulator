package data;

import java.io.Serializable;

public class Platform implements Serializable {

    private int platformNumber;

    public Platform(int platformNumber) {
        this.platformNumber = platformNumber;
    }

    public int getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(int platformNumber) {
        this.platformNumber = platformNumber;
    }

    @Override
    public String toString() {
        return "" + getPlatformNumber();
    }
}
