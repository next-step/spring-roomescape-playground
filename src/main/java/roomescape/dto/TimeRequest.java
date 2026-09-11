package roomescape.dto;

import java.time.LocalTime;

public class TimeRequest {

    private final LocalTime time;

    public TimeRequest(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }
}
