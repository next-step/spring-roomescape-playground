package roomescape.dto;

import java.time.LocalTime;

public class TimeResponseDto {

    public Long id;
    public String time;

    public TimeResponseDto(Long id, LocalTime time) {
        this.id = id;
        this.time = time.toString();
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
