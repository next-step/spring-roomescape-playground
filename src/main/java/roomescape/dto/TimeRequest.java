package roomescape.dto;

import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;

public class TimeRequest {
    @DateTimeFormat(pattern = "HH:MM")
    private LocalTime time;

    private TimeRequest() { }

    public TimeRequest(final LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }
}
