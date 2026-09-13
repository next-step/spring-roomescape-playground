package roomescape.dto;

import roomescape.domain.Time;

import java.time.LocalTime;

public class TimeResponseDto {

    private final Long id;
    private final LocalTime time;

    public TimeResponseDto(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public static TimeResponseDto from(Time time) {
        return new TimeResponseDto(time.getId(), time.getTime());
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
