package util;

import java.time.LocalTime;

public class TimeFormatter {

    public static LocalTime intToLocalTime(int num) {
        String string = String.valueOf(num);
        int hours = 0;
        int minutes = 0;

        if (string.length() == 4) {
            hours = Integer.parseInt(string.substring(0, 2));
            minutes = Integer.parseInt(string.substring(2, 4));
        } else if (string.length() == 3) {
            hours = Integer.parseInt(string.substring(0, 1));
            minutes = Integer.parseInt(string.substring(1, 3));
        } else if (string.length() == 2) {
            minutes = Integer.parseInt(string.substring(0, 2));
        } else if (string.length() == 1) {
            minutes = Integer.parseInt(string);
        }

        return LocalTime.of(hours, minutes);
    }

    public static int localTimeToInt(LocalTime time) {
        int hours = time.getHour() * 100;
        int minutes = time.getMinute();
        return hours + minutes;
    }
}
