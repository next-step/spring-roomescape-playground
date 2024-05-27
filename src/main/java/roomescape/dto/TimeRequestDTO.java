package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public class TimeRequestDTO {
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    private TimeRequestDTO() { }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }
}