package roomescape.domain;

import java.time.LocalTime;

public class Time {

    private final LocalTime time;

    public Time(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }
}
