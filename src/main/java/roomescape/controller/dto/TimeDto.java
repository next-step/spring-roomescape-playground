package roomescape.controller.dto;

import roomescape.domain.Time;

import java.time.LocalTime;

public class TimeDto {
    private Long id;
    private LocalTime time;

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
