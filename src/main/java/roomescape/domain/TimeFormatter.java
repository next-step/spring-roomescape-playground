package roomescape.domain;

import java.time.format.DateTimeFormatter;

public class TimeFormatter {
    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
}
