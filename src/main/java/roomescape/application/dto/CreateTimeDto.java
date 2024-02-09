package roomescape.application.dto;

import roomescape.domain.Time;

import java.time.LocalTime;

public class CreateTimeDto {

    private final LocalTime time;

    public CreateTimeDto(final LocalTime time) {
        this.time = time;
    }

    public Time toEntity() {
        return new Time(null, time);
    }

    public LocalTime getTime() {
        return time;
    }
}
