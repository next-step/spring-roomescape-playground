package roomescape.domain;

import java.time.LocalTime;

public class Time {

    private Long id;
    private LocalTime time;

    public Time(Long id, LocalTime time) {
        validateTime(time);
        this.id = id;
        this.time = time;
    }

    private void validateTime(LocalTime time) {
        if (time == null) {
            throw new IllegalArgumentException("Invalid time.");
        }
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
