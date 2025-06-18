package roomescape.dto;

import roomescape.domain.Time;

import java.time.format.DateTimeFormatter;

public record TimeResponse(Long id, String time) {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static TimeResponse from(Time time) {
        return new TimeResponse(time.getId(), time.getTime().format(FORMATTER));
    }
}
