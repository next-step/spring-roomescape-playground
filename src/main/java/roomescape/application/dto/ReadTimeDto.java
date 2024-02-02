package roomescape.application.dto;

import roomescape.domain.Time;

import java.time.LocalTime;

public class ReadTimeDto {

    private final Long id;
    private final LocalTime time;

    private ReadTimeDto(final Long id, final LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public static ReadTimeDto from(final Time time) {
        return new ReadTimeDto(time.getId(), time.getTime());
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
