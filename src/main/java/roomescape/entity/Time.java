package roomescape.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.exception.InvalidException;

public class Time {

    private final long id;

    @JsonFormat(pattern = "HH:mm")
    private final LocalTime time;

    public Time(long id, LocalTime time) {
        this.id = id;
        validateTime(time);
        this.time = time;
    }

    private void validateTime(LocalTime time) {
        if (time == null) {
            throw new InvalidException("Time is required");
        }
    }

    public long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }

}
