package roomescape.global.util;

import java.time.LocalTime;

public class TimeParser {

    public static final String DELIMITER = ":";

    private TimeParser() {
    }

    public static LocalTime parseToLocalTime(String time) {
        String[] times = time.split(DELIMITER);
        return LocalTime.of(Integer.parseInt(times[0]), Integer.parseInt(times[1]));
    }
}
