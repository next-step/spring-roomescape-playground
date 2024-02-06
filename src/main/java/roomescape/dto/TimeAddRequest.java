package roomescape.dto;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

public class TimeAddRequest {
    @DateTimeFormat(pattern = "HH:MM")
    private LocalTime time;

    public TimeAddRequest() {
        super();
    }

    public TimeAddRequest(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }
}
