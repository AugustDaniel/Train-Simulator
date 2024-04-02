package data;

import java.io.Serializable;
import java.security.SecureRandom;

public class Machinist implements Serializable {

    private String name;

    public Machinist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
