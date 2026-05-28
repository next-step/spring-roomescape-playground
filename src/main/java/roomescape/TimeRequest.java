package roomescape;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

public class TimeRequest {

    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    public TimeRequest() {
    }

    public TimeRequest(LocalTime time) {
        this.time = time;
    }

    public Time toEntity(Long id) {
        return new Time(id, this.time);
    }

    public LocalTime getTime() { return time; }
}
