package util;

import java.time.LocalTime;

public class TimeFormatter {

    public static LocalTime intToLocalTime(int num) {
        int hours = num / 100;
        int minutes = (num - hours * 100) % 60;
        return LocalTime.of(hours, minutes);
    }

    public static int localTimeToInt(LocalTime time) {
        int hours = time.getHour() * 100;
        int minutes = time.getMinute();
        return hours + minutes;
    }
}
