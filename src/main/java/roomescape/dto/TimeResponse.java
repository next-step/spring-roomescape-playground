package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.Time;

public record TimeResponse(
    Long id,
    String time
) {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static TimeResponse from(Time time) {
        return new TimeResponse(
            time.getId(),
            time.getTime().format(TIME_FORMATTER)
        );
    }
}
