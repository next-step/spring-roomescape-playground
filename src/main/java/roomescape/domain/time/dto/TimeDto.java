package roomescape.domain.time.dto;

import roomescape.domain.time.entity.Time;

public class TimeDto {
    private final Long id;
    private final String time;

    TimeDto(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public static TimeDto toDto(Time time) {
        return new TimeDto(time.id(), time.time());
    }
}
