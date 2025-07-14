package roomescape.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(
        "yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static String format(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    public static String format(LocalTime time) {
        return time.format(TIME_FORMATTER);
    }
}
