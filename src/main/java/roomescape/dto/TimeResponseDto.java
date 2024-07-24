package roomescape.dto;

import roomescape.domain.Time;

public class TimeResponseDto {

    private Long id;
    private String time;

    public TimeResponseDto(Time time) {
        this.id = time.getId();
        this.time = time.getTime();
    }

    public TimeResponseDto(Long id, Time time) {
        this.id = id;
        this.time = time.getTime();
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
