package roomescape.dto;

import roomescape.domain.Time;

public class TimeResponseDto {

    private final Long id;
    private final String time;

    public TimeResponseDto(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public static TimeResponseDto from(Time time) {
        return new TimeResponseDto(time.getId(), time.getTime());
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
