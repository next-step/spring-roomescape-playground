package roomescape.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

public class Time {

    private final long id;

    @JsonFormat(pattern = "HH:mm")
    private final LocalTime time;

    public Time(long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }

}
