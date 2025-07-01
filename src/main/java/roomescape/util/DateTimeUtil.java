package roomescape.util;

import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(
        "yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static String format(final java.time.LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    public static String format(final java.time.LocalTime time) {
        return time.format(TIME_FORMATTER);
    }
}
