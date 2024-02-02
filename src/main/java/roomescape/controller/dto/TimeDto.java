package roomescape.controller.dto;

import roomescape.domain.Time;

public class TimeDto {
    private final Long id;
    private final String time;

    TimeDto(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }

    public static TimeDto toDto(Time time) {
        return new TimeDto(time.getId(), time.getTime());
    }
}
