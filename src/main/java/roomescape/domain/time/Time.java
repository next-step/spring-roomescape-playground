package roomescape.domain.time;

import java.time.LocalTime;

public class Time {

    private Long id;

    private final LocalTime time;

    public Time(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
